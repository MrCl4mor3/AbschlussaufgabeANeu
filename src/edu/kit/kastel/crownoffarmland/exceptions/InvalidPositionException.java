package edu.kit.kastel.crownoffarmland.exceptions;

/**
 * This exception is thrown when an invalid position is provided.
 *
 * @author ucgdi
 * @see CrownOfFarmlandException
 */
public class InvalidPositionException extends CrownOfFarmlandException {
    private static final String MESSAGE_INVALID_POSITION =
            "Invalid position: '%s'. The position must be within the bounds of the game board.";
    private static final String MESSAGE_EMPTY_POSITION =
            "The position cannot be empty. Please provide a valid position within the bounds of the game board.";

    /**
     * Creates a new exception for an invalid position.
     *
     * @param invalidPosition the invalid position
     */
    public InvalidPositionException(String invalidPosition) {
        super(MESSAGE_INVALID_POSITION.formatted(invalidPosition));
    }

    /**
     * Creates a new exception for an empty position input.
     */
    public InvalidPositionException() {
        super(MESSAGE_EMPTY_POSITION);
    }
}