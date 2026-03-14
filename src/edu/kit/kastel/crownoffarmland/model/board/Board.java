package edu.kit.kastel.crownoffarmland.model.board;

import edu.kit.kastel.crownoffarmland.model.units.BoardEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the game board as a grid of fields.
 * The board provides read and write access to field occupants and positions.
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
     * Constructs a new board and initializes all fields.
     */
    public Board() {
        grid = new Field[BOARD_SIZE][BOARD_SIZE];
        initializeFields();
    }

    @Override
    public int getBoardSize() {
        return BOARD_SIZE;
    }

    @Override
    public Position getPositionAt(int rowIndex, int columnIndex) {
        char columnName = (char) (START_COLUMN_NAME + columnIndex);
        int rowNumber = BOARD_SIZE - rowIndex;
        return new Position(rowNumber, columnName);
    }

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

        return neighbors;
    }

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
     * Removes and returns the occupant at the given position.
     *
     * @param position the position to clear
     * @return the previous occupant, or {@code null} if the field was empty
     */
    public BoardEntity removeOccupant(Position position) {
        BoardEntity occupant = getOccupant(position);
        getField(position).setOccupant(null);
        return occupant;
    }

    /**
     * Sets the occupant at the given position.
     *
     * @param position the target position
     * @param entity the new occupant
     */
    public void setOccupant(Position position, BoardEntity entity) {
        getField(position).setOccupant(entity);
    }

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

    private int rowIndex(Position position) {
        return BOARD_SIZE - position.getRow();
    }

    private int columnIndex(Position position) {
        return position.getColumn() - START_COLUMN_NAME;
    }

    private Field getField(Position position) {
        return grid[rowIndex(position)][columnIndex(position)];
    }

    private boolean isValidRow(int row) {
        return row >= MIN_ROW && row <= BOARD_SIZE;
    }

    private boolean isValidColumn(int column) {
        return column >= START_COLUMN_NAME && column < START_COLUMN_NAME + BOARD_SIZE;
    }

    private void addIfValid(List<Position> neighbors, Position position) {
        if (isValidPosition(position)) {
            neighbors.add(position);
        }
    }
}