package edu.kit.kastel.crownoffarmland.startup.parser;

import edu.kit.kastel.crownoffarmland.startup.result.StartupError;
import edu.kit.kastel.crownoffarmland.startup.result.StartupResult;

/**
 * Parses seed values.
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