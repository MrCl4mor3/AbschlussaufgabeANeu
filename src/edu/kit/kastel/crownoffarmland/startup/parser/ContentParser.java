package edu.kit.kastel.crownoffarmland.startup.parser;

import edu.kit.kastel.crownoffarmland.startup.StartupResult;

/**
 * This interface defines a contract for parsing content of type T. It contains a single method, parse, which takes a String input and
 * returns a StartupResult containing either the parsed value of type T or an error message if the parsing fails. Implementations of this
 * interface can be used to parse various types of content, such as configuration files, command line arguments, or any other
 * string-based input that needs to be converted into a specific data structure.
 * @param <T> the type of the parsed value that the parser will return upon successful parsing
 *
 * @author ucgdi
 */
public interface ContentParser<T> {
    /**
     * Parses the given content and returns a result containing either the parsed value or an error message.
     *
     * @param content the content to parse
     * @return a StartupResult containing the parsed value or an error message
     */
    StartupResult<T> parse(String content);
}
