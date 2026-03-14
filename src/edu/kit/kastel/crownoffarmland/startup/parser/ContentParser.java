package edu.kit.kastel.crownoffarmland.startup.parser;

import edu.kit.kastel.crownoffarmland.startup.result.StartupResult;

/**
 * Parses string content into a value.
 *
 * @param <T> the parsed value type
 *
 * @author ucgdi
 */
public interface ContentParser<T> {

    /**
     * Parses the given content.
     *
     * @param content the content to parse
     * @return the parsing result
     */
    StartupResult<T> parse(String content);
}