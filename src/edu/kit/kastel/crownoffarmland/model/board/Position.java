package edu.kit.kastel.crownoffarmland.model.board;


/**
 * Represents a position on the game board, defined by a row and a column.
 * The row is represented as an integer, while the column is represented as a character.
 *
 * @author  ucgdi
 */
public class Position {
    private final int row;
    private final char column;

    /**
     * Constructor for creating a Position object with the specified row and column.
     * @param row the row of the position as an integer.
     * @param column the column of the position as a character.
     */
    public Position(int row, char column) {
        this.row = row;
        this.column = column;
    }

    /**
     * Getter for column of the position.
     * @return the column of the position as a character.
     */
    public char getColumn() {
        return column;
    }

    /**
     * Getter for row of the position.
     * @return the row of the position as an integer.
     */
    public int getRow() {
        return row;
    }
}
