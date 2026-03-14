package edu.kit.kastel.crownoffarmland.startup.result;

import edu.kit.kastel.crownoffarmland.startup.config.StartupKey;

import java.util.Iterator;

/**
 * Provides utility methods for startup errors.
 *
 * @author ucgdi
 */
public final class StartupError {
    private static final String UTILITY_CLASSES_CANNOT_BE_INSTANTIATED = "Utility classes cannot be instantiated";
    private static final String LINE_BREAK_REGEX = "\\R";
    private static final String TRAILING_LINE_BREAKS_REGEX = LINE_BREAK_REGEX + "\\z";
    private static final String PREFIX = "ERROR: ";
    private static final String LIST_SEPARATOR = ", ";

    private StartupError() {
        throw new UnsupportedOperationException(UTILITY_CLASSES_CANNOT_BE_INSTANTIATED);
    }

    /**
     * Returns an error result with a formatted message.
     *
     * @param format the error message format
     * @param args the format arguments
     * @param <T> the result type
     * @return the error result
     */
    public static <T> StartupResult<T> error(String format, Object... args) {
        return StartupResult.error(String.format(format, args));
    }

    /**
     * Returns a formatted error message with a prefix.
     *
     * @param format the error message format
     * @param args the format arguments
     * @return the formatted error message
     */
    public static String formatErrorMessage(String format, Object... args) {
        return PREFIX + String.format(format, args);
    }

    /**
     * Joins startup keys into a comma-separated string.
     *
     * @param keys the startup keys
     * @return the joined key string
     */
    public static String joinKeys(Iterable<StartupKey> keys) {
        StringBuilder sb = new StringBuilder();
        Iterator<StartupKey> iterator = keys.iterator();

        while (iterator.hasNext()) {
            sb.append(iterator.next().getKey());
            if (iterator.hasNext()) {
                sb.append(LIST_SEPARATOR);
            }
        }

        return sb.toString();
    }

    /**
     * Removes trailing line breaks from the given string.
     *
     * @param input the input string
     * @return the trimmed string, or {@code null} if the input is {@code null}
     */
    public static String removeTrailingLineBreaks(String input) {
        if (input == null) {
            return null;
        }
        return input.replaceAll(TRAILING_LINE_BREAKS_REGEX, "");
    }
}