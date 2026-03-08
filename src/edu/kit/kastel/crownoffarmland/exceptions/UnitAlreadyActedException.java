package edu.kit.kastel.crownoffarmland.exceptions;

public class UnitAlreadyActedException extends InvalidGameStateException {
    private static final String MESSAGE_FORMAT = "Unit '%s' is already acted by another unit.";
    public UnitAlreadyActedException(String entityName) {
        super(String.format(MESSAGE_FORMAT, entityName));
    }
}
