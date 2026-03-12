package edu.kit.kastel.crownoffarmland.startup;

import edu.kit.kastel.crownoffarmland.model.RandomGenerator;
import edu.kit.kastel.crownoffarmland.model.units.UnitTemplate;
import edu.kit.kastel.crownoffarmland.startup.config.StartupKey;
import edu.kit.kastel.crownoffarmland.startup.config.Verbosity;
import edu.kit.kastel.crownoffarmland.startup.context.StartupContext;
import edu.kit.kastel.crownoffarmland.startup.context.StartupDecks;
import edu.kit.kastel.crownoffarmland.startup.context.StartupOutput;
import edu.kit.kastel.crownoffarmland.startup.context.StartupTeams;
import edu.kit.kastel.crownoffarmland.startup.parser.BoardSymbolParser;
import edu.kit.kastel.crownoffarmland.startup.parser.DeckFileParser;
import edu.kit.kastel.crownoffarmland.startup.parser.RawArgsParser;
import edu.kit.kastel.crownoffarmland.startup.parser.SeedParser;
import edu.kit.kastel.crownoffarmland.startup.parser.UnitFileParser;
import edu.kit.kastel.crownoffarmland.startup.result.StartupError;
import edu.kit.kastel.crownoffarmland.startup.result.StartupResult;
import edu.kit.kastel.crownoffarmland.ui.renderer.board.boardsymbols.CustomBoardSymbolSet;
import edu.kit.kastel.crownoffarmland.ui.renderer.board.boardsymbols.StandardBoardSymbolSet;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

/**
 * Loads and validates all startup configuration from the command line arguments.
 *
 * @author ucgdi
 */
public final class StartupLoader {

    private static final String FILE_NOT_FOUND_ERROR = "File not found: %s";
    private static final int MAX_TEAM_NAME_LENGTH = 14;
    private static final String STANDARD_TEAM1_NAME = "Player";
    private static final String STANDARD_TEAM2_NAME = "Enemy";
    private static final String INVALID_TEAMNAME_ERROR =
            "Invalid team name(s): '%s', '%s'. Team names must be at most %d characters long.";
    private static final String INVALID_VERBOSITY_NAME =
            "Invalid verbosity level: '%s'. Valid options are: 'all', 'compact'.";

    private final RawArgsParser argsParser;
    private final SeedParser seedParser;
    private final BoardSymbolParser boardSymbolParser;
    private final UnitFileParser unitFileParser;

    /**
     * Constructs a new StartupLoader instance.
     */
    public StartupLoader() {
        this.argsParser = new RawArgsParser();
        this.seedParser = new SeedParser();
        this.boardSymbolParser = new BoardSymbolParser();
        this.unitFileParser = new UnitFileParser();
    }

    /**
     * Creates a validated startup context from the raw command line arguments.
     *
     * @param args the raw command line arguments
     * @return a startup result containing either the validated startup context or an error
     */
    public StartupResult<StartupContext> createStartupContext(String[] args) {
        StartupResult<Map<StartupKey, String>> parseResult = argsParser.parseRawArgs(args);
        if (parseResult.isError()) {
            return StartupError.error(parseResult.getErrorMessage());
        }

        Map<StartupKey, String> arguments = parseResult.getValue();
        StartupContext context = StartupContext.empty();
        StartupResult<StartupContext> stepResult;

        stepResult = stepSeed(arguments, context);
        if (stepResult.isError()) {
            return StartupError.error(stepResult.getErrorMessage());
        }
        context = stepResult.getValue();

        stepResult = stepBoard(arguments, context);
        if (stepResult.isError()) {
            return StartupError.error(stepResult.getErrorMessage());
        }
        context = stepResult.getValue();

        stepResult = stepUnits(arguments, context);
        if (stepResult.isError()) {
            return StartupError.error(stepResult.getErrorMessage());
        }
        context = stepResult.getValue();

        stepResult = stepDecks(arguments, context);
        if (stepResult.isError()) {
            return StartupError.error(stepResult.getErrorMessage());
        }
        context = stepResult.getValue();

        stepResult = stepTeams(arguments, context);
        if (stepResult.isError()) {
            return StartupError.error(stepResult.getErrorMessage());
        }
        context = stepResult.getValue();

        stepResult = stepVerbosity(arguments, context);
        if (stepResult.isError()) {
            return StartupError.error(stepResult.getErrorMessage());
        }

        return stepResult;
    }

    private StartupResult<StartupContext> stepSeed(Map<StartupKey, String> arguments, StartupContext context) {
        String rawSeed = arguments.get(StartupKey.SEED);
        StartupResult<Long> seedResult = seedParser.parse(rawSeed);
        if (seedResult.isError()) {
            return StartupError.error(seedResult.getErrorMessage());
        }

        RandomGenerator randomGenerator = new RandomGenerator(seedResult.getValue());
        return StartupResult.success(context.withRandomGenerator(randomGenerator));
    }

    private StartupResult<StartupContext> stepBoard(Map<StartupKey, String> arguments, StartupContext context) {
        String rawBoardPath = arguments.get(StartupKey.BOARD);

        if (rawBoardPath == null || rawBoardPath.isEmpty()) {
            StartupOutput output = context.getOutput().withBoardSymbolSet(new StandardBoardSymbolSet());
            return StartupResult.success(context.withOutput(output));
        }

        StartupResult<String> fileContentResult = readFileContent(rawBoardPath);
        if (fileContentResult.isError()) {
            return StartupError.error(fileContentResult.getErrorMessage());
        }

        StartupResult<String> boardResult = boardSymbolParser.parse(fileContentResult.getValue());
        if (boardResult.isError()) {
            return StartupError.error(boardResult.getErrorMessage());
        }

        StartupOutput output = context.getOutput()
                .withBoardSymbolSet(new CustomBoardSymbolSet(boardResult.getValue()));
        return StartupResult.success(context.withOutput(output));
    }

    private StartupResult<StartupContext> stepUnits(Map<StartupKey, String> arguments, StartupContext context) {
        String rawUnitsPath = arguments.get(StartupKey.UNITS);
        StartupResult<String> fileContentResult = readFileContent(rawUnitsPath);
        if (fileContentResult.isError()) {
            return StartupError.error(fileContentResult.getErrorMessage());
        }

        StartupResult<List<UnitTemplate>> unitsResult = unitFileParser.parse(fileContentResult.getValue());
        if (unitsResult.isError()) {
            return StartupError.error(unitsResult.getErrorMessage());
        }

        return StartupResult.success(context.withUnitTemplates(unitsResult.getValue()));
    }

    private StartupResult<StartupContext> stepDecks(Map<StartupKey, String> arguments, StartupContext context) {
        int expectedLines = context.getUnitTemplates().size();
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

            StartupDecks decks = StartupDecks.mirrored(deckResult.getValue());
            return StartupResult.success(context.withDecks(decks));
        }

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

        StartupDecks decks = StartupDecks.of(deckResultTeam1.getValue(), deckResultTeam2.getValue());
        return StartupResult.success(context.withDecks(decks));
    }

    private StartupResult<StartupContext> stepTeams(Map<StartupKey, String> arguments, StartupContext context) {
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

        StartupTeams teams = StartupTeams.of(team1Name, team2Name);
        return StartupResult.success(context.withTeams(teams));
    }

    private StartupResult<StartupContext> stepVerbosity(Map<StartupKey, String> arguments, StartupContext context) {
        String rawVerbosity = arguments.get(StartupKey.VERBOSITY);

        if (rawVerbosity == null || rawVerbosity.isEmpty()) {
            StartupOutput output = context.getOutput().withVerbosity(Verbosity.ALL);
            return StartupResult.success(context.withOutput(output));
        }

        Verbosity verbosity = Verbosity.fromString(rawVerbosity);
        if (verbosity == null) {
            return StartupError.error(INVALID_VERBOSITY_NAME, rawVerbosity);
        }

        StartupOutput output = context.getOutput().withVerbosity(verbosity);
        return StartupResult.success(context.withOutput(output));
    }

    private StartupResult<String> readFileContent(String filePath) {
        try {
            byte[] encoded = Files.readAllBytes(Paths.get(filePath));
            String content = new String(encoded, StandardCharsets.UTF_8);
            printRawFileContent(content);
            return StartupResult.success(content);
        } catch (IOException e) {
            return StartupError.error(FILE_NOT_FOUND_ERROR, filePath);
        }
    }

    private void printRawFileContent(String content) {
        System.out.print(content);
    }
}