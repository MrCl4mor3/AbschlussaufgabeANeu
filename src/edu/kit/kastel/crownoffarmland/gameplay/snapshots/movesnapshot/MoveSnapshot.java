package edu.kit.kastel.crownoffarmland.gameplay.snapshots.movesnapshot;

import edu.kit.kastel.crownoffarmland.gameplay.snapshots.EntitySnapshot;

/**
 * This class represents a snapshot of a move action in the game, containing information about the entity that was moved and the target
 * position name.
 *
 * @author ucgdi
 */
public abstract class MoveSnapshot {
    private final MoveType moveType;
    private final String toPositionName;
    private final boolean wasBlocked;
    private final EntitySnapshot movedEntity;

    /**
     * Creates a new MoveSnapshot with the given parameters, including the entity that was moved, the target position name, whether the
     * move was blocked, and the type of move.
     * @param movedEntity the EntitySnapshot representing the entity that was moved
     * @param toPositionName the name of the position to which the entity was moved
     * @param wasBlocked a boolean indicating whether the move was blocked by an obstacle or another entity
     * @param moveType the type of move that was performed, represented by the MoveType enum
     */
    protected MoveSnapshot(EntitySnapshot movedEntity, String toPositionName, boolean wasBlocked, MoveType moveType) {
        this.moveType = moveType;
        this.movedEntity = movedEntity;
        this.toPositionName = toPositionName;
        this.wasBlocked = wasBlocked;
    }

    /**
     * Getter for the moved entity.
     * @return the EntitySnapshot representing the entity that was moved in this move action
     */
    public EntitySnapshot getMovedEntity() {
        return movedEntity;
    }

    /**
     * Getter for the target position name.
     * @return the name of the position to which the entity was moved in this move action
     */
    public String getToPositionName() {
        return toPositionName;
    }

    /**
     * Indicates whether the entity was in block Mode before or not.
     * @return true if the entity was blocked, false otherwise
     */
    public boolean wasBlocked() {
        return wasBlocked;
    }

    /**
     * Getter for the type of move that was performed.
     * @return the MoveType enum value representing the type of move that was performed in this move action
     */
    public MoveType getMoveType() {
        return moveType;
    }

    /**
     * Protected getter for the moved entity, which can be used by subclasses to access the moved entity's information without exposing
     * it publicly.
     * @return the EntitySnapshot representing the entity that was moved in this move action, accessible to subclasses of MoveSnapshot
     */
    protected EntitySnapshot getMovingEntitySnapshot() {
        return movedEntity;
    }
}
