package edu.kit.kastel.crownoffarmland.exceptions.gamestateexceptions;

import edu.kit.kastel.crownoffarmland.exceptions.InvalidGameStateException;


/**
 * This exception is thrown when a movement is not allowed.
 *
 * @author ucgdi
 * @see InvalidGameStateException
 */
public final class MovementException extends InvalidGameStateException {
    private static final String MESSAGE_TARGET_TOO_FAR = "Cannot move from %s to %s. A move may cover at most %d field(s).";
    private static final String MESSAGE_FARMER_KING_ONTO_ENEMY = "A Farmer King cannot move onto the enemy-occupied field %s.";
    private static final String MESSAGE_ONTO_OWN_FARMER_KING = "Cannot move onto your own Farmer King on field %s.";

    private MovementException(String message) {
        super(message);
    }

    /**
     * Creates an exception for a move that exceeds the allowed movement distance.
     *
     * @param source the source position
     * @param target the target position
     * @param maxDistance the maximum allowed move distance
     * @return the movement exception
     */
    public static MovementException targetTooFar(String source, String target, int maxDistance) {
        return new MovementException(MESSAGE_TARGET_TOO_FAR.formatted(source, target, maxDistance));
    }

    /**
     * Creates an exception for a Farmer King trying to move onto an enemy-occupied field.
     *
     * @param target the target position
     * @return the movement exception
     */
    public static MovementException farmerKingOntoEnemy(String target) {
        return new MovementException(MESSAGE_FARMER_KING_ONTO_ENEMY.formatted(target));
    }

    /**
     * Creates an exception for moving onto a field occupied by the own Farmer King.
     *
     * @param target the target position
     * @return the movement exception
     */
    public static MovementException ontoOwnFarmerKing(String target) {
        return new MovementException(MESSAGE_ONTO_OWN_FARMER_KING.formatted(target));
    }
}