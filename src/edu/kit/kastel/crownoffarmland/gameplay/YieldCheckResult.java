package edu.kit.kastel.crownoffarmland.gameplay;

/**
 * Enum for Checking the YieldResult.
 *
 * @author ucgdi
 */
public enum YieldCheckResult {
    /**
     * If the yield is successful.
     */
    SUCCESS,
    /**
     * If the yield is not successful, because player must discard a card from his hand.
     */
    DISCARD_REQUIRED,
    /**
     * If the yield is not successful, because player want to discard a card from his hand, but it is not allowed.
     */
    DISCARDED_NOT_ALLOWED;
}
