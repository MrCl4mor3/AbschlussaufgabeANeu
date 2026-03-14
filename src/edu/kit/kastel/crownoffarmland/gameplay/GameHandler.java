package edu.kit.kastel.crownoffarmland.gameplay;

import edu.kit.kastel.crownoffarmland.exceptions.CrownOfFarmlandException;
import edu.kit.kastel.crownoffarmland.exceptions.InvalidGameStateException;
import edu.kit.kastel.crownoffarmland.exceptions.InvalidPositionException;
import edu.kit.kastel.crownoffarmland.exceptions.KingCannotBlockedException;
import edu.kit.kastel.crownoffarmland.exceptions.UnitAlreadyRevealedException;
import edu.kit.kastel.crownoffarmland.exceptions.gamestateexceptions.EmptySelectedFieldException;
import edu.kit.kastel.crownoffarmland.exceptions.gamestateexceptions.EnemyUnitSelectedException;
import edu.kit.kastel.crownoffarmland.exceptions.gamestateexceptions.InvalidHandException;
import edu.kit.kastel.crownoffarmland.exceptions.gamestateexceptions.NoSelectionException;
import edu.kit.kastel.crownoffarmland.exceptions.gamestateexceptions.UnitAlreadyActedException;
import edu.kit.kastel.crownoffarmland.gameplay.ai.AIDecisionService;
import edu.kit.kastel.crownoffarmland.gameplay.ai.AITurnController;
import edu.kit.kastel.crownoffarmland.gameplay.ai.WeightedRandomSelector;
import edu.kit.kastel.crownoffarmland.gameplay.combat.DuelManager;
import edu.kit.kastel.crownoffarmland.gameplay.snapshots.EndTurnSnapshot;
import edu.kit.kastel.crownoffarmland.gameplay.snapshots.EntityOnPositionSnapshot;
import edu.kit.kastel.crownoffarmland.gameplay.snapshots.EntitySnapshot;
import edu.kit.kastel.crownoffarmland.gameplay.snapshots.PlaceStepSnapshot;
import edu.kit.kastel.crownoffarmland.gameplay.snapshots.SnapshotFactory;
import edu.kit.kastel.crownoffarmland.gameplay.snapshots.SnapshotProvider;
import edu.kit.kastel.crownoffarmland.gameplay.snapshots.movesnapshot.MoveSnapshot;
import edu.kit.kastel.crownoffarmland.gameplay.unitmerge.UnitMerger;
import edu.kit.kastel.crownoffarmland.model.Game;
import edu.kit.kastel.crownoffarmland.model.board.Position;
import edu.kit.kastel.crownoffarmland.model.team.TeamID;
import edu.kit.kastel.crownoffarmland.model.units.BoardEntity;
import edu.kit.kastel.crownoffarmland.model.units.Unit;
import edu.kit.kastel.crownoffarmland.ui.renderer.GameOutputPrinter;

import java.util.List;

/**
 * Coordinates gameplay actions and turn flow.
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
    private final UnitMerger unitMerger;

    private AITurnController turnController;

    /**
     * Creates a new game handler.
     *
     * @param game the game model
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
     * Initializes the game state.
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

    /**
     * Initializes the AI controller.
     *
     * @param gameOutputPrinter the output printer
     */
    public void initializeAI(GameOutputPrinter gameOutputPrinter) {
        WeightedRandomSelector weightedRandomSelector = new WeightedRandomSelector(game.getRandomGenerator());
        this.turnController = new AITurnController(
                this,
                game,
                new AIDecisionService(game, turnState, unitMerger, weightedRandomSelector),
                gameOutputPrinter
        );
    }

    /**
     * Returns the selected position.
     *
     * @return the selected position, or {@code null} if none is selected
     */
    public Position getSelectedPos() {
        return turnState.getSelectedPos();
    }

    /**
     * Returns the current team ID.
     *
     * @return the current team ID
     */
    public TeamID getCurrentTeamID() {
        return game.getCurrentTeamID();
    }

    /**
     * Returns the snapshot provider.
     *
     * @return the snapshot provider
     */
    public SnapshotProvider snapshots() {
        return snapshotFactory;
    }

    /**
     * Returns whether the yield restriction is active.
     *
     * @return {@code true} if the yield restriction is active
     */
    public boolean isYieldRestrictionActive() {
        return turnState.isYieldRestrictionActive();
    }

    /**
     * Returns whether the game is over.
     *
     * @return {@code true} if the game is over
     */
    public boolean isGameOver() {
        return game.getWinnerID() != null;
    }

    /**
     * Returns the winning team name.
     *
     * @return the winning team name, or {@code null} if the game is not over
     */
    public String getWinner() {
        if (isGameOver()) {
            return game.teamView(game.getWinnerID()).getName();
        }
        return null;
    }

    /**
     * Returns whether the current player is AI-controlled.
     *
     * @return {@code true} if the current player is AI-controlled
     */
    public boolean isCurrentPlayerAI() {
        return getCurrentTeamID() == TeamID.TEAM_2;
    }

    /**
     * Selects the given board position.
     *
     * @param position the position to select
     * @throws InvalidPositionException if the position is invalid
     */
    public void setSelected(Position position) throws InvalidPositionException {
        if (!game.boardView().isValidPosition(position)) {
            throw new InvalidPositionException(position.toString());
        }
        turnState.setSelectedPos(position);
    }

    /**
     * Reveals the selected entity.
     *
     * @return the revealed entity snapshot
     * @throws InvalidGameStateException if no valid entity is selected
     * @throws UnitAlreadyRevealedException if the entity is already revealed
     */
    public EntityOnPositionSnapshot flipSelectedEntity() throws InvalidGameStateException {
        BoardEntity entity = getSelectedEntity();
        if (entity.isRevealed()) {
            throw new UnitAlreadyRevealedException(entity.getName().toString());
        }
        entity.reveal();
        return snapshotFactory.createEntitySnapshotAtSelected();
    }

    /**
     * Blocks the selected unit.
     *
     * @return the blocked entity snapshot
     * @throws InvalidGameStateException if no valid entity is selected
     * @throws KingCannotBlockedException if the selected entity is a king
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
     * Places units from the hand on the selected field.
     *
     * @param userIndices the one-based hand indices
     * @return the placement step snapshots
     * @throws InvalidGameStateException if the placement is not allowed
     */
    public List<PlaceStepSnapshot> placeUnits(int[] userIndices) throws InvalidGameStateException {
        return placementService.placeUnits(userIndices);
    }

    /**
     * Moves the selected unit to the given position.
     *
     * @param target the target position
     * @return the move snapshot
     * @throws InvalidGameStateException if the move is not allowed
     * @throws InvalidPositionException if the target position is invalid
     */
    public MoveSnapshot moveUnit(Position target) throws InvalidGameStateException {
        if (!game.boardView().isValidPosition(target)) {
            throw new InvalidPositionException(target.toString());
        }

        return movementService.moveUnit(target, getCurrentTeamID());
    }

    /**
     * Checks whether the turn may be ended.
     *
     * @param discardRequested whether a discard was requested
     * @return the yield check result
     */
    public YieldCheckResult checkYieldAttempt(boolean discardRequested) {
        boolean handFull = game.teamView(game.getCurrentTeamID()).isHandFull();

        if (!discardRequested && handFull) {
            turnState.activateYieldRestriction();
            return YieldCheckResult.DISCARD_REQUIRED;
        }

        if (discardRequested && !handFull) {
            turnState.activateYieldRestriction();
            return YieldCheckResult.DISCARD_NOT_ALLOWED;
        }

        return YieldCheckResult.SUCCESS;
    }

    /**
     * Ends the current turn.
     *
     * @return the end turn snapshot
     */
    public EndTurnSnapshot endTurn() {
        return finishTurn(null);
    }

    /**
     * Ends the current turn after discarding a card.
     *
     * @param index the one-based hand index of the discarded card
     * @return the end turn snapshot
     * @throws InvalidGameStateException if the discard is not allowed
     * @throws InvalidHandException if the index is out of bounds
     */
    public EndTurnSnapshot endTurnWithDiscard(int index) throws InvalidGameStateException {
        int handSize = game.teamView(game.getCurrentTeamID()).getHandSize();
        int internalIndex = index - HAND_INDEX_OFFSET;

        if (internalIndex < 0 || internalIndex >= handSize) {
            throw new InvalidHandException(String.valueOf(index));
        }

        Unit discardedCard = game.removeHandCardAt(getCurrentTeamID(), internalIndex);
        EntitySnapshot snapshot = new EntitySnapshot(discardedCard, game.teamView(game.getCurrentTeamID()).getName());

        return finishTurn(snapshot);
    }

    /**
     * Executes the AI turn.
     *
     * @throws CrownOfFarmlandException if the AI turn cannot be executed
     */
    public void executeAITurn() throws CrownOfFarmlandException {
        turnController.executeTurn();
    }

    private void drawCards(TeamID teamID, int amount) {
        for (int index = 0; index < amount; index++) {
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

    private EndTurnSnapshot finishTurn(EntitySnapshot discardedCard) {
        EndTurnSnapshot endTurnSnapshot = new EndTurnSnapshot(
                discardedCard,
                game.teamView(game.getEnemyTeamID()).getName(),
                isGameOver()
        );
        game.nextTurn();
        startCurrentTurn();
        return endTurnSnapshot;
    }
}