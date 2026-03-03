package edu.kit.kastel.crownoffarmland.model.board;

import edu.kit.kastel.crownoffarmland.model.units.BoardEntity;

public class Board {
    private static final int BOARD_SIZE = 7;
    private static final char START_COLUMN_NAME = 'A';
    private final Field[][] grid;


    public Board() {
        grid = new Field[BOARD_SIZE][BOARD_SIZE];
        initializeFields();
    }

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

    public BoardEntity getOccupant(Position position) {
        return getField(position).getOccupant();
    }

    public boolean isFieldEmpty(Position position) {
        return getField(position).isEmpty();
    }
}
