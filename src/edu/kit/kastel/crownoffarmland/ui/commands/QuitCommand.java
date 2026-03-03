package edu.kit.kastel.crownoffarmland.ui.commands;

import edu.kit.kastel.crownoffarmland.gameplay.GameHandler;

public class QuitCommand extends Command {

    private static final String COMMAND_NAME = "quit";


    protected QuitCommand(CommandHandler commandHandler, GameHandler gameHandler) {
        super(COMMAND_NAME, COMMAND_NAME, commandHandler, gameHandler);
    }

    @Override
    public void execute(String[] commandArguments) {
        commandHandler.quit();
    }

}
