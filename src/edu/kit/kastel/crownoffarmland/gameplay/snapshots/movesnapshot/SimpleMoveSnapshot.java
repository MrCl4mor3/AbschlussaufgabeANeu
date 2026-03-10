package edu.kit.kastel.crownoffarmland.gameplay.snapshots.movesnapshot;

import edu.kit.kastel.crownoffarmland.gameplay.snapshots.EntitySnapshot;


/**
 * This class represents a snapshot of a simple move in the game. It contains information about the moved entity, the position to which
 * it was moved, and whether the move was blocked by another entity. It extends the MoveSnapshot class, which is a more general
 * representation of a move in the game.
 *
 * @author ucgdi
 */
public final class SimpleMoveSnapshot extends  MoveSnapshot {

    private static final MoveType MOVE_TYPE = MoveType.SIMPLE;

    /**
     * Creates a new SimpleMoveSnapshot with the given parameters.
     * @param movedEntity the entity that was moved
     * @param toPositionName the name of the position to which the entity was moved
     * @param wasBlocked indicates whether the move was blocked by another entity
     */
    public SimpleMoveSnapshot(EntitySnapshot movedEntity, String toPositionName, boolean wasBlocked) {
        super(movedEntity, toPositionName, wasBlocked, MOVE_TYPE);
    }
}
