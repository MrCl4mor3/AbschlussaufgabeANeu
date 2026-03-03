package edu.kit.kastel.crownoffarmland.startup;

/**
 * This enum represents the different modes for deck configuration during the startup process.
 * It defines two modes: SHARED_DECK and SPLIT_DECKS.
 * In SHARED_DECK mode, both teams share the same deck configuration.
 * In SPLIT_DECKS mode, each team has its own separate deck configuration.
 *
 * @author ucgdi
 */
public enum DeckConfigMode {
    /**
     * In this mode, both teams share the same deck configuration.
     */
    SHARED_DECK,
    /**
     * In this mode, each team has its own separate deck configuration.
     */
    SPLIT_DECKS;
}

