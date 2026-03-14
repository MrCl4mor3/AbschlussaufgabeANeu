package edu.kit.kastel.crownoffarmland.startup.context;

/**
 * Stores startup team names.
 *
 * @author ucgdi
 */
public final class StartupTeams {
    private final String team1Name;
    private final String team2Name;

    private StartupTeams(String team1Name, String team2Name) {
        this.team1Name = team1Name;
        this.team2Name = team2Name;
    }

    /**
     * Returns an empty team configuration.
     *
     * @return the empty team configuration
     */
    public static StartupTeams empty() {
        return new StartupTeams(null, null);
    }

    /**
     * Returns a team configuration for both teams.
     *
     * @param team1Name the name of team 1
     * @param team2Name the name of team 2
     * @return the team configuration
     */
    public static StartupTeams of(String team1Name, String team2Name) {
        return new StartupTeams(team1Name, team2Name);
    }

    /**
     * Returns the name of team 1.
     *
     * @return the name of team 1
     */
    public String getTeam1Name() {
        return team1Name;
    }

    /**
     * Returns the name of team 2.
     *
     * @return the name of team 2
     */
    public String getTeam2Name() {
        return team2Name;
    }
}