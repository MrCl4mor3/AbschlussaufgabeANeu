package edu.kit.kastel.crownoffarmland.ui.commands;

import edu.kit.kastel.crownoffarmland.exceptions.CrownOfFarmlandException;
import edu.kit.kastel.crownoffarmland.exceptions.InvalidCommandArgumentException;
import edu.kit.kastel.crownoffarmland.exceptions.InvalidPositionException;
import edu.kit.kastel.crownoffarmland.gameplay.GameHandler;


public class SelectCommand extends Command {

    private static final String COMMAND_NAME = "select";

    public SelectCommand(CommandHandler commandHandler, GameHandler gameHandler) {
        super(COMMAND_NAME, commandHandler, gameHandler);
    }

    @Override
    public void execute(String[] commandArguments) throws CrownOfFarmlandException {
        ensureOneArguments(commandArguments);
        gameHandler.setSelected(commandArguments[0]);
        commandHandler.printBoard();
        commandHandler.printShow();
    }
}
