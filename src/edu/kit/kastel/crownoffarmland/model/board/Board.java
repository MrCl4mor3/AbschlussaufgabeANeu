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
                int rowNumber = BOARD_SIZE - 1;
                Position position = new Position(rowNumber, columnName);
                grid[i][j] = new Field(position);
            }
        }
    }

    private int rowIndex(Position position) {
        return BOARD_SIZE - position.getRow();
    }

    private int columnIndex(Position position) {
        return position.getColumn() - START_COLUMN_NAME;
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
}
