package edu.kit.kastel.crownoffarmland.ui.renderer.board;

import edu.kit.kastel.crownoffarmland.model.board.Position;
import edu.kit.kastel.crownoffarmland.startup.config.Verbosity;
import edu.kit.kastel.crownoffarmland.ui.renderer.board.boardsymbols.BoardSymbolSet;
import edu.kit.kastel.crownoffarmland.ui.renderer.board.boardsymbols.JunctionType;
import edu.kit.kastel.crownoffarmland.ui.renderer.board.boardsymbols.SelectedRelative;
import edu.kit.kastel.crownoffarmland.gameplay.snapshots.BoardCellSnapshot;
import edu.kit.kastel.crownoffarmland.gameplay.snapshots.BoardSnapshot;

/**
 * Renders a visual representation of a board snapshot as a string, using a specified set of symbols and formatting rules.
 *
 * @author ucgdi
 */
public class BoardRenderer {
    private static final int CELL_WIDTH = 3;
    private static final int ROW_LABEL_WIDTH = 2;
    private static final int NO_FIELD_SELECTED = -1;
    private static final int FIRST_INDEX = 0;
    private static final int INDEX_OFFSET = 1;
    private static final char SPACE = ' ';
    private static final char START_COLUMN_NAME = 'A';

    private final BoardEntityTokenFormatter tokenFormatter;
    private final BoardSymbolSet symbolSet;
    private final Verbosity verbosity;

    /**
     * Constructs a new BoardRenderer with the specified symbol set, token formatter, and verbosity level.
     *
     * @param symbolSet the BoardSymbolSet to use for rendering the board's borders and junctions
     * @param tokenFormatter the BoardEntityTokenFormatter to use for formatting individual board cells for display
     * @param verbosity the Verbosity level that determines the amount of detail included in the rendered output
     */
    public BoardRenderer(BoardSymbolSet symbolSet, BoardEntityTokenFormatter tokenFormatter, Verbosity verbosity) {
        this.tokenFormatter = tokenFormatter;
        this.symbolSet = symbolSet;
        this.verbosity = verbosity;
    }

    /**
     * Renders the given board snapshot as a string representation.
     *
     * @param snapshot the board snapshot to render
     * @return a string representation of the rendered board
     */
    public String renderBoard(BoardSnapshot snapshot) {
        final int boardSize = snapshot.getBoardSize();
        final Position selected = snapshot.getSelected();
        final int selectedRow = (selected != null) ? boardSize - selected.getRow() : NO_FIELD_SELECTED;
        final int selectedColumn = (selected != null) ? selected.getColumn() - START_COLUMN_NAME : NO_FIELD_SELECTED;

        StringBuilder output = new StringBuilder();

        if (verbosity == Verbosity.ALL) {
            appendConnectionRow(output, FIRST_INDEX, boardSize, selectedRow, selectedColumn);

            for (int rowIndex = FIRST_INDEX; rowIndex < boardSize; rowIndex++) {
                appendFieldRow(output, snapshot, rowIndex, selectedRow, selectedColumn);
                appendConnectionRow(output, rowIndex + INDEX_OFFSET, boardSize, selectedRow, selectedColumn);
            }
        } else {
            for (int rowIndex = FIRST_INDEX; rowIndex < boardSize; rowIndex++) {
                appendFieldRow(output, snapshot, rowIndex, selectedRow, selectedColumn);
            }
        }

        appendColumnLabels(output, boardSize);

        return output.toString();
    }

    private void appendConnectionRow(StringBuilder output, int junctionRow, int boardSize, int selectedRow, int selectedColumn) {
        appendRepeated(output, SPACE, ROW_LABEL_WIDTH);

        for (int junctionColumn = FIRST_INDEX; junctionColumn <= boardSize; junctionColumn++) {
            JunctionType junctionType = determineJunctionType(junctionRow, junctionColumn, boardSize);
            SelectedRelative relative = determineSelectedRelative(junctionRow, junctionColumn, boardSize, selectedRow, selectedColumn);

            output.append(symbolSet.getJunctionIcon(junctionType, relative));

            if (junctionColumn < boardSize) {
                boolean selectedHorizontal = isHorizontalSelected(junctionRow, junctionColumn, selectedRow, selectedColumn);
                appendRepeated(output, symbolSet.getHorizontalIcon(selectedHorizontal), CELL_WIDTH);
            }
        }

        output.append(System.lineSeparator());
    }

    private void appendFieldRow(StringBuilder output, BoardSnapshot snapshot, int rowIndex, int selectedRow, int selectedColumn) {
        final int boardSize = snapshot.getBoardSize();
        output.append(boardSize - rowIndex).append(SPACE);

        for (int boundaryColumn = FIRST_INDEX; boundaryColumn <= boardSize; boundaryColumn++) {
            boolean selectedVertical = isVerticalSelected(rowIndex, boundaryColumn, selectedRow, selectedColumn);
            output.append(symbolSet.getVerticalIcon(selectedVertical));

            if (boundaryColumn < boardSize) {
                BoardCellSnapshot cell = snapshot.getCell(rowIndex, boundaryColumn);
                output.append(tokenFormatter.format(cell));
            }
        }

        output.append(System.lineSeparator());
    }

    private void appendColumnLabels(StringBuilder output, int boardSize) {
        appendRepeated(output, SPACE, ROW_LABEL_WIDTH + INDEX_OFFSET + CELL_WIDTH / 2);

        for (int columnIndex = FIRST_INDEX; columnIndex < boardSize; columnIndex++) {
            if (columnIndex > FIRST_INDEX) {
                appendRepeated(output, SPACE, CELL_WIDTH);
            }
            output.append((char) (START_COLUMN_NAME + columnIndex));
        }
    }

    private void appendRepeated(StringBuilder output, char symbol, int count) {
        output.append(String.valueOf(symbol).repeat(Math.max(FIRST_INDEX, count)));
    }

    private boolean isHorizontalSelected(int junctionRow, int junctionColumn, int selectedRow, int selectedColumn) {
        if (selectedRow == NO_FIELD_SELECTED || selectedColumn == NO_FIELD_SELECTED) {
            return false;
        }
        return junctionColumn == selectedColumn && (junctionRow == selectedRow || junctionRow == selectedRow + INDEX_OFFSET);
    }

    private boolean isVerticalSelected(int junctionRow, int junctionColumn, int selectedRow, int selectedColumn) {
        if (selectedRow == NO_FIELD_SELECTED || selectedColumn == NO_FIELD_SELECTED) {
            return false;
        }
        return junctionRow == selectedRow && (junctionColumn == selectedColumn || junctionColumn == selectedColumn + INDEX_OFFSET);
    }

    private JunctionType determineJunctionType(int junctionRow, int junctionColumn, int boardSize) {
        if (junctionRow == FIRST_INDEX && junctionColumn == FIRST_INDEX) {
            return JunctionType.TOP_LEFT_CORNER;
        } else if (junctionRow == FIRST_INDEX && junctionColumn == boardSize) {
            return JunctionType.TOP_RIGHT_CORNER;
        } else if (junctionRow == boardSize && junctionColumn == FIRST_INDEX) {
            return JunctionType.BOTTOM_LEFT_CORNER;
        } else if (junctionRow == boardSize && junctionColumn == boardSize) {
            return JunctionType.BOTTOM_RIGHT_CORNER;
        } else if (junctionRow == FIRST_INDEX) {
            return JunctionType.TOP_BORDER;
        } else if (junctionRow == boardSize) {
            return JunctionType.BOTTOM_BORDER;
        } else if (junctionColumn == FIRST_INDEX) {
            return JunctionType.LEFT_BORDER;
        } else if (junctionColumn == boardSize) {
            return JunctionType.RIGHT_BORDER;
        } else {
            return JunctionType.CENTER;
        }
    }

    private SelectedRelative determineSelectedRelative(int junctionRow, int junctionColumn, int boardSize,
        int selectedRow, int selectedColumn) {
        if (selectedRow == NO_FIELD_SELECTED || selectedColumn == NO_FIELD_SELECTED) {
            return SelectedRelative.NONE;
        }

        if (isCorner(junctionRow, junctionColumn, boardSize)) {
            return determineCornerRelative(junctionRow, junctionColumn, boardSize, selectedRow, selectedColumn);
        }

        if (junctionRow == FIRST_INDEX || junctionRow == boardSize) {
            return determineHorizontalBorderRelative(junctionRow, junctionColumn, boardSize, selectedRow, selectedColumn);
        }

        if (junctionColumn == FIRST_INDEX || junctionColumn == boardSize) {
            return determineVerticalBorderRelative(junctionRow, junctionColumn, boardSize, selectedRow, selectedColumn);
        }

        return determineCenterRelative(junctionRow, junctionColumn, selectedRow, selectedColumn);
    }

    private boolean isCorner(int junctionRow, int junctionColumn, int boardSize) {
        return (junctionRow == FIRST_INDEX || junctionRow == boardSize)
                && (junctionColumn == FIRST_INDEX || junctionColumn == boardSize);
    }

    private SelectedRelative determineCornerRelative(int junctionRow, int junctionColumn, int boardSize,
        int selectedRow, int selectedColumn) {
        if (junctionRow == FIRST_INDEX && junctionColumn == FIRST_INDEX) {
            return selectedRow == FIRST_INDEX && selectedColumn == FIRST_INDEX
                    ? SelectedRelative.TOP_LEFT : SelectedRelative.NONE;
        }
        if (junctionRow == FIRST_INDEX && junctionColumn == boardSize) {
            return selectedRow == FIRST_INDEX && selectedColumn == boardSize - INDEX_OFFSET
                    ? SelectedRelative.TOP_RIGHT : SelectedRelative.NONE;
        }
        if (junctionRow == boardSize && junctionColumn == FIRST_INDEX) {
            return selectedRow == boardSize - INDEX_OFFSET && selectedColumn == FIRST_INDEX
                    ? SelectedRelative.BOTTOM_LEFT : SelectedRelative.NONE;
        }
        return selectedRow == boardSize - INDEX_OFFSET && selectedColumn == boardSize - INDEX_OFFSET
                ? SelectedRelative.BOTTOM_RIGHT : SelectedRelative.NONE;
    }

    private SelectedRelative determineHorizontalBorderRelative(int junctionRow, int junctionColumn, int boardSize,
        int selectedRow, int selectedColumn) {
        if (junctionRow == FIRST_INDEX) {
            if (selectedRow == FIRST_INDEX && selectedColumn == junctionColumn - INDEX_OFFSET) {
                return SelectedRelative.LEFT;
            }
            if (selectedRow == FIRST_INDEX && selectedColumn == junctionColumn) {
                return SelectedRelative.RIGHT;
            }
            return SelectedRelative.NONE;
        }

        if (selectedRow == boardSize - INDEX_OFFSET && selectedColumn == junctionColumn - INDEX_OFFSET) {
            return SelectedRelative.LEFT;
        }
        if (selectedRow == boardSize - INDEX_OFFSET && selectedColumn == junctionColumn) {
            return SelectedRelative.RIGHT;
        }
        return SelectedRelative.NONE;
    }

    private SelectedRelative determineVerticalBorderRelative(int junctionRow, int junctionColumn, int boardSize,
        int selectedRow, int selectedColumn) {
        if (junctionColumn == FIRST_INDEX) {
            if (selectedColumn == FIRST_INDEX && selectedRow == junctionRow - INDEX_OFFSET) {
                return SelectedRelative.TOP;
            }
            if (selectedColumn == FIRST_INDEX && selectedRow == junctionRow) {
                return SelectedRelative.BOTTOM;
            }
            return SelectedRelative.NONE;
        }

        if (selectedColumn == boardSize - INDEX_OFFSET && selectedRow == junctionRow - INDEX_OFFSET) {
            return SelectedRelative.TOP;
        }
        if (selectedColumn == boardSize - INDEX_OFFSET && selectedRow == junctionRow) {
            return SelectedRelative.BOTTOM;
        }
        return SelectedRelative.NONE;
    }

    private SelectedRelative determineCenterRelative(int junctionRow, int junctionColumn,
        int selectedRow, int selectedColumn) {
        if (selectedRow == junctionRow - INDEX_OFFSET && selectedColumn == junctionColumn - INDEX_OFFSET) {
            return SelectedRelative.TOP_LEFT;
        }
        if (selectedRow == junctionRow - INDEX_OFFSET && selectedColumn == junctionColumn) {
            return SelectedRelative.TOP_RIGHT;
        }
        if (selectedRow == junctionRow && selectedColumn == junctionColumn - INDEX_OFFSET) {
            return SelectedRelative.BOTTOM_LEFT;
        }
        if (selectedRow == junctionRow && selectedColumn == junctionColumn) {
            return SelectedRelative.BOTTOM_RIGHT;
        }
        return SelectedRelative.NONE;
    }
}