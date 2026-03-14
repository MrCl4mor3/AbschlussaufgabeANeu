package edu.kit.kastel.crownoffarmland.model.board;

import edu.kit.kastel.crownoffarmland.model.units.BoardEntity;

import java.util.List;

/**
 * Provides read-only access to positions and occupants on the game board.
 *
 * @author ucgdi
 */
public interface GameBoardView {
    /**
     * Returns the size of the board.
     *
     * @return the board size
     */
    int getBoardSize();
    /**
     * Returns the position at the given row and column indices.
     *
     * @param row the row index
     * @param column the column index
     * @return the corresponding position
     */
    Position getPositionAt(int row, int column);
    /**
     * Returns the occupant at the given position.
     *
     * @param position the position to inspect
     * @return the occupying entity, or {@code null} if the position is empty
     */
    BoardEntity getOccupant(Position position);
    /**
     * Checks whether the given position is on the board.
     *
     * @param position the position to check
     * @return {@code true} if the position is valid, otherwise {@code false}
     */
    boolean isValidPosition(Position position);
    /**
     * Returns the orthogonally adjacent positions of the given position.
     *
     * @param position the center position
     * @return the orthogonal neighbors
     */
    List<Position> getOrthogonalNeighbors(Position position);
    /**
     * Returns all surrounding positions of the given position.
     *
     * @param position the center position
     * @return the surrounding positions
     */
    List<Position> getSurroundingPositions(Position position);
}