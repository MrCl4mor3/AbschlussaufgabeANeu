package edu.kit.kastel.crownoffarmland.ui.commands;

import edu.kit.kastel.crownoffarmland.exceptions.CrownOfFarmlandException;
import edu.kit.kastel.crownoffarmland.gameplay.GameHandler;
import edu.kit.kastel.crownoffarmland.gameplay.snapshots.EntitySnapshot;

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
    private static final String COMMAND_OUTPUT_FORMAT = "%s was flipped on %s!%n";

    /**
     * Creates a new instance of the FlipCommand.
     * @param commandHandler the CommandHandler to which this command belongs
     * @param gameHandler the GameHandler that provides access to the game state and logic
     */
    public FlipCommand(CommandHandler commandHandler, GameHandler gameHandler) {
        super(COMMAND_NAME, commandHandler, gameHandler);
    }

    @Override
    public void execute(String[] commandArguments) throws CrownOfFarmlandException {
        ensureNoArguments(commandArguments);

        EntitySnapshot selectedEntity = gameHandler.flipSelectedEntity();
        String entitySummary = commandHandler.getEntityFormatter().formatEntitySummary(selectedEntity);
        String selectedField = gameHandler.getSelectedPos().toString();
        System.out.printf(COMMAND_OUTPUT_FORMAT, entitySummary, selectedField);
        commandHandler.printBoard();
        commandHandler.printShow();
    }
}
