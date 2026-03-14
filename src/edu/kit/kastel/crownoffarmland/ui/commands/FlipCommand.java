package edu.kit.kastel.crownoffarmland.ui.commands;

import edu.kit.kastel.crownoffarmland.exceptions.CrownOfFarmlandException;
import edu.kit.kastel.crownoffarmland.gameplay.GameHandler;
import edu.kit.kastel.crownoffarmland.ui.renderer.GameOutputPrinter;

/**
 * Command for flipping the selected entity.
 *
 * @author ucgdi
 */
public class FlipCommand extends Command {
    private static final String COMMAND_NAME = "flip";

    /**
     * Creates a new flip command.
     *
     * @param commandHandler the command handler
     * @param gameHandler the game handler
     * @param gameOutputPrinter the game output printer
     */
    public FlipCommand(CommandHandler commandHandler, GameHandler gameHandler, GameOutputPrinter gameOutputPrinter) {
        super(COMMAND_NAME, commandHandler, gameHandler, gameOutputPrinter);
    }

    @Override
    public void execute(String[] commandArguments) throws CrownOfFarmlandException {
        ensureNoArguments(commandArguments);

        System.out.println(gameOutputPrinter.formatFlip(gameHandler.flipSelectedEntity()));
        System.out.println(gameOutputPrinter.formatBoard(gameHandler.snapshots().createBoardSnapshot()));
        System.out.println(gameOutputPrinter.formatShow(gameHandler.snapshots().createEntitySnapshot()));
    }
}