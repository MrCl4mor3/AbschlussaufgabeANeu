package edu.kit.kastel.crownoffarmland.exceptions;

/**
 * This exception is thrown when the player tries to perform an action that requires selecting an entity, but no selection has been made.
 *
 * @author ucgdi
 * @see InvalidGameStateException
 */
public class NoSelectionException extends InvalidGameStateException {
    private static final String MESSAGE = "No selection was made. Please select an entity before performing this action.";

    /**
     * Creates a new NoSelectionException with a default message.
     */
    public NoSelectionException() {
        super(MESSAGE);
    }
}
