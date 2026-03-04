package edu.kit.kastel.crownoffarmland.startup.parser;


import edu.kit.kastel.crownoffarmland.startup.StartupError;
import edu.kit.kastel.crownoffarmland.startup.StartupResult;

/**
 * This class implements the ContentParser interface to parse a seed value from a string input. The seed is expected to be a signed
 * 64-bit integer. The parser trims the input string and checks if it is empty. If the input is empty, it returns a StartupResult
 * containing an error message indicating that the seed is invalid. If the input is not empty, it attempts to parse the string into a
 * long value. If the parsing is successful, it returns a StartupResult containing the parsed seed value. If the parsing fails due to a
 * NumberFormatException, it returns a StartupResult containing an error message indicating that the seed is invalid.
 *
 * @author ucgdi
 */
public class SeedParser implements ContentParser<Long> {


    private static final String INVALID_SEED_ERROR = "Invalid seed: '%s'. Expected a signed 64-bit integer.";

    @Override
    public StartupResult<Long> parse(String content) {
        if (content.trim().isEmpty()) {
            return StartupError.error(INVALID_SEED_ERROR, content);
        }

        String trimmed = content.trim();

        try {
            long seed = Long.parseLong(trimmed);
            return StartupResult.success(seed);
        } catch (NumberFormatException e) {
            return StartupError.error(INVALID_SEED_ERROR, content);
        }
    }
}
