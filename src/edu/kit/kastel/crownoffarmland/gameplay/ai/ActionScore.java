package edu.kit.kastel.crownoffarmland.gameplay.ai;

import edu.kit.kastel.crownoffarmland.model.board.Position;

/**
 * Represents an Action Score, wich helps for the AI decision.
 *
 * @author ucgdi
 */
final class ActionScore {
    private final UnitActionType actionType;
    private final Position target;
    private final int score;

    /**
     * Creates a new Score.
     * @param actionType wich type of action
     * @param target the target to move
     * @param score the actual position of my unit
     */
    ActionScore(UnitActionType actionType, Position target, int score) {
        this.actionType = actionType;
        this.target = target;
        this.score = score;
    }

    /**
     * Getter for ActionType.
     * @return the unitActionType
     */
    UnitActionType getActionType() {
        return actionType;
    }

    /**
     * Getter for Target.
     * @return the target position
     */
    Position getTarget() {
        return target;
    }

    /**
     * Getter for Score.
     * @return the score
     */
    int getScore() {
        return score;
    }
}