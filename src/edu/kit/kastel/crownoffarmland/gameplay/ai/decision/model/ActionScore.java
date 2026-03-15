package edu.kit.kastel.crownoffarmland.gameplay.ai.decision.model;

import edu.kit.kastel.crownoffarmland.model.board.Position;

/**
 * Stores a possible action and its score for the AI.
 *
 * @author ucgdi
 */
public final class ActionScore {
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
    public ActionScore(UnitActionType actionType, Position target, int score) {
        this.actionType = actionType;
        this.target = target;
        this.score = score;
    }

    /**
     * Returns the action type.
     *
     * @return the action type
     */
    public UnitActionType getActionType() {
        return actionType;
    }

    /**
     * Returns the target position.
     *
     * @return the target position
     */
    public Position getTarget() {
        return target;
    }

    /**
     * Returns the score of the action.
     *
     * @return the score
     */
    public int getScore() {
        return score;
    }
}