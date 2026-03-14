package edu.kit.kastel.crownoffarmland.ui.commands;

import edu.kit.kastel.crownoffarmland.exceptions.CrownOfFarmlandException;
import edu.kit.kastel.crownoffarmland.gameplay.GameHandler;
import edu.kit.kastel.crownoffarmland.ui.renderer.GameOutputPrinter;

/**
 * Command for blocking the selected entity.
 *
 * @author ucgdi
 */
public class BlockCommand extends Command {
    private static final String COMMAND_NAME = "block";

    /**
     * Creates a new block command.
     *
     * @param commandHandler the command handler
     * @param gameHandler the game handler
     * @param gameOutputPrinter the game output printer
     */
    public BlockCommand(CommandHandler commandHandler, GameHandler gameHandler, GameOutputPrinter gameOutputPrinter) {
        super(COMMAND_NAME, commandHandler, gameHandler, gameOutputPrinter);
    }

    @Override
    public void execute(String[] commandArguments) throws CrownOfFarmlandException {
        ensureNoArguments(commandArguments);

        System.out.println(gameOutputPrinter.formatBlock(gameHandler.blockSelected()));
        System.out.println(gameOutputPrinter.formatBoard(gameHandler.snapshots().createBoardSnapshot()));
        System.out.println(gameOutputPrinter.formatShow(gameHandler.snapshots().createEntitySnapshot()));
    }
}