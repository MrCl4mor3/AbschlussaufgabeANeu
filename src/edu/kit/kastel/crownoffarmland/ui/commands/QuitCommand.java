package edu.kit.kastel.crownoffarmland.ui.commands;

import edu.kit.kastel.crownoffarmland.gameplay.GameHandler;

/**
 * Implements the quit command.
 * This command does not have a pattern. The name would match the pattern and therefore the name is the pattern
 * in this case.
 *
 * @author Programmieren-Team
 */
public class QuitCommand extends Command {

    private static final String COMMAND_NAME = "quit";

    /**
     * Creates a new quit command object.
     * @param commandHandler The command handler
     * @param gameHandler The game handler
     */
    protected QuitCommand(CommandHandler commandHandler, GameHandler gameHandler) {
        super(COMMAND_NAME, COMMAND_NAME, commandHandler, gameHandler);
    }

    @Override
    public void execute(String[] commandArguments) {
        commandHandler.quit();
    }

}
