package edu.kit.kastel.crownoffarmland.gameplay.ai;

import edu.kit.kastel.crownoffarmland.model.board.Position;

/**
 * A UnitActionDecision.
 *
 * @author ucgdi
 */
public final class UnitActionDecision {

    private final Position source;
    private final UnitActionType actionType;
    private final Position target;

    /**
     * Creates a new UnitActionDecision.
     * @param source the Position of the unit
     * @param actionType wich type of action did won
     * @param target the target Position to move
     */
    public UnitActionDecision(Position source, UnitActionType actionType, Position target) {
        this.source = source;
        this.actionType = actionType;
        this.target = target;
    }

    /**
     * Getter for the Source Position.
     * @return the source Position
     */
    public Position getSource() {
        return source;
    }

    /**
     * Getter for the ActionType.
     * @return the action type
     */
    public UnitActionType getActionType() {
        return actionType;
    }

    /**
     * Getter for the target Position.
     * @return the target Position
     */
    public Position getTarget() {
        return target;
    }
}
