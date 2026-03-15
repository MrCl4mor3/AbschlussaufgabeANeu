package edu.kit.kastel.crownoffarmland.exceptions.gamestateexceptions;

import edu.kit.kastel.crownoffarmland.exceptions.InvalidGameStateException;

/**
 * This exception is thrown when an action attempts to put a king into block
 * mode, which is not allowed.
 *
 * @author ucgdi
 * @see InvalidGameStateException
 */
public class KingCannotBlockException extends InvalidGameStateException {
    private static final String MESSAGE = "The king cannot enter block mode.";

    /**
     * Creates a new exception for attempting to put a king into block mode.
     */
    public KingCannotBlockException() {
        super(MESSAGE);
    }
}