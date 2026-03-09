package edu.kit.kastel.crownoffarmland.ui.commands;

import edu.kit.kastel.crownoffarmland.gameplay.GameHandler;

/**
 * Implements the move command.
 * Command to move a entity to a new position.
 *
 * @author ucgdi
 */
public class MoveCommand extends  Command {
    private static final String COMMAND_NAME = "move";


    /**
     * Creates a new MoveCommand.
     *
     * @param commandHandler the CommandHandler to handle this command
     * @param gameHandler the GameHandler to execute this command
     */
    public MoveCommand(CommandHandler commandHandler, GameHandler gameHandler) {
        super(COMMAND_NAME, commandHandler, gameHandler);
    }

    @Override
    public void execute(String[] commandArgs) {

    }
}
