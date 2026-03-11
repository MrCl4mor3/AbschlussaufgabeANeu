package edu.kit.kastel.crownoffarmland.gameplay.snapshots.movesnapshot;

import edu.kit.kastel.crownoffarmland.gameplay.snapshots.EntitySnapshot;

/**
 * This class represents a snapshot of a merge move in the game. It contains information about the moved entity, the target entity,
 * the position to which the entity was moved, whether the move was blocked, and whether the merge was successful.
 *
 * @author ucgdi
 */
public final class MergeMoveSnapshot extends  MoveSnapshot {
    private static final MoveType MOVE_TYPE = MoveType.MERGE;

    private final String targetEntityName;
    private final boolean mergeSuccess;


    /**
     * Creates a new MergeMoveSnapshot with the given parameters.
     * @param movedEntity the entity that was moved in the merge
     * @param toPositionName the name of the position to which the entity was moved
     * @param wasBlocked indicates whether the movedEntity was in Block Mode
     * @param mergeSuccess indicates whether the merge was successful
     * @param targetEntityName the name of the target entity involved in the merge
     */
    public MergeMoveSnapshot(EntitySnapshot movedEntity, String toPositionName, boolean wasBlocked, boolean mergeSuccess,
        String targetEntityName) {
        super(movedEntity, toPositionName, wasBlocked, MOVE_TYPE);
        this.mergeSuccess = mergeSuccess;
        this.targetEntityName = targetEntityName;
    }


    /**
     * Returns whether the merge was successful.
     * @return true if the merge was successful, false otherwise
     */
    public boolean isMergeSuccess() {
        return mergeSuccess;
    }

    /**
     * Returns the name of the target entity involved in the merge.
     * @return the name of the target entity involved in the merge
     */
    public String getTargetEntityName() {
        return targetEntityName;
    }

}
