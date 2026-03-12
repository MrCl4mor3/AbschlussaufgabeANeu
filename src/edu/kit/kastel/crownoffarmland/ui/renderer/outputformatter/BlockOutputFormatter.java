package edu.kit.kastel.crownoffarmland.ui.renderer.outputformatter;


import edu.kit.kastel.crownoffarmland.gameplay.snapshots.EntityOnPositionSnapshot;

/**
 * Formats snapshots of type EntityOnPositionSnapshot into string representations that indicate the blocking status of an entity on a
 * specific position. The formatted output includes the name of the entity and the field that is being blocked, providing a clear and
 * concise message about the blocking action for display in the user interface.
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
