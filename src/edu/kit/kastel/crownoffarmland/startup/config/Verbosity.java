package edu.kit.kastel.crownoffarmland.startup.config;

/**
 * Defines the available verbosity levels.
 *
 * @author ucgdi
 */
public enum Verbosity {
    /**
     * Full output.
     */
    ALL("all"),

    /**
     * Reduced output.
     */
    COMPACT("compact");

    private final String raw;

    Verbosity(String raw) {
        this.raw = raw;
    }

    /**
     * Returns the matching verbosity level for the given string.
     *
     * @param rawKey the verbosity string
     * @return the matching verbosity level, or {@code null} if none matches
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