package edu.kit.kastel.crownoffarmland.ui.renderer.boardsymbols;

/**
 * Represents a set of symbols used to render the game board, including icons for horizontal and vertical connections, as well as junctions.
 * This abstract class defines the structure for different symbol sets, allowing for customization of the board's appearance based on the
 * selected theme or style.
 *
 * @author ucgdi
 */
public abstract class BoardSymbolSet {
    private final char horizontalIcon;
    private final char selectedHorizontalIcon;
    private final char verticalIcon;
    private final char selectedVerticalIcon;


    /**
     * Constructs a new BoardSymbolSet with the specified icons for horizontal and vertical connections, as well as their selected states.
     * @param horizontalIcon the character used to represent a horizontal connection between tiles on the board
     * @param selectedHorizontalIcon the character used to represent a selected horizontal connection between tiles on the board,
     *                               indicating that it is currently active or highlighted
     * @param verticalIcon the character used to represent a vertical connection between tiles on the board
     * @param selectedVerticalIcon the character used to represent a selected vertical connection between tiles on the board, indicating
     *                             that it is currently active or highlighted
     */
    public BoardSymbolSet(char horizontalIcon, char selectedHorizontalIcon, char verticalIcon, char selectedVerticalIcon) {
        this.horizontalIcon = horizontalIcon;
        this.selectedHorizontalIcon = selectedHorizontalIcon;
        this.verticalIcon = verticalIcon;
        this.selectedVerticalIcon = selectedVerticalIcon;
    }


    /**
     * Returns the character used to represent a junction on the board, based on the specified junction type and selected relative position.
     * @param type the type of junction, which indicates the configuration of connections at that point (e.g., T-junction, cross-junction)
     * @param relative the relative position of the junction in relation to the selected tile, which can affect how the junction is
     *                 rendered (e.g., whether it is highlighted or not)
     * @return the character used to represent the junction on the board, which may vary based on the junction type and selected relative
     *     position
     */
    public abstract char getJunctionIcon(JunctionType type, SelectedRelative relative);

    /**
     * Returns the character used to represent a horizontal connection on the board, based on whether it is selected or not. The selected
     * state indicates whether the connection is currently active or highlighted, which can affect how it is rendered on the board.
     * @param selected true if the horizontal connection is selected (active or highlighted), false otherwise
     * @return the character used to represent the horizontal connection on the board, which may vary based on whether it is selected or
     *     not
     */
    public char getHorizontalIcon(boolean selected) {
        return selected ? selectedHorizontalIcon : horizontalIcon;
    }

    /**
     * Returns the character used to represent a vertical connection on the board, based on whether it is selected or not. The selected
     * state indicates whether the connection is currently active or highlighted, which can affect how it is rendered on the board.
     * @param selected true if the vertical connection is selected (active or highlighted), false otherwise
     * @return the character used to represent the vertical connection on the board, which may vary based on whether it is selected or not
     */
    public char getVerticalIcon(boolean selected) {
        return selected ? selectedVerticalIcon : verticalIcon;
    }
}
