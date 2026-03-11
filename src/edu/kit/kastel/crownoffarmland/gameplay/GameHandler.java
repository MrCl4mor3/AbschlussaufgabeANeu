package edu.kit.kastel.crownoffarmland.gameplay;

import edu.kit.kastel.crownoffarmland.exceptions.EmptySelectedFieldException;
import edu.kit.kastel.crownoffarmland.exceptions.EnemyUnitSelectedException;
import edu.kit.kastel.crownoffarmland.exceptions.InvalidGameStateException;
import edu.kit.kastel.crownoffarmland.exceptions.InvalidHandException;
import edu.kit.kastel.crownoffarmland.exceptions.InvalidPositionException;
import edu.kit.kastel.crownoffarmland.exceptions.KingCannotBlockedException;
import edu.kit.kastel.crownoffarmland.exceptions.NoSelectionException;
import edu.kit.kastel.crownoffarmland.exceptions.UnitAlreadyActedException;
import edu.kit.kastel.crownoffarmland.exceptions.UnitAlreadyRevealedException;
import edu.kit.kastel.crownoffarmland.exceptions.YieldException;
import edu.kit.kastel.crownoffarmland.gameplay.ai.AIDecisionService;
import edu.kit.kastel.crownoffarmland.gameplay.ai.AITurnController;
import edu.kit.kastel.crownoffarmland.gameplay.ai.WeightedRandomSelector;
import edu.kit.kastel.crownoffarmland.gameplay.combat.DuelManager;
import edu.kit.kastel.crownoffarmland.gameplay.snapshots.movesnapshot.MoveSnapshot;
import edu.kit.kastel.crownoffarmland.gameplay.snapshots.PlaceStepSnapshot;
import edu.kit.kastel.crownoffarmland.gameplay.snapshots.SnapshotFactory;
import edu.kit.kastel.crownoffarmland.gameplay.unitmerge.UnitMerger;
import edu.kit.kastel.crownoffarmland.gameplay.snapshots.BoardSnapshot;
import edu.kit.kastel.crownoffarmland.gameplay.snapshots.EntitySnapshot;
import edu.kit.kastel.crownoffarmland.gameplay.snapshots.TeamStateSnapshot;
import edu.kit.kastel.crownoffarmland.model.Game;
import edu.kit.kastel.crownoffarmland.model.board.Position;
import edu.kit.kastel.crownoffarmland.model.team.TeamID;
import edu.kit.kastel.crownoffarmland.model.units.BoardEntity;
import edu.kit.kastel.crownoffarmland.model.units.Unit;


import java.util.List;


/**
 * The GameHandler class is responsible for managing the state and flow of the game. It interacts with the Game model to execute player
 * commands, manage turns, and handle game logic. The GameHandler uses a DuelManager to resolve combat between units and a UnitMerger to
 * handle unit merging mechanics. It maintains the currently selected position on the board and tracks whether a unit has been placed
 * during the current turn. The GameHandler provides methods for processing player commands, updating the game state, and determining the
 * outcome of the game based on player actions and interactions between units on the board.
 *
 * @author ucgdi
 */
public class GameHandler {
    private static final Position TEAM1_KING_START = new Position(1, 'D');
    private static final Position TEAM2_KING_START = new Position(7, 'D');
    private static final int HAND_INDEX_OFFSET = 1;
    private static final int OPENING_HAND_SIZE = 4;
    private final Game game;
    private final SnapshotFactory snapshotFactory;
    private final TurnState turnState;
    private final PlacementService placementService;
    private final MovementService movementService;
    private final AITurnController AITurnController;
    /**
     * Constructs a new GameHandler instance with the specified Game model. The GameHandler initializes the DuelManager and UnitMerger,
     * and sets up the initial state of the game. The selected position is initially set to null, and the placedThisTurn flag is set to
     * false, indicating that no unit has been placed during the current turn. The GameHandler is responsible for managing the game state
     * and processing player commands based on the provided Game model.
     * @param game The Game model that represents the current state of the game. The GameHandler will interact with this model to execute
     *            player commands and manage the flow of the game. The Game model should be properly initialized with the necessary game
     *             components, such as the board, teams, and units, before being passed to the GameHandler constructor.
     */
    public GameHandler(Game game) {
        this(game, new UnitMerger(), new SnapshotFactory(), new TurnState(), new DuelManager());
    }
    private GameHandler(Game game, UnitMerger unitMerger, SnapshotFactory snapshotFactory, TurnState turnState, DuelManager duelManager) {
        this.game = game;
        this.snapshotFactory = snapshotFactory;
        this.turnState = turnState;
        this.placementService = new PlacementService(game, unitMerger, turnState);
        this.movementService = new MovementService(game, unitMerger, turnState, duelManager);
        WeightedRandomSelector weightedRandomSelector = new WeightedRandomSelector(game.getRandomGenerator());
        AIDecisionService AIDecisionService = new AIDecisionService(game, turnState, weightedRandomSelector);
        this.AITurnController = new AITurnController(this, game, AIDecisionService);
    }

    /**
     * Initializes the game by shuffling the decks, drawing opening hands for both teams, placing the Kings on their starting positions,
     * and starting the first turn. This method sets up the initial state of the game and prepares it for player interaction. It should
     * be called after creating a GameHandler instance to ensure that the game is properly initialized before players start taking actions.
     */
    public void initializeGame() {
        game.shuffleDrawPile(TeamID.TEAM_1);
        game.shuffleDrawPile(TeamID.TEAM_2);
        drawCards(TeamID.TEAM_1, OPENING_HAND_SIZE);
        drawCards(TeamID.TEAM_2, OPENING_HAND_SIZE);
        game.setOccupant(TEAM1_KING_START, game.getKing(TeamID.TEAM_1));
        game.setOccupant(TEAM2_KING_START, game.getKing(TeamID.TEAM_2));
        startCurrentTurn();
    }
    private void drawCards(TeamID teamID, int amount) {
        for (int i = 0; i < amount; i++) {
            game.drawToHand(teamID);
        }
    }
    private void startCurrentTurn() {
        turnState.resetForNewTurn();
        TeamID currentTeam = game.getCurrentTeamID();
        if (game.isDrawPileEmpty(currentTeam)) {
            game.setWinner(currentTeam.getNext());
            return;
        }
        game.drawToHand(currentTeam);
    }
    /**
     * Returns the currently selected position on the board.
     * @return The Position witch is currently selected. This can be null if no position is selected.
     */
    public Position getSelectedPos() {
        return turnState.getSelectedPos();
    }
    /**
     * Returns the TeamID of the current player whose turn it is.
     * @return The TeamID of the current player
     */
    public TeamID getCurrentTeamID() {
        return game.getCurrentTeamID();
    }
    /**
     * Returns the name of the current player whose turn it is.
     * @return The name of the current player
     */
    public String getCurrentTeamName() {
        return game.getTeamName(game.getCurrentTeamID());
    }

    /**
     * Flips the currently selected entity on the board, revealing its details. If the selected entity is already revealed, an exception
     * is thrown.
     * @return An EntitySnapshot containing the details of the flipped entity after it has been revealed.
     * @throws InvalidGameStateException if there is no selected position, the selected field is empty, the selected entity belongs to
     *      the enemy team, or the selected unit has already acted this turn.
     * @throws UnitAlreadyRevealedException if the selected entity is already revealed, indicating that it cannot be flipped again.
     */
    public EntitySnapshot flipSelectedEntity() throws InvalidGameStateException {
        BoardEntity entity = getSelectedEntity();
        if (entity.isRevealed()) {
            throw new UnitAlreadyRevealedException(entity.getName().toString());
        }
        entity.reveal();
        return createEntitySnapshotAtSelected();
    }

    private BoardEntity getSelectedEntity() throws InvalidGameStateException {
        if (turnState.getSelectedPos() == null) {
            throw new NoSelectionException();
        }
        BoardEntity entity = game.getOccupant(turnState.getSelectedPos());
        if (entity == null) {
            throw new EmptySelectedFieldException(turnState.getSelectedPos().toString());
        }
        if (entity.getOwner() != game.getCurrentTeamID()) {
            throw new EnemyUnitSelectedException();
        }
        if (turnState.hasMoved(entity)) {
            throw new UnitAlreadyActedException(entity.getName().toString());
        }
        return entity;
    }

    /**
     * Sets the selected position on the board based on the provided raw position string. The raw position string should be in a valid
     * format.
     * @param rawPosition A string representing the position to be selected, typically in a format like "A1", "B2", etc.
     * @throws InvalidPositionException if the provided raw position string is not in a valid format or does not correspond to a valid
     *      position on the board.
     */
    public void setSelected(String rawPosition) throws InvalidPositionException {
        turnState.setSelectedPos(game.parsePosition(rawPosition));
    }

    public void setSelected(Position position) throws InvalidPositionException {
        turnState.setSelectedPos(position);
    }

    /**
     * Creates a snapshot of the current state of the board, including the positions and details of all entities on the board. This snapshot
     * can be used for rendering the board state.
     * @return A BoardSnapshot representing the current state of the board
     */
    public BoardSnapshot createBoardSnapshot() {
        return snapshotFactory.createBoardSnapshot(game, turnState.getSelectedPos(), turnState.getMovedEntities());
    }

    /**
     * Creates a snapshot of the currently selected entity on the board, including its details and state. This snapshot can be used for
     * rendering the selected entity's information.
     * @return An EntitySnapshot representing the currently selected entity on the board
     * @throws InvalidGameStateException if there is no selected position, the selected field is empty
     */
    public EntitySnapshot createEntitySnapshotAtSelected() throws InvalidGameStateException {
        return snapshotFactory.createEntitySnapshotAtSelected(game, turnState.getSelectedPos());
    }

    /**
     * Creates a snapshot of the current state of the player's hand, including the cards in hand and their details.
     * @return A list of EntitySnapshots representing the current state of the player's hand
     */
    public List<EntitySnapshot> createHandSnapshot() {
        return snapshotFactory.createHandSnapshot(game);
    }

    /**
     * Creates a snapshot of the current state of the specified team, including information about the team's units on the board and in hand.
     * @param teamID The TeamID of the team for which to create the snapshot
     * @return A TeamStateSnapshot representing the current state of the specified team
     */
    public TeamStateSnapshot createTeamStateSnapshots(TeamID teamID) {
        return snapshotFactory.createTeamStateSnapshot(game, teamID);
    }

    /**
     * Blocks the currently selected unit on the board, preventing it from acting for the remainder of the turn. If the selected entity
     * is not a unit, an exception is thrown. If the selected unit is the Farmer King, an exception is thrown, as the King cannot be
     * blocked. After blocking the unit, it is marked as having moved this turn, and a snapshot of the blocked unit is returned.
     * @return An EntitySnapshot representing the currently selected unit after it has been blocked
     * @throws InvalidGameStateException if there is a problem with the game state
     * @throws KingCannotBlockedException if the selected unit is the Farmer King, as the King cannot be blocked.
     */
    public EntitySnapshot blockSelected() throws InvalidGameStateException {
        BoardEntity entity = getSelectedEntity();
        if (entity.isFarmerKing()) {
            throw new KingCannotBlockedException();
        }
        Unit unit = (Unit) entity;
        unit.block();
        turnState.markMoved(unit);
        return createEntitySnapshotAtSelected();
    }
    /**
     * Checks if the yield restriction is currently active for the current turn. The yield restriction is activated when a player
     * attempts to end their turn with a full hand, forcing them to discard a card before they can end their turn. This method returns
     * true if the yield restriction is active, indicating that the player must discard a card before ending their turn, and false
     * otherwise.
     * @return true if the yield restriction is active for the current turn, false otherwise
     */
    public boolean isYieldRestrictionActive() {
        return turnState.isYieldRestrictionActive();
    }
    /**
     * Attempts to end the current player's turn. If the player's hand is full, the yield restriction is activated, and a YieldException
     * is thrown, indicating that the player must discard a card before they can end their turn. If the player's hand is not full, the
     * turn is successfully ended, and the next round begins.
     * @return true if the turn was successfully ended and the next round has begun, false if the turn could not be ended due to a full hand
     * @throws InvalidGameStateException if there is a problem with the game state that prevents ending the turn
     * @throws YieldException if the player's hand is full, indicating that they must discard a card before they can end their turn
     */
    public boolean tryEndTurn() throws InvalidGameStateException {
        if (game.isHandFull(game.getCurrentTeamID())) {
            turnState.activateYieldRestriction();
            throw new YieldException(game.getTeamName(game.getCurrentTeamID()));
        } else {
            nextRound();
            return true;
        }
    }
    /**
     * Attempts to end the current player's turn by discarding a card from their hand. The player must provide the index of the card they
     * wish to discard.
     * @param index The index of the card in the player's hand that they wish to discard.
     * @return An EntitySnapshot representing the card that was discarded from the player's hand.
     * @throws InvalidGameStateException if there is a problem with the game state that prevents discarding the card or ending the turn
     * @throws InvalidHandException if the provided index is not a valid index for the player's hand, indicating that the player cannot
     *      discard a card at the specified index.
     * @throws YieldException if the player's hand is not full, indicating that they cannot end their turn by discarding a card
     */
    //ToDo: Exceptions noch richtig werfen!
    public EntitySnapshot tryEndTurnWithDiscard(int index) throws InvalidGameStateException {
        if (!game.isHandFull(getCurrentTeamID())) {
            turnState.activateYieldRestriction();
            throw new YieldException(game.getTeamName(game.getCurrentTeamID()));
        } else {
            int handSize = game.getHandSize(game.getCurrentTeamID());

            int internalIndex = index - HAND_INDEX_OFFSET;

            if (internalIndex < 0 || internalIndex >= handSize) {
                throw new InvalidHandException("Invalid hand index: " + index);
            }

            Unit discardedCard = game.removeHandCardAt(getCurrentTeamID(), internalIndex);
            if (discardedCard == null) {
                throw new InvalidGameStateException("Cannot discard from an empty hand.");
            }
            EntitySnapshot output = new EntitySnapshot(discardedCard, game.getTeamName(game.getCurrentTeamID()));
            nextRound();

            return output;
        }
    }
    private void nextRound() {
        game.nextTurn();
        startCurrentTurn();
    }

    /**
     * Checks if the game is over by determining if there is a winner.
     * @return true if the game is over and there is a winner, false otherwise
     */
    public boolean isGameOver() {
        return game.getWinner() != null;
    }

    /**
     * Returns the name of the winning team if the game is over, or null if the game is not yet over.
     * @return The name of the winning team if the game is over, or null if the game is not yet over
     */
    public String getWinner() {
        if (isGameOver()) {
            return game.getTeamName(game.getWinner());
        }
        return null;
    }
    /**
     * Attempts to place units from the player's hand onto the board at the currently selected position. The player must provide an array of
     * user indices corresponding to the cards in their hand that they wish to place. The method validates the target position for
     * placement, checks that the player has not already placed a unit this turn, and ensures that the target position is adjacent to the
     * player's King. If the placement is valid, the
     * @param userIndices An array of user indices corresponding to the cards in the player's hand that they wish to place on the board.
     * @return A list of PlaceStepSnapshots representing the results of placing each unit on the board, including any merges that occurred.
     * @throws InvalidGameStateException if there is a problem with the game state that prevents placing the units
     * @throws InvalidHandException if any of the provided user indices are not valid indices for the player's hand, indicating that the
     *      player cannot place the specified cards from their hand.
     */
    public List<PlaceStepSnapshot> placeUnits(int[] userIndices) throws InvalidGameStateException {
        return placementService.placeUnits(userIndices);
    }

    /**
     * Attempts to move the currently selected unit on the board to a target position specified by the player. The method validates the
     * target position for movement.
     * @param target A string representing the target position
     * @return A MoveSnapshot representing the result of the move action, including any combat or merges that occurred as a result of the
     *      move.
     * @throws InvalidGameStateException if there is a problem with the game state that prevents moving the unit
     */
    public MoveSnapshot moveUnit(String target) throws InvalidGameStateException {
        Position targetPosition = game.parsePosition(target);
        return movementService.moveUnit(targetPosition, getCurrentTeamID());
    }

    public MoveSnapshot moveUnit(Position targetPosition) throws InvalidGameStateException {
        return movementService.moveUnit(targetPosition, getCurrentTeamID());
    }

    public void executeAITurn() {
        AITurnController.executeTurn();
    }

    public boolean isCurrentPlayerAI() {
        return getCurrentTeamID() == TeamID.TEAM_2;
    }
}