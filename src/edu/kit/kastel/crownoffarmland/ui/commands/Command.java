package edu.kit.kastel.crownoffarmland.ui.commands;

import edu.kit.kastel.crownoffarmland.exceptions.CrownOfFarmlandException;
import edu.kit.kastel.crownoffarmland.exceptions.InvalidCommandArgumentException;
import edu.kit.kastel.crownoffarmland.gameplay.GameHandler;

/**
 * This implements the command class the same way/in a similar way than it was implemented by the Programmieren-Team
 * in previous excercises.
 *
 * @author Programmieren-Team
 * @author ucgdi
 */
public abstract class Command {
    private static final int NO_ARGUMENT_NEEDED = 0;
    private static final int ONE_ARGUMENT_NEEDED = 1;
    private static final boolean ALLOW_EXECUTE_DURING_YIELD_RESTRICTION_STANDARD = false;
    protected final CommandHandler commandHandler;
    protected final GameHandler gameHandler;


    private final String commandName;

    /**
     * Commands that have the same name as regex, for example quit, do not have a separate regex.
     *
     * @param commandName The name of the command
     * @param commandHandler The command handler
     * @param gameHandler The game handler, which is needed for some commands to execute their logic.
     */
    public Command(String commandName, CommandHandler commandHandler, GameHandler gameHandler) {
        this.commandName = commandName;
        this.commandHandler = commandHandler;
        this.gameHandler = gameHandler;
    }

    /**
     * Executes a given command. The arguments are already split by the command handler.
     * @param commandArguments The arguments the command needs to run. Can contain optional arguments
     * @throws CrownOfFarmlandException If the arguments don't match the required types or formats,
     *     this exception will be thrown
     */
    public abstract void execute(String[] commandArguments) throws CrownOfFarmlandException;


    /**
     * This returns the command name.
     *
     * @return The name of the command.
     */
    public final String getCommandName() {
        return commandName;
    }


    /**
     * This method returns whether the command is allowed to be executed during a yield restriction.
     * By default, it returns false, but it can be overridden by specific commands that are allowed during yield restrictions.
     *
     * @return true if the command is allowed during yield restrictions, false otherwise.
     */
    protected boolean isAllowedDuringYieldRestriction() {
        return ALLOW_EXECUTE_DURING_YIELD_RESTRICTION_STANDARD;
    }


    /**
     * This method ensures that the command has no arguments. If there are arguments, it throws an InvalidCommandArgumentException.
     * @param commandArguments The arguments to check
     * @throws InvalidCommandArgumentException If the number of arguments is not zero, this exception will be thrown, indicating the
     *     expected and actual number of arguments.
     */
    protected final void ensureNoArguments(String[] commandArguments) throws InvalidCommandArgumentException {
        if (commandArguments.length != NO_ARGUMENT_NEEDED) {
            throw new InvalidCommandArgumentException(NO_ARGUMENT_NEEDED, commandArguments.length);
        }
    }

    /**
     * This method ensures that the command has exactly one argument. If there are not exactly one argument, it throws an
     * InvalidCommandArgumentException.
     * @param commandArguments The arguments to check
     * @throws InvalidCommandArgumentException If the number of arguments is not one, this exception will be thrown, indicating the
     *     expected and actual number of arguments.
     */
    protected final void ensureOneArguments(String[] commandArguments) throws InvalidCommandArgumentException {
        if (commandArguments.length != ONE_ARGUMENT_NEEDED) {
            throw new InvalidCommandArgumentException(ONE_ARGUMENT_NEEDED, commandArguments.length);
        }
    }


}