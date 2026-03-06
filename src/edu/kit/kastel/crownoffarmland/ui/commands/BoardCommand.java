package edu.kit.kastel.crownoffarmland.ui.commands;

import edu.kit.kastel.crownoffarmland.gameplay.GameHandler;

public class BoardCommand extends Command {

    private static final String COMMAND_NAME = "board";

    public BoardCommand(CommandHandler handler, GameHandler gameHandler) {
        super(COMMAND_NAME, COMMAND_NAME, handler, gameHandler);
    }

    @Override
    public void execute(String[] commandArguments) {
        System.out.println("Board Command");
    }
}
