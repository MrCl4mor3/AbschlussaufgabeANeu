package edu.kit.kastel.crownoffarmland.model.units;

import edu.kit.kastel.crownoffarmland.model.team.TeamID;

/**
 * Represents a unit on the board. It has a name, a team, and status values (attack and defense). It can be revealed or hidden, and it
 * can be blocked or unblocked.
 *
 * @author ucgdi
 */
public class Unit extends BoardEntity {
    private final StatusValue statusValue;



    /**
     * Constructor for creating a Unit object with the specified team ID and unit template. The unit is initialized with the name and status
     * values from the template, and it is initially hidden and unblocked.
     * @param teamId the unique identifier of the team to which the unit belongs
     * @param template the template containing the name and status values for the unit
     */
    public Unit(TeamID teamId, UnitTemplate template) {
        this(teamId, template.getName(), template.getStats());
    }
    /**
     * Constructor for creating a Unit object with the specified team ID, name, and status values. The unit is initially hidden and
     * unblocked.
     * @param teamId the unique identifier of the team to which the unit belongs
     * @param name the name of the unit, which is hidden until the unit is revealed
     * @param statusValue the status values of the unit, including attack and defense, which are hidden until the unit is revealed
     */
    public Unit(TeamID teamId, UnitName name, StatusValue statusValue) {
        super(name, teamId, false);
        this.statusValue = statusValue;
    }

    /**
     * Returns the attack value of the unit, which determines how much damage it can deal to other units.
     * The attack value is a non-negative integer.
     * @return the attack value of the unit
     */
    public int getAtk() {
        return statusValue.getAtk();
    }

    /**
     * Returns the defense value of the unit, which determines how much damage it can absorb from attacks.
     * The defense value is a non-negative integer.
     * @return the defense value of the unit
     */
    public int getDef() {
        return statusValue.getDef();
    }

    /**
     * Indicates that this unit is not a Farmer King.
     * @return false, since this unit is not a Farmer King
     */
    @Override
    public boolean isFarmerKing() {
        return false;
    }

    /**
     * Blocks the unit, preventing it from performing any actions until it is unblocked. A blocked unit cannot move, attack, or be attacked.
     */
    public void block() {
        this.blocking = true;
    }

    /**
     * Unblocks the unit, allowing it to perform actions again. An unblocked unit can move, attack, and be attacked as normal.
     */
    public void unblock() {
        this.blocking = false;
    }

}