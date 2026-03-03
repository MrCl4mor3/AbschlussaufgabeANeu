package edu.kit.kastel.crownoffarmland.startup;

import java.util.EnumSet;
import java.util.Set;

/**
 * This enum represents the different keys that can be used during the startup process to configure the game.
 * Each key has a boolean value indicating whether it is required or optional, and a string value representing the key itself.
 * The enum provides methods to check if a key is required, to get the string representation of the key, and to convert a string to a
 * StartupKey.
 *
 * @author ucgdi
 */
public enum StartupKey {
    /**
     * The SEED key is required for the startup process and represents the seed for the random generator.
     */
    SEED(true, "seed"),
    /**
     * The BOARD key is optional for the startup process and represents the configuration of the game board.
     */
    BOARD(false, "board"),
    /**
     * The UNITS key is required for the startup process and represents the configuration of the units in the game.
     */
    UNITS(true, "units"),
    /**
     * The DECK key is optional for the startup process and represents the configuration of the deck used in the game.
     */
    DECK(false, "deck"),
    /**
     * The DECK1 key is optional for the startup process and represents the configuration of the first deck used in the game.
     */
    DECK1(false, "deck1"),
    /**
     * The DECK2 key is optional for the startup process and represents the configuration of the second deck used in the game.
     */
    DECK2(false, "deck2"),
    /**
     * The TEAM1 key is optional for the startup process and represents the configuration of the first team in the game.
     */
    TEAM1(false, "team1"),
    /**
     * The TEAM2 key is optional for the startup process and represents the configuration of the second team in the game.
     */
    TEAM2(false, "team2"),
    /**
     * The VERBOSITY key is optional for the startup process and represents the verbosity level of the game's output.
     */
    VERBOSITY(false, "verbosity");

    private final boolean required;
    private final String key;

    StartupKey(boolean required, String key) {
        this.required = required;
        this.key = key;
    }

    /**
     * Indicates whether this startup key is required for the startup process.
     * @return true if this key is required, false otherwise
     */
    public boolean isRequired() {
        return this.required;
    }

    /**
     * Returns the string representation of this startup key.
     * @return the string representation of this startup key
     */
    public String getKey() {
        return this.key;
    }

    /**
     * Converts a string to a StartupKey enum value. The comparison is case-insensitive.
     * @param rawKey the string representation of the startup key to be converted
     * @return the corresponding StartupKey enum value if the input string matches a key, or null if no match is found
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
     * Returns a set of all required StartupKey enum values.
     * @return a set of all required StartupKey enum values
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