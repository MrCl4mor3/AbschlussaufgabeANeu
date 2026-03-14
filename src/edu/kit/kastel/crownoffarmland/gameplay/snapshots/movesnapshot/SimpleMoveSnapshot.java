package edu.kit.kastel.crownoffarmland.gameplay.snapshots.movesnapshot;

import edu.kit.kastel.crownoffarmland.gameplay.snapshots.EntitySnapshot;

/**
 * Represents a snapshot of a simple move.
 *
 * @author ucgdi
 */
public final class SimpleMoveSnapshot extends MoveSnapshot {
    private static final MoveType MOVE_TYPE = MoveType.SIMPLE;

    /**
     * Creates a new simple move snapshot.
     *
     * @param movedEntity the moved entity
     * @param toPositionName the target position name
     * @param wasBlocked whether the entity was blocked before the move
     */
    public SimpleMoveSnapshot(EntitySnapshot movedEntity, String toPositionName, boolean wasBlocked) {
        super(movedEntity, toPositionName, wasBlocked, MOVE_TYPE);
    }
}