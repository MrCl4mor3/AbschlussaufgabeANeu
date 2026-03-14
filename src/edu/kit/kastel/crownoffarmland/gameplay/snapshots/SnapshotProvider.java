package edu.kit.kastel.crownoffarmland.gameplay.snapshots;

import edu.kit.kastel.crownoffarmland.exceptions.InvalidGameStateException;
import edu.kit.kastel.crownoffarmland.gameplay.snapshots.boardsnapshot.BoardSnapshot;
import edu.kit.kastel.crownoffarmland.model.team.TeamID;


import java.util.List;

public interface SnapshotProvider {

    BoardSnapshot createBoardSnapshot();
    EntitySnapshot createEntitySnapshot() throws InvalidGameStateException;
    EntityOnPositionSnapshot createEntitySnapshotAtSelected() throws InvalidGameStateException;
    List<EntitySnapshot> createHandSnapshot();
    TeamStateSnapshot createTeamStateSnapshot(TeamID teamID) throws InvalidGameStateException;
}
