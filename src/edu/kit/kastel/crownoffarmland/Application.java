package edu.kit.kastel.crownoffarmland;


import edu.kit.kastel.crownoffarmland.gameplay.GameHandler;
import edu.kit.kastel.crownoffarmland.startup.StartupError;
import edu.kit.kastel.crownoffarmland.startup.StartupLoader;
import edu.kit.kastel.crownoffarmland.startup.StartupResult;
import edu.kit.kastel.crownoffarmland.ui.commands.CommandHandler;

/**
 * This is the main entry class for the program.
 *
 * @author Programmieren-Team
 */
public final class Application {
    private static final String UTILITY_CLASS_CONSTRUCTOR_MESSAGES = "Utility classes cannot be instantiated";

    private static final String EMPTY_ARGUMENTS_ERROR = "No arguments found.";

    private Application() {
        throw  new UnsupportedOperationException(UTILITY_CLASS_CONSTRUCTOR_MESSAGES);
    }


    /**
     * This is the main entry point for the program. There are no arguments expected.
     * If there are arguments, an error will be thrown.
     * @param args The command line arguments given at the start of the program
     */
    public static void main(String[] args) {
        if  (args.length == 0) {
            System.err.println(EMPTY_ARGUMENTS_ERROR);
            return;
        }

        StartupLoader loader = new StartupLoader();
        StartupResult<GameHandler> result = loader.createGameHandler(args);

        if (result.isError()) {
            System.err.println(StartupError.formatErrorMessage(result.getErrorMessage()));
            return;
        }

        CommandHandler handler = new CommandHandler(result.getValue());
        handler.handleUserInput();
    }
}
