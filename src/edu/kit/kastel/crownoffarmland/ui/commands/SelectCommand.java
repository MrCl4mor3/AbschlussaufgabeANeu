package edu.kit.kastel.crownoffarmland.ui.commands;

import edu.kit.kastel.crownoffarmland.exceptions.CrownOfFarmlandException;
import edu.kit.kastel.crownoffarmland.gameplay.GameHandler;


/**
 * Implements the select command.
 * This command allows the player to select a field by its name. After selecting a field, the board and the show are printed. The player
 * can then use the selected field for other commands.
 *
 * @author ucgdi
 */
public class SelectCommand extends Command {

    private static final String COMMAND_NAME = "select";

    /**
     * Constructs a new SelectCommand with the specified CommandHandler and GameHandler.
     *
     * @param commandHandler the CommandHandler to use for executing the command
     * @param gameHandler    the GameHandler to use for accessing and modifying the game state
     */
    public SelectCommand(CommandHandler commandHandler, GameHandler gameHandler) {
        super(COMMAND_NAME, commandHandler, gameHandler);
    }

    @Override
    public void execute(String[] commandArguments) throws CrownOfFarmlandException {
        ensureOneArguments(commandArguments);
        gameHandler.setSelected(commandArguments[0]);
        commandHandler.printBoard();
        commandHandler.printShow();
    }
}
