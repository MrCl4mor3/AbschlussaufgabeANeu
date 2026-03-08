package edu.kit.kastel.crownoffarmland.ui.commands;

import edu.kit.kastel.crownoffarmland.exceptions.CrownOfFarmlandException;
import edu.kit.kastel.crownoffarmland.exceptions.InvalidCommandArgumentException;
import edu.kit.kastel.crownoffarmland.exceptions.InvalidHandException;
import edu.kit.kastel.crownoffarmland.gameplay.GameHandler;
import edu.kit.kastel.crownoffarmland.ui.snapshots.EntitySnapshot;

public class YieldCommand extends  Command {

    private static final String COMMAND_NAME = "yield";
    private static final boolean ALLOW_EXECUTE_DURING_YIELD_RESTRICTION = true;
    private static final int EXPECTED_NUMBER_OF_ARGUMENTS = 1;
    private static final String SUCCESSFULLY_ENDED_TURN_MESSAGE = "It is %s turn!%n";
    private static final String DISCARDING_CARD_MESSAGE = "%s discarded %s.%n";

    public YieldCommand(CommandHandler commandHandler, GameHandler gameHandler) {
        super(COMMAND_NAME, commandHandler, gameHandler);
    }

    @Override
    public void execute(String[] args) throws CrownOfFarmlandException {
        if (args.length > 1) {
            throw new InvalidCommandArgumentException(EXPECTED_NUMBER_OF_ARGUMENTS, args.length);
        }

        if (args.length == 0) {
            if (gameHandler.tryEndTurn()) {
                printOutput(null);
            }
        } else {
            int handIndex;
            try {
                handIndex = Integer.parseInt(args[0]);
                EntitySnapshot discardedCard = gameHandler.tryEndTurnWithDiscard(handIndex);
                printOutput(discardedCard);
            } catch (NumberFormatException e) {
                throw new InvalidHandException(args[0]);
            }
        }
    }

    private void printOutput(EntitySnapshot discardedCard) {
        if (discardedCard != null) {
            System.out.printf(DISCARDING_CARD_MESSAGE, discardedCard.getTeamName(),
                    commandHandler.getEntityFormatter().formatEntitySummary(discardedCard));
        }
        System.out.format(SUCCESSFULLY_ENDED_TURN_MESSAGE, gameHandler.getCurrentTeamName());
    }


    @Override
    protected boolean isAllowedDuringYieldRestriction() {
        return ALLOW_EXECUTE_DURING_YIELD_RESTRICTION;
    }
}
