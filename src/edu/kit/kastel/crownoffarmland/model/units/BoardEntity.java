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
    protected boolean blocking;
    private final UnitName name;
    private final TeamID teamID;
    private boolean revealed;


    /**
     * Constructs a new BoardEntity with the specified name, team ID, and revealed status. This constructor is protected to allow only
     * subclasses to create instances of BoardEntity, ensuring that it cannot be instantiated directly.
     * @param name the UnitName of this board entity, which includes its role and qualificator
     * @param teamID the TeamID of the team to which this board entity belongs
     * @param revealed true if this board entity is revealed to the enemy player, false if it is hidden; this status can be changed later
     *                using the setRevealed method
     */
    protected BoardEntity(UnitName name, TeamID teamID, boolean revealed) {
        this.name = name;
        this.teamID = teamID;
        this.revealed = revealed;
        this.blocking = false;
    }

    /**
     * Returns the team ID associated with this board entity.
     * @return the team ID of this board entity
     */
    public TeamID getOwner() {
        return teamID;
    }
    /**
     * Indicates whether this board entity has been revealed to the enemy player.
     * @return true if the entity is revealed, false otherwise
     */
    public boolean isRevealed() {
        return revealed;
    }
    /**
     * Returns the name of this board entity, which includes its role and qualificator.
     * @return the UnitName of this board entity
     */
    public UnitName getName() {
        return name;
    }
    /**
     * Returns the role of this board entity, which indicates its primary function or type in the game (e.g., "Knight", "Archer").
     * @return the role of this board entity
     */
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
     * revealed. Once an entity is revealed, it cannot be hidden again, so this method only allows changing the status from false to true.
     */
    public void reveal() {
        this.revealed = true;
    }

    /**
     * Indicates whether this board entity is the Farmer King, which is a special unit in the game. This method must be implemented by
     * subclasses to specify their behavior regarding the Farmer King status.
     * @return true if this board entity is the Farmer King, false otherwise
     */
    public abstract boolean isFarmerKing();

    /**
     * Returns whether the unit is currently blocked. A blocked unit cannot perform actions until it is unblocked.
     * @return true if the unit is blocked, false otherwise
     */
    public boolean isBlocked() {
        return blocking;
    }
}