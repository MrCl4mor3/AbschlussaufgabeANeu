package edu.kit.kastel.crownoffarmland;

import edu.kit.kastel.crownoffarmland.gameplay.GameHandler;
import edu.kit.kastel.crownoffarmland.startup.GameFactory;
import edu.kit.kastel.crownoffarmland.startup.StartupLoader;
import edu.kit.kastel.crownoffarmland.startup.context.StartupContext;
import edu.kit.kastel.crownoffarmland.startup.context.StartupOutput;
import edu.kit.kastel.crownoffarmland.startup.result.StartupError;
import edu.kit.kastel.crownoffarmland.startup.result.StartupResult;
import edu.kit.kastel.crownoffarmland.ui.commands.CommandHandler;
import edu.kit.kastel.crownoffarmland.ui.renderer.board.BoardEntityTokenFormatter;
import edu.kit.kastel.crownoffarmland.ui.renderer.board.BoardRenderer;

/**
 * This is the main entry class for the program.
 *
 * @author Programmieren-Team
 * @author ucgdi
 */
public final class Application {
    private static final String UTILITY_CLASS_CONSTRUCTOR_MESSAGES = "Utility classes cannot be instantiated";
    private static final String EMPTY_ARGUMENTS_ERROR = "No arguments found.";

    private Application() {
        throw new UnsupportedOperationException(UTILITY_CLASS_CONSTRUCTOR_MESSAGES);
    }

    /**
     * This is the main entry point for the program. There are no arguments expected.
     * If there are arguments, an error will be thrown.
     *
     * @param args The command line arguments given at the start of the program
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println(EMPTY_ARGUMENTS_ERROR);
            return;
        }

        StartupLoader loader = new StartupLoader();
        StartupResult<StartupContext> result = loader.createStartupContext(args);

        if (result.isError()) {
            System.err.println(StartupError.formatErrorMessage(result.getErrorMessage()));
            return;
        }

        StartupContext context = result.getValue();

        GameFactory factory = new GameFactory(context);
        GameHandler gameHandler = factory.createGameHandler();

        StartupOutput output = context.getOutput();
        BoardRenderer boardRenderer = new BoardRenderer(
                output.getBoardSymbolSet(),
                new BoardEntityTokenFormatter(),
                output.getVerbosity()
        );

        CommandHandler commandHandler = new CommandHandler(gameHandler, boardRenderer);
        commandHandler.handleUserInput();
    }
}