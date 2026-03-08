package edu.kit.kastel.crownoffarmland.exceptions;

/**
 * This exception is thrown when the player tries to perform an action on a unit that is already revealed.
 * For example, if the player tries to move a unit that has already been revealed, this exception will be thrown.
 *
 * @author ucgdi
 * @see InvalidGameStateException
 */
public class UnitAlreadyRevealedException extends InvalidGameStateException {
    private static final String MESSAGE_FORMAT = "The selected entity '%s' is already revealed. Please select a different unit.";

    /**
     * Creates a new UnitAlreadyRevealedException with the given entity name.
     * @param entityName The name of the entity that is already revealed.
     */
    public UnitAlreadyRevealedException(String entityName) {
        super(String.format(MESSAGE_FORMAT, entityName));
    }
}
