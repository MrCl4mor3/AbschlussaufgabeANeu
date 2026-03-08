package edu.kit.kastel.crownoffarmland.exceptions;

/**
 * This exception is thrown when the player tries to perform an action with a unit that has already acted in the current turn.
 *
 * @author ucgdi
 * @see InvalidGameStateException
 */
public class UnitAlreadyActedException extends InvalidGameStateException {
    private static final String MESSAGE_FORMAT = "Unit '%s' is already acted by another unit.";

    /**
     * Creates a new UnitAlreadyActedException with the given entity name.
     * @param entityName The name of the entity that has already acted.
     */
    public UnitAlreadyActedException(String entityName) {
        super(String.format(MESSAGE_FORMAT, entityName));
    }
}
