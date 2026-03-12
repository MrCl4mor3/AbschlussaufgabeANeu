package edu.kit.kastel.crownoffarmland.gameplay.ai;

import edu.kit.kastel.crownoffarmland.model.board.Position;

final class ActionScore {
    private final UnitActionType actionType;
    private final Position target;
    private final int score;

    ActionScore(UnitActionType actionType, Position target, int score) {
        this.actionType = actionType;
        this.target = target;
        this.score = score;
    }

    UnitActionType getActionType() {
        return actionType;
    }

    Position getTarget() {
        return target;
    }

    int getScore() {
        return score;
    }
}