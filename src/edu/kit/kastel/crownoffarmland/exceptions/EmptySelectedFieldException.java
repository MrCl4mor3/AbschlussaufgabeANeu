package edu.kit.kastel.crownoffarmland.exceptions;

public class EmptySelectedFieldException extends  InvalidGameStateException {
    private static final String MESSAGE_FORMAT = "The selected field '%s' is empty. Please select a field with an entity on it.";

    public EmptySelectedFieldException(String fieldName) {
        super(String.format(MESSAGE_FORMAT, fieldName));
    }
}
