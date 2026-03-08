package edu.kit.kastel.crownoffarmland.ui.commands;

import edu.kit.kastel.crownoffarmland.exceptions.CrownOfFarmlandException;
import edu.kit.kastel.crownoffarmland.gameplay.GameHandler;
import edu.kit.kastel.crownoffarmland.ui.snapshots.EntitySnapshot;

public class ShowCommand extends  Command {
    private static final String COMMAND_NAME = "show";

    private static final String COMMAND_ERROR_MESSAGE = "Show command does not take any arguments.";



    public ShowCommand(CommandHandler commandHandler, GameHandler gameHandler)  {
        super(COMMAND_NAME, commandHandler, gameHandler);
    }


    @Override
    public void execute(String[] commandArguments) throws CrownOfFarmlandException {
        ensureNoArguments(commandArguments);

        EntitySnapshot snapshot = gameHandler.createEntitySnapshotAtSelected();
        System.out.println(commandHandler.getEntityFormatter().format(snapshot));
    }


}
