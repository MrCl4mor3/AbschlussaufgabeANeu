package edu.kit.kastel.crownoffarmland.ui.commands;

import edu.kit.kastel.crownoffarmland.exceptions.CrownOfFarmlandException;
import edu.kit.kastel.crownoffarmland.gameplay.GameHandler;
import edu.kit.kastel.crownoffarmland.ui.renderer.GameOutputPrinter;

/**
 * Implements the flip command.
 * Command to flip the entity on the currently selected field. The entity will be flipped and won't be able to perform
 * any actions for the current turn. The command will print the name of the flipped entity and the position of the field where it is
 * located.
 *
 * @author ucgdi
 */
public class FlipCommand extends  Command {
    private static final String COMMAND_NAME = "flip";

    /**
     * Creates a new instance of the FlipCommand.
     * @param commandHandler the CommandHandler to which this command belongs
     * @param gameHandler the GameHandler that provides access to the game state and logic
     * @param gameOutputPrinter the GameOutputPrinter that provides methods to format the output of the command
     */
    public FlipCommand(CommandHandler commandHandler, GameHandler gameHandler, GameOutputPrinter gameOutputPrinter) {
        super(COMMAND_NAME, commandHandler, gameHandler, gameOutputPrinter);
    }

    @Override
    public void execute(String[] commandArguments) throws CrownOfFarmlandException {
        ensureNoArguments(commandArguments);

        System.out.println(gameOutputPrinter.formatFlip(gameHandler.flipSelectedEntity()));
        System.out.println(gameOutputPrinter.formatBoard(gameHandler.createBoardSnapshot()));
        System.out.println(gameOutputPrinter.formatShow(gameHandler.createEntitySnapshot()));
    }
}
