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

    private static final String ERROR_MESSAGE = "There is no card with the index %s in the hand. Please provide a valid hand index.";

    /**
     * Creates a new InvalidHandException with the given hand index.
     * @param handIndex The hand index that is not valid.
     */
    public InvalidHandException(String handIndex) {
        super(String.format(ERROR_MESSAGE, handIndex));
    }
}
