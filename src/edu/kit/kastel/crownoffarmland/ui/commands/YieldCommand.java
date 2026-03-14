package edu.kit.kastel.crownoffarmland.ui.commands;

import edu.kit.kastel.crownoffarmland.exceptions.CrownOfFarmlandException;
import edu.kit.kastel.crownoffarmland.exceptions.InvalidCommandArgumentException;
import edu.kit.kastel.crownoffarmland.exceptions.gamestateexceptions.InvalidHandException;
import edu.kit.kastel.crownoffarmland.gameplay.GameHandler;
import edu.kit.kastel.crownoffarmland.gameplay.YieldCheckResult;
import edu.kit.kastel.crownoffarmland.gameplay.snapshots.EndTurnSnapshot;
import edu.kit.kastel.crownoffarmland.ui.renderer.GameOutputPrinter;

/**
 * Command for ending the current turn.
 *
 * @author ucgdi
 */
public class YieldCommand extends Command {
    private static final String COMMAND_NAME = "yield";
    private static final boolean ALLOW_EXECUTE_DURING_YIELD_RESTRICTION = true;

    private static final int EXPECTED_NUMBER_OF_ARGUMENTS = 1;

    private static final String ERROR_PREFIX = "ERROR: ";
    private static final String HAND_FULL_MESSAGE = "Hand ist full, you must discard a card!";
    private static final String DISCARD_NOT_ALLOWED_MESSAGE = "Cannot discard, hand is not full!";
    private static final String UNEXPECTED_YIELD_CHECK_RESULT = "Unexpected yield check result.";

    /**
     * Creates a new yield command.
     *
     * @param commandHandler the command handler
     * @param gameHandler the game handler
     * @param gameOutputPrinter the game output printer
     */
    public YieldCommand(CommandHandler commandHandler, GameHandler gameHandler, GameOutputPrinter gameOutputPrinter) {
        super(COMMAND_NAME, commandHandler, gameHandler, gameOutputPrinter);
    }

    @Override
    public void execute(String[] commandArgs) throws CrownOfFarmlandException {
        if (commandArgs.length > EXPECTED_NUMBER_OF_ARGUMENTS) {
            throw new InvalidCommandArgumentException(EXPECTED_NUMBER_OF_ARGUMENTS, commandArgs.length);
        }

        boolean discardRequested = commandArgs.length == EXPECTED_NUMBER_OF_ARGUMENTS;
        YieldCheckResult checkResult = gameHandler.checkYieldAttempt(discardRequested);

        switch (checkResult) {
            case DISCARD_REQUIRED -> {
                System.err.println(ERROR_PREFIX + HAND_FULL_MESSAGE);
                return;
            }
            case DISCARD_NOT_ALLOWED -> {
                System.err.println(ERROR_PREFIX + DISCARD_NOT_ALLOWED_MESSAGE);
                return;
            }
            case SUCCESS -> {
                // continue below
            }
            default -> throw new IllegalStateException(UNEXPECTED_YIELD_CHECK_RESULT);
        }

        EndTurnSnapshot endTurnSnapshot;
        if (!discardRequested) {
            endTurnSnapshot = gameHandler.endTurn();
        } else {
            int handIndex;
            try {
                handIndex = Integer.parseInt(commandArgs[0]);
            } catch (NumberFormatException e) {
                throw new InvalidHandException(commandArgs[0]);
            }
            endTurnSnapshot = gameHandler.endTurnWithDiscard(handIndex);
        }

        System.out.println(gameOutputPrinter.formatYield(endTurnSnapshot));
    }

    @Override
    protected boolean isAllowedDuringYieldRestriction() {
        return ALLOW_EXECUTE_DURING_YIELD_RESTRICTION;
    }
}