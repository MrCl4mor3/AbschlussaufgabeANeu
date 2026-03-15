package edu.kit.kastel.crownoffarmland.exceptions;

/**
 * This exception is thrown when a command receives invalid arguments, for
 * example because the number, type, or format of the arguments is incorrect.
 *
 * @author ucgdi
 */
public class InvalidCommandArgumentException extends CrownOfFarmlandException {
    private static final String ERROR_WRONG_NUMBER_OF_ARGUMENTS = "Wrong number of arguments. Expected %d but got %d.";

    /**
     * Creates a new exception for an invalid number of command arguments.
     *
     * @param expected the expected number of arguments
     * @param actual the actual number of arguments
     */
    public InvalidCommandArgumentException(int expected, int actual) {
        super(ERROR_WRONG_NUMBER_OF_ARGUMENTS.formatted(expected, actual));
    }
}