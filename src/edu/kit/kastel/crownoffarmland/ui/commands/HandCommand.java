package edu.kit.kastel.crownoffarmland.ui.commands;

import edu.kit.kastel.crownoffarmland.exceptions.CrownOfFarmlandException;
import edu.kit.kastel.crownoffarmland.gameplay.GameHandler;
import edu.kit.kastel.crownoffarmland.ui.snapshots.EntitySnapshot;

import java.util.List;

public class HandCommand extends Command {
    private static final String COMMAND_NAME = "hand";

    private static final int INDEX_OFFSET = 1;
    private static final String HAND_ENTRY_FORMAT = "[%d] %s";
    private static final boolean ALLOW_EXECUTE_DURING_YIELD_RESTRICTION = true;


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
