package edu.kit.kastel.crownoffarmland.model.team;

/**
 * Identifies a team in the game.
 *
 * @author ucgdi
 */
public enum TeamID {
    /**
     * The first team.
     */
    TEAM_1,

    /**
     * The second team.
     */
    TEAM_2;

    private static final TeamID[] VALUES = values();

    /**
     * Returns the next team in turn order.
     *
     * @return the next team
     */
    public TeamID getNext() {
        return VALUES[(this.ordinal() + 1) % VALUES.length];
    }
}