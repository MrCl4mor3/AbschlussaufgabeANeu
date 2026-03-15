package edu.kit.kastel.crownoffarmland.exceptions.gamestateexceptions;

import edu.kit.kastel.crownoffarmland.exceptions.InvalidGameStateException;

/**
 * This exception is thrown when an action requires an entity to be selected,
 * but no selection has been made.
 *
 * @author ucgdi
 * @see InvalidGameStateException
 */
public class NoSelectionException extends InvalidGameStateException {
    private static final String MESSAGE = "No selection was made. Please select an entity before performing this action.";

    /**
     * Creates a new exception for a missing selection.
     */
    public NoSelectionException() {
        super(MESSAGE);
    }
}