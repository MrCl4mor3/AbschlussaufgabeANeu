package edu.kit.kastel.crownoffarmland.model.team;

/**
 * Enum representing the different teams in the game.
 * Each team has a method to get the next team, allowing for easy switching between teams during gameplay.
 * This design allows for scalability if more teams are added in the future, as the getNext method will automatically cycle through all
 * available teams without needing to modify the logic for switching teams.
 *
 * @author ucgdi
 */
public enum TeamID {
    TEAM_1,
    TEAM_2;


    private static final TeamID[] VALUES = values();

    /**
     * Returns the next team in the sequence. If the current team is the last one, it wraps around to the first team.
     * This method is useful for switching turns between teams during gameplay.
     *
     * @return the next TeamID in the sequence
     */
    public TeamID getNext() {
        return VALUES[(this.ordinal() + 1) % VALUES.length];
    }
}
