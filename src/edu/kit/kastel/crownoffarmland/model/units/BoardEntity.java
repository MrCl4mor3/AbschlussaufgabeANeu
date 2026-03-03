package edu.kit.kastel.crownoffarmland.model.units;

import edu.kit.kastel.crownoffarmland.model.team.TeamID;

public abstract class BoardEntity {
    private final UnitName name;
    private final TeamID teamId;
    private boolean revealed;


    protected BoardEntity(UnitName name, TeamID teamId, boolean revealed) {
        this.name = name;
        this.teamId = teamId;
        this.revealed = revealed;
    }

    public TeamID getTeamId() { return teamId; }
    public boolean isRevealed() { return revealed; }
    public UnitName getName() { return name; }
    public String getRole() {
        return name.getRole();
    }
    public String getQualificator() {
        return name.getQualificator();
    }

    protected void setRevealed(boolean revealed) { this.revealed = revealed; }


    public abstract boolean isFarmerKing();

}