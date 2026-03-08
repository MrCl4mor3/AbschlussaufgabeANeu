package edu.kit.kastel.crownoffarmland.exceptions;

/**
 * This exception is thrown when the arguments of a command are invalid, for example if they don't match the required types or formats.
 *
 * @author ucgdi
 */
public class InvalidCommandArgumentException extends  CrownOfFarmlandException {

    private static final String ERROR_WRONG_NUMBER_OF_ARGUMENTS = "Wrong number of arguments. Expected %d but got %d.";
    /**
     * Creates a new InvalidCommandArgumentException with the given message.
     * @param expected The message to be displayed when the exception is thrown.
     */
    public InvalidCommandArgumentException(int expected, int actual) {
        super(String.format(ERROR_WRONG_NUMBER_OF_ARGUMENTS, expected, actual));
    }
}
