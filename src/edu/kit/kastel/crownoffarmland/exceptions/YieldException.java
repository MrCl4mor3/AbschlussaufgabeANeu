package edu.kit.kastel.crownoffarmland.exceptions;

/**
 * This exception is thrown when a player tries to end their turn, but it isn't possible at the moment, for example because their hand is
 * full.
 *
 * @author ucgdi
 * @see InvalidGameStateException
 */
public class YieldException extends InvalidGameStateException {
    private static final String ERROR_MESSAGE_CANNOT_END_TURN = "%s hand is full!";


    /**
     * Creates a new YieldException with the given team name.
     * @param teamName The name of the team that cannot end their turn.
     */
    public YieldException(String teamName) {
        super(String.format(ERROR_MESSAGE_CANNOT_END_TURN, teamName));
    }

}
