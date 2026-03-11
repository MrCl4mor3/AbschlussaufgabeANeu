package edu.kit.kastel.crownoffarmland.startup.parser;

import edu.kit.kastel.crownoffarmland.startup.result.StartupError;
import edu.kit.kastel.crownoffarmland.startup.result.StartupResult;

import java.util.Objects;

/**
 * This class is responsible for parsing the content of a board symbol file, which should contain exactly one line of symbols.
 * It validates that the content contains exactly one line and that the number of symbols matches the expected count. If the content is
 * valid, it returns a successful StartupResult containing the normalized symbols; otherwise, it returns an error StartupResult with an
 * appropriate error message.
 * @author ucgdi
 */
public class BoardSymbolParser implements ContentParser<String> {
    private static final int BOARD_SYMBOL_COUNT = 29;
    private static final String MORE_THAN_ONE_LINE_ERROR =
            "The Board file must contain exactly one line of symbols, but found multiple lines.";

    private static final String INVALID_BOARD_SYMBOLS_ERROR =
            "The Board file must contain exactly " + BOARD_SYMBOL_COUNT + " symbols, but found %d.";


    @Override
    public StartupResult<String> parse(String content) {
        Objects.requireNonNull(content);

        String normalizedContent = StartupError.dropSingleTrailingLineBreak(content);

        if (containsLineBreak(normalizedContent)) {
            return StartupError.error(MORE_THAN_ONE_LINE_ERROR);
        }

        if (normalizedContent.length() != BOARD_SYMBOL_COUNT) {
            return StartupError.error(INVALID_BOARD_SYMBOLS_ERROR, normalizedContent.length());
        }

        return StartupResult.success(normalizedContent);
    }


    private boolean containsLineBreak(String content) {
        //ToDO: Konstanten für Zeilenumbrüche definieren und hier verwenden
        return content.contains("\n") || content.contains("\r");
    }
}
