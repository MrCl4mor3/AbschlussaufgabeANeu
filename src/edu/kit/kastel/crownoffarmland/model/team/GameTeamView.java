package edu.kit.kastel.crownoffarmland.model.team;

/**
 * Provides read-only access to a team's state.
 *
 * @author ucgdi
 */
public interface GameTeamView {
    /**
     * Returns the name of the team.
     *
     * @return the team name
     */
    String getName();

    /**
     * Returns the current life points of the team.
     *
     * @return the current life points
     */
    int getLifePoints();

    /**
     * Returns the number of cards currently in the hand.
     *
     * @return the current hand size
     */
    int getHandSize();

    /**
     * Checks whether the team's hand is full.
     *
     * @return {@code true} if the hand is full, otherwise {@code false}
     */
    boolean isHandFull();

    /**
     * Returns the number of cards currently in the draw pile.
     *
     * @return the current draw pile size
     */
    int getDrawPileSize();

    /**
     * Returns the initial size of the draw pile.
     *
     * @return the initial draw pile size
     */
    int getStartDeckSize();

    /**
     * Checks whether the draw pile is empty.
     *
     * @return {@code true} if the draw pile is empty, otherwise {@code false}
     */
    boolean isDrawPileEmpty();

    /**
     * Returns the maximum number of units the team may have on the board at the same time.
     *
     * @return the maximum number of units on the board
     */
    int getMaxUnitsOnBoard();
}