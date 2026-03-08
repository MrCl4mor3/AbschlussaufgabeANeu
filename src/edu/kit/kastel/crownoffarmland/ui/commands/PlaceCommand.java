package edu.kit.kastel.crownoffarmland.ui.commands;

import edu.kit.kastel.crownoffarmland.gameplay.GameHandler;

public class PlaceCommand extends Command {
    private static final String COMMAND_NAME = "place";


    public PlaceCommand(CommandHandler commandHandler, GameHandler gameHandler) {
        super(COMMAND_NAME, commandHandler, gameHandler);
    }

     @Override
    public void execute(String[] commamndArgs) {

     }
}
