package edu.kit.kastel.crownoffarmland.ui.renderer.board.boardsymbols;

/**
 * Uses standard symbols to render the board.
 *
 * @author ucgdi
 */
public class StandardBoardSymbolSet extends BoardSymbolSet {
    private static final char HORIZONTAL_ICON = '-';
    private static final char SELECTED_HORIZONTAL_ICON = '=';

    private static final char VERTICAL_ICON = '|';
    private static final char SELECTED_VERTICAL_ICON = 'N';

    private static final char CORNER_ICON = '+';
    private static final char SELECTED_CORNER_ICON = '#';

    /**
     * Creates a new standard board symbol set.
     */
    public StandardBoardSymbolSet() {
        super(HORIZONTAL_ICON, SELECTED_HORIZONTAL_ICON, VERTICAL_ICON, SELECTED_VERTICAL_ICON);
    }

    @Override
    public char getJunctionIcon(JunctionType type, SelectedRelative relative) {
        return relative == SelectedRelative.NONE ? CORNER_ICON : SELECTED_CORNER_ICON;
    }
}