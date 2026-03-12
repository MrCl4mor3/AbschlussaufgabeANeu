package edu.kit.kastel.crownoffarmland.ui.commands;

import edu.kit.kastel.crownoffarmland.exceptions.CrownOfFarmlandException;
import edu.kit.kastel.crownoffarmland.gameplay.GameHandler;
import edu.kit.kastel.crownoffarmland.ui.renderer.GameOutputPrinter;


/**
 * Implements the board command.
 * Command to print the current state of the game board. This command does not take any arguments and will display the current layout of
 * the board, including the positions of all entities and fields. It is useful for players to get an overview of the game state at any
 * point during their turn.
 *
 * @author ucgdi
 */
public class BoardCommand extends Command {

    private static final String COMMAND_NAME = "board";

    /**
     * Creates a new board command object.
     * @param commandHandler The command handler
     * @param gameHandler The game handler
     * @param gameOutputPrinter The game output printer
     */
    protected BoardCommand(CommandHandler commandHandler, GameHandler gameHandler, GameOutputPrinter gameOutputPrinter) {
        super(COMMAND_NAME, commandHandler, gameHandler, gameOutputPrinter);
    }

    @Override
    public void execute(String[] commandArguments) throws CrownOfFarmlandException {
        ensureNoArguments(commandArguments);
        System.out.printf(gameOutputPrinter.formatBoard(gameHandler.createBoardSnapshot()));
    }
}
