package edu.kit.kastel.crownoffarmland.gameplay.ai;

/**
 * A Enum to represent the different type of action a ai can be decide.
 *
 *
 * @author ucgdi
 */
public enum UnitActionType {
    /**
     * Move the unit to a new position. The position is determined by the ai and is not fixed.
     */
    MOVE,
    /**
     * Stay on field and go to blocking state.
     */
    BLOCK,
    /**
     * Do nothing with the unit and stay on the current position.
     */
    STAY;
}
