package edu.kit.kastel.crownoffarmland.exceptions.gamestateexceptions;

import edu.kit.kastel.crownoffarmland.exceptions.InvalidGameStateException;

/**
 * This exception is thrown when an action is attempted with a unit that has
 * already acted in the current turn.
 *
 * @author ucgdi
 * @see InvalidGameStateException
 */
public class EntityAlreadyActedException extends InvalidGameStateException {
    private static final String MESSAGE_FORMAT = "Entity '%s' has already acted this turn.";

    /**
     * Creates a new exception for a unit that has already acted in the current
     * turn.
     *
     * @param entityNAme the name of the entity that has already acted
     */
    public EntityAlreadyActedException(String entityNAme) {
        super(MESSAGE_FORMAT.formatted(entityNAme));
    }
}