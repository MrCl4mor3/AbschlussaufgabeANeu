package edu.kit.kastel.crownoffarmland.model.units;

import edu.kit.kastel.crownoffarmland.model.team.TeamID;

public class Unit extends BoardEntity {
    private final StatusValue statusValue;

    private boolean blocking;

    public Unit(TeamID teamId, UnitName name, StatusValue statusValue) {
        super(name, teamId, false);
        this.statusValue = statusValue;
    }

    public boolean isBlocked() {
        return blocking;
    }

    public void reveale() {
        this.setRevealed(true);
    }

    public int getAtk() {
        return statusValue.getAtk();
    }

    public int getDef() {
        return statusValue.getDef();
    }

    @Override
    public boolean isFarmerKing() {
        return false;
    }

}