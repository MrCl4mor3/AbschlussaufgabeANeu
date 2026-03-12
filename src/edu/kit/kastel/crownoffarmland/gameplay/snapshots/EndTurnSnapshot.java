package edu.kit.kastel.crownoffarmland.gameplay.snapshots;

/**
 * A Snapshot.
 *
 * @author ucgdi
 */
public final class EndTurnSnapshot {
    private final EntitySnapshot discardedCard;
    private final String nextTeamName;
    private final boolean isGameOver;

    /**
     * A new Instance.
     * @param discardedCard the discardedCard
     * @param nextTeamName the NextTeamName
     * @param isGameOver True, if a Winner ist set, false otherwise
     */
    public EndTurnSnapshot(EntitySnapshot discardedCard, String nextTeamName, boolean isGameOver) {
        this.discardedCard = discardedCard;
        this.nextTeamName = nextTeamName;
        this.isGameOver = isGameOver;
    }

    /**
     * Getter for the DiscardedCard.
     * @return the EntitySnapshot of the discardedCard, or null if no card was discarded
     */
    public EntitySnapshot getDiscardedCard() {
        return discardedCard;
    }

    /**
     * Getter for the NextTeamName.
     * @return a String
     */
    public String getNextTeamName() {
        return nextTeamName;
    }

    /**
     * Getter for the isGameOver.
     * @return true, if a Winner is set, false otherwise
     */
    public boolean isGameOver() {
        return isGameOver;
    }
}
