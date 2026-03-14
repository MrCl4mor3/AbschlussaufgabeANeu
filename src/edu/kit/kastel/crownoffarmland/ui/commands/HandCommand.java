package edu.kit.kastel.crownoffarmland.ui.commands;

import edu.kit.kastel.crownoffarmland.exceptions.CrownOfFarmlandException;
import edu.kit.kastel.crownoffarmland.gameplay.GameHandler;
import edu.kit.kastel.crownoffarmland.ui.renderer.GameOutputPrinter;

/**
 * Command for printing the current hand.
 *
 * @author ucgdi
 */
public class HandCommand extends Command {
    private static final String COMMAND_NAME = "hand";
    private static final boolean ALLOW_EXECUTE_DURING_YIELD_RESTRICTION = true;

    /**
     * Creates a new hand command.
     *
     * @param commandHandler the command handler
     * @param gameHandler the game handler
     * @param gameOutputPrinter the game output printer
     */
    public HandCommand(CommandHandler commandHandler, GameHandler gameHandler, GameOutputPrinter gameOutputPrinter) {
        super(COMMAND_NAME, commandHandler, gameHandler, gameOutputPrinter);
    }

    @Override
    public void execute(String[] commandArguments) throws CrownOfFarmlandException {
        ensureNoArguments(commandArguments);
        System.out.println(gameOutputPrinter.formatHand(gameHandler.snapshots().createHandSnapshot()));
    }

    @Override
    protected boolean isAllowedDuringYieldRestriction() {
        return ALLOW_EXECUTE_DURING_YIELD_RESTRICTION;
    }
}