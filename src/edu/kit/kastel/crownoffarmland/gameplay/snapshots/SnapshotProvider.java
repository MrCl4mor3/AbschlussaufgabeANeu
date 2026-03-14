package edu.kit.kastel.crownoffarmland.gameplay.snapshots;

import edu.kit.kastel.crownoffarmland.exceptions.InvalidGameStateException;
import edu.kit.kastel.crownoffarmland.gameplay.snapshots.boardsnapshot.BoardSnapshot;
import edu.kit.kastel.crownoffarmland.model.team.TeamID;

import java.util.List;

/**
 * Provides snapshots of the current game state.
 *
 * @author ucgdi
 */
public interface SnapshotProvider {

    /**
     * Creates a snapshot of the board.
     *
     * @return the board snapshot
     */
    BoardSnapshot createBoardSnapshot();

    /**
     * Creates a snapshot of the selected entity.
     *
     * @return the entity snapshot
     * @throws InvalidGameStateException if no valid entity snapshot can be created
     */
    EntitySnapshot createEntitySnapshot() throws InvalidGameStateException;

    /**
     * Creates a snapshot of the selected entity together with its position.
     *
     * @return the entity-on-position snapshot
     * @throws InvalidGameStateException if no valid snapshot can be created
     */
    EntityOnPositionSnapshot createEntitySnapshotAtSelected() throws InvalidGameStateException;

    /**
     * Creates a snapshot of the current hand.
     *
     * @return the hand snapshot
     */
    List<EntitySnapshot> createHandSnapshot();

    /**
     * Creates a snapshot of a team's state.
     *
     * @param teamID the team ID
     * @return the team state snapshot
     * @throws InvalidGameStateException if no valid snapshot can be created
     */
    TeamStateSnapshot createTeamStateSnapshot(TeamID teamID) throws InvalidGameStateException;
}