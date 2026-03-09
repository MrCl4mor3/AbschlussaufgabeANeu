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
import edu.kit.kastel.crownoffarmland.gameplay.combat.DuelManager;
import edu.kit.kastel.crownoffarmland.gameplay.snapshots.PlaceStepSnapshot;
import edu.kit.kastel.crownoffarmland.gameplay.snapshots.SnapshotFactory;
import edu.kit.kastel.crownoffarmland.gameplay.unitmerge.MergeResult;
import edu.kit.kastel.crownoffarmland.gameplay.unitmerge.UnitMerger;
import edu.kit.kastel.crownoffarmland.gameplay.snapshots.BoardSnapshot;
import edu.kit.kastel.crownoffarmland.gameplay.snapshots.EntitySnapshot;
import edu.kit.kastel.crownoffarmland.gameplay.snapshots.TeamStateSnapshot;
import edu.kit.kastel.crownoffarmland.model.Game;
import edu.kit.kastel.crownoffarmland.model.board.Position;
import edu.kit.kastel.crownoffarmland.model.team.TeamID;
import edu.kit.kastel.crownoffarmland.model.units.BoardEntity;
import edu.kit.kastel.crownoffarmland.model.units.Unit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    private static final int MAX_MOVE_DISTANCE = 1;
    private static final int MAX_UNITS_ON_BOARD = 5;
    private final Game game;
    private final DuelManager duelManager;
    private final UnitMerger unitMerger;
    private final SnapshotFactory snapshotFactory;
    private final TurnState turnState;
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
        this(game, new DuelManager(), new UnitMerger(), new SnapshotFactory(), new TurnState());
    }
    private GameHandler(Game game, DuelManager duelManager, UnitMerger unitMerger, SnapshotFactory snapshotFactory, TurnState turnState) {
        this.game = game;
        this.duelManager = duelManager;
        this.unitMerger = unitMerger;
        this.snapshotFactory = snapshotFactory;
        this.turnState = turnState;
    }

    /**
     * Initializes the game by shuffling the decks, drawing opening hands for both teams, placing the Kings on their starting positions,
     * and starting the first turn. This method sets up the initial state of the game and prepares it for player interaction. It should
     * be called after creating a GameHandler instance to ensure that the game is properly initialized before players start taking actions.
     */
    public void initializeGame() {
        shuffleDecks();
        drawOpeningHands();
        placeKings();
        startCurrentTurn();
    }
    private void shuffleDecks() {
        game.shuffleDrawPile(TeamID.TEAM_1);
        game.shuffleDrawPile(TeamID.TEAM_2);
    }
    private void drawOpeningHands() {
        drawCards(TeamID.TEAM_1, OPENING_HAND_SIZE);
        drawCards(TeamID.TEAM_2, OPENING_HAND_SIZE);
    }
    private void drawCards(TeamID teamID, int amount) {
        for (int i = 0; i < amount; i++) {
            game.drawToHand(teamID);
        }
    }
    private void placeKings() {
        game.setOccupant(TEAM1_KING_START, game.getKing(TeamID.TEAM_1));
        game.setOccupant(TEAM2_KING_START, game.getKing(TeamID.TEAM_2));
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
    private boolean hasMovedThisTurn(BoardEntity entity) {
        return turnState.hasMoved(entity);
    }
    private void markAsMovedThisTurn(BoardEntity entity) {
        turnState.markMoved(entity);
    }

    /**
     * Returns the currently selected position on the board.
     * @return The Position witch is currently selected. This can be null if no position is selected.
     */
    public Position getSelectedPos() {
        return turnState.getSelectedPos();
    }

    /**
     * Checks if the current player's hand is full, meaning they have reached the maximum number of cards allowed in their hand.
     * @return true if the current player's hand is full, false otherwise.
     */
    public boolean isHandFull() {
        return game.isHandFull(getCurrentTeamID());
    }

    /**
     * Returns the name of the next player to take a turn.
     * @return The name of the next player
     */
    public String getNextPlayerName() {
        return game.getTeamName(game.getEnemyTeamID());
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
     */
    public EntitySnapshot flipSelectedEntity() throws InvalidGameStateException {
        BoardEntity entity = getSelectedEntity();
        if (entity.isRevealed()) {
            throw new UnitAlreadyRevealedException(entity.getName().toString());
        }
        entity.revealeEntity();
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
        if (entity.getTeamID() != getCurrentTeamID()) {
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
        return snapshotFactory.createTeamStateSnapshot(game, teamID, getUnitsPlaced(teamID));
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
     * Counts the number of units (excluding the Farmer King) that the specified team has placed on the board. This method iterates
     * through all positions on the board and counts the units that belong to the specified team, excluding any Farmer King units.
     * @param teamID The TeamID of the team for which to count the placed units on the board
     * @return The number of units (excluding the Farmer King) that the specified team has placed on the board
     */
    public int getUnitsPlaced(TeamID teamID) {
        int count = 0;
        for (int rowIndex = 0; rowIndex < game.getBoardSize(); rowIndex++) {
            for (int columnIndex = 0; columnIndex < game.getBoardSize(); columnIndex++) {
                Position position = game.getPositionAt(rowIndex, columnIndex);
                BoardEntity entity = game.getOccupant(position);
                if (entity != null && entity.getTeamID() == teamID && !entity.isFarmerKing()) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * Checks if the yield restriction is currently active for the current turn. The yield restriction is activated when a player
     * attempts to end their turn with a full hand, forcing them to discard a card before they can end their turn. This method returns
     * true if the yield restriction is active, indicating that the player must discard a card before ending their turn, and false otherwise.
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
        if (isHandFull()) {
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
        if (!isHandFull()) {
            turnState.activateYieldRestriction();
            throw new YieldException(game.getTeamName(game.getCurrentTeamID()));
        } else {
            int handSize = game.getHandSize(game.getCurrentTeamID());

            int internalIndex = parseToInternalHandIndex(index);

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
    private int parseToInternalHandIndex(int userIndex) throws InvalidHandException {
        int internalIndex = userIndex - HAND_INDEX_OFFSET;
        int handSize = game.getHandSize(getCurrentTeamID());
        if (internalIndex < 0 || internalIndex >= handSize) {
            throw new InvalidHandException(String.valueOf(userIndex));
        }
        return internalIndex;
    }
    //ToDO: Exceptions noch richtig werfen!
    private List<Integer> parseToInternalHandIndices(int[] userIndices) throws InvalidHandException {
        List<Integer> internalIndices = new ArrayList<>();
        Set<Integer> seenIndices = new HashSet<>();
        for (int userIndex : userIndices) {
            int internalIndex = parseToInternalHandIndex(userIndex);
            if (!seenIndices.add(internalIndex)) {
                throw new InvalidHandException("Duplicate hand index: " + userIndex);
            }
            internalIndices.add(internalIndex);
        }
        return internalIndices;
    }
    private boolean isAdjacentToKing(Position target, Position kingPosition) {
        int rowDiff = Math.abs(target.getRow() - kingPosition.getRow());
        int colDiff = Math.abs(target.getColumn() - kingPosition.getColumn());
        return Math.max(rowDiff, colDiff) == MAX_MOVE_DISTANCE;
    }
    private Position getCurrentKingPosition() {
        return game.getKingPosition(game.getCurrentTeamID());
    }
    //ToDO: Exceptions noch richtig werfen!
    private void validatePlaceTarget() throws InvalidGameStateException {
        if (turnState.getSelectedPos() == null) {
            throw new NoSelectionException();
        }
        if (turnState.hasPlacedThisTurn()) {
            throw new InvalidGameStateException("You have already placed a unit this turn.");
        }
        Position kingPosition = getCurrentKingPosition();
        Position targetPosition = turnState.getSelectedPos();
        if (!isAdjacentToKing(targetPosition, kingPosition)) {
            throw new InvalidGameStateException("You can only place a unit adjacent to your King.");
        }
        BoardEntity occupant = game.getOccupant(targetPosition);
        if (occupant != null && occupant.getTeamID() != game.getCurrentTeamID()) {
            throw new InvalidGameStateException("You cannot place a an enemy occupied field.");
        }
    }
    private List<Unit> extractUnitsFromHand(List<Integer> internalIndices) {
        List<Unit> units = new ArrayList<>();
        for (int internalIndex : internalIndices) {
            units.add(game.getHandCardAt(getCurrentTeamID(), internalIndex));
        }
        return units;
    }
    private void removeHandCardsDescending(List<Integer> internalIndices) {
        List<Integer> sortedIndices = new ArrayList<>(internalIndices);
        sortedIndices.sort(Collections.reverseOrder());
        for (int internalIndex : sortedIndices) {
            game.removeHandCardAt(getCurrentTeamID(), internalIndex);
        }
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
        validatePlaceTarget();
        List<Integer> internalIndices = parseToInternalHandIndices(userIndices);
        List<Unit> unitsToPlace = extractUnitsFromHand(internalIndices);
        removeHandCardsDescending(internalIndices);
        List<PlaceStepSnapshot> results = new ArrayList<>();
        for (Unit unit : unitsToPlace) {
            results.add(placeSingleUnit(unit));
        }
        turnState.markPlacedThisTurn();
        return results;
    }
    private PlaceStepSnapshot placeSingleUnit(Unit incomingUnit) throws InvalidGameStateException {
        BoardEntity occupant = game.getOccupant(turnState.getSelectedPos());
        if (occupant == null) {
            game.setOccupant(turnState.getSelectedPos(), incomingUnit);
            eliminateIfBoardLimitExceeded(turnState.getSelectedPos(), incomingUnit);
            return new PlaceStepSnapshot(getCurrentTeamName(), incomingUnit.getName().toString(), null, null, turnState.getSelectedPos().toString());
        }
        if (occupant.isFarmerKing()) {
            throw new InvalidGameStateException("You cannot place a unit on top of a Farmer King.");
        }
        Unit existingUnit = (Unit) occupant;
        String existingUnitName = existingUnit.getName().toString();
        MergeResult result = unitMerger.tryMerge(incomingUnit, existingUnit);
        if (result.isSuccessful()) {
            Unit mergedUnit = result.getUnit();
            game.setOccupant(turnState.getSelectedPos(), mergedUnit);
            return new PlaceStepSnapshot(getCurrentTeamName(), incomingUnit.getName().toString(), existingUnitName, null,
                    turnState.getSelectedPos().toString());
        } else  {
            game.setOccupant(turnState.getSelectedPos(), incomingUnit);
            return new PlaceStepSnapshot(getCurrentTeamName(), incomingUnit.getName().toString(), existingUnitName, existingUnitName,
                    turnState.getSelectedPos().toString());
        }
    }
    private void eliminateIfBoardLimitExceeded(Position targetPosition, Unit justPlacedUnit) {
        if (getUnitsPlaced(getCurrentTeamID()) > MAX_UNITS_ON_BOARD
                && game.getOccupant(targetPosition) == justPlacedUnit) {
            game.setOccupant(targetPosition, null);
        }
    }
}