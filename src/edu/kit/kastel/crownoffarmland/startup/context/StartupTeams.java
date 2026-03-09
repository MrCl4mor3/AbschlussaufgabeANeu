package edu.kit.kastel.crownoffarmland.startup.context;
/**
 * Immutable team name configuration during startup.
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
     * @return empty team configuration
     */
    public static StartupTeams empty() {
        return new StartupTeams(null, null);
    }

    /**
     * Returns a team configuration for both teams.
     *
     * @param team1Name the name of team 1
     * @param team2Name the name of team 2
     * @return team configuration for both teams
     */
    public static StartupTeams of(String team1Name, String team2Name) {
        return new StartupTeams(team1Name, team2Name);
    }

    /**
     * Returns the name of team 1.
     *
     * @return team 1 name
     */
    public String getTeam1Name() {
        return team1Name;
    }

    /**
     * Returns the name of team 2.
     *
     * @return team 2 name
     */
    public String getTeam2Name() {
        return team2Name;
    }
}