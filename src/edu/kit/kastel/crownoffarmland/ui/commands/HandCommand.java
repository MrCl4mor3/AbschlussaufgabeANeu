package edu.kit.kastel.crownoffarmland.ui.commands;

import edu.kit.kastel.crownoffarmland.exceptions.CrownOfFarmlandException;
import edu.kit.kastel.crownoffarmland.gameplay.GameHandler;
import edu.kit.kastel.crownoffarmland.gameplay.snapshots.EntitySnapshot;

import java.util.List;

/**
 * Implements the hand command.
 * Command to display the player's current hand of cards. This command will show a list of all cards currently in the player's hand,
 * along with their corresponding indices. The player can use these indices to reference specific cards when executing other commands
 * that require card selection.
 *
 * @author ucgdi
 */
public class HandCommand extends Command {
    private static final String COMMAND_NAME = "hand";

    private static final int INDEX_OFFSET = 1;
    private static final String HAND_ENTRY_FORMAT = "[%d] %s";
    private static final boolean ALLOW_EXECUTE_DURING_YIELD_RESTRICTION = true;


    /**
     * Creates a new hand command object.
     * @param commandHandler The command handler to which this command belongs
     * @param gameHandler The game handler that provides access to the game state and logic
     */
    public HandCommand(CommandHandler commandHandler, GameHandler gameHandler) {
        super(COMMAND_NAME, commandHandler, gameHandler);
    }

    @Override
    public void execute(String[] commandArguments) throws CrownOfFarmlandException {
        ensureNoArguments(commandArguments);


        List<EntitySnapshot> handEntries = gameHandler.createHandSnapshot();
        System.out.println(formatHand(handEntries));
    }


    private String formatHand(List<EntitySnapshot> handEntries) {
        StringBuilder output = new StringBuilder();

        for (int index = 0; index < handEntries.size(); index++) {
            EntitySnapshot entry = handEntries.get(index);
            output.append(String.format(HAND_ENTRY_FORMAT, index + INDEX_OFFSET,
                    commandHandler.getEntityFormatter().formatEntitySummary(entry)));
            if (index < handEntries.size() - 1) {
                output.append(System.lineSeparator());
            }
        }
        return output.toString();
    }


    @Override
    protected boolean isAllowedDuringYieldRestriction() {
        return ALLOW_EXECUTE_DURING_YIELD_RESTRICTION;
    }
}
