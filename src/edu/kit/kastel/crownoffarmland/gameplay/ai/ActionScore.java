package edu.kit.kastel.crownoffarmland.gameplay.ai;

import edu.kit.kastel.crownoffarmland.model.board.Position;

/**
 * Stores a possible action and its score for the AI.
 *
 * @author ucgdi
 */
final class ActionScore {
    private final UnitActionType actionType;
    private final Position target;
    private final int score;

    /**
     * Creates a scored action.
     *
     * @param actionType the type of action
     * @param target the target position
     * @param score the score of the action
     */
    ActionScore(UnitActionType actionType, Position target, int score) {
        this.actionType = actionType;
        this.target = target;
        this.score = score;
    }

    /**
     * Returns the action type.
     *
     * @return the action type
     */
    UnitActionType getActionType() {
        return actionType;
    }

    /**
     * Returns the target position.
     *
     * @return the target position
     */
    Position getTarget() {
        return target;
    }

    /**
     * Returns the score of the action.
     *
     * @return the score
     */
    int getScore() {
        return score;
    }
}