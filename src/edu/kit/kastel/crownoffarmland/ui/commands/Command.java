package edu.kit.kastel.crownoffarmland.ui.commands;

import edu.kit.kastel.crownoffarmland.exceptions.InvalidCommandArgumentException;
import edu.kit.kastel.crownoffarmland.gameplay.GameHandler;

/**
 * This implements the command class the same way/in a similar way than it was implemented by the Programmieren-Team
 * in previous excercises.
 *
 * @author Programmieren-Team
 */
public abstract class Command {
    protected final CommandHandler commandHandler;
    private final String commandName;
    private final String commandRegex;

    /**
     * Commands that have the same name as regex, for example quit, do not have a separate regex.
     *
     * @param commandName The name of the command
     * @param commandRegex The regex to match the command against
     * @param commandHandler The command handler
     * @param gameHandler The game handler, which is needed for some commands to execute their logic.
     */
    public Command(String commandName, String commandRegex, CommandHandler commandHandler, GameHandler gameHandler) {
        this.commandName = commandName;
        this.commandRegex = commandRegex;
        this.commandHandler = commandHandler;
    }

    /**
     * Executes a given command. The arguments are already split by the command handler.
     * @param commandArguments The arguments the command needs to run. Can contain optional arguments
     * @throws InvalidCommandArgumentException If the arguments don't match the required types or formats,
     *     this exception will be thrown
     */
    public abstract void execute(String[] commandArguments) throws InvalidCommandArgumentException;


    /**
     * This returns the command name.
     *
     * @return The name of the command.
     */
    public final String getCommandName() {
        return commandName;
    }

    /**
     * This returns the regex that the input has to match against for the command to be executed.
     *
     * @return The pattern of the command.
     */
    public final String getCommandRegex() {
        return commandRegex;
    }
}