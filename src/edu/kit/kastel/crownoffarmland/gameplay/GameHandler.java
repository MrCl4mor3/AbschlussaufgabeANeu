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
import edu.kit.kastel.crownoffarmland.gameplay.unitmerge.UnitMerger;

import edu.kit.kastel.crownoffarmland.model.Game;
import edu.kit.kastel.crownoffarmland.model.board.Position;
import edu.kit.kastel.crownoffarmland.model.team.TeamID;
import edu.kit.kastel.crownoffarmland.model.units.BoardEntity;
import edu.kit.kastel.crownoffarmland.model.units.StatusValue;
import edu.kit.kastel.crownoffarmland.model.units.Unit;


import edu.kit.kastel.crownoffarmland.model.units.UnitName;
import edu.kit.kastel.crownoffarmland.ui.snapshots.BoardCellSnapshot;
import edu.kit.kastel.crownoffarmland.ui.snapshots.BoardSnapshot;
import edu.kit.kastel.crownoffarmland.ui.snapshots.EntitySnapshot;
import edu.kit.kastel.crownoffarmland.ui.snapshots.TeamStateSnapshot;


import java.util.ArrayList;
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

    private final Game game;
    private final DuelManager duelManager;
    private final UnitMerger unitMerger;

    private Position selected;
    private boolean yieldRestrictionActive;
    private final Set<BoardEntity> movedEntityThisTurn;
    private boolean placedThisTurn;

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
        this(game, new DuelManager(), new UnitMerger());
    }

    private GameHandler(Game game, DuelManager duelManager, UnitMerger unitMerger) {
        this.game = game;
        this.duelManager = duelManager;
        this.unitMerger = unitMerger;
        this.selected = null;
        this.movedEntityThisTurn = new HashSet<>();
        this.placedThisTurn = false;
        this.yieldRestrictionActive = false;
    }

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

        Unit unit = new Unit(TeamID.TEAM_2, new UnitName("Best", "King"), new StatusValue(1, 1));
        game.setOccupant(new Position(2, 'D'), unit);
    }



    private void startCurrentTurn() {
        selected = null;
        placedThisTurn = false;
        movedEntityThisTurn.clear();
        yieldRestrictionActive = false;

        TeamID currentTeam = game.getCurrentTeamID();

        if (game.isDrawPileEmpty(currentTeam)) {
            game.setWinner(currentTeam.getNext());
            return;
        }

        game.drawToHand(currentTeam);
    }


    private boolean hasMovedThisTurn(BoardEntity entity) {
        return movedEntityThisTurn.contains(entity);
    }

    private void markAsMovedThisTurn(BoardEntity entity) {
        movedEntityThisTurn.add(entity);
    }

    public Position getSelectedPos() {
        return selected;
    }

    public boolean isHandFull() {
        return game.isHandFull(getCurrentTeamID());
    }

    public String getNextPlayerName() {
        return game.getTeamName(game.getEnemyTeamID());
    }


    public EntitySnapshot flipSelectedEntity() throws InvalidGameStateException {
        BoardEntity entity = getSelectedEntity();
        if (entity.isRevealed()) {
            throw new UnitAlreadyRevealedException(entity.getName().toString());
        }
        entity.revealeEntity();
        return createEntitySnapshotAtSelected();
    }

    private BoardEntity getSelectedEntity() throws InvalidGameStateException {
        if (selected == null) {
            throw new NoSelectionException();
        }
        BoardEntity entity = game.getOccupant(selected);
        if (entity == null) {
            throw new EmptySelectedFieldException(selected.toString());
        }
        if (entity.getTeamID() != getCurrentTeamID()) {
            throw new EnemyUnitSelectedException();
        }
        if (movedEntityThisTurn.contains(entity)) {
            throw new UnitAlreadyActedException(entity.getName().toString());
        }
        return entity;
    }

    public void setSelected(String rawPosition) throws InvalidPositionException {
        this.selected = game.parsePosition(rawPosition);
    }

    public TeamID getCurrentTeamID() {
        return game.getCurrentTeamID();
    }

    public String getCurrentTeamName() {
        return game.getTeamName(game.getCurrentTeamID());
    }


    public Set<BoardEntity> getMovedEntityThisTurn() {
        return movedEntityThisTurn;
    }

    public BoardSnapshot createBoardSnapshot() {
        final int boardSize = game.getBoardSize();
        BoardCellSnapshot[][] cells = new BoardCellSnapshot[boardSize][boardSize];

        for (int rowIndex = 0; rowIndex < boardSize; rowIndex++) {
            for (int columnIndex = 0; columnIndex < boardSize; columnIndex++) {
                Position position = game.getPositionAt(rowIndex, columnIndex);
                cells[rowIndex][columnIndex] = createCellSnapshot(position);
            }
        }
        return new BoardSnapshot(cells, selected);
    }

    private BoardCellSnapshot createCellSnapshot(Position position) {
        BoardEntity occupant = game.getOccupant(position);

        if (occupant == null) {
            return BoardCellSnapshot.empty();
        }

        boolean isOwnTeam = occupant.getTeamID() == game.getCurrentTeamID();
        boolean isMoveable = isOwnTeam && !hasMovedThisTurn(occupant);

        return new BoardCellSnapshot(true, occupant.isFarmerKing(), occupant.isBlocked(), isOwnTeam, isMoveable);
    }

    public EntitySnapshot createEntitySnapshotAtSelected() throws InvalidGameStateException {
        if (selected == null) {
            throw new NoSelectionException();
        }

        BoardEntity entity = game.getOccupant(selected);
        if (entity == null) {
            return EntitySnapshot.noUnit();
        }

        String teamName = game.getTeamName(entity.getTeamID());
        boolean hidden = !entity.isRevealed() && (entity.getTeamID() != game.getCurrentTeamID());
        return new EntitySnapshot(entity, teamName, entity.isFarmerKing(), hidden);
    }

    public List<EntitySnapshot> createHandSnapshot() {
        List<EntitySnapshot> handEntries = new ArrayList<>();
        int handSize = game.getHandSize(game.getCurrentTeamID());

        for (int index = 0; index < handSize; index++) {
            Unit unit = game.getHandCardAt(getCurrentTeamID(), index);

            handEntries.add(new EntitySnapshot(unit, game.getTeamName(game.getCurrentTeamID())));
        }
        return List.copyOf(handEntries);
    }

    public EntitySnapshot blockSelected() throws InvalidGameStateException {
        BoardEntity entity = getSelectedEntity();
        if (entity.isFarmerKing()) {
            throw new KingCannotBlockedException();
        }
        Unit unit = (Unit) entity;
        unit.block();
        movedEntityThisTurn.add(unit);
        return createEntitySnapshotAtSelected();
    }

    public TeamStateSnapshot createTeamStateSnapshots(TeamID teamID) {
        String teamName = game.getTeamName(teamID);
        int remainingDeckSize = game.getDrawPileSize(teamID);
        int lifePoints = game.getLifePoints(teamID);
        return new TeamStateSnapshot(teamName, lifePoints, remainingDeckSize, getUnitsPlaced(teamID));
    }

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



    public boolean isYieldRestrictionActive() {
        return yieldRestrictionActive;
    }


    public boolean tryEndTurn() throws InvalidGameStateException {
        if (isHandFull()) {
            setYieldRestrictionActive();
            throw new YieldException(game.getTeamName(game.getCurrentTeamID()));
        } else {
            nextRound();
            return true;
        }
    }

    public EntitySnapshot tryEndTurnWithDiscard(int index) throws InvalidGameStateException {
        if (!isHandFull()) {
            setYieldRestrictionActive();
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

    private void setYieldRestrictionActive() {
        this.yieldRestrictionActive = true;
    }


    private void nextRound() {
        game.nextTurn();
        startCurrentTurn();
    }




    public boolean isGameOver() {
        return game.getWinner() != null;
    }

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
}