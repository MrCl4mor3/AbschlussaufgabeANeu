package edu.kit.kastel.crownoffarmland.ui.renderer.board.boardsymbols;

/**
 * Defines the symbols used to render the board.
 *
 * @author ucgdi
 */
public abstract class BoardSymbolSet {
    private final char horizontalIcon;
    private final char selectedHorizontalIcon;
    private final char verticalIcon;
    private final char selectedVerticalIcon;

    /**
     * Creates a new board symbol set.
     *
     * @param horizontalIcon the horizontal icon
     * @param selectedHorizontalIcon the selected horizontal icon
     * @param verticalIcon the vertical icon
     * @param selectedVerticalIcon the selected vertical icon
     */
    public BoardSymbolSet(char horizontalIcon, char selectedHorizontalIcon, char verticalIcon,
            char selectedVerticalIcon) {
        this.horizontalIcon = horizontalIcon;
        this.selectedHorizontalIcon = selectedHorizontalIcon;
        this.verticalIcon = verticalIcon;
        this.selectedVerticalIcon = selectedVerticalIcon;
    }

    /**
     * Returns the icon for the given junction.
     *
     * @param type the junction type
     * @param relative the position relative to the selection
     * @return the junction icon
     */
    public abstract char getJunctionIcon(JunctionType type, SelectedRelative relative);

    /**
     * Returns the horizontal icon.
     *
     * @param selected whether the icon is selected
     * @return the horizontal icon
     */
    public char getHorizontalIcon(boolean selected) {
        return selected ? selectedHorizontalIcon : horizontalIcon;
    }

    /**
     * Returns the vertical icon.
     *
     * @param selected whether the icon is selected
     * @return the vertical icon
     */
    public char getVerticalIcon(boolean selected) {
        return selected ? selectedVerticalIcon : verticalIcon;
    }
}