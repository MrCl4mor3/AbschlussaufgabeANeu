package edu.kit.kastel.crownoffarmland.gameplay;

/**
 * Defines the possible results of a yield check.
 *
 * @author ucgdi
 */
public enum YieldCheckResult {
    /**
     * The yield is valid.
     */
    SUCCESS,

    /**
     * A discard is required before yielding.
     */
    DISCARD_REQUIRED,

    /**
     * Discarding is not allowed.
     */
    DISCARD_NOT_ALLOWED
}