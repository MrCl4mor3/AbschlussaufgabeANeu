package edu.kit.kastel.crownoffarmland.gameplay.ai.decision.model;

/**
 * Defines the possible actions of a unit.
 *
 * @author ucgdi
 */
public enum UnitActionType {
    /**
     * Moves the unit to a target position.
     */
    MOVE,

    /**
     * Sets the unit to blocking.
     */
    BLOCK,

    /**
     * Leaves the unit on its current position.
     */
    STAY
}