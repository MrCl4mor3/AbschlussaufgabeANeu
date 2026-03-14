package edu.kit.kastel.crownoffarmland.startup.config;

import java.util.EnumSet;
import java.util.Set;

/**
 * Defines the supported startup configuration keys.
 *
 * @author ucgdi
 */
public enum StartupKey {
    /**
     * Random seed configuration.
     */
    SEED(true, "seed"),

    /**
     * Board configuration.
     */
    BOARD(false, "board"),

    /**
     * Unit configuration.
     */
    UNITS(true, "units"),

    /**
     * Shared deck configuration.
     */
    DECK(false, "deck"),

    /**
     * First deck configuration.
     */
    DECK1(false, "deck1"),

    /**
     * Second deck configuration.
     */
    DECK2(false, "deck2"),

    /**
     * First team configuration.
     */
    TEAM1(false, "team1"),

    /**
     * Second team configuration.
     */
    TEAM2(false, "team2"),

    /**
     * Output verbosity configuration.
     */
    VERBOSITY(false, "verbosity");

    private final boolean required;
    private final String key;

    StartupKey(boolean required, String key) {
        this.required = required;
        this.key = key;
    }

    /**
     * Returns whether this key is required.
     *
     * @return {@code true} if this key is required, otherwise {@code false}
     */
    public boolean isRequired() {
        return this.required;
    }

    /**
     * Returns the string representation of this key.
     *
     * @return the key string
     */
    public String getKey() {
        return this.key;
    }

    /**
     * Returns the matching startup key for the given string.
     *
     * @param rawKey the key string
     * @return the matching startup key, or {@code null} if none matches
     */
    public static StartupKey fromString(String rawKey) {
        for (StartupKey key : StartupKey.values()) {
            if (key.getKey().equalsIgnoreCase(rawKey)) {
                return key;
            }
        }
        return null;
    }

    /**
     * Returns all required startup keys.
     *
     * @return the required startup keys
     */
    public static Set<StartupKey> getRequiredKeys() {
        EnumSet<StartupKey> requiredKeys = EnumSet.noneOf(StartupKey.class);
        for (StartupKey key : StartupKey.values()) {
            if (key.isRequired()) {
                requiredKeys.add(key);
            }
        }
        return requiredKeys;
    }
}