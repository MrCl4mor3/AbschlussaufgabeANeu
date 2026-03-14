package edu.kit.kastel.crownoffarmland.model.board;


import edu.kit.kastel.crownoffarmland.exceptions.CrownOfFarmlandException;
import edu.kit.kastel.crownoffarmland.exceptions.InvalidCommandArgumentException;
import edu.kit.kastel.crownoffarmland.exceptions.InvalidPositionException;

import java.util.Objects;

/**
 * Represents a position on the game board, defined by a row and a column.
 * The row is represented as an integer, while the column is represented as a character.
 *
 * @author  ucgdi
 */
public final class Position {
    private static final int EXPECTED_POSITION_LENGTH = 2;
    private final int row;
    private final char column;


    /**
     * Constructor for creating a Position object with the specified row and column.
     * @param row the row of the position as an integer.
     * @param column the column of the position as a character.
     */
    public Position(int row, char column) {
        this.row = row;
        this.column = Character.toUpperCase(column);
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

    @Override
    public String toString() {
        return column + String.valueOf(row);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj == this) {
            return true;
        }

        if (this.getClass() != obj.getClass()) {
            return false;
        }

        Position position = (Position) obj;
        return (this.row == position.row) && (this.column == position.column);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.row, this.column);
    }


    /**
     * Parse a String input to a position object.
     * @param input the input String to parse.
     * @return a new Position
     * @throws CrownOfFarmlandException if an invalide String was found
     * @throws InvalidPositionException if the input is null or cannot be parsed to a valid position
     */
    public static Position fromString(String input) throws CrownOfFarmlandException {
        if (input == null) {
            throw new InvalidPositionException();
        }

        String trimmed = input.trim().toUpperCase();

        if (trimmed.length() != EXPECTED_POSITION_LENGTH) {
            throw new InvalidPositionException(input);
        }
        char column = trimmed.charAt(0);
        String rowPart = trimmed.substring(1);


        int row;
        try {
            row = Integer.parseInt(rowPart);
        } catch (NumberFormatException e) {
            throw new InvalidPositionException(input);
        }
        return new Position(row, column);
    }
}
