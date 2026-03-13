package edu.kit.kastel.crownoffarmland.exceptions;

/**
 * This exception is thrown when an invalid position is provided in the game.
 *
 * @author ucgdi
 * @see InvalidGameStateException
 */
public class InvalidPositionException extends InvalidGameStateException {

    private static final String ERROR_MESSAGE = "Invalid position: '%s'. The position must be within the bounds of the game board.";
    private static final String ERROR_MESSAGE_EMPTY_POSITION = "The position cannot be empty. Please provide a valid position within the "
            + "bounds of the game board.";

    /**
     * Creates a new InvalidPositionException with the given invalid position.
     * @param invalidPosition The invalid position that caused the exception to be thrown.
     */
    public InvalidPositionException(String invalidPosition) {
        super(String.format(ERROR_MESSAGE, invalidPosition));
    }

    /**
     * Creates a new InvalidPositionException with a default error message indicating that the position cannot be empty.
     */
    public InvalidPositionException() {
        super(ERROR_MESSAGE_EMPTY_POSITION);
    }
}
