package edu.kit.kastel.crownoffarmland.exceptions;

/**
 * The base exception for all exceptions in the Crown of Farmland project.
 *
 * @author ucgdi
 */
public class CrownOfFarmlandException extends Exception {

    /**
     * Creates a new exception with the given message.
     *
     * @param message the detail message
     */
    public CrownOfFarmlandException(String message) {
        super(message);
    }
}