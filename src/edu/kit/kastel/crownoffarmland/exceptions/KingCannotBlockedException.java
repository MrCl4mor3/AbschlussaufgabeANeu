package edu.kit.kastel.crownoffarmland.exceptions;

/**
 * This exception is thrown when the player tries to block the king, which is not allowed in the game.
 *
 * @author ucgdi
 * @see InvalidGameStateException
 */
public class KingCannotBlockedException extends InvalidGameStateException {

    private static final String MESSAGE = "The king cannot be blocked.";

    /**
     * Creates a new KingCannotBlockedException with a default message.
     */
    public KingCannotBlockedException() {
        super(MESSAGE);
    }
}
