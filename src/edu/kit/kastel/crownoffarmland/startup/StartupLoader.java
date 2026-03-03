package edu.kit.kastel.crownoffarmland.startup;

import edu.kit.kastel.crownoffarmland.gameplay.GameHandler;
import edu.kit.kastel.crownoffarmland.model.Game;
import edu.kit.kastel.crownoffarmland.model.RandomGenerator;
import edu.kit.kastel.crownoffarmland.model.team.Team;
import edu.kit.kastel.crownoffarmland.model.units.StatusValue;
import edu.kit.kastel.crownoffarmland.model.units.UnitName;
import edu.kit.kastel.crownoffarmland.model.units.UnitTemplate;
import edu.kit.kastel.crownoffarmland.util.FileLoader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;

public final class StartupLoader {
    private static final String Utility_CLASS_CONSTRUCTOR_MESSAGES = "Utility classes cannot be instantiated";
    private static final int DECK_SIZE_REQUIRED = 40;
    private static final int MAX_TEAMNAME_LENGTH = 14;
    private static final int BOARD_SYMBOL_COUNT = 29;
    private static final int MAX_UNITS = 80;

    private static final String ARGUMENT_DELIMITER = "=";
    private static final int KEY_VALUE_SPLIT_LIMIT = 2;
    private static final String ARGUMENT_LIST_SEPARATOR = ", ";

    private static final String ERROR_PREFIX = "Error: ";
    private static final String INVALID_ARGUMENT_FORMAT_ERROR = "Invalid argument format: '%s'. Expected format: key=value.";
    private static final String DUPLICATE_ARGUMENT_ERROR = "Duplicate argument(s) found: %s. Each argument should be unique.";
    private static final String MISSING_MANDATORY_ARGUMENT_ERROR = "Missing required argument(s): %s.";
    private static final String UNKNOWN_ARGUMENT_ERROR = "Unknown argument key: '%s'.";
    private static final String INVALID_DECK_CONFIG_ERROR = "Invalid deck configuration. Use either deck=... OR deck1=... and deck2=... " +
            "(not both). Given: %s.";
    private static final String INVALID_VERBOSITY_ERROR = "Invalid verbosity: '%s'. Allowed values: all, compact.";
    private static final String INVALID_SEED_ERROR = "Invalid seed: '%s'. Expected a signed 64-bit integer.";
    private static final String FILE_NOT_FOUND_ERROR = "File not found: '%s'";
    private static final String INVALID_BOARD_SYMBOLS_ERROR = "Invalid board symbols. Expected exactly one line with %d characters";
    private static final String TOO_MANY_UNITS_ERROR = "Too many units (max %d), but there are %d!";
    private static final String FILE_IS_EMPTY_ERROR = "File '%s' is empty!";
    private static final String INVALID_UNIT_LINE = "Invalid unit line: '%s'.";


    private StartupLoader() {
        throw new UnsupportedOperationException(Utility_CLASS_CONSTRUCTOR_MESSAGES);
    }

    public static StartupResult<GameHandler> createGameHandler(String[] args) {

        StartupResult<Map<StartupKey, String>> raw = parseRawArgs(args);
        if (!raw.isSuccess()) {
            return StartupResult.error(raw.getErrorMessage());
        }

        Map<StartupKey, String> arguments = raw.getValue();
        DeckConfigMode deckMode = validateDeckConfig(arguments).getValue();

        StartupResult<Long> seedRes = parseSeed(arguments.get(StartupKey.SEED));
        if (!seedRes.isSuccess()) {
            return StartupResult.error(seedRes.getErrorMessage());
        }
        RandomGenerator generator = new RandomGenerator(seedRes.getValue());
        //ToDO: Board, Units, decks und anschließend Team erstellen, verbosity prüfen


        //ToDO: unötig komplex // wenn erstes if false --> StandardSymbolsatz // wenn boardSymbols.isSucess --> angepassten Symbolsatz
        if (arguments.containsKey(StartupKey.BOARD)) {
            StartupResult<String> boardSymbolPath = readFileSafe(arguments.get(StartupKey.BOARD));
            if (!boardSymbolPath.isSuccess()) {
                return StartupResult.error(boardSymbolPath.getErrorMessage());
            } else {
                StartupResult<String> boardSymbols = validateBoardSymbols(arguments.get(StartupKey.BOARD));
                if (!boardSymbols.isSuccess()) {
                    return StartupResult.error(boardSymbols.getErrorMessage());
                }
            }
        }

        //ToDo: Nun müsen die Units eingelesen werden --> mit Semicolon getrennt --> Liste an Einheiten erstellen
        StartupResult<String> unitsPath = readFileSafe(arguments.get(StartupKey.UNITS));
        if (!unitsPath.isSuccess()) {
            return StartupResult.error(unitsPath.getErrorMessage());
        }
        StartupResult<List<UnitTemplate>> units = parseUnits(arguments.get(StartupKey.UNITS));


        //ToDo: Nun die Decks entsprechend der Deckconfig erstellen --> Teams erstellen und dann das Game final erstellen (siehe hier als
        // Bsp) --> Defaultnamen, falls nichts angegeben
        Team team1 = new Team(null,null,null,null);
        Team team2 = new Team(null,null,null,null);
        Game game = new Game(team1,team2, generator);
        return StartupResult.success(new GameHandler(game));
    }


    private static StartupResult<Map<StartupKey, String>> parseRawArgs(String[] args) {
        Map<StartupKey, String> arguments = new EnumMap<StartupKey, String>(StartupKey.class);
        EnumSet<StartupKey> duplicates = EnumSet.noneOf(StartupKey.class);

        for (String arg : args) {
            String[] keyValue = arg.split(ARGUMENT_DELIMITER, KEY_VALUE_SPLIT_LIMIT);

            // Split at first '=' only
            if (keyValue.length != KEY_VALUE_SPLIT_LIMIT) {
                return StartupResult.error(ERROR_PREFIX + String.format(INVALID_ARGUMENT_FORMAT_ERROR, arg));
            }

            String rawKey = keyValue[0].trim();
            String rawValue = keyValue[1].trim();

            // key/value must not be empty
            if (rawKey.isEmpty() || rawValue.isEmpty()) {
                return StartupResult.error(ERROR_PREFIX + String.format(INVALID_ARGUMENT_FORMAT_ERROR, arg));
            }

            // try to parse key
            StartupKey key = StartupKey.fromString(rawKey);
            if (key == null) {
                return StartupResult.error(ERROR_PREFIX + String.format(UNKNOWN_ARGUMENT_ERROR, rawKey));
            }


            if (arguments.containsKey(key)) {
                duplicates.add(key);
                continue;
            }

            arguments.put(key, rawValue);
        }

        if (!duplicates.isEmpty()) {
            return StartupResult.error(ERROR_PREFIX + String.format(DUPLICATE_ARGUMENT_ERROR, joinKeys(duplicates)));
        }

        List<StartupKey> missingRequiredKeys = getMissingRequiredKeys(arguments);
        if (!missingRequiredKeys.isEmpty()) {
            return StartupResult.error(ERROR_PREFIX + String.format(MISSING_MANDATORY_ARGUMENT_ERROR, joinKeys(missingRequiredKeys)));
        }

        StartupResult<DeckConfigMode> deckConfig = validateDeckConfig(arguments);
        if (!deckConfig.isSuccess()) {
            return StartupResult.error(deckConfig.getErrorMessage());
        }

        return StartupResult.success(arguments);
    }

    private static List<StartupKey> getMissingRequiredKeys(Map<StartupKey, String> arguments) {
        List<StartupKey> missingKeys = new ArrayList<>();
        for (StartupKey key : StartupKey.getRequiredKeys()) {
            if (!arguments.containsKey(key)) {
                missingKeys.add(key);
            }
        }
        return  missingKeys;
    }

    private static StartupResult<DeckConfigMode> validateDeckConfig(Map<StartupKey, String> arguments) {
        boolean hasDeck = arguments.containsKey(StartupKey.DECK);
        boolean hasDeck1 = arguments.containsKey(StartupKey.DECK1);
        boolean hasDeck2 = arguments.containsKey(StartupKey.DECK2);

        if (hasDeck && (hasDeck1 || hasDeck2)) {
            return StartupResult.error(ERROR_PREFIX + String.format(INVALID_DECK_CONFIG_ERROR, joinKeys(arguments.keySet())));
        } else if (hasDeck) {
            return StartupResult.success(DeckConfigMode.SHARED_DECK);
        } else if (hasDeck1 && hasDeck2) {
            return StartupResult.success(DeckConfigMode.SPLIT_DECKS);
        } else  {
            return StartupResult.error(ERROR_PREFIX + String.format(INVALID_DECK_CONFIG_ERROR, joinKeys(arguments.keySet())));
        }
    }

    private static String joinKeys(Iterable<StartupKey> keys) {
        StringBuilder sb = new StringBuilder();
        for (StartupKey key : keys) {
            if (sb.length() > 0) {
                sb.append(ARGUMENT_LIST_SEPARATOR);
            }
            sb.append(key.getKey());
        }
        return sb.toString();
    }

    private static StartupResult<Long> parseSeed(String raw) {
        if (raw.trim().isEmpty()) {
            return StartupResult.error(ERROR_PREFIX + String.format(INVALID_SEED_ERROR, raw));
        }
        String trimmed = raw.trim();
        try {
            long seed = Long.parseLong(trimmed);
            return StartupResult.success(seed);
        } catch (NumberFormatException e) {
            return StartupResult.error(ERROR_PREFIX + String.format(INVALID_SEED_ERROR, raw));
        }
    }

    private static StartupResult<String> readFileSafe(String path) {
        try {
            String content = FileLoader.readFileFromPath(path);
            System.out.println(content);
            return StartupResult.success(content);
        } catch (IOException e) {
            return StartupResult.error(ERROR_PREFIX + String.format(FILE_NOT_FOUND_ERROR, path));
        }
    }

    private static StartupResult<String> validateBoardSymbols(String rawContent) {
        return StartupResult.success(rawContent);
    }

    private static StartupResult<List<UnitTemplate>> parseUnits(String path) {
        String rawFileContent = readFileSafe(path).getValue();
        if(rawFileContent.isEmpty()) {
            return StartupResult.error(ERROR_PREFIX + String.format(FILE_IS_EMPTY_ERROR, rawFileContent));
        }

        String[] lines = rawFileContent.split(System.lineSeparator());
        if (lines.length > MAX_UNITS) {
            return StartupResult.error(ERROR_PREFIX + String.format(TOO_MANY_UNITS_ERROR, MAX_UNITS, lines.length));
        }

        List<UnitTemplate> unitTemplates = new ArrayList<>();
        for (String line : lines) {
            String[] parts = line.split(";", -1);
            if (parts.length != 4) {
                return StartupResult.error(ERROR_PREFIX + String.format(INVALID_UNIT_LINE, line));
            }
            String qualificator = parts[0];
            String role = parts[1];
            StartupResult<Integer> atk = parseNonNegativeInt(parts[2]);
            StartupResult<Integer> def = parseNonNegativeInt(parts[3]);
            if (!atk.isSuccess() && !def.isSuccess()) {
                return StartupResult.error("invalid ATK/DEF");
            }
            unitTemplates.add(new UnitTemplate(new UnitName(qualificator, role), new StatusValue(atk.getValue(), def.getValue())));
        }
        return StartupResult.success(unitTemplates);
    }

    private static StartupResult<Integer> parseNonNegativeInt(String rawContent) {
        if (rawContent.isEmpty()) {
            return StartupResult.error("Test");
        }
        try {
            int value = Integer.parseInt(rawContent);
            if (value < 0) {
                return StartupResult.error("negativ");
            } else {
                return StartupResult.success(value);
            }
        } catch (NumberFormatException e) {
            return StartupResult.error("out of range");
        }
    }
}