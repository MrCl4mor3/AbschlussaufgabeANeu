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
    private final int startDeckSize;
    private final int maxUnitsOnBoard;


    /**
     * Creates a new TeamStateSnapshot with the specified team name, life points, remaining deck cards, and placed units.
     * @param teamName the name of the team
     * @param lifePoints the current life points of the team
     * @param remainingDeckCards the number of cards remaining in the team's deck
     * @param placedUnits the number of units currently placed on the board by the team
     * @param startDeckSize the startDeck Size
     * @param maxUnitsOnBoard the Number of Units of one team, that can max. at the same time on the board
     */
    public TeamStateSnapshot(String teamName, int lifePoints, int remainingDeckCards, int placedUnits, int startDeckSize,
        int maxUnitsOnBoard) {
        this.teamName = teamName;
        this.lifePoints = lifePoints;
        this.remainingDeckCards = remainingDeckCards;
        this.placedUnits = placedUnits;
        this.startDeckSize = startDeckSize;
        this.maxUnitsOnBoard = maxUnitsOnBoard;
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

    /**
     * Getter for the maximal number of cards that can be in the team's deck at the start of the game.
     * @return The number of maximal deck cards for the team.
     */
    public int getStartDeckSize() {
        return startDeckSize;
    }

    /**
     * Getter for the maximal number of cards that can be on the board at the same time.
     * @return the number of max. units on board for the team.
     */
    public int getMaxUnitsOnBoard() {
        return maxUnitsOnBoard;
    }
}
