package edu.kit.kastel.crownoffarmland.gameplay.ai.decision.model;

import edu.kit.kastel.crownoffarmland.model.board.Position;

/**
 * Represents a decision for a unit action.
 *
 * @author ucgdi
 */
public final class UnitActionDecision {
    private final Position source;
    private final UnitActionType actionType;
    private final Position target;

    /**
     * Creates a new unit action decision.
     *
     * @param source the source position
     * @param actionType the selected action type
     * @param target the target position
     */
    public UnitActionDecision(Position source, UnitActionType actionType, Position target) {
        this.source = source;
        this.actionType = actionType;
        this.target = target;
    }

    /**
     * Returns the source position.
     *
     * @return the source position
     */
    public Position getSource() {
        return source;
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
}