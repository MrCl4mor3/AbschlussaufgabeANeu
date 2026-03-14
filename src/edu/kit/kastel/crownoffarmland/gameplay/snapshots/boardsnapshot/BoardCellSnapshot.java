package edu.kit.kastel.crownoffarmland.gameplay.snapshots.boardsnapshot;

/**
 * Represents the snapshot of a single board cell.
 *
 * @author ucgdi
 */
public final class BoardCellSnapshot {
    private static final boolean NO_ENTITY = false;
    private static final boolean NOT_A_FARMER_KING = false;
    private static final boolean NOT_BLOCKED = false;
    private static final boolean NOT_OWN_TEAM = false;
    private static final boolean NOT_MOVEABLE = false;

    private static final BoardCellSnapshot EMPTY =
            new BoardCellSnapshot(NO_ENTITY, NOT_A_FARMER_KING, NOT_BLOCKED, NOT_OWN_TEAM, NOT_MOVEABLE);

    private final boolean hasEntity;
    private final boolean farmerKing;
    private final boolean blocked;
    private final boolean teamOne;
    private final boolean moveable;

    /**
     * Creates a new board cell snapshot.
     *
     * @param hasEntity whether the cell contains an entity
     * @param farmerKing whether the entity is a farmer king
     * @param blocked whether the entity is blocked
     * @param teamOne whether the entity belongs to team one
     * @param moveable whether the entity can still move this turn
     */
    public BoardCellSnapshot(boolean hasEntity, boolean farmerKing, boolean blocked, boolean teamOne, boolean moveable) {
        this.hasEntity = hasEntity;
        this.farmerKing = farmerKing;
        this.blocked = blocked;
        this.teamOne = teamOne;
        this.moveable = moveable;
    }

    /**
     * Returns the shared empty cell snapshot.
     *
     * @return the empty cell snapshot
     */
    public static BoardCellSnapshot empty() {
        return EMPTY;
    }

    /**
     * Returns whether the cell contains an entity.
     *
     * @return {@code true} if the cell contains an entity
     */
    public boolean hasEntity() {
        return hasEntity;
    }

    /**
     * Returns whether the entity is a farmer king.
     *
     * @return {@code true} if the entity is a farmer king
     */
    public boolean isFarmerKing() {
        return farmerKing;
    }

    /**
     * Returns whether the entity is blocked.
     *
     * @return {@code true} if the entity is blocked
     */
    public boolean isBlocked() {
        return blocked;
    }

    /**
     * Returns whether the entity belongs to team one.
     *
     * @return {@code true} if the entity belongs to the team one
     */
    public boolean isTeamOne() {
        return teamOne;
    }

    /**
     * Returns whether the entity can still move this turn.
     *
     * @return {@code true} if the entity can still move
     */
    public boolean isMoveable() {
        return moveable;
    }
}