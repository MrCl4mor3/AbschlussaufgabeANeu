package edu.kit.kastel.crownoffarmland.model.units;

import edu.kit.kastel.crownoffarmland.model.team.TeamID;

/**
 * Represents an entity on the board, which can be a unit or a tile.
 * Contains common properties and methods for both units and tiles, such as name, team affiliation, and visibility.
 * This class is abstract and serves as a base for specific types of board entities, such as Unit and Tile.
 *
 * @author ucgdi
 */
public abstract class BoardEntity {
    private final UnitName name;
    private final TeamID teamId;
    private boolean revealed;


    protected BoardEntity(UnitName name, TeamID teamId, boolean revealed) {
        this.name = name;
        this.teamId = teamId;
        this.revealed = revealed;
    }

    /**
     * Returns the team ID associated with this board entity.
     * @return the team ID of this board entity
     */
    public TeamID getTeamId() { return teamId; }
    /**
     * Indicates whether this board entity has been revealed to the enemy player.
     * @return true if the entity is revealed, false otherwise
     */
    public boolean isRevealed() { return revealed; }
    /**
     * Returns the name of this board entity, which includes its role and qualificator.
     * @return the UnitName of this board entity
     */
    public UnitName getName() { return name; }
    public String getRole() {
        return name.getRole();
    }
    /**
     * Returns the qualificator of this board entity, which provides additional information about its type or status.
     * @return the qualificator of this board entity
     */
    public String getQualificator() {
        return name.getQualificator();
    }

    /**
     * Sets the revealed status of this board entity. This method is protected to allow subclasses to control when an entity becomes
     * revealed.
     * @param revealed true to mark the entity as revealed, false to mark it as hidden
     */
    protected void setRevealed(boolean revealed) { this.revealed = revealed; }

    /**
     * Indicates whether this board entity is the Farmer King, which is a special unit in the game. This method must be implemented by
     * subclasses to specify their behavior regarding the Farmer King status.
     * @return true if this board entity is the Farmer King, false otherwise
     */
    public abstract boolean isFarmerKing();

}