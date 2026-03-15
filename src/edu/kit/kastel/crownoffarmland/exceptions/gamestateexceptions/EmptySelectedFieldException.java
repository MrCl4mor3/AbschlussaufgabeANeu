package edu.kit.kastel.crownoffarmland.exceptions.gamestateexceptions;

import edu.kit.kastel.crownoffarmland.exceptions.InvalidGameStateException;

/**
 * This exception is thrown when an action requires the selected field to contain
 * an entity, but the selected field is empty.
 *
 * @author ucgdi
 * @see InvalidGameStateException
 */
public class EmptySelectedFieldException extends InvalidGameStateException {
    private static final String MESSAGE_FORMAT = "The selected field '%s' is empty. Please select a field with an entity on it.";

    /**
     * Creates a new exception for an empty selected field.
     *
     * @param fieldName the name of the empty field
     */
    public EmptySelectedFieldException(String fieldName) {
        super(MESSAGE_FORMAT.formatted(fieldName));
    }
}