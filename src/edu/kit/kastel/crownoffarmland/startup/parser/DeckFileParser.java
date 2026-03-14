package edu.kit.kastel.crownoffarmland.startup.parser;


import edu.kit.kastel.crownoffarmland.model.team.DrawPile;
import edu.kit.kastel.crownoffarmland.startup.result.StartupError;
import edu.kit.kastel.crownoffarmland.startup.result.StartupResult;

import java.util.Arrays;
import java.util.List;

/**
 * This class implements the ContentParser interface to parse a deck configuration from a string input. The expected format of the input
 * is a list of non-negative integers, each representing the count of a specific card type in the deck. The parser checks that the total
 * number of cards in the deck equals 40 and that each entry is a valid non-negative integer. If the input is valid, it returns a
 * StartupResult containing an array of integers representing the card counts. If the input is invalid, it returns a StartupResult
 * containing an appropriate error message.
 *
 * @author ucgdi
 */
public class DeckFileParser implements ContentParser<int[]> {


    private static final String INVALID_DECK_SIZE_ERROR = "Invalid deck size: expected %d cards, but got %d.";
    private static final String DECK_FILE_EMPTY_ERROR = "The deck file is empty.";
    private static final String INVALID_ENTRY_ERROR = "Invalid card count in deck file: '%s' is not a valid non-negative integer.";
    private static final int MUST_NOT_NEGATIVE = 0;


    private final int expectedLines;

    /**
     * Creates a new DeckFileParser with the specified expected number of lines (card types) in the deck file.
     * @param expectedLines the expected number of lines (card types) in the deck file
     */
    public DeckFileParser(int expectedLines) {
        this.expectedLines = expectedLines;
    }

    @Override
    public StartupResult<int[]> parse(String content) {
        String normalizedContent = StartupError.dropSingleTrailingLineBreak(content);

        if (normalizedContent == null || normalizedContent.isEmpty()) {
            return StartupError.error(DECK_FILE_EMPTY_ERROR);
        }


        List<String> lines = splitIntoLines(normalizedContent);
        if (lines.size() != expectedLines) {
            return StartupError.error(INVALID_DECK_SIZE_ERROR, expectedLines, lines.size());
        }

        int sum = 0;
        int[] cardCounts = new int[expectedLines];

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i).trim();
            StartupResult<Integer> parseResult = parseNonNegativeInt(line);
            if (parseResult.isError()) {
                return StartupError.error(INVALID_ENTRY_ERROR, line);
            }
            int count = parseResult.getValue();
            cardCounts[i] = count;
            sum += count;
        }
        if (sum != DrawPile.getStartSizeDefault()) {
            return StartupError.error(INVALID_DECK_SIZE_ERROR, DrawPile.getStartSizeDefault(), sum);
        }

        return StartupResult.success(cardCounts);
    }

    private StartupResult<Integer> parseNonNegativeInt(String rawContent) {
        try {
            int value = Integer.parseInt(rawContent);
            if (value < MUST_NOT_NEGATIVE) {
                return StartupError.error("Negative card count in deck file: '%s' is not allowed.", rawContent);
            }
            return StartupResult.success(value);
        } catch (NumberFormatException e) {
            return StartupError.error("Invalid card count in deck file: '%s' is not a valid integer.", rawContent);
        }
    }

    private List<String> splitIntoLines(String content) {
        return Arrays.asList(content.split(System.lineSeparator()));
    }
}
