package edu.kit.kastel.crownoffarmland.startup;

import edu.kit.kastel.crownoffarmland.gameplay.GameHandler;
import edu.kit.kastel.crownoffarmland.model.RandomGenerator;
import edu.kit.kastel.crownoffarmland.model.units.UnitTemplate;
import edu.kit.kastel.crownoffarmland.startup.config.StartupKey;
import edu.kit.kastel.crownoffarmland.startup.config.Verbosity;
import edu.kit.kastel.crownoffarmland.startup.parser.BoardSymbolParser;
import edu.kit.kastel.crownoffarmland.startup.parser.DeckFileParser;
import edu.kit.kastel.crownoffarmland.startup.parser.RawArgsParser;
import edu.kit.kastel.crownoffarmland.startup.parser.SeedParser;
import edu.kit.kastel.crownoffarmland.startup.parser.UnitFileParser;
import edu.kit.kastel.crownoffarmland.util.FileLoader;



import java.util.List;
import java.util.Map;

/**
 * The StartupLoader class is responsible for processing the command-line arguments provided at the start of the program and creating a
 * GameHandler instance based on those arguments. It follows a multi-step process to parse and validate the arguments, ensuring that all
 * necessary configurations are correctly set up before initializing the game. The StartupLoader uses various parsers to handle different
 * aspects of the configuration, such as the random seed, board symbols, unit templates, and deck configurations. If any step of the
 * parsing or validation process fails, the StartupLoader returns an appropriate error message, allowing the user to understand what went
 * wrong and how to fix it.
 *
 * @author ucgdi
 */
public final class StartupLoader {

    private static final String FILE_NOT_FOUND_ERROR = "File not found: %s";
    private static final int MAX_TEAM_NAME_LENGTH = 14;
    private static final String STANDARD_TEAM1_NAME = "Player";
    private static final String STANDARD_TEAM2_NAME = "Enemy";
    private static final String INVALID_TEAMNAME_ERROR = "Invalid team name(s): '%s', '%s'. Team names must be at most %d characters long.";
    private final RawArgsParser argsParser;
    private final SeedParser seedParser;
    private final BoardSymbolParser boardSymbolParser;
    private final UnitFileParser unitFileParser;

    /**
     * Constructs a new StartupLoader instance and initializes the necessary parsers for processing the startup configuration.
     *
     * @author ucgdi
     */
    public StartupLoader() {
        argsParser = new RawArgsParser();
        seedParser = new SeedParser();
        boardSymbolParser = new BoardSymbolParser();
        unitFileParser = new UnitFileParser();
    }

    /**
     * This method is responsible for creating a GameHandler instance based on the provided command-line arguments. It follows a
     * multi-step process:
     * @param args The raw command-line arguments provided at the start of the program. These arguments are expected to contain key-value
     *     pairs that configure the game.
     * @return A StartupResult containing either a GameHandler instance if the creation was successful, or an error message if there was
     *     an issue during the parsing or validation of the arguments. The method first parses the raw arguments into a structured format,
     *     then validates the required keys and their values, and finally creates a GameHandler instance based on the validated
     *     argument. If any step fails, it returns an appropriate error message.
     */
    public StartupResult<GameHandler> createGameHandler(String[] args) {

        // 1. Parse raw arguments into a structured format (map of StartupKey to String)
        StartupResult<Map<StartupKey, String>> parseResult = argsParser.parseRawArgs(args);
        if (parseResult.isError()) {
            return StartupError.error(parseResult.getErrorMessage());
        }

        Map<StartupKey, String> arguments = parseResult.getValue();
        StartupContext container = StartupContext.empty();
        StartupResult<StartupContext> parseRes;

        // 2. Validate in the correct order
        parseRes = stepSeed(arguments, container);
        if (parseRes.isError()) {
            return StartupError.error(parseRes.getErrorMessage());
        }
        container = parseRes.getValue();

        parseRes = stepBoard(arguments, container);
        if (parseRes.isError()) {
            return StartupError.error(parseRes.getErrorMessage());
        }
        container = parseRes.getValue();

        parseRes = stepUnits(arguments, container);
        if (parseRes.isError()) {
            return StartupError.error(parseRes.getErrorMessage());
        }
        container = parseRes.getValue();

        parseRes = stepDecks(arguments, container);
        if (parseRes.isError()) {
            return StartupError.error(parseRes.getErrorMessage());
        }
        container = parseRes.getValue();

        parseRes = stepTeams(arguments, container);
        if (parseRes.isError()) {
            return StartupError.error(parseRes.getErrorMessage());
        }
        container = parseRes.getValue();

        parseRes = stepVerbosity(arguments, container);
        if (parseRes.isError()) {
            return StartupError.error(parseRes.getErrorMessage());
        }
        container = parseRes.getValue();

        StartupResult<GameHandler> handlerRes = buildGameHandler(container);
        if (handlerRes.isError()) {
            return StartupError.error(handlerRes.getErrorMessage());
        }
        return handlerRes;
    }

    private StartupResult<StartupContext> stepSeed(Map<StartupKey, String> arguments, StartupContext container) {
        String rawSeed = arguments.get(StartupKey.SEED);
        StartupResult<Long> seedResult = seedParser.parse(rawSeed);
        if (seedResult.isError()) {
            return StartupError.error(seedResult.getErrorMessage());
        }

        long seed = seedResult.getValue();
        RandomGenerator randomGenerator = new RandomGenerator(seed);
        return StartupResult.success(container.withSeed(seed, randomGenerator));
    }

    //ToDo StandardSymbolset, falls "board" nicht angegeben ist --> wird hier nicht abgefangen
    private StartupResult<StartupContext> stepBoard(Map<StartupKey, String> arguments, StartupContext container) {
        String rawBoardPath = arguments.get(StartupKey.BOARD);
        StartupResult<String> fileContentResult = readFileContent(rawBoardPath);
        if (fileContentResult.isError()) {
            return StartupError.error(fileContentResult.getErrorMessage());
        }
        StartupResult<String> boardResult = boardSymbolParser.parse(fileContentResult.getValue());
        if (boardResult.isError()) {
            return StartupError.error(boardResult.getErrorMessage());
        }
        return StartupResult.success(container.withBoardSymbols(boardResult.getValue()));
    }

    private StartupResult<StartupContext> stepUnits(Map<StartupKey, String> arguments, StartupContext container) {
        String rawUnitsPath = arguments.get(StartupKey.UNITS);
        StartupResult<String> fileContentResult = readFileContent(rawUnitsPath);
        if (fileContentResult.isError()) {
            return StartupError.error(fileContentResult.getErrorMessage());
        }
        StartupResult<List<UnitTemplate>> unitsResult = unitFileParser.parse(fileContentResult.getValue());
        if (unitsResult.isError()) {
            return StartupError.error(unitsResult.getErrorMessage());
        }
        return StartupResult.success(container.withUnitTemplates(unitsResult.getValue()));
    }

    private StartupResult<StartupContext> stepDecks(Map<StartupKey, String> arguments, StartupContext container) {
        int expectedLines = container.getUnitTemplates().size();
        DeckFileParser deckFileParser = new DeckFileParser(expectedLines);

        if (arguments.containsKey(StartupKey.DECK)) {
            String rawDeckPath = arguments.get(StartupKey.DECK);

            StartupResult<String> deckContent = readFileContent(rawDeckPath);
            if (deckContent.isError()) {
                return StartupError.error(deckContent.getErrorMessage());
            }

            StartupResult<int[]> deckResult = deckFileParser.parse(deckContent.getValue());
            if (deckResult.isError()) {
                return StartupError.error(deckResult.getErrorMessage());
            }
            return StartupResult.success(container.withSharedDeck(deckResult.getValue()));
        } else {
            String rawDeckPathTeam1 = arguments.get(StartupKey.DECK1);
            String rawDeckPathTeam2 = arguments.get(StartupKey.DECK2);

            StartupResult<String> deckContentTeam1 = readFileContent(rawDeckPathTeam1);
            if (deckContentTeam1.isError()) {
                return StartupError.error(deckContentTeam1.getErrorMessage());
            }

            StartupResult<String> deckContentTeam2 = readFileContent(rawDeckPathTeam2);
            if (deckContentTeam2.isError()) {
                return StartupError.error(deckContentTeam2.getErrorMessage());
            }

            StartupResult<int[]> deckResultTeam1 = deckFileParser.parse(deckContentTeam1.getValue());
            if (deckResultTeam1.isError()) {
                return StartupError.error(deckResultTeam1.getErrorMessage());
            }

            StartupResult<int[]> deckResultTeam2 = deckFileParser.parse(deckContentTeam2.getValue());
            if (deckResultTeam2.isError()) {
                return StartupError.error(deckResultTeam2.getErrorMessage());
            }

            return StartupResult.success(container.withSplitDecks(deckResultTeam1.getValue(), deckResultTeam2.getValue()));
        }
    }

    private StartupResult<StartupContext> stepTeams(Map<StartupKey, String> arguments, StartupContext container) {
        String team1Name = arguments.get(StartupKey.TEAM1);
        String team2Name = arguments.get(StartupKey.TEAM2);

        if (team1Name == null || team1Name.isEmpty()) {
            team1Name = STANDARD_TEAM1_NAME;
        }
        if (team2Name == null || team2Name.isEmpty()) {
            team2Name = STANDARD_TEAM2_NAME;
        }

        if (team1Name.length() > MAX_TEAM_NAME_LENGTH || team2Name.length() > MAX_TEAM_NAME_LENGTH) {
            return StartupError.error(INVALID_TEAMNAME_ERROR, team1Name, team2Name, MAX_TEAM_NAME_LENGTH);
        }

        return StartupResult.success(container.withTeams(team1Name, team2Name));
    }

    private StartupResult<StartupContext> stepVerbosity(Map<StartupKey, String> arguments, StartupContext container) {
        String rawVerbosity = arguments.get(StartupKey.VERBOSITY);
        if (rawVerbosity == null || rawVerbosity.isEmpty()) {
            return StartupResult.success(container.withVerbosity(Verbosity.ALL));
        }
        return null;
    }

    private StartupResult<String> readFileContent(String filePath) {
        try {
            String content = FileLoader.readFileFromPath(filePath);
            System.out.println(content);
            return StartupResult.success(content);
        } catch (Exception e) {
            return StartupError.error(FILE_NOT_FOUND_ERROR, filePath);
        }
    }

    private StartupResult<GameHandler> buildGameHandler(StartupContext context) {
        GameFactory factory = new GameFactory(context);
        return StartupResult.success(factory.getGameHandler());
    }
}
