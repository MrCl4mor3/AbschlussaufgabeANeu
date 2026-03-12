package edu.kit.kastel.crownoffarmland.ui.renderer.outputformatter;

import edu.kit.kastel.crownoffarmland.gameplay.snapshots.EntitySnapshot;
import edu.kit.kastel.crownoffarmland.ui.renderer.entity.EntityFormatter;

import java.util.List;

/**
 * Formats a list of EntitySnapshot objects representing the entries in a player's hand into a string representation suitable for display
 * in the user interface. Each entry in the hand is formatted using the provided EntityFormatter to create a summary of the entity, and
 * the formatted output includes an index for each entry to indicate its position in the hand. The resulting string provides a clear and
 * concise representation of the player's hand for display purposes.
 *
 * @author ucgdi
 */
public class HandOutputFormatter extends AbstractOutputFormatter<List<EntitySnapshot>> {
    private static final String HAND_ENTRY_FORMAT = "[%d] %s";
    private static final int INDEX_OFFSET = 1;

    /**
     * Creates a new HandoutputFormatter with the specified EntityFormatter dependency. The EntityFormatter is used to format each entry
     * in the player's hand into a summary representation for display in the user interface.
     * @param entityFormatter the EntityFormatter to be used for formatting each entry in the player's hand into a summary representation
     *                       for display in the user interface
     */
    public HandOutputFormatter(EntityFormatter entityFormatter) {
        super(entityFormatter);
    }


    @Override
    public String format(List<EntitySnapshot> handEntries) {
        StringBuilder output = new StringBuilder();

        for (int index = 0; index < handEntries.size(); index++) {
            EntitySnapshot entry = handEntries.get(index);
            output.append(String.format(HAND_ENTRY_FORMAT, index + INDEX_OFFSET, entityFormatter.formatEntitySummary(entry)));
            if (index < handEntries.size() - 1) {
                output.append(System.lineSeparator());
            }
        }
        return output.toString();
    }
}
