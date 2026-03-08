package edu.kit.kastel.crownoffarmland.exceptions;

public class NoSelectionException extends InvalidGameStateException {
    private static final String MESSAGE = "No selection was made. Please select an entity before performing this action.";
    public NoSelectionException() {
        super(MESSAGE);
    }
}
