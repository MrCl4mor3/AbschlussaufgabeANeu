package edu.kit.kastel.crownoffarmland.ui.commands;

import edu.kit.kastel.crownoffarmland.gameplay.GameHandler;

public class MoveCommand extends  Command {
    private static final String COMMAND_NAME = "move";


    public MoveCommand(CommandHandler commandHandler, GameHandler gameHandler) {
        super(COMMAND_NAME, commandHandler, gameHandler);
    }

     @Override
    public void execute(String[] commamndArgs) {

     }
}
