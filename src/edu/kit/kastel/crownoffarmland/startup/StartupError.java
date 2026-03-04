package edu.kit.kastel.crownoffarmland.startup;

import edu.kit.kastel.crownoffarmland.startup.config.StartupKey;

import java.util.Iterator;

/**
 * Utility class for handling startup errors and formatting error messages.
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
     * Formats an error message with the given format and arguments, and returns a StartupResult containing the error message.
     * @param format the format string for the error message
     * @param args the arguments to be formatted into the error message
     * @return a StartupResult containing the formatted error message
     * @param <T> the type of the StartupResult
     */
    public static <T> StartupResult<T> error(String format, Object... args) {
        return StartupResult.error(PREFIX + String.format(format, args));
    }

    /**
     * Joins the keys of the given iterable of StartupKey objects into a single string, separated by a comma and a space.
     * @param keys the iterable of StartupKey objects whose keys are to be joined
     * @return a string containing the joined keys of the StartupKey objects, separated by a comma and a space
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
        return  sb.toString();
    }

    /**
     * Removes a single trailing line break from the input string, if it exists. If the input is null, it returns null.
     * @param input the input string from which to remove a single trailing line break
     * @return the input string with a single trailing line break removed, or null if the input is null
     */
    public static String dropSingleTrailingLineBreak(String input) {
        if (input == null) {
            return null;
        }
        return input.replaceAll(TRAILING_LINE_BREAKS_REGEX, "");
    }
}
