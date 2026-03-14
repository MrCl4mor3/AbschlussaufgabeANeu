package edu.kit.kastel.crownoffarmland.gameplay.snapshots.movesnapshot;

import edu.kit.kastel.crownoffarmland.gameplay.snapshots.EntitySnapshot;

/**
 * Represents a snapshot of a move.
 *
 * @author ucgdi
 */
public abstract class MoveSnapshot {
    private final MoveType moveType;
    private final String toPositionName;
    private final boolean wasBlocked;
    private final EntitySnapshot movedEntity;

    /**
     * Creates a new move snapshot.
     *
     * @param movedEntity the moved entity
     * @param toPositionName the target position name
     * @param wasBlocked whether the entity was blocked before the move
     * @param moveType the move type
     */
    protected MoveSnapshot(EntitySnapshot movedEntity, String toPositionName, boolean wasBlocked, MoveType moveType) {
        this.moveType = moveType;
        this.movedEntity = movedEntity;
        this.toPositionName = toPositionName;
        this.wasBlocked = wasBlocked;
    }

    /**
     * Returns the moved entity.
     *
     * @return the moved entity
     */
    public EntitySnapshot getMovedEntity() {
        return movedEntity;
    }

    /**
     * Returns the target position name.
     *
     * @return the target position name
     */
    public String getToPositionName() {
        return toPositionName;
    }

    /**
     * Returns whether the entity was blocked before the move.
     *
     * @return {@code true} if the entity was blocked before the move
     */
    public boolean wasBlocked() {
        return wasBlocked;
    }

    /**
     * Returns the move type.
     *
     * @return the move type
     */
    public MoveType getMoveType() {
        return moveType;
    }
}