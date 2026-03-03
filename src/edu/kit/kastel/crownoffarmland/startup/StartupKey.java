package edu.kit.kastel.crownoffarmland.startup;

import java.util.EnumSet;
import java.util.Set;

public enum StartupKey {
    SEED(true, "seed"),
    BOARD(false, "board"),
    UNITS(true, "units"),
    DECK(false, "deck"),
    DECK1(false, "deck1"),
    DECK2(false, "deck2"),
    TEAM1(false, "team1"),
    TEAM2(false, "team2"),
    VERBOSITY(false, "verbosity");

    private final boolean required;
    private final String key;

    StartupKey(boolean required, String key) {
        this.required = required;
        this.key = key;
    }

    public boolean isRequired() {
        return this.required;
    }

    public String getKey() {
        return this.key;
    }

    public static StartupKey fromString(String rawKey) {
        for (StartupKey key : StartupKey.values()) {
            if (key.getKey().equalsIgnoreCase(rawKey)) {
                return key;
            }
        }
        return null;
    }

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