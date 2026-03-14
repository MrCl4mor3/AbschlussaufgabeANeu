package edu.kit.kastel.crownoffarmland.model.board;


import edu.kit.kastel.crownoffarmland.model.units.BoardEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the game board, which consists of a grid of fields. Each field can be occupied by a BoardEntity.
 * The board provides methods to access and manipulate the fields and their occupants.
 * To view the board state, there is the GameBoardView.
 *
 *
 * @author ucgdi
 */
public class Board implements GameBoardView {
    private static final int NEIGHBOR_DISTANCE = 1;
    private static final int BOARD_SIZE = 7;
    private static final char START_COLUMN_NAME = 'A';
    private static final int MIN_ROW = 1;
    private static final int[][] DIRECTIONS_CLOCKWISE = {
            {1, 0}, {1, 1}, {0, 1}, {-1, 1}, {-1, 0}, {-1, -1}, {0, -1}, {1, -1}
    };

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
    @Override
    public int getBoardSize() {
        return BOARD_SIZE;
    }

    /**
     * Returns the Position corresponding to the given row and column indices. The column name is calculated based on the column index,
     * with index 0 corresponding to 'A' and index 6 corresponding to 'G'. The row number is calculated based on the row index, with
     * index 6 corresponding to row 1 and index 0 corresponding to row 7.
     * @param rowIndex the row index to convert
     * @param columnIndex the column index to convert
     * @return the Position corresponding to the given row and column indices
     */
    @Override
    public Position getPositionAt(int rowIndex, int columnIndex) {
        char columnName = (char) (START_COLUMN_NAME + columnIndex);
        int rowNumber = BOARD_SIZE - rowIndex;
        return new Position(rowNumber, columnName);
    }

    /**
     * Returns the BoardEntity occupying the field at the given position, or null if the field is empty.
     * @param position the position of the field to check
     * @return the BoardEntity occupying the field, or null if the field is empty
     */
    @Override
    public BoardEntity getOccupant(Position position) {
        return getField(position).getOccupant();
    }

    @Override
    public boolean isValidPosition(Position position) {
        if (position == null) {
            return false;
        }
        return isValidRow(position.getRow()) && isValidColumn(position.getColumn());
    }

    /**
     * Gets the neighbor position of a given center, wich is orthogonally adjacent (up, down, left, right) to the given center. If a
     * neighbor position is out of bounds, it is not included in the result. The method returns a list of valid neighboring positions.
     * @param center the center to look for neighbors
     * @return a List of neighboring positions that are orthogonally adjacent to the given center and within the bounds of the board
     */
    @Override
    public List<Position> getOrthogonalNeighbors(Position center) {
        List<Position> neighbors = new ArrayList<>();

        Position up = new Position(center.getRow() + NEIGHBOR_DISTANCE, center.getColumn());
        Position down = new Position(center.getRow() - NEIGHBOR_DISTANCE, center.getColumn());
        Position left = new Position(center.getRow(), (char) (center.getColumn() - NEIGHBOR_DISTANCE));
        Position right = new Position(center.getRow(), (char) (center.getColumn() + NEIGHBOR_DISTANCE));

        addIfValid(neighbors, up);
        addIfValid(neighbors, right);
        addIfValid(neighbors, down);
        addIfValid(neighbors, left);

        return  neighbors;
    }

    /**
     * Gets all neighboring positions of a given position, which are adjacent (including diagonals) to the given position. If a
     * neighboring position is out of bounds, it is not included in the result. The method returns a list of valid surrounding positions.
     * @param center the center to look for surrounding positions
     * @return a List of neighboring positions that are adjacent (including diagonals) to the given center and within the bounds of the
     *      board
     */
    @Override
    public List<Position> getSurroundingPositions(Position center) {
        List<Position> surroundingPositions = new ArrayList<>();

        for (int[] direction : DIRECTIONS_CLOCKWISE) {
            int row = center.getRow() + direction[0];
            int column = center.getColumn() + direction[1];
            Position position = new Position(row, (char) column);
            if (isValidPosition(position)) {
                surroundingPositions.add(position);
            }
        }
        return surroundingPositions;
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


    private Field getField(Position position) {
        return grid[rowIndex(position)][columnIndex(position)];
    }

    /**
     * Removes and returns the BoardEntity occupying the field at the given position, if any. If the field is empty, it returns null.
     * @param position the position of the field from which to remove the occupant
     * @return the BoardEntity that was occupying the field, or null if the field was empty
     */
    public BoardEntity removeOccupant(Position position) {
        BoardEntity occupant = getOccupant(position);
        getField(position).setOccupant(null);
        return occupant;
    }
    /**
     * Sets the occupant of the field at the given position to the specified BoardEntity. If the position is invalid, an
     * InvalidPositionException is thrown.
     * @param position the position of the field to set the occupant for
     * @param entity the BoardEntity to set as the occupant of the field
     */
    public void setOccupant(Position position, BoardEntity entity) {
        getField(position).setOccupant(entity);
    }

    private boolean isValidRow(int row) {
        return row >= MIN_ROW && row <= BOARD_SIZE;
    }

    private  boolean isValidColumn(int column) {
        return column >= START_COLUMN_NAME && column < START_COLUMN_NAME + BOARD_SIZE;
    }


    private void addIfValid(List<Position> neighbors, Position position) {
        if (isValidPosition(position)) {
            neighbors.add(position);
        }
    }




}
