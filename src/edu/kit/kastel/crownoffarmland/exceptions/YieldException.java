package edu.kit.kastel.crownoffarmland.exceptions;

public class YieldException extends InvalidGameStateException {
    private static final String ERROR_MESSAGE_CANNOT_END_TURN = "%s hand is full!";

    public YieldException(String teamName) {
        super(String.format(ERROR_MESSAGE_CANNOT_END_TURN, teamName));
    }

}
