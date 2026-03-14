package edu.kit.kastel.crownoffarmland.gameplay.snapshots.movesnapshot;

import edu.kit.kastel.crownoffarmland.gameplay.snapshots.EntitySnapshot;

/**
 * Represents a snapshot of a merge move.
 *
 * @author ucgdi
 */
public final class MergeMoveSnapshot extends MoveSnapshot {
    private static final MoveType MOVE_TYPE = MoveType.MERGE;

    private final String targetEntityName;
    private final boolean mergeSuccess;

    /**
     * Creates a new merge move snapshot.
     *
     * @param movedEntity the moved entity
     * @param toPositionName the target position name
     * @param wasBlocked whether the entity was blocked before the move
     * @param mergeSuccess whether the merge was successful
     * @param targetEntityName the name of the target entity
     */
    public MergeMoveSnapshot(EntitySnapshot movedEntity, String toPositionName, boolean wasBlocked, boolean mergeSuccess,
            String targetEntityName) {
        super(movedEntity, toPositionName, wasBlocked, MOVE_TYPE);
        this.mergeSuccess = mergeSuccess;
        this.targetEntityName = targetEntityName;
    }

    /**
     * Returns whether the merge was successful.
     *
     * @return {@code true} if the merge was successful
     */
    public boolean isMergeSuccess() {
        return mergeSuccess;
    }

    /**
     * Returns the name of the target entity.
     *
     * @return the target entity name
     */
    public String getTargetEntityName() {
        return targetEntityName;
    }
}