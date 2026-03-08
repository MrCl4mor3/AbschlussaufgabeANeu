package edu.kit.kastel.crownoffarmland.exceptions;

public class KingCannotBlockedException extends InvalidGameStateException {

    private static final String MESSAGE = "The king cannot be blocked.";

    public KingCannotBlockedException() {
        super(MESSAGE);
    }
}
