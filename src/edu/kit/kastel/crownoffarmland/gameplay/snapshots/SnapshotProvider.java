package edu.kit.kastel.crownoffarmland.gameplay.snapshots;

import edu.kit.kastel.crownoffarmland.exceptions.InvalidGameStateException;
import edu.kit.kastel.crownoffarmland.gameplay.snapshots.boardsnapshot.BoardSnapshot;
import edu.kit.kastel.crownoffarmland.model.team.TeamID;


import java.util.List;

/**
 * Interface.
 *
 *
 * @author ucgdi
 */
public interface SnapshotProvider {

    /**
     * w.
     * @return The snapshot of the whole board.
     */
    BoardSnapshot createBoardSnapshot();

    /**
     * w.
     * @return an Entity Snapshot.
     * @throws InvalidGameStateException if the gamestate is invalid for creating the snapshot, e.g. if no field is selected for
     *      createEntitySnapshotAtSelected.
     */
    EntitySnapshot createEntitySnapshot() throws InvalidGameStateException;

    /**
     * W.
     * @return w
     * @throws InvalidGameStateException w
     */
    EntityOnPositionSnapshot createEntitySnapshotAtSelected() throws InvalidGameStateException;

    /**
     * w.
     * @return w
     */
    List<EntitySnapshot> createHandSnapshot();

    /**
     * w.
     * @param teamID w
     * @return w
     * @throws InvalidGameStateException w
     */
    TeamStateSnapshot createTeamStateSnapshot(TeamID teamID) throws InvalidGameStateException;
}
