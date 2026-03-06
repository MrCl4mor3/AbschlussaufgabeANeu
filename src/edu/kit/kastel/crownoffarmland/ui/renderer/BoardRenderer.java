package edu.kit.kastel.crownoffarmland.ui.renderer;


import edu.kit.kastel.crownoffarmland.model.board.Board;
import edu.kit.kastel.crownoffarmland.model.board.Position;
import edu.kit.kastel.crownoffarmland.model.team.TeamID;
import edu.kit.kastel.crownoffarmland.model.units.BoardEntity;
import edu.kit.kastel.crownoffarmland.startup.config.Verbosity;
import edu.kit.kastel.crownoffarmland.ui.renderer.boardsymbols.BoardSymbolSet;
import edu.kit.kastel.crownoffarmland.ui.renderer.boardsymbols.JunctionType;
import edu.kit.kastel.crownoffarmland.ui.renderer.boardsymbols.SelectedRelative;

import java.util.Set;

/**
 * Renders a visual representation of the game board as a string, using a specified set of symbols and formatting rules. The renderer
 * takes into account the current state of the board, including the positions of entities, the selected field, and the current team, to
 * create an accurate and informative display of the game state.
 *
 * @author ucgdi
 */
public class BoardRenderer {

    private static final int CELL_WIDTH = 3;
    private static final int ROW_LABEL_WIDTH = 2;
    private static final int NO_FIELD_SELECTED = -1;
    private static final char SPACE = ' ';

    private final BoardEntityTokenFormatter tokenFormatter;
    private final BoardSymbolSet symbolSet;
    private final Verbosity verbosity;

    /**
     * Constructs a new BoardRenderer with the specified symbol set, token formatter, and verbosity level. The symbol set defines the visual
     * representation of the board's borders and junctions, while the token formatter determines how individual board entities are
     * displayed. The verbosity level controls the amount of detail included in the rendered output, allowing for different levels of
     * information to be shown based on user preferences or game settings.
     * @param symbolSet the BoardSymbolSet to use for rendering the board's borders and junctions
     * @param tokenFormatter the BoardEntityTokenFormatter to use for formatting individual board entities for display
     * @param verbosity the Verbosity level that determines the amount of detail included in the rendered output, affecting how much
     *                  information about the board's state is shown
     */
    public BoardRenderer(BoardSymbolSet symbolSet, BoardEntityTokenFormatter tokenFormatter, Verbosity verbosity) {
        this.tokenFormatter = tokenFormatter;
        this.symbolSet = symbolSet;
        this.verbosity = verbosity;
    }


    /**
     * Renders the game board as a string representation, taking into account the current state of the board, the selected field, the
     * current team, and the set of moveable units. The method constructs a visual representation of the board by iterating through the
     * rows and columns, appending the appropriate symbols for borders, junctions, and entities based on their positions and states. The
     * rendered output includes row labels, column labels, and visual cues for selected fields and moveable units, providing a clear and
     * informative display of the game state.
     * @param board the Board to render, containing the current state of the game, including the positions of entities and the layout of
     *              the board
     * @param selected the Position of the currently selected field on the board, which is used to determine how to visually highlight
     *                 the selected area in
     * @param currentTeam the TeamID of the current team, which is used to determine how to format entities on the board based on their
     *                    team affiliation
     * @param moveableUnits a Set of BoardEntity objects that are currently moveable, which is used to determine how to visually indicate
     *                     moveable units on the
     * @return a String representation of the rendered board, including visual symbols for borders, junctions, entities, and labels,
     *     providing a clear and informative display of the game state based on the current board configuration, selected field, current
     *     team, and moveable units
     */
    public String renderBoard(Board board, Position selected, TeamID currentTeam, Set<BoardEntity> moveableUnits) {
        final int boardSize = board.getBoardSize();
        final int selectedRow = (selected != null) ? board.rowIndex(selected) : NO_FIELD_SELECTED;
        final int selectedColumn = (selected != null) ? board.columnIndex(selected) : NO_FIELD_SELECTED;

        StringBuilder output = new StringBuilder();

        if (verbosity == Verbosity.ALL) {
            appendConnectionRow(output, 0, boardSize, selectedRow, selectedColumn);

            for (int rowIndex = 0; rowIndex < boardSize; rowIndex++) {
                appendFieldRow(output, board, rowIndex, selectedRow, selectedColumn, currentTeam, moveableUnits);
                appendConnectionRow(output, rowIndex + 1, boardSize, selectedRow, selectedColumn);
            }
        } else {
            for (int rowIndex = 0; rowIndex < boardSize; rowIndex++) {
                appendFieldRow(output, board, rowIndex, selectedRow, selectedColumn, currentTeam, moveableUnits);
            }
        }

        appendColumnLabels(output, board);

        return output.toString();
    }


    private void appendConnectionRow(StringBuilder output, int junctionRow, int boardSize, int selectedRow, int selectedColumn) {
        appendRepeated(output, SPACE, ROW_LABEL_WIDTH);
        for (int junctionColumn = 0; junctionColumn <= boardSize; junctionColumn++) {
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

    private void appendFieldRow(StringBuilder output, Board board, int rowIndex, int selectedRow, int selectedColumn, TeamID currentTeam,
                                Set<BoardEntity> moveableUnits) {
        output.append(board.getBoardSize() - rowIndex).append(SPACE);

        for (int boundaryCoulumn = 0; boundaryCoulumn <= board.getBoardSize(); boundaryCoulumn++) {
            boolean selectedVertical = isVerticalSelected(rowIndex, boundaryCoulumn, selectedRow, selectedColumn);
            output.append(symbolSet.getVerticalIcon(selectedVertical));

            if (boundaryCoulumn < board.getBoardSize()) {
                Position position = board.getPositionAt(rowIndex, boundaryCoulumn);
                BoardEntity entity = board.getOccupant(position);
                boolean moveable = entity != null && moveableUnits.contains(entity);

                output.append(tokenFormatter.format(entity, currentTeam, moveable));
            }
        }
        output.append(System.lineSeparator());
    }



    private void appendColumnLabels(StringBuilder output, Board board) {
        appendRepeated(output, SPACE, ROW_LABEL_WIDTH + 1 + CELL_WIDTH / 2);

        for (int columnIndex = 0; columnIndex < board.getBoardSize(); columnIndex++) {
            if (columnIndex > 0) {
                appendRepeated(output, SPACE, CELL_WIDTH);
            }
            output.append((char) (board.getStartColumnName() + columnIndex));
        }
    }

    private void appendRepeated(StringBuilder output, char symbol, int count) {
        output.append(String.valueOf(symbol).repeat(Math.max(0, count)));
    }

    private boolean isHorizontalSelected(int junctionRow, int junctionColumn, int selectedRow, int selectedColumn) {
        if (selectedRow == NO_FIELD_SELECTED || selectedColumn == NO_FIELD_SELECTED) {
            return false;
        }
        return junctionColumn == selectedColumn && (junctionRow == selectedRow || junctionRow == selectedRow + 1);
    }

    private boolean isVerticalSelected(int junctionRow, int junctionColumn, int selectedRow, int selectedColumn) {
        if (selectedRow == NO_FIELD_SELECTED || selectedColumn == NO_FIELD_SELECTED) {
            return false;
        }
        return junctionRow == selectedRow && (junctionColumn == selectedColumn || junctionColumn == selectedColumn + 1);
    }

    private JunctionType determineJunctionType(int junctionRow, int junctionColumn, int boardSize) {
        if (junctionRow == 0 && junctionColumn == 0) {
            return JunctionType.TOP_LEFT_CORNER;
        } else if (junctionRow == 0 && junctionColumn == boardSize) {
            return JunctionType.TOP_RIGHT_CORNER;
        } else if (junctionRow == boardSize && junctionColumn == 0) {
            return JunctionType.BOTTOM_LEFT_CORNER;
        } else if (junctionRow == boardSize && junctionColumn == boardSize) {
            return JunctionType.BOTTOM_RIGHT_CORNER;
        } else if (junctionRow == 0) {
            return JunctionType.TOP_BORDER;
        } else if (junctionRow == boardSize) {
            return JunctionType.BOTTOM_BORDER;
        } else if (junctionColumn == 0) {
            return JunctionType.LEFT_BORDER;
        } else if (junctionColumn == boardSize) {
            return JunctionType.RIGHT_BORDER;
        } else {
            return JunctionType.CENTER;
        }
    }

    private SelectedRelative determineSelectedRelative(int junctionRow, int junctionColumn, int boardSize, int selectedRow, int selectedColumn) {
        if (selectedRow == NO_FIELD_SELECTED || selectedColumn == NO_FIELD_SELECTED) {
            return SelectedRelative.NONE;
        }

        if (junctionRow == 0 && junctionColumn == 0) {
            return selectedRow == 0 && selectedColumn == 0
                    ? SelectedRelative.TOP_LEFT : SelectedRelative.NONE;
        }
        if (junctionRow == 0 && junctionColumn == boardSize) {
            return selectedRow == 0 && selectedColumn == boardSize - 1
                    ? SelectedRelative.TOP_RIGHT : SelectedRelative.NONE;
        }
        if (junctionRow == boardSize && junctionColumn == 0) {
            return selectedRow == boardSize - 1 && selectedColumn == 0
                    ? SelectedRelative.BOTTOM_LEFT : SelectedRelative.NONE;
        }
        if (junctionRow == boardSize && junctionColumn == boardSize) {
            return selectedRow == boardSize - 1 && selectedColumn == boardSize - 1
                    ? SelectedRelative.BOTTOM_RIGHT : SelectedRelative.NONE;
        }

        if (junctionRow == 0) {
            if (selectedRow == 0 && selectedColumn == junctionColumn - 1) {
                return SelectedRelative.LEFT;
            }
            if (selectedRow == 0 && selectedColumn == junctionColumn) {
                return SelectedRelative.RIGHT;
            }
            return SelectedRelative.NONE;
        }

        if (junctionRow == boardSize) {
            if (selectedRow == boardSize - 1 && selectedColumn == junctionColumn - 1) {
                return SelectedRelative.LEFT;
            }
            if (selectedRow == boardSize - 1 && selectedColumn == junctionColumn) {
                return SelectedRelative.RIGHT;
            }
            return SelectedRelative.NONE;
        }

        if (junctionColumn == 0) {
            if (selectedColumn == 0 && selectedRow == junctionRow - 1) {
                return SelectedRelative.TOP;
            }
            if (selectedColumn == 0 && selectedRow == junctionRow) {
                return SelectedRelative.BOTTOM;
            }
            return SelectedRelative.NONE;
        }

        if (junctionColumn == boardSize) {
            if (selectedColumn == boardSize - 1 && selectedRow == junctionRow - 1) {
                return SelectedRelative.TOP;
            }
            if (selectedColumn == boardSize - 1 && selectedRow == junctionRow) {
                return SelectedRelative.BOTTOM;
            }
            return SelectedRelative.NONE;
        }

        if (selectedRow == junctionRow - 1 && selectedColumn == junctionColumn - 1) {
            return SelectedRelative.TOP_LEFT;
        }
        if (selectedRow == junctionRow - 1 && selectedColumn == junctionColumn) {
            return SelectedRelative.TOP_RIGHT;
        }
        if (selectedRow == junctionRow && selectedColumn == junctionColumn - 1) {
            return SelectedRelative.BOTTOM_LEFT;
        }
        if (selectedRow == junctionRow && selectedColumn == junctionColumn) {
            return SelectedRelative.BOTTOM_RIGHT;
        }

        return SelectedRelative.NONE;
    }
}