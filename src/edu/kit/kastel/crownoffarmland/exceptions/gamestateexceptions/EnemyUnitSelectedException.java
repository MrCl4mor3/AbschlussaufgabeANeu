package edu.kit.kastel.crownoffarmland.exceptions.gamestateexceptions;


import edu.kit.kastel.crownoffarmland.exceptions.InvalidGameStateException;

/**
 * This exception is thrown when the player tries to perform an action that requires selecting a friendly unit, but an enemy unit is
 * selected instead.
 *
 * @author ucgdi
 * @see InvalidGameStateException
 */
public class EnemyUnitSelectedException extends InvalidGameStateException {
    private static final String MESSAGE = "An enemy unit is selected. Please select a friendly unit to perform this action.";

    /**
     * Creates a new EnemyUnitSelectedException with a default message.
     */
    public EnemyUnitSelectedException() {
        super(MESSAGE);
    }
}
