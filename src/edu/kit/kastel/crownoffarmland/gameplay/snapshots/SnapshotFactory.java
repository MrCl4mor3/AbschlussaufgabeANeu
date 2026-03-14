package edu.kit.kastel.crownoffarmland.gameplay.snapshots;

import edu.kit.kastel.crownoffarmland.exceptions.gamestateexceptions.NoSelectionException;
import edu.kit.kastel.crownoffarmland.gameplay.TurnState;
import edu.kit.kastel.crownoffarmland.gameplay.snapshots.boardsnapshot.BoardCellSnapshot;
import edu.kit.kastel.crownoffarmland.gameplay.snapshots.boardsnapshot.BoardSnapshot;
import edu.kit.kastel.crownoffarmland.model.Game;
import edu.kit.kastel.crownoffarmland.model.board.Position;
import edu.kit.kastel.crownoffarmland.model.team.TeamID;
import edu.kit.kastel.crownoffarmland.model.units.BoardEntity;
import edu.kit.kastel.crownoffarmland.model.units.Unit;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Creates immutable snapshot objects from the current game state.
 * This class contains no game logic. It only translates model state into
 * snapshot objects that can later be rendered by the UI.
 *
 * @author ucgdi
 */
public class SnapshotFactory implements SnapshotProvider {

    private final Game game;
    private final TurnState turnState;


    public SnapshotFactory(Game game, TurnState turnState) {
        this.game = game;
        this.turnState = turnState;
    }

    /**
    * Creates a snapshot of the whole board.
    *
    * @return the board snapshot
    */
    @Override
    public BoardSnapshot createBoardSnapshot() {
        int boardSize = game.boardView().getBoardSize();
        BoardCellSnapshot[][] cells = new BoardCellSnapshot[boardSize][boardSize];

        for (int rowIndex = 0; rowIndex < boardSize; rowIndex++) {
            for (int columnIndex = 0; columnIndex < boardSize; columnIndex++) {
                Position position = game.boardView().getPositionAt(rowIndex, columnIndex);
                cells[rowIndex][columnIndex] = createBoardCellSnapshot(game, position, turnState.getMovedEntities(),
                        game.getCurrentTeamID());
            }
        }

        return new BoardSnapshot(cells, turnState.getSelectedPos());
    }

    /**
    * Creates a snapshot of the entity on the selected field.
    *
    * @return the entity snapshot, or a no-unit snapshot if the field is empty
    * @throws NoSelectionException if no field is selected
    */
    @Override
    public EntityOnPositionSnapshot createEntitySnapshotAtSelected() throws NoSelectionException {
        return new EntityOnPositionSnapshot(createEntitySnapshot(), turnState.getSelectedPos().toString());
    }

    /**
     * Creates an entitySnapshot.
     * @return a EntitySnapshot of the selected position, or a no-unit snapshot if the field is empty
     * @throws NoSelectionException if no field is selected
     */
    @Override
    public EntitySnapshot createEntitySnapshot() throws NoSelectionException {
        if (turnState.getSelectedPos() == null) {
            throw new NoSelectionException();
        }

        BoardEntity entity = game.boardView().getOccupant(turnState.getSelectedPos());
        if (entity == null) {
            return EntitySnapshot.noUnit();
        }

        String teamName = game.teamView(entity.getOwner()).getName();
        boolean hidden = !entity.isRevealed() && entity.getOwner() != game.getCurrentTeamID();

        return new EntitySnapshot(entity, teamName, entity.isFarmerKing(), hidden);
    }


    /**
     * Creates a snapshot of the current team's hand.
     *
     * @return immutable list of hand entry snapshots
     */
    @Override
    public List<EntitySnapshot> createHandSnapshot() {
        List<EntitySnapshot> handEntries = new ArrayList<>();
        int handSize = game.teamView(game.getCurrentTeamID()).getHandSize();
        String teamName = game.teamView(game.getCurrentTeamID()).getName();

        for (int index = 0; index < handSize; index++) {
            Unit unit = game.getHandCardAt(game.getCurrentTeamID(), index);
            handEntries.add(new EntitySnapshot(unit, teamName));
        }

        return List.copyOf(handEntries);
    }


    /**
     * Creates a snapshot of the current team's state, including life points, remaining deck cards, and placed units.
     * @param teamID the team for which to create the snapshot
     * @return the team state snapshot
     */
    @Override
    public TeamStateSnapshot createTeamStateSnapshot(TeamID teamID) {
        String teamName = game.teamView(teamID).getName();
        int remainingDeckCards = game.teamView(teamID).getDrawPileSize();
        int lifePoints = game.teamView(teamID).getLifePoints();

        return new TeamStateSnapshot(teamName, lifePoints, remainingDeckCards, game.getUnitsPlaced(teamID));
    }

    private BoardCellSnapshot createBoardCellSnapshot(Game game, Position position, Set<BoardEntity> movedEntities, TeamID currentTeamID) {
        BoardEntity occupant = game.boardView().getOccupant(position);

        if (occupant == null) {
            return BoardCellSnapshot.empty();
        }

        boolean isPlayerTeam = occupant.getOwner() == TeamID.TEAM_1;
        boolean isMoveable = occupant.getOwner().equals(currentTeamID) && !movedEntities.contains(occupant);

        return new BoardCellSnapshot(true, occupant.isFarmerKing(), occupant.isBlocked(), isPlayerTeam, isMoveable);
    }
}