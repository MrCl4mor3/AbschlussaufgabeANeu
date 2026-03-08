package edu.kit.kastel.crownoffarmland.exceptions;

/**
 * This exception is thrown when the player tries to perform an action on the hand, but the hand index is not valid.
 * I.e. there is no hand with the given index
 *
 *
 * @author ucgdi
 * @see InvalidGameStateException
 */
public class InvalidHandException extends InvalidGameStateException {

    private static final String ERROR_MESSAGE = "The hand index %s is not valid. It should be a number between 1 and 5.";

    /**
     * Creates a new InvalidHandException with the given hand index.
     * @param handIndex The hand index that is not valid.
     */
    public InvalidHandException(String handIndex) {
        super(String.format(ERROR_MESSAGE, handIndex));
    }
}
