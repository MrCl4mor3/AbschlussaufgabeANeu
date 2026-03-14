package edu.kit.kastel.crownoffarmland.gameplay.snapshots;

/**
 * Represents the result of ending a turn.
 *
 * @author ucgdi
 */
public final class EndTurnSnapshot {
    private final EntitySnapshot discardedCard;
    private final String nextTeamName;
    private final boolean isGameOver;

    /**
     * Creates a new end turn snapshot.
     *
     * @param discardedCard the discarded card, or {@code null} if no card was discarded
     * @param nextTeamName the name of the next team
     * @param isGameOver whether the game is over
     */
    public EndTurnSnapshot(EntitySnapshot discardedCard, String nextTeamName, boolean isGameOver) {
        this.discardedCard = discardedCard;
        this.nextTeamName = nextTeamName;
        this.isGameOver = isGameOver;
    }

    /**
     * Returns the discarded card.
     *
     * @return the discarded card, or {@code null} if no card was discarded
     */
    public EntitySnapshot getDiscardedCard() {
        return discardedCard;
    }

    /**
     * Returns the name of the next team.
     *
     * @return the next team name
     */
    public String getNextTeamName() {
        return nextTeamName;
    }

    /**
     * Returns whether the game is over.
     *
     * @return {@code true} if the game is over
     */
    public boolean isGameOver() {
        return isGameOver;
    }
}