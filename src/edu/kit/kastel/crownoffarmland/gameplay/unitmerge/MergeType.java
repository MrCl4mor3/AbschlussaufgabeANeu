package edu.kit.kastel.crownoffarmland.gameplay.unitmerge;

/**
 * Enum representing the different types of merges that can occur between units in the game.
 * Each merge type has its own unique properties and effects on the units involved.
 *
 * @author ucgdi
 */
public enum MergeType {
    /**
     * Represents a symbiosis merge.
     */
    SYMBIOSIS,
    /**
     * Represents an alignment merge.
     */
    ALIGNMENT,
    /**
     * Represents a prime merge.
     */
    PRIME,
    /**
     * Represents an incompatible merge, where the units cannot be merged due to incompatibility.
     */
    INCOMPATIBLE;
}
