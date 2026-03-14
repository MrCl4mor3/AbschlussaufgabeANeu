package edu.kit.kastel.crownoffarmland.model.units;

import edu.kit.kastel.crownoffarmland.model.team.TeamID;

/**
 * Represents an entity on the board.
 * A board entity has a name, an owner, and a reveal state.
 *
 * @author ucgdi
 */
public abstract class BoardEntity {
    protected boolean blocking;
    private final UnitName name;
    private final TeamID teamID;
    private boolean revealed;

    /**
     * Constructs a board entity with the given name, owner, and reveal state.
     *
     * @param name the entity name
     * @param teamID the owning team
     * @param revealed whether the entity is initially revealed
     */
    protected BoardEntity(UnitName name, TeamID teamID, boolean revealed) {
        this.name = name;
        this.teamID = teamID;
        this.revealed = revealed;
        this.blocking = false;
    }

    /**
     * Returns the owning team of this entity.
     *
     * @return the owning team
     */
    public TeamID getOwner() {
        return teamID;
    }

    /**
     * Returns whether this entity is revealed.
     *
     * @return {@code true} if this entity is revealed, otherwise {@code false}
     */
    public boolean isRevealed() {
        return revealed;
    }

    /**
     * Returns the name of this entity.
     *
     * @return the entity name
     */
    public UnitName getName() {
        return name;
    }

    /**
     * Returns the role of this entity.
     *
     * @return the entity role
     */
    public String getRole() {
        return name.getRole();
    }

    /**
     * Returns the qualificator of this entity.
     *
     * @return the entity qualificator
     */
    public String getQualificator() {
        return name.getQualificator();
    }

    /**
     * Reveals this entity.
     */
    public void reveal() {
        this.revealed = true;
    }

    /**
     * Returns whether this entity is blocked.
     *
     * @return {@code true} if this entity is blocked, otherwise {@code false}
     */
    public boolean isBlocked() {
        return blocking;
    }

    /**
     * Returns whether this entity is a farmer king.
     *
     * @return {@code true} if this entity is a farmer king, otherwise {@code false}
     */
    public abstract boolean isFarmerKing();

}