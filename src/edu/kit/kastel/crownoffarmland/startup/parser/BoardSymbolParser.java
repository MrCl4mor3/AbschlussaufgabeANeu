package edu.kit.kastel.crownoffarmland.startup.parser;

import edu.kit.kastel.crownoffarmland.startup.result.StartupError;
import edu.kit.kastel.crownoffarmland.startup.result.StartupResult;

import java.util.Objects;

/**
 * Parses board symbol file content.
 *
 * @author ucgdi
 */
public class BoardSymbolParser implements ContentParser<String> {
    private static final int BOARD_SYMBOL_COUNT = 29;
    private static final char LINE_FEED = '\n';
    private static final char CARRIAGE_RETURN = '\r';

    private static final String MORE_THAN_ONE_LINE_ERROR =
            "The Board file must contain exactly one line of symbols, but found multiple lines.";
    private static final String INVALID_BOARD_SYMBOLS_ERROR =
            "The Board file must contain exactly " + BOARD_SYMBOL_COUNT + " symbols, but found %d.";

    @Override
    public StartupResult<String> parse(String content) {
        Objects.requireNonNull(content);

        String normalizedContent = StartupError.removeTrailingLineBreaks(content);

        if (containsLineBreak(normalizedContent)) {
            return StartupError.error(MORE_THAN_ONE_LINE_ERROR);
        }
        if (normalizedContent.length() != BOARD_SYMBOL_COUNT) {
            return StartupError.error(INVALID_BOARD_SYMBOLS_ERROR, normalizedContent.length());
        }

        return StartupResult.success(normalizedContent);
    }

    private boolean containsLineBreak(String content) {
        return content.indexOf(LINE_FEED) >= 0 || content.indexOf(CARRIAGE_RETURN) >= 0;
    }
}