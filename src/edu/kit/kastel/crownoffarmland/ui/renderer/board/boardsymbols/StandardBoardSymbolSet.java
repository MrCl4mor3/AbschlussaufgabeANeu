package edu.kit.kastel.crownoffarmland.ui.renderer.board.boardsymbols;

/**
 * A standard implementation of {@link BoardSymbolSet} that uses simple ASCII characters to represent the board symbols.
 * This implementation provides a clear and straightforward visual representation of the board, making it easy to understand and use.
 *
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
     * Constructs a new StandardBoardSymbolSet with predefined ASCII characters for horizontal, vertical, and junction icons.
     */
    public StandardBoardSymbolSet() {
        super(HORIZONTAL_ICON, SELECTED_HORIZONTAL_ICON, VERTICAL_ICON, SELECTED_VERTICAL_ICON);
    }

    @Override
    public char getJunctionIcon(JunctionType type, SelectedRelative relative) {
        return (relative == SelectedRelative.NONE) ? CORNER_ICON : SELECTED_CORNER_ICON;
    }
}
