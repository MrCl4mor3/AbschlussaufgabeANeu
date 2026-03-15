package edu.kit.kastel.crownoffarmland.exceptions.gamestateexceptions;

import edu.kit.kastel.crownoffarmland.exceptions.InvalidGameStateException;

/**
 * This exception is thrown when an action requires a friendly unit to be
 * selected, but an enemy unit is selected instead.
 *
 * @author ucgdi
 * @see InvalidGameStateException
 */
public class EnemyUnitSelectedException extends InvalidGameStateException {
    private static final String MESSAGE = "An enemy unit is selected. Please select a friendly unit to perform this action.";

    /**
     * Creates a new exception for an enemy unit being selected.
     */
    public EnemyUnitSelectedException() {
        super(MESSAGE);
    }
}