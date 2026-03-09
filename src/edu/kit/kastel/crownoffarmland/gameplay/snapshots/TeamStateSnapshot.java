package edu.kit.kastel.crownoffarmland.gameplay.snapshots;

/**
 * Snapshot of a team's state at a specific point in time during the game.
 * This class is immutable and provides information about the team's name,
 * life points, remaining deck cards, and placed units.
 *
 * @author ucgdi
 */
public final class TeamStateSnapshot {
    private final String teamName;
    private final int lifePoints;
    private final int remainingDeckCards;
    private final int placedUnits;


    /**
     * Creates a new TeamStateSnapshot with the specified team name, life points, remaining deck cards, and placed units.
     * @param teamName the name of the team
     * @param lifePoints the current life points of the team
     * @param remainingDeckCards the number of cards remaining in the team's deck
     * @param placedUnits the number of units currently placed on the board by the team
     */
    public TeamStateSnapshot(String teamName, int lifePoints, int remainingDeckCards, int placedUnits) {
        this.teamName = teamName;
        this.lifePoints = lifePoints;
        this.remainingDeckCards = remainingDeckCards;
        this.placedUnits = placedUnits;
    }

    /**
     * Returns the name of the team.
     * @return the team name
     */
    public String getTeamName() {
        return teamName;
    }

    /**
     * Returns the current life points of the team.
     * @return the life points
     */
    public int getLifePoints() {
        return lifePoints;
    }

    /**
     * Returns the number of cards remaining in the team's deck.
     * @return the remaining deck cards
     */
    public int getRemainingDeckCards() {
        return remainingDeckCards;
    }

    /**
     * Returns the number of units currently placed on the board by the team.
     * @return the placed units
     */
    public int getPlacedUnits() {
        return placedUnits;
    }
}
