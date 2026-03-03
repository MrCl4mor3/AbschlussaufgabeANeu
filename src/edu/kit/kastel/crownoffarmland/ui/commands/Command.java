package edu.kit.kastel.crownoffarmland.ui.commands;

import edu.kit.kastel.crownoffarmland.exceptions.InvalidCommandArgumentException;
import edu.kit.kastel.crownoffarmland.gameplay.GameHandler;

public abstract class Command {
    protected final CommandHandler commandHandler;
    private final String commandName;
    private final String commandRegex;


    public Command(String commandName, String commandRegex, CommandHandler commandHandler, GameHandler gameHandler) {
        this.commandName = commandName;
        this.commandRegex = commandRegex;
        this.commandHandler = commandHandler;
    }

    public abstract void execute(String[] commandArguments) throws InvalidCommandArgumentException;


    public final String getCommandName() {
        return commandName;
    }
    public final String getCommandRegex() {
        return commandRegex;
    }
}