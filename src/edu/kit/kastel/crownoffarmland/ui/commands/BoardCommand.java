package edu.kit.kastel.crownoffarmland.ui.commands;

import edu.kit.kastel.crownoffarmland.exceptions.CrownOfFarmlandException;
import edu.kit.kastel.crownoffarmland.gameplay.GameHandler;


/**
 * Implements the board command.
 * This command does not have a pattern. The name would match the pattern and therefore the name is the pattern
 * in this case.
 *
 * @author Programmieren-Team
 */
public class BoardCommand extends Command {

    private static final String COMMAND_NAME = "board";

    /**
     * Creates a new board command object.
     * @param commandHandler The command handler
     * @param gameHandler The game handler
     */
    protected BoardCommand(CommandHandler commandHandler, GameHandler gameHandler) {
        super(COMMAND_NAME, commandHandler, gameHandler);
    }

    @Override
    public void execute(String[] commandArguments) throws CrownOfFarmlandException {
        ensureNoArguments(commandArguments);
        commandHandler.printBoard();
    }
}
