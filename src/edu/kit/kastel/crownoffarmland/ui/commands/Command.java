package edu.kit.kastel.crownoffarmland.ui.commands;

import edu.kit.kastel.crownoffarmland.exceptions.CrownOfFarmlandException;
import edu.kit.kastel.crownoffarmland.exceptions.InvalidCommandArgumentException;
import edu.kit.kastel.crownoffarmland.gameplay.GameHandler;
import edu.kit.kastel.crownoffarmland.ui.renderer.GameOutputPrinter;

/**
 * Base class for all commands.
 *
 * @author Programmieren-Team
 * @author ucgdi
 */
public abstract class Command {
    private static final int NO_ARGUMENT_NEEDED = 0;
    private static final int ONE_ARGUMENT_NEEDED = 1;
    private static final boolean ALLOW_EXECUTE_DURING_YIELD_RESTRICTION_STANDARD = false;

    protected final GameOutputPrinter gameOutputPrinter;
    protected final CommandHandler commandHandler;
    protected final GameHandler gameHandler;

    private final String commandName;

    /**
     * Creates a new command.
     *
     * @param commandName the command name
     * @param commandHandler the command handler
     * @param gameHandler the game handler
     * @param gameOutputPrinter the game output printer
     */
    public Command(String commandName, CommandHandler commandHandler, GameHandler gameHandler, GameOutputPrinter gameOutputPrinter) {
        this.commandName = commandName;
        this.commandHandler = commandHandler;
        this.gameHandler = gameHandler;
        this.gameOutputPrinter = gameOutputPrinter;
    }

    /**
     * Executes the command.
     *
     * @param commandArguments the command arguments
     * @throws CrownOfFarmlandException if command execution fails
     */
    public abstract void execute(String[] commandArguments) throws CrownOfFarmlandException;

    /**
     * Returns the command name.
     *
     * @return the command name
     */
    public final String getCommandName() {
        return commandName;
    }

    /**
     * Returns whether the command may be executed during a yield restriction.
     *
     * @return {@code true} if execution is allowed, otherwise {@code false}
     */
    protected boolean isAllowedDuringYieldRestriction() {
        return ALLOW_EXECUTE_DURING_YIELD_RESTRICTION_STANDARD;
    }

    /**
     * Ensures that no arguments were passed.
     *
     * @param commandArguments the command arguments
     * @throws InvalidCommandArgumentException if the number of arguments is invalid
     */
    protected final void ensureNoArguments(String[] commandArguments) throws InvalidCommandArgumentException {
        if (commandArguments.length != NO_ARGUMENT_NEEDED) {
            throw new InvalidCommandArgumentException(NO_ARGUMENT_NEEDED, commandArguments.length);
        }
    }

    /**
     * Ensures that exactly one argument was passed.
     *
     * @param commandArguments the command arguments
     * @throws InvalidCommandArgumentException if the number of arguments is invalid
     */
    protected final void ensureOneArguments(String[] commandArguments) throws InvalidCommandArgumentException {
        if (commandArguments.length != ONE_ARGUMENT_NEEDED) {
            throw new InvalidCommandArgumentException(ONE_ARGUMENT_NEEDED, commandArguments.length);
        }
    }
}