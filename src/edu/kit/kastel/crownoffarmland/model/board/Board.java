package edu.kit.kastel.crownoffarmland.model.board;

import edu.kit.kastel.crownoffarmland.model.units.BoardEntity;

/**
 * Represents the game board, which consists of a grid of fields. Each field can be occupied by a BoardEntity (e.g., a unit).
 * The board provides methods to access and manipulate the fields and their occupants.
 *
 *
 * @author ucgdi
 */
public class Board {
    private static final int BOARD_SIZE = 7;
    private static final char START_COLUMN_NAME = 'A';
    private final Field[][] grid;

    /**
     * Constructs a new Board and initializes its fields with their corresponding positions.
     */
    public Board() {
        grid = new Field[BOARD_SIZE][BOARD_SIZE];
        initializeFields();
    }

    /**
     * Initializes the fields of the board with their corresponding positions. The columns are named from 'A' to 'G', and the rows are
     * numbered from 1 to 7, with (1, 'A') being the bottom-left corner of the board and (7, 'G') being the top-right corner.
     */
    private void initializeFields() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                char columnName = (char) (START_COLUMN_NAME + j);
                int rowNumber = BOARD_SIZE - i;
                Position position = new Position(rowNumber, columnName);
                grid[i][j] = new Field(position);
            }
        }
    }

    /**
     * Returns the size of the board (number of rows and columns).
     * @return the size of the board
     */
    public int getBoardSize() {
        return BOARD_SIZE;
    }

    /**
     * Returns the name of the starting column (the leftmost column).
     * @return the name of the starting column
     */
    public char getStartColumnName() {
        return START_COLUMN_NAME;
    }

    /**
     * Converts a Position to its corresponding row index in the grid. The row index is calculated based on the row number of the
     * position, with the bottom row (row 1) corresponding to index 6 and the top row (row 7) corresponding to index 0.
     * @param position the position to convert
     * @return the row index corresponding to the given position
     */
    public int rowIndex(Position position) {
        return BOARD_SIZE - position.getRow();
    }

    /**
     * Converts a Position to its corresponding column index in the grid. The column index is calculated based on the column name of the
     * position, with column 'A' corresponding to index 0 and column 'G' corresponding to index 6.
     * @param position the position to convert
     * @return the column index corresponding to the given position
     */
    public int columnIndex(Position position) {
        return position.getColumn() - START_COLUMN_NAME;
    }

    /**
     * Returns the Position corresponding to the given row and column indices. The column name is calculated based on the column index,
     * with index 0 corresponding to 'A' and index 6 corresponding to 'G'. The row number is calculated based on the row index, with
     * index 6 corresponding to row 1 and index 0 corresponding to row 7.
     * @param rowIndex the row index to convert
     * @param columnIndex the column index to convert
     * @return the Position corresponding to the given row and column indices
     */
    public Position getPositionAt(int rowIndex, int columnIndex) {
        char columnName = (char) (START_COLUMN_NAME + columnIndex);
        int rowNumber = BOARD_SIZE - rowIndex;
        return new Position(rowNumber, columnName);
    }

    private Field getField(Position position) {
        return grid[rowIndex(position)][columnIndex(position)];
    }

    /**
     * Returns the BoardEntity occupying the field at the given position, or null if the field is empty.
     * @param position the position of the field to check
     * @return the BoardEntity occupying the field, or null if the field is empty
     */
    public BoardEntity getOccupant(Position position) {
        return getField(position).getOccupant();
    }

    /**
     * Checks if the field at the given position is empty (i.e., has no occupant).
     * @param position the position of the field to check
     * @return true if the field is empty, false otherwise
     */
    public boolean isFieldEmpty(Position position) {
        return getField(position).isEmpty();
    }

    //ToDO Test
    public void setOccupant(Position position, BoardEntity occupant) {
        getField(position).setOccupant(occupant);
    }
}
