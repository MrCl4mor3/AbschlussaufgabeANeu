package edu.kit.kastel.crownoffarmland.gameplay.snapshots.boardsnapshot;

/**
 * Immutable snapshot of a single board cell for rendering purposes.
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
    private final boolean ownTeam;
    private final boolean moveable;

    /**
     * Creates a new immutable cell snapshot.
     *
     * @param hasEntity true if the cell contains an entity
     * @param farmerKing true if the entity on the cell is a farmer king
     * @param blocked true if the entity on the cell is blocked
     * @param ownTeam true if the entity on the cell belongs to the current team
     * @param moveable true if the entity on the cell may still move in this turn
     */
    public BoardCellSnapshot(boolean hasEntity, boolean farmerKing, boolean blocked, boolean ownTeam, boolean moveable) {
        this.hasEntity = hasEntity;
        this.farmerKing = farmerKing;
        this.blocked = blocked;
        this.ownTeam = ownTeam;
        this.moveable = moveable;
    }

    /**
     * Returns a shared snapshot representing an empty field.
     *
     * @return the empty cell snapshot
     */
    public static BoardCellSnapshot empty() {
        return EMPTY;
    }

    /**
     * Returns whether the cell contains an entity.
     *
     * @return true if the cell contains an entity
     */
    public boolean hasEntity() {
        return hasEntity;
    }

    /**
     * Returns whether the entity on this cell is a farmer king.
     *
     * @return true if the entity is a farmer king
     */
    public boolean isFarmerKing() {
        return farmerKing;
    }

    /**
     * Returns whether the entity on this cell is blocked.
     *
     * @return true if the entity is blocked
     */
    public boolean isBlocked() {
        return blocked;
    }

    /**
     * Returns whether the entity on this cell belongs to the current team.
     *
     * @return true if the entity belongs to the current team
     */
    public boolean isPlayerTeam() {
        return ownTeam;
    }

    /**
     * Returns whether the entity on this cell may still move in this turn.
     *
     * @return true if the entity may still move
     */
    public boolean isMoveable() {
        return moveable;
    }
}