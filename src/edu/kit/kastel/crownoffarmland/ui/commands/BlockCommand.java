package edu.kit.kastel.crownoffarmland.ui.commands;

import edu.kit.kastel.crownoffarmland.exceptions.CrownOfFarmlandException;
import edu.kit.kastel.crownoffarmland.gameplay.GameHandler;
import edu.kit.kastel.crownoffarmland.gameplay.snapshots.EntitySnapshot;

/**
 * Implements the block command.
 * Command to block the entity on the currently selected field. The entity will be blocked and won't be able to perform
 * any actions for the current turn. The command will print the name of the blocked entity and the position of the field where it is
 * located.
 *
 * @author ucgdi
 */
public class BlockCommand extends Command {
    private static final String COMMAND_NAME = "block";

    private static final String COMMAND_OUTPUT_FORMAT = "%s (%s) blocks!%n";


    /**
     * Creates a new instance of the BlockCommand.
     *
     * @param commandHandler the CommandHandler to which this command belongs
     * @param gameHandler the GameHandler that provides access to the game state and logic
     */
    public BlockCommand(CommandHandler commandHandler, GameHandler gameHandler) {
        super(COMMAND_NAME, commandHandler, gameHandler);
    }

    @Override
    public void execute(String[] commandArguments) throws CrownOfFarmlandException {
        ensureNoArguments(commandArguments);
        EntitySnapshot snapshot = gameHandler.blockSelected();
        String selectedField = gameHandler.getSelectedPos().toString();
        System.out.printf(COMMAND_OUTPUT_FORMAT, snapshot.getEntityName(), selectedField);
        commandHandler.printBoard();
        commandHandler.printShow();
    }
}
