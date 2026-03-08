package edu.kit.kastel.crownoffarmland.ui.renderer.board.boardsymbols;

/**
 * Enum representing the relative position of a selected symbol to the current Field.
 *
 *
 * @author ucgdi
 */
public enum SelectedRelative {
    /**
     * The symbol is not selected or has no relative position to the current Field.
     */
    NONE,
    /**
     * The symbol is left of the current Field.
     */
    LEFT,
    /**
     * The symbol is right of the current Field.
     */
    RIGHT,
    /**
     * The symbol is above the current Field.
     */
    TOP,
    /**
     * The symbol is below the current Field.
     */
    BOTTOM,
    /**
     * The symbol is at the top left of the current Field. (diagonal)
     */
    TOP_LEFT,
    /**
     * The symbol is at the top right of the current Field. (diagonal)
     */
    TOP_RIGHT,
    /**
     * The symbol is at the bottom left of the current Field. (diagonal)
     */
    BOTTOM_LEFT,
    /**
     * The symbol is at the bottom right of the current Field. (diagonal)
     */
    BOTTOM_RIGHT;
}
