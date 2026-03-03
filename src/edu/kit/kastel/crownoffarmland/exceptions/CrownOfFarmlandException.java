package edu.kit.kastel.crownoffarmland.exceptions;

/**
 * This is the base exception for all exceptions in the Crown of Farmland project.
 *
 * @author Programmieren-Team
 */
public class CrownOfFarmlandException extends Exception {

    /**
     * Creates a new CrownOfFarmlandException with the given message.
     * @param message The message to be displayed when the exception is thrown.
     */
    public CrownOfFarmlandException(String message) {
        super(message);
    }
}
