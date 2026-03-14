package edu.kit.kastel.crownoffarmland.ui.renderer.outputformatter;

import edu.kit.kastel.crownoffarmland.gameplay.snapshots.EntityOnPositionSnapshot;

/**
 * Formats block actions for UI output.
 *
 * @author ucgdi
 */
public class BlockOutputFormatter implements OutputFormatter<EntityOnPositionSnapshot> {

    private static final String BLOCK_OUTPUT_FORMAT = "%s (%s) blocks!";

    @Override
    public String format(EntityOnPositionSnapshot snapshot) {
        return String.format(BLOCK_OUTPUT_FORMAT, snapshot.getSnapshot().getEntityName(), snapshot.getSelectedField());
    }
}