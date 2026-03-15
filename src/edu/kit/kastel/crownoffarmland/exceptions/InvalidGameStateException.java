package edu.kit.kastel.crownoffarmland.exceptions;

/**
 * This exception is thrown when the game is in an invalid state for a specific
 * action.
 *
 * @author ucgdi
 */
public class InvalidGameStateException extends CrownOfFarmlandException {

    /**
     * Creates a new exception for an invalid game state.
     *
     * @param message the detail message
     */
    public InvalidGameStateException(String message) {
        super(message);
    }
}