package edu.kit.kastel.crownoffarmland.gameplay.ai;

import edu.kit.kastel.crownoffarmland.model.board.Position;

public final class UnitActionDecision {

    private final Position source;
    private final UnitActionType actionType;
    private final Position target;

    public UnitActionDecision(Position source, UnitActionType actionType, Position target) {
        this.source = source;
        this.actionType = actionType;
        this.target = target;
    }

    public Position getSource() {
        return source;
    }
    public UnitActionType getActionType() {
        return actionType;
    }
    public Position getTarget() {
        return target;
    }
}
