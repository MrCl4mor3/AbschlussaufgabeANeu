package edu.kit.kastel.crownoffarmland.ui.renderer.outputformatter;

import edu.kit.kastel.crownoffarmland.gameplay.snapshots.EntitySnapshot;
import edu.kit.kastel.crownoffarmland.ui.renderer.entity.EntityFormatter;

import java.util.List;

public class HandOutputFormatter extends AbstractOutputFormatter<List<EntitySnapshot>> {
    private static final String HAND_ENTRY_FORMAT = "[%d] %s";
    private static final int INDEX_OFFSET = 1;

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
