package edu.kit.kastel.crownoffarmland.exceptions.gamestateexceptions;

import edu.kit.kastel.crownoffarmland.exceptions.InvalidGameStateException;

/**
 * This exception is thrown when an action requires a valid hand index, but the
 * given index does not refer to a card in the current hand.
 *
 * @author ucgdi
 * @see InvalidGameStateException
 */
public class InvalidHandIndexException extends InvalidGameStateException {
    private static final String ERROR_MESSAGE =
            "There is no card with index %s in the hand. Please provide a valid hand index.";

    /**
     * Creates a new exception for an invalid hand index.
     *
     * @param handIndex the invalid hand index
     */
    public InvalidHandIndexException(String handIndex) {
        super(ERROR_MESSAGE.formatted(handIndex));
    }
}