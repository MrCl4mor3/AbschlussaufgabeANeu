package edu.kit.kastel.crownoffarmland.ui.commands;

import edu.kit.kastel.crownoffarmland.exceptions.CrownOfFarmlandException;
import edu.kit.kastel.crownoffarmland.exceptions.InvalidCommandArgumentException;
import edu.kit.kastel.crownoffarmland.gameplay.GameHandler;
import edu.kit.kastel.crownoffarmland.ui.snapshots.EntitySnapshot;

public class BlockCommand extends Command {
    private static final String COMMAND_NAME = "block";

    private static final String COMMAND_OUTPUT_FORMAT = "%s (%s) blocks!%n";


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
