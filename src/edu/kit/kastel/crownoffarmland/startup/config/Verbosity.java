package edu.kit.kastel.crownoffarmland.startup.config;

import java.util.List;

/**
 * This enum represents the different verbosity levels that can be configured during the startup process.
 * It defines two levels: ALL and COMPACT.
 * The ALL level indicates that all output should be displayed, while the COMPACT level indicates that only essential output should be
 * displayed.
 *
 * @author ucgdi
 */
public enum Verbosity {
    /**
     * This level indicates that all output should be displayed.
     */
    ALL("all"),
    /**
     * This level indicates that only essential output should be displayed.
     */
    COMPACT("compact");

    private final String raw;

    Verbosity(String raw) {
        this.raw = raw;
    }

    /**
     * Returns the string representation of the verbosity level.
     */
    public static Verbosity fromString(String rawKey) {
        for (Verbosity verbosity : Verbosity.values()) {
            if (verbosity.raw.equalsIgnoreCase(rawKey)) {
                return verbosity;
            }
        }
        return null;
    }
}
