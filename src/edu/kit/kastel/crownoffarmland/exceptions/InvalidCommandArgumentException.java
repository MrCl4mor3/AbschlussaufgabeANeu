package edu.kit.kastel.crownoffarmland.exceptions;

/**
 * This exception is thrown when the arguments of a command are invalid, for example if they don't match the required types or formats.
 *
 * @author Programmieren-Team
 */
public class InvalidCommandArgumentException extends  CrownOfFarmlandException {
    /**
     * Creates a new InvalidCommandArgumentException with the given message.
     * @param message The message to be displayed when the exception is thrown.
     */
    public InvalidCommandArgumentException(String message) {
        super(message);
    }
}
