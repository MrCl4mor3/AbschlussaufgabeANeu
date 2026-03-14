package edu.kit.kastel.crownoffarmland.model.units;

import edu.kit.kastel.crownoffarmland.model.team.TeamID;

/**
 * Represents a unit on the board.
 * A unit has status values and belongs to a team.
 *
 * @author ucgdi
 */
public class Unit extends BoardEntity {
    private final StatusValue statusValue;

    /**
     * Constructs a unit from the given template.
     *
     * @param teamId the owning team
     * @param template the unit template
     */
    public Unit(TeamID teamId, UnitTemplate template) {
        this(teamId, template.getName(), template.getStats());
    }

    /**
     * Constructs a unit with the given name and status values.
     *
     * @param teamId the owning team
     * @param name the unit name
     * @param statusValue the unit status values
     */
    public Unit(TeamID teamId, UnitName name, StatusValue statusValue) {
        super(name, teamId, false);
        this.statusValue = statusValue;
    }

    @Override
    public boolean isFarmerKing() {
        return false;
    }

    /**
     * Returns the attack value of this unit.
     *
     * @return the attack value
     */
    public int getAtk() {
        return statusValue.getAtk();
    }

    /**
     * Returns the defense value of this unit.
     *
     * @return the defense value
     */
    public int getDef() {
        return statusValue.getDef();
    }

    /**
     * Blocks this unit.
     */
    public void block() {
        this.blocking = true;
    }

    /**
     * Unblocks this unit.
     */
    public void unblock() {
        this.blocking = false;
    }
}