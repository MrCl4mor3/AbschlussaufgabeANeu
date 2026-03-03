package edu.kit.kastel.crownoffarmland.model.team;

public enum TeamID {
    TEAM_1,
    TEAM_2;


    private static final TeamID[] VALUES = values();

    public TeamID getNext() {
        return VALUES[(this.ordinal() + 1) % VALUES.length];
    }
}
