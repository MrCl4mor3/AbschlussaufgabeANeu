package edu.kit.kastel.crownoffarmland.ui.commands;

import edu.kit.kastel.crownoffarmland.exceptions.CrownOfFarmlandException;
import edu.kit.kastel.crownoffarmland.gameplay.GameHandler;

/**
 * Implements the quit command.
 * Command to quit the game. This command does not take any arguments and will end the current game session.
 *
 * @author Programmieren-Team
 * @author ucgdi
 */
public class QuitCommand extends Command {

    private static final String COMMAND_NAME = "quit";
    private static final boolean ALLOW_EXECUTE_DURING_YIELD_RESTRICTION = true;

    /**
     * Constructs a new QuitCommand with the specified CommandHandler and GameHandler.
     *
     * @param commandHandler the CommandHandler to use for executing the command
     * @param gameHandler    the GameHandler to use for accessing and modifying the game state
     */
    protected QuitCommand(CommandHandler commandHandler, GameHandler gameHandler) {
        super(COMMAND_NAME, commandHandler, gameHandler);
    }

    @Override
    public void execute(String[] commandArguments) throws CrownOfFarmlandException {
        ensureNoArguments(commandArguments);
        commandHandler.quit();
    }


    @Override
    protected boolean isAllowedDuringYieldRestriction() {
        return ALLOW_EXECUTE_DURING_YIELD_RESTRICTION;
    }

}
