package edu.kit.kastel.crownoffarmland.gameplay.snapshots;

import edu.kit.kastel.crownoffarmland.exceptions.NoSelectionException;
import edu.kit.kastel.crownoffarmland.gameplay.snapshots.movesnapshot.EntityOnPositionSnapshot;
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
public class SnapshotFactory {

    /**
    * Creates a snapshot of the whole board.
    *
    * @param game the current game
    * @param selected the currently selected position, may be null
    * @param movedEntities the entities that have already acted this turn
    * @return the board snapshot
    */
    public BoardSnapshot createBoardSnapshot(Game game, Position selected, Set<BoardEntity> movedEntities) {
        int boardSize = game.getBoardSize();
        BoardCellSnapshot[][] cells = new BoardCellSnapshot[boardSize][boardSize];

        for (int rowIndex = 0; rowIndex < boardSize; rowIndex++) {
            for (int columnIndex = 0; columnIndex < boardSize; columnIndex++) {
                Position position = game.getPositionAt(rowIndex, columnIndex);
                cells[rowIndex][columnIndex] = createBoardCellSnapshot(game, position, movedEntities, game.getCurrentTeamID());
            }
        }

        return new BoardSnapshot(cells, selected);
    }

    /**
    * Creates a snapshot of the entity on the selected field.
    *
    * @param game the current game
    * @param selected the selected field
    * @return the entity snapshot, or a no-unit snapshot if the field is empty
    * @throws NoSelectionException if no field is selected
    */
    public EntityOnPositionSnapshot createEntitySnapshotAtSelected(Game game, Position selected) throws NoSelectionException {
        return new EntityOnPositionSnapshot(createEntitySnapshot(game, selected), selected.toString());
    }

    public EntitySnapshot createEntitySnapshot(Game game, Position selected) throws NoSelectionException {
        if (selected == null) {
            throw new NoSelectionException();
        }

        BoardEntity entity = game.getOccupant(selected);
        if (entity == null) {
            return EntitySnapshot.noUnit();
        }

        String teamName = game.getTeamName(entity.getOwner());
        boolean hidden = !entity.isRevealed() && entity.getOwner() != game.getCurrentTeamID();

        return new EntitySnapshot(entity, teamName, entity.isFarmerKing(), hidden);
    }


    /**
     * Creates a snapshot of the current team's hand.
     *
     * @param game the current game
     * @return immutable list of hand entry snapshots
     */
    public List<EntitySnapshot> createHandSnapshot(Game game) {
        List<EntitySnapshot> handEntries = new ArrayList<>();
        int handSize = game.getHandSize(game.getCurrentTeamID());
        String teamName = game.getTeamName(game.getCurrentTeamID());

        for (int index = 0; index < handSize; index++) {
            Unit unit = game.getHandCardAt(game.getCurrentTeamID(), index);
            handEntries.add(new EntitySnapshot(unit, teamName));
        }

        return List.copyOf(handEntries);
    }


    /**
     * Creates a snapshot of the current team's state, including life points, remaining deck cards, and placed units.
     * @param game the current game
     * @param teamID the team for which to create the snapshot
     * @return the team state snapshot
     */
    public TeamStateSnapshot createTeamStateSnapshot(Game game, TeamID teamID) {
        String teamName = game.getTeamName(teamID);
        int remainingDeckCards = game.getDrawPileSize(teamID);
        int lifePoints = game.getLifePoints(teamID);

        return new TeamStateSnapshot(teamName, lifePoints, remainingDeckCards, game.getUnitsPlaced(teamID));
    }

    private BoardCellSnapshot createBoardCellSnapshot(Game game, Position position, Set<BoardEntity> movedEntities, TeamID currentTeamID) {
        BoardEntity occupant = game.getOccupant(position);

        if (occupant == null) {
            return BoardCellSnapshot.empty();
        }

        boolean isOwnTeam = occupant.getOwner() == currentTeamID;
        boolean isMoveable = isOwnTeam && !movedEntities.contains(occupant);

        return new BoardCellSnapshot(true, occupant.isFarmerKing(), occupant.isBlocked(), isOwnTeam, isMoveable);
    }
}