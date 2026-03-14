package edu.kit.kastel.crownoffarmland.gameplay.snapshots;

/**
 * Represents a snapshot of a team's state.
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
     * Creates a new team state snapshot.
     *
     * @param teamName the team name
     * @param lifePoints the current life points
     * @param remainingDeckCards the remaining deck cards
     * @param placedUnits the number of placed units
     * @param startDeckSize the initial deck size
     * @param maxUnitsOnBoard the maximum number of units on the board
     */
    public TeamStateSnapshot(String teamName, int lifePoints, int remainingDeckCards, int placedUnits,
            int startDeckSize, int maxUnitsOnBoard) {
        this.teamName = teamName;
        this.lifePoints = lifePoints;
        this.remainingDeckCards = remainingDeckCards;
        this.placedUnits = placedUnits;
        this.startDeckSize = startDeckSize;
        this.maxUnitsOnBoard = maxUnitsOnBoard;
    }

    /**
     * Returns the team name.
     *
     * @return the team name
     */
    public String getTeamName() {
        return teamName;
    }

    /**
     * Returns the current life points.
     *
     * @return the life points
     */
    public int getLifePoints() {
        return lifePoints;
    }

    /**
     * Returns the number of remaining deck cards.
     *
     * @return the remaining deck cards
     */
    public int getRemainingDeckCards() {
        return remainingDeckCards;
    }

    /**
     * Returns the number of placed units.
     *
     * @return the number of placed units
     */
    public int getPlacedUnits() {
        return placedUnits;
    }

    /**
     * Returns the initial deck size.
     *
     * @return the initial deck size
     */
    public int getStartDeckSize() {
        return startDeckSize;
    }

    /**
     * Returns the maximum number of units on the board.
     *
     * @return the maximum number of units on the board
     */
    public int getMaxUnitsOnBoard() {
        return maxUnitsOnBoard;
    }
}