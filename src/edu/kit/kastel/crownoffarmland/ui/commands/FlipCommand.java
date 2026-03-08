package edu.kit.kastel.crownoffarmland.ui.commands;

import edu.kit.kastel.crownoffarmland.exceptions.CrownOfFarmlandException;
import edu.kit.kastel.crownoffarmland.exceptions.InvalidCommandArgumentException;
import edu.kit.kastel.crownoffarmland.gameplay.GameHandler;
import edu.kit.kastel.crownoffarmland.ui.snapshots.EntitySnapshot;

public class FlipCommand extends  Command {
    private static final String COMMAND_NAME = "flip";
    private static final String COMMAND_OUTPUT_FORMAT = "%s was flipped on %s!%n";

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
