package edu.kit.kastel.crownoffarmland.exceptions;

/**
 * This exception is thrown when the player tries to do an action that requires selecting a field, but the selected field is empty (i.e.,
 * it does not contain any entity).
 *
 * @author ucgdi
 * @see InvalidGameStateException
 */
public class EmptySelectedFieldException extends  InvalidGameStateException {
    private static final String MESSAGE_FORMAT = "The selected field '%s' is empty. Please select a field with an entity on it.";

    /**
     * Creates a new EmptySelectedFieldException with the given field name.
     * @param fieldName The name of the field that is empty.
     */
    public EmptySelectedFieldException(String fieldName) {
        super(String.format(MESSAGE_FORMAT, fieldName));
    }
}
