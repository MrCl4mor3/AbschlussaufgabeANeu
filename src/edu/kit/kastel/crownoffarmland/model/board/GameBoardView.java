package edu.kit.kastel.crownoffarmland.model.board;

import edu.kit.kastel.crownoffarmland.model.units.BoardEntity;

import java.util.List;

/**
 * Interface for board.
 *
 *
 * @author ucgdi
 */
public interface GameBoardView {
    /**
     * Gets the board size.
     * @return the size of the board.
     */
    int getBoardSize();
    /**
     * Returns the Position object at the specified row and column indices on the game board. This method allows you to access specific
     * positions on the board by providing their row and column indices.
     * @param row the index of the row on the game board
     * @param column the index of the column on the game board
     * @return the Position object located at the specified row and column indices on the game board
     */
    Position getPositionAt(int row, int column);
    /**
     * Returns the BoardEntity that occupies the specified position on the game board. If the position is unoccupied, this method may
     * return null or a specific value indicating that the position is empty.
     * @param position the Position object representing the location on the game board for which to retrieve the occupant
     * @return the BoardEntity that occupies the specified position on the game board, or null if the position is unoccupied
     */
    BoardEntity getOccupant(Position position);
    /**
     * Checks if the specified position on the game board is valid. A valid position is one that falls within the bounds of the board and
     * can be occupied by units or other entities during the game.
     * @param position the Position object representing the location on the game board to check for validity
     * @return true if the specified position is valid on the game board, false otherwise
     */
    boolean isValidPosition(Position position);
    /**
     * Getter for the OrthogonalNeighbors.
     * @param position the Center Position
     * @return a List of neighbors
     */
    List<Position> getOrthogonalNeighbors(Position position);
    /**
     * Getter for SurroundingPosition.
     * @param position the Center Position
     * @return a List of neighbors
     */
    List<Position> getSurroundingPositions(Position position);
}