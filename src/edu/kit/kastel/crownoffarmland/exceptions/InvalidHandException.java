package edu.kit.kastel.crownoffarmland.exceptions;

public class InvalidHandException extends CrownOfFarmlandException{

    private static final String ERROR_MESSAGE = "The hand index %s is not valid. It should be a number between 1 and 5.";

    public InvalidHandException(String handIndex) {
        super(String.format(ERROR_MESSAGE, handIndex));
    }
}
