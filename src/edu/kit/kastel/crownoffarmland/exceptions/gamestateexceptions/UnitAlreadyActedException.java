package edu.kit.kastel.crownoffarmland.exceptions.gamestateexceptions;

import edu.kit.kastel.crownoffarmland.exceptions.InvalidGameStateException;

/**
 * This exception is thrown when the player tries to perform an action with a unit that has already acted in the current turn.
 *
 * @author ucgdi
 * @see InvalidGameStateException
 */
public class UnitAlreadyActedException extends InvalidGameStateException {
    private static final String MESSAGE_FORMAT = "Unit '%s' has already acted this turn.";

    /**
     * Creates a new UnitAlreadyActedException with the given entity name.
     * @param entityName The name of the entity that has already acted.
     */
    public UnitAlreadyActedException(String entityName) {
        super(String.format(MESSAGE_FORMAT, entityName));
    }
}
