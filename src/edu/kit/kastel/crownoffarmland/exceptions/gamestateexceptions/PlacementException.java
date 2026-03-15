package edu.kit.kastel.crownoffarmland.exceptions.gamestateexceptions;

import edu.kit.kastel.crownoffarmland.exceptions.InvalidGameStateException;


/**
 * This exception is thrown when a placement is not allowed.
 *
 * @author ucgdi
 * @see InvalidGameStateException
 */
public final class PlacementException extends InvalidGameStateException {
    private static final String MESSAGE_ALREADY_PLACED_THIS_TURN =
            "You have already placed a unit this turn.";
    private static final String MESSAGE_NOT_ADJACENT_TO_KING =
            "You can only place a unit adjacent to your King. Target: %s, King: %s.";
    private static final String MESSAGE_ENEMY_OCCUPIED_FIELD =
            "You cannot place on the enemy occupied field %s.";
    private static final String MESSAGE_DUPLICATE_HAND_INDEX =
            "Each hand index may only be used once per place command. Duplicate index: %d.";
    private static final String MESSAGE_ONTO_FARMER_KING =
            "You cannot place a unit on top of a Farmer King at %s.";

    private PlacementException(String message) {
        super(message);
    }

    /**
     * Creates an exception for placing more than once in a turn.
     *
     * @return the placement exception
     */
    public static PlacementException alreadyPlacedThisTurn() {
        return new PlacementException(MESSAGE_ALREADY_PLACED_THIS_TURN);
    }

    /**
     * Creates an exception for a target field that is not adjacent to the own king.
     *
     * @param targetPosition the target position
     * @param kingPosition the king position
     * @return the placement exception
     */
    public static PlacementException notAdjacentToKing(String targetPosition, String kingPosition) {
        return new PlacementException(MESSAGE_NOT_ADJACENT_TO_KING.formatted(targetPosition, kingPosition));
    }

    /**
     * Creates an exception for placing on an enemy-occupied field.
     *
     * @param targetPosition the target position
     * @return the placement exception
     */
    public static PlacementException enemyOccupiedField(String targetPosition) {
        return new PlacementException(MESSAGE_ENEMY_OCCUPIED_FIELD.formatted(targetPosition));
    }

    /**
     * Creates an exception for using the same hand index multiple times.
     *
     * @param userIndex the duplicated one-based hand index
     * @return the placement exception
     */
    public static PlacementException duplicateHandIndex(int userIndex) {
        return new PlacementException(MESSAGE_DUPLICATE_HAND_INDEX.formatted(userIndex));
    }

    /**
     * Creates an exception for placing on top of a Farmer King.
     *
     * @param targetPosition the target position
     * @return the placement exception
     */
    public static PlacementException ontoFarmerKing(String targetPosition) {
        return new PlacementException(MESSAGE_ONTO_FARMER_KING.formatted(targetPosition));
    }
}