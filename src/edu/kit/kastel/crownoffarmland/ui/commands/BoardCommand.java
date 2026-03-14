package edu.kit.kastel.crownoffarmland.ui.commands;

import edu.kit.kastel.crownoffarmland.exceptions.CrownOfFarmlandException;
import edu.kit.kastel.crownoffarmland.gameplay.GameHandler;
import edu.kit.kastel.crownoffarmland.ui.renderer.GameOutputPrinter;

/**
 * Command for printing the board.
 *
 * @author ucgdi
 */
public class BoardCommand extends Command {
    private static final String COMMAND_NAME = "board";

    /**
     * Creates a new board command.
     *
     * @param commandHandler the command handler
     * @param gameHandler the game handler
     * @param gameOutputPrinter the game output printer
     */
    protected BoardCommand(CommandHandler commandHandler, GameHandler gameHandler, GameOutputPrinter gameOutputPrinter) {
        super(COMMAND_NAME, commandHandler, gameHandler, gameOutputPrinter);
    }

    @Override
    public void execute(String[] commandArguments) throws CrownOfFarmlandException {
        ensureNoArguments(commandArguments);
        System.out.println(gameOutputPrinter.formatBoard(gameHandler.snapshots().createBoardSnapshot()));
    }
}