package edu.kit.kastel.crownoffarmland.model.team;


/**
 * Interface.
 *
 * @author ucgdi
 */
public interface GameTeamView {
    /**
     * Getter for the name of a team.
     * @return the Name of the selected team.
     */
    String getName();

    /**
     * Getter for Life Points.
     * @return The current Life Points of the selected team.
     */
    int getLifePoints();

    /**
     * Getter for the HandSize.
     * @return the current Size of the hand.
     */
    int getHandSize();

    /**
     * Check if the hand is full.
     * @return true, if the hand is full, false otherwise
     */
    boolean isHandFull();

    /**
     * Getter for the Size of the DrawPile.
     * @return The Size of the Draw Pile
     */
    int getDrawPileSize();

    /**
     * Getter for the Start Size of the DrawPile.
     * @return the size of the DrawPile
     */
    int getStartDeckSize();

    /**
     * Check if the Draw Pile is empty.
     * @return true, if the DrawPile is empty, false otherwise
     */
    boolean isDrawPileEmpty();

    /**
     * Getter for the maximal Number of Units that can place at the same time on the board.
     * @return The MAximal Number of Units that can be placed at the same time on the board.
     */
    int getMaxUnitsOnBoard();
}

