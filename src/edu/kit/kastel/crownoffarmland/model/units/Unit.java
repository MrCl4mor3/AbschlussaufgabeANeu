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

    private boolean blocking;

    public Unit(TeamID teamId, UnitName name, StatusValue statusValue) {
        super(name, teamId, false);
        this.statusValue = statusValue;
    }

    /**
     * Returns whether the unit is currently blocked. A blocked unit cannot perform actions until it is unblocked.
     * @return true if the unit is blocked, false otherwise
     */
    public boolean isBlocked() {
        return blocking;
    }

    /**
     * Reveals the unit, making its name and status values visible to all players.
     * Once revealed, the unit's information cannot be hidden again.
     */
    public void reveale() {
        this.setRevealed(true);
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

}