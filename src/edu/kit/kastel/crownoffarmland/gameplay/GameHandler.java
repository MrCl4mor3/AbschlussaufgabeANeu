package edu.kit.kastel.crownoffarmland.gameplay;

import edu.kit.kastel.crownoffarmland.exceptions.CrownOfFarmlandException;
import edu.kit.kastel.crownoffarmland.exceptions.gamestateexceptions.EmptySelectedFieldException;
import edu.kit.kastel.crownoffarmland.exceptions.gamestateexceptions.EnemyUnitSelectedException;
import edu.kit.kastel.crownoffarmland.exceptions.InvalidGameStateException;
import edu.kit.kastel.crownoffarmland.exceptions.gamestateexceptions.InvalidHandException;
import edu.kit.kastel.crownoffarmland.exceptions.InvalidPositionException;
import edu.kit.kastel.crownoffarmland.exceptions.KingCannotBlockedException;
import edu.kit.kastel.crownoffarmland.exceptions.gamestateexceptions.NoSelectionException;
import edu.kit.kastel.crownoffarmland.exceptions.gamestateexceptions.UnitAlreadyActedException;
import edu.kit.kastel.crownoffarmland.exceptions.UnitAlreadyRevealedException;
import edu.kit.kastel.crownoffarmland.gameplay.ai.AIDecisionService;
import edu.kit.kastel.crownoffarmland.gameplay.ai.AITurnController;
import edu.kit.kastel.crownoffarmland.gameplay.ai.WeightedRandomSelector;
import edu.kit.kastel.crownoffarmland.gameplay.combat.DuelManager;
import edu.kit.kastel.crownoffarmland.gameplay.snapshots.EndTurnSnapshot;
import edu.kit.kastel.crownoffarmland.gameplay.snapshots.EntityOnPositionSnapshot;
import edu.kit.kastel.crownoffarmland.gameplay.snapshots.SnapshotProvider;
import edu.kit.kastel.crownoffarmland.gameplay.snapshots.movesnapshot.MoveSnapshot;
import edu.kit.kastel.crownoffarmland.gameplay.snapshots.PlaceStepSnapshot;
import edu.kit.kastel.crownoffarmland.gameplay.snapshots.SnapshotFactory;
import edu.kit.kastel.crownoffarmland.gameplay.unitmerge.UnitMerger;
import edu.kit.kastel.crownoffarmland.gameplay.snapshots.EntitySnapshot;
import edu.kit.kastel.crownoffarmland.model.Game;
import edu.kit.kastel.crownoffarmland.model.board.Position;
import edu.kit.kastel.crownoffarmland.model.team.TeamID;
import edu.kit.kastel.crownoffarmland.model.units.BoardEntity;
import edu.kit.kastel.crownoffarmland.model.units.Unit;
import edu.kit.kastel.crownoffarmland.ui.renderer.GameOutputPrinter;


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
    private static final int OPENING_HAND_SIZE = 5;
    private final Game game;
    private final SnapshotFactory snapshotFactory;
    private final TurnState turnState;
    private final PlacementService placementService;
    private final MovementService movementService;
    private AITurnController turnController;
    private final UnitMerger unitMerger;

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
        this.game = game;
        this.unitMerger = new UnitMerger();
        this.turnState = new TurnState();
        this.snapshotFactory = new SnapshotFactory(game, turnState);
        this.placementService = new PlacementService(game, unitMerger, turnState);
        this.movementService = new MovementService(game, unitMerger, turnState, new DuelManager());
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
        if (game.teamView(currentTeam).isDrawPileEmpty()) {
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
     * Flips the currently selected entity on the board, revealing its details. If the selected entity is already revealed, an exception
     * is thrown.
     * @return An EntitySnapshot containing the details of the flipped entity after it has been revealed.
     * @throws InvalidGameStateException if there is no selected position, the selected field is empty, the selected entity belongs to
     *      the enemy team, or the selected unit has already acted this turn.
     * @throws UnitAlreadyRevealedException if the selected entity is already revealed, indicating that it cannot be flipped again.
     */
    public EntityOnPositionSnapshot flipSelectedEntity() throws InvalidGameStateException {
        BoardEntity entity = getSelectedEntity();
        if (entity.isRevealed()) {
            throw new UnitAlreadyRevealedException(entity.getName().toString());
        }
        entity.reveal();
        return snapshotFactory.createEntitySnapshotAtSelected();
    }

    private BoardEntity getSelectedEntity() throws InvalidGameStateException {
        if (turnState.getSelectedPos() == null) {
            throw new NoSelectionException();
        }
        BoardEntity entity = game.boardView().getOccupant(turnState.getSelectedPos());
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
     * Set the selected Pointer to the selected field.
     * @param position the selected position
     * @throws InvalidPositionException if an invalid position was selected
     */
    public void setSelected(Position position) throws InvalidPositionException {
        if (!game.boardView().isValidPosition(position)) {
            throw new InvalidPositionException(position.toString());
        }
        turnState.setSelectedPos(position);
    }

    /**
     * Returns the SnapshotProvider instance associated with this GameHandler. The SnapshotProvider is responsible for creating snapshots
     * of the current game state, including the board, entities, and team states. It provides methods for generating snapshots that can
     * be used to update the user interface or for other purposes where a representation of the current game state is needed.
     * @return w
     */
    public SnapshotProvider snapshots() {
        return snapshotFactory;
    }

    /**
     * Blocks the currently selected unit on the board, preventing it from acting for the remainder of the turn. If the selected entity
     * is not a unit, an exception is thrown. If the selected unit is the Farmer King, an exception is thrown, as the King cannot be
     * blocked. After blocking the unit, it is marked as having moved this turn, and a snapshot of the blocked unit is returned.
     * @return An EntitySnapshot representing the currently selected unit after it has been blocked
     * @throws InvalidGameStateException if there is a problem with the game state
     * @throws KingCannotBlockedException if the selected unit is the Farmer King, as the King cannot be blocked.
     */
    public EntityOnPositionSnapshot blockSelected() throws InvalidGameStateException {
        BoardEntity entity = getSelectedEntity();
        if (entity.isFarmerKing()) {
            throw new KingCannotBlockedException();
        }
        Unit unit = (Unit) entity;
        unit.block();
        turnState.markMoved(unit);
        return snapshots().createEntitySnapshotAtSelected();
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
     * Checks whether a player can attempt to end their turn based on the current state of their hand and whether they have requested to
     * discard a card. If the player has a full hand and has not requested to discard, the yield restriction is activated, and the method
     * returns a result indicating that discarding is required. If the player has requested to discard but does not have a full hand, the
     * yield restriction is activated, and the method returns a result indicating that discarding is not allowed.
     * @param discardRequested A boolean indicating whether the player has requested to discard a card before ending their turn
     * @return the Yield result.
     */
    public YieldCheckResult checkYieldAttempt(boolean discardRequested) {
        boolean handFull = game.teamView(game.getCurrentTeamID()).isHandFull();

        if (!discardRequested && handFull) {
            turnState.activateYieldRestriction();
            return YieldCheckResult.DISCARDED_REQUIRED;
        }

        if (discardRequested && !handFull) {
            turnState.activateYieldRestriction();
            return YieldCheckResult.DISCARDED_NOT_ALLOWED;
        }
        return YieldCheckResult.SUCCESS;
    }

    /**
     * Attempts to end the current player's turn. If the player's hand is full, the yield restriction is activated, and a YieldException
     * is thrown, indicating that the player must discard a card before they can end their turn. If the player's hand is not full, the
     * turn is successfully ended, and the next round begins.
     * @return An EndTurnSnapshot representing the result of ending the turn, including any relevant information about the turn
     *      transition and game state.
     */
    public EndTurnSnapshot endTurn() {
        return finishTurn(null);
    }
    /**
     * Attempts to end the current player's turn by discarding a card from their hand. The player must provide the index of the card they
     * wish to discard.
     * @param index The index of the card in the player's hand that they wish to discard.
     * @return An EntitySnapshot representing the card that was discarded from the player's hand.
     * @throws InvalidGameStateException if there is a problem with the game state that prevents discarding the card or ending the turn
     * @throws InvalidHandException if the provided index is not a valid index for the player's hand, indicating that the player cannot
     *      discard a card at the specified index.
     */
    public EndTurnSnapshot endTurnWithDiscard(int index) throws InvalidGameStateException {
        int handSize = game.teamView(game.getCurrentTeamID()).getHandSize();
        int internalIndex = index - HAND_INDEX_OFFSET;

        if (internalIndex < 0 || internalIndex >= handSize) {
            throw new InvalidHandException(String.valueOf(index));
        }

        Unit discardedCard = game.removeHandCardAt(getCurrentTeamID(), internalIndex);
        if (discardedCard == null) {
            throw new InvalidGameStateException("Cannot discard from an empty hand.");
        }
        EntitySnapshot snapshot = new EntitySnapshot(discardedCard, game.teamView(game.getCurrentTeamID()).getName());

        return finishTurn(snapshot);
    }

    private EndTurnSnapshot finishTurn(EntitySnapshot discardedCard) {
        EndTurnSnapshot endTurnSnapshot = new EndTurnSnapshot(discardedCard, game.teamView(game.getEnemyTeamID()).getName(), isGameOver());
        game.nextTurn();
        startCurrentTurn();
        return endTurnSnapshot;
    }

    /**
     * Checks if the game is over by determining if there is a winner.
     * @return true if the game is over and there is a winner, false otherwise
     */
    public boolean isGameOver() {
        return game.getWinnerID() != null;
    }

    /**
     * Returns the name of the winning team if the game is over, or null if the game is not yet over.
     * @return The name of the winning team if the game is over, or null if the game is not yet over
     */
    public String getWinner() {
        if (isGameOver()) {
            return game.teamView(game.getWinnerID()).getName();
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
     * @throws InvalidPositionException if the Position is invalid
     */
    public MoveSnapshot moveUnit(Position target) throws InvalidGameStateException {
        if (!game.boardView().isValidPosition(target)) {
            throw new InvalidPositionException(target.toString());
        }

        return movementService.moveUnit(target, getCurrentTeamID());
    }
    /**
     * Execute the AI turn.
     * @throws CrownOfFarmlandException if there is a problem with the game state that prevents the AI from taking its turn, or if there
     *      is an error in the AI's decision-making process.
     */
    public void executeAITurn() throws CrownOfFarmlandException {
        turnController.executeTurn();
    }
    /**
     * Checks if the current player is an AI-controlled player. This method determines whether the current player's team is controlled by
     * the AI, which can be used to decide whether to allow player input or to execute the AI's turn automatically.
     * @return true if the current player is an AI-controlled player, false otherwise
     */
    public boolean isCurrentPlayerAI() {
        return getCurrentTeamID() == TeamID.TEAM_2;
    }
    /**
     * Initialize the AI.
     * @param gameOutputPrinter the printer for ui
     */
    public void initializeAI(GameOutputPrinter gameOutputPrinter) {
        WeightedRandomSelector weightedRandomSelector = new WeightedRandomSelector(game.getRandomGenerator());
        this.turnController = new AITurnController(this, game,
                new AIDecisionService(game, turnState, unitMerger, weightedRandomSelector), gameOutputPrinter);
    }
}