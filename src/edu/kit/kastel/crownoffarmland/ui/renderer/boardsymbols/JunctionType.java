package edu.kit.kastel.crownoffarmland.ui.renderer.boardsymbols;

/**
 * Enumeration representing the different types of junctions that can occur in a board symbol.
 * Each junction type corresponds to a specific configuration of connections between paths or edges in the board symbol.
 *
 * @author ucgdi
 */
public enum JunctionType {
    /**
     * Represents a junction located at the top left corner of the board symbol, where two paths or edges meet at a right angle.
     */
    TOP_LEFT_CORNER,
    /**
     * Represents a junction located at the top right corner of the board symbol, where two paths or edges meet at a right angle.
     */
    TOP_RIGHT_CORNER,
    /**
     * Represents a junction located at the bottom left corner of the board symbol, where two paths or edges meet at a right angle.
     */
    BOTTOM_LEFT_CORNER,
    /**
     * Represents a junction located at the bottom right corner of the board symbol, where two paths or edges meet at a right angle.
     */
    BOTTOM_RIGHT_CORNER,
    /**
     * Represents a junction located at the top border of the board symbol, where a path or edge meets the top edge of the symbol.
     */
    TOP_BORDER,
    /**
     * Represents a junction located at the right border of the board symbol, where a path or edge meets the right edge of the symbol.
     */
    RIGHT_BORDER,
    /**
     * Represents a junction located at the bottom border of the board symbol, where a path or edge meets the bottom edge of the symbol.
     */
    BOTTOM_BORDER,
    /**
     * Represents a junction located at the left border of the board symbol, where a path or edge meets the left edge of the symbol.
     */
    LEFT_BORDER,
    /**
     * Represents a junction located at the center of the board symbol, where multiple paths or edges meet.
     */
    CENTER;
}
