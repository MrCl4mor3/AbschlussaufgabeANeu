package edu.kit.kastel.crownoffarmland.exceptions;

public class UnitAlreadyRevealedException extends InvalidGameStateException {
    private static final String MESSAGE_FORMAT = "The selected entity '%s' is already revealed. Please select a different unit.";
    public UnitAlreadyRevealedException(String entityName) {
        super(String.format(MESSAGE_FORMAT, entityName));
    }
}
