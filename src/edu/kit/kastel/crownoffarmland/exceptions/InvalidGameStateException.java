package edu.kit.kastel.crownoffarmland.exceptions;

/**
 * This exception is thrown when the game is in an invalid state for performing a certain action. For example, if the player tries to
 * perform an action that requires selecting a unit, but no unit is selected, this exception would be thrown.
 *
 * @author ucgdi
 */
public class InvalidGameStateException extends CrownOfFarmlandException{


    /**
     * Creates a new InvalidGameStateException with the given message.
     * @param message The message to be displayed when the exception is thrown.
     */
    public InvalidGameStateException(String message) {
        super(message);
    }
}
