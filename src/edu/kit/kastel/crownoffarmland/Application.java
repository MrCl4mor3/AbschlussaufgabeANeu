package edu.kit.kastel.crownoffarmland;

import edu.kit.kastel.crownoffarmland.gameplay.GameHandler;
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

    private Application() {
        throw  new UnsupportedOperationException(UTILITY_CLASS_CONSTRUCTOR_MESSAGES);
    }




    /**
     * This is the main entry point for the program. There are no arguments expected.
     * If there are arguments, an error will be thrown.
     * @param args The command line arguments given at the start of the program
     */
    public static void main(String[] args) {
        StartupResult<GameHandler> result = StartupLoader.createGameHandler(args);

        if (!result.isSuccess()) {
            System.err.println(result.getErrorMessage());
            return;
        }

        GameHandler gameHandler = result.getValue();
        CommandHandler handler = new CommandHandler(gameHandler);
        handler.handleUserInput();
    }
}
