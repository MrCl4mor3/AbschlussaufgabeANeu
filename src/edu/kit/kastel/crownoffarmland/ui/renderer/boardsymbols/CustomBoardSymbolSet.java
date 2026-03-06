package edu.kit.kastel.crownoffarmland.ui.renderer.boardsymbols;

/**
 * A custom implementation of {@link BoardSymbolSet} that allows for a flexible set of symbols to be used for rendering the board.
 * The symbols are provided as a single string, where each character corresponds to a specific junction type and selection state.
 * The mapping of symbols to junction types and selection states is defined by the indices in the string.
 * This class overrides the {@link #getJunctionIcon(JunctionType, SelectedRelative)} method to return the appropriate symbol based on the
 * junction type and selection state.
 *
 * @author ucgdi
 */
public class CustomBoardSymbolSet extends BoardSymbolSet {

    private static final int IDX_TL_CORNER = 0;
    private static final int IDX_TR_CORNER = 1;
    private static final int IDX_BL_CORNER = 2;
    private static final int IDX_BR_CORNER = 3;

    private static final int IDX_TOP_BORDER = 4;
    private static final int IDX_RIGHT_BORDER = 5;
    private static final int IDX_BOTTOM_BORDER = 6;
    private static final int IDX_LEFT_BORDER = 7;

    private static final int IDX_HORIZONTAL = 8;
    private static final int IDX_VERTICAL = 9;

    private static final int IDX_CENTER = 10;

    private static final int IDX_TL_CORNER_SELECTED = 11;
    private static final int IDX_TR_CORNER_SELECTED = 12;
    private static final int IDX_BL_CORNER_SELECTED = 13;
    private static final int IDX_BR_CORNER_SELECTED = 14;


    private static final int IDX_TOP_BORDER_SELECTED_LEFT = 15;
    private static final int IDX_TOP_BORDER_SELECTED_RIGHT = 16;

    private static final int IDX_RIGHT_BORDER_SELECTED_TOP = 17;
    private static final int IDX_RIGHT_BORDER_SELECTED_BOTTOM = 18;

    private static final int IDX_BOTTOM_BORDER_SELECTED_LEFT = 19;
    private static final int IDX_BOTTOM_BORDER_SELECTED_RIGHT = 20;

    private static final int IDX_LEFT_BORDER_SELECTED_TOP = 21;
    private static final int IDX_LEFT_BORDER_SELECTED_BOTTOM = 22;

    private static final int IDX_HORIZONTAL_SELECTED = 23;
    private static final int IDX_VERTICAL_SELECTED = 24;

    private static final int IDX_CENTER_SELECTED_TL = 25;
    private static final int IDX_CENTER_SELECTED_TR = 26;
    private static final int IDX_CENTER_SELECTED_BL = 27;
    private static final int IDX_CENTER_SELECTED_BR = 28;


    private final char[] symbols;


    /**
     * Constructs a new {@code CustomBoardSymbolSet} with the specified symbols.
     *
     * @param symbolsLine a string containing the symbols for the board, where each character corresponds to a specific junction type and
     *                   selection state
     */
    public CustomBoardSymbolSet(String symbolsLine) {
        super(extractSymbol(symbolsLine, IDX_HORIZONTAL), extractSymbol(symbolsLine, IDX_HORIZONTAL_SELECTED), extractSymbol(symbolsLine,
                IDX_VERTICAL), extractSymbol(symbolsLine, IDX_VERTICAL_SELECTED));
        this.symbols = symbolsLine.toCharArray();
    }

    private static char extractSymbol(String symbols, int index) {
        return symbols.charAt(index);
    }

    @Override
    public char getJunctionIcon(JunctionType type, SelectedRelative relative) {
        return switch (type) {
            case TOP_LEFT_CORNER ->
                (relative == SelectedRelative.NONE) ? symbols[IDX_TL_CORNER] : symbols[IDX_TL_CORNER_SELECTED];
            case TOP_RIGHT_CORNER ->
                (relative == SelectedRelative.NONE) ? symbols[IDX_TR_CORNER] : symbols[IDX_TR_CORNER_SELECTED];
            case BOTTOM_LEFT_CORNER ->
                (relative == SelectedRelative.NONE) ? symbols[IDX_BL_CORNER] : symbols[IDX_BL_CORNER_SELECTED];
            case BOTTOM_RIGHT_CORNER ->
                (relative == SelectedRelative.NONE) ? symbols[IDX_BR_CORNER] : symbols[IDX_BR_CORNER_SELECTED];
            case TOP_BORDER -> {
                if (relative == SelectedRelative.LEFT) {
                    yield symbols[IDX_TOP_BORDER_SELECTED_LEFT];
                }
                if (relative == SelectedRelative.RIGHT) {
                    yield symbols[IDX_TOP_BORDER_SELECTED_RIGHT];
                }
                yield symbols[IDX_TOP_BORDER];
            }
            case RIGHT_BORDER -> {
                if (relative == SelectedRelative.TOP) {
                    yield symbols[IDX_RIGHT_BORDER_SELECTED_TOP];
                }
                if (relative == SelectedRelative.BOTTOM) {
                    yield symbols[IDX_RIGHT_BORDER_SELECTED_BOTTOM];
                }
                yield symbols[IDX_RIGHT_BORDER];
            }
            case BOTTOM_BORDER -> {
                if (relative == SelectedRelative.LEFT) {
                    yield symbols[IDX_BOTTOM_BORDER_SELECTED_LEFT];
                }
                if (relative == SelectedRelative.RIGHT) {
                    yield symbols[IDX_BOTTOM_BORDER_SELECTED_RIGHT];
                }
                yield symbols[IDX_BOTTOM_BORDER];
            }
            case LEFT_BORDER -> {
                if (relative == SelectedRelative.TOP) {
                    yield symbols[IDX_LEFT_BORDER_SELECTED_TOP];
                }
                if (relative == SelectedRelative.BOTTOM) {
                    yield symbols[IDX_LEFT_BORDER_SELECTED_BOTTOM];
                }
                yield symbols[IDX_LEFT_BORDER];
            }
            case CENTER -> {
                if (relative == SelectedRelative.TOP_LEFT) {
                    yield symbols[IDX_CENTER_SELECTED_TL];
                }
                if (relative == SelectedRelative.TOP_RIGHT) {
                    yield symbols[IDX_CENTER_SELECTED_TR];
                }
                if (relative == SelectedRelative.BOTTOM_LEFT) {
                    yield symbols[IDX_CENTER_SELECTED_BL];
                }
                if (relative == SelectedRelative.BOTTOM_RIGHT) {
                    yield symbols[IDX_CENTER_SELECTED_BR];
                }
                yield symbols[IDX_CENTER];
            }
        };
    }
}
