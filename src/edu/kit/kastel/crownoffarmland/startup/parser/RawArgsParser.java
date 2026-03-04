package edu.kit.kastel.crownoffarmland.startup.parser;


import edu.kit.kastel.crownoffarmland.startup.config.DeckConfigMode;
import edu.kit.kastel.crownoffarmland.startup.StartupError;
import edu.kit.kastel.crownoffarmland.startup.config.StartupKey;
import edu.kit.kastel.crownoffarmland.startup.StartupResult;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;

/**
 * This class is responsible for parsing raw command line arguments into a structured format. It validates the format of each argument,
 * checks for duplicate keys, ensures that all required keys are present, and validates the deck configuration. The main method,
 * parseRawArgs, takes an array of strings (the raw arguments) and returns a StartupResult containing either a map of parsed arguments or
 * an error message if any validation fails.
 *
 * @author ucgdi
 */
public final class RawArgsParser {

    private static final String ARGUMENT_DELIMITER = "=";
    private static final int KEY_VALUE_SPLIT_LIMIT = 2;

    private static final String INVALID_ARGUMENT_FORMAT_ERROR =
            "Invalid argument format: '%s'. Expected format: key=value.";
    private static final String DUPLICATE_ARGUMENT_ERROR =
            "Duplicate argument(s) found: %s. Each argument should be unique.";
    private static final String MISSING_MANDATORY_ARGUMENT_ERROR =
            "Missing required argument(s): %s.";
    private static final String UNKNOWN_ARGUMENT_ERROR =
            "Unknown argument key: '%s'.";
    private static final String INVALID_DECK_CONFIG_ERROR =
            "Invalid deck configuration. Use either deck=... OR deck1=... and deck2=... (not both). Given: %s.";

    /**
     * Parses the raw command line arguments and returns a StartupResult containing either a map of parsed arguments or an error message
     * if any validation fails.
     * @param args the raw command line arguments to parse
     * @return a StartupResult containing either a map of parsed arguments or an error message if any validation fails
     */
    public StartupResult<Map<StartupKey, String>> parseRawArgs(String[] args) {
        Map<StartupKey, String> arguments = new EnumMap<StartupKey, String>(StartupKey.class);
        EnumSet<StartupKey> duplicates = EnumSet.noneOf(StartupKey.class);

        for (String arg : args) {
            String[] keyValue = arg.split(ARGUMENT_DELIMITER, KEY_VALUE_SPLIT_LIMIT);

            // Split at first '=' only
            if (keyValue.length != KEY_VALUE_SPLIT_LIMIT) {
                return StartupError.error(INVALID_ARGUMENT_FORMAT_ERROR, arg);
            }

            String rawKey = keyValue[0].trim();
            String rawValue = keyValue[1].trim();

            // key/value must not be empty
            if (rawKey.isEmpty() || rawValue.isEmpty()) {
                return StartupError.error(INVALID_ARGUMENT_FORMAT_ERROR, arg);
            }

            // try to parse key
            StartupKey key = StartupKey.fromString(rawKey);
            if (key == null) {
                return StartupError.error(UNKNOWN_ARGUMENT_ERROR, arg);
            }

            if (arguments.containsKey(key)) {
                duplicates.add(key);
                continue;
            }

            arguments.put(key, rawValue);
        }

        if (!duplicates.isEmpty()) {
            return StartupError.error(DUPLICATE_ARGUMENT_ERROR, StartupError.joinKeys(duplicates));
        }


        List<StartupKey> missingRequiredKeys = getMissingRequiredKeys(arguments);
        if (!missingRequiredKeys.isEmpty()) {
            return StartupError.error(MISSING_MANDATORY_ARGUMENT_ERROR, StartupError.joinKeys(missingRequiredKeys));
        }


        StartupResult<DeckConfigMode> deckConfig = validateDeckConfig(arguments);
        if (!deckConfig.isSuccess()) {
            return StartupResult.error(deckConfig.getErrorMessage());
        }

        return StartupResult.success(arguments);
    }



    private List<StartupKey> getMissingRequiredKeys(Map<StartupKey, String> arguments) {
        List<StartupKey> missingKeys = new ArrayList<>();
        for (StartupKey key : StartupKey.getRequiredKeys()) {
            if (!arguments.containsKey(key)) {
                missingKeys.add(key);
            }
        }
        return missingKeys;
    }

    private StartupResult<DeckConfigMode> validateDeckConfig(Map<StartupKey, String> arguments) {
        boolean hasDeck = arguments.containsKey(StartupKey.DECK);
        boolean hasDeck1 = arguments.containsKey(StartupKey.DECK1);
        boolean hasDeck2 = arguments.containsKey(StartupKey.DECK2);

        if (hasDeck && (hasDeck1 || hasDeck2)) {
            return StartupError.error(INVALID_DECK_CONFIG_ERROR, StartupError.joinKeys(arguments.keySet()));
        } else if (hasDeck) {
            return StartupResult.success(DeckConfigMode.SHARED_DECK);
        } else if (hasDeck1 && hasDeck2) {
            return StartupResult.success(DeckConfigMode.SPLIT_DECKS);
        } else  {
            return StartupError.error(INVALID_DECK_CONFIG_ERROR, StartupError.joinKeys(arguments.keySet()));
        }
    }
}
