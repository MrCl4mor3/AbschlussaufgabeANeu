package edu.kit.kastel.crownoffarmland.ui.renderer.outputformatter;


import edu.kit.kastel.crownoffarmland.gameplay.snapshots.EntityOnPositionSnapshot;
import edu.kit.kastel.crownoffarmland.ui.renderer.entity.EntityFormatter;

/**
 * Formats snapshots of type EntityOnPositionSnapshot into string representations that indicate the flipping action performed on an
 * entity at a specific position. The formatted output includes the name of the entity and the field that is being flipped, providing a
 * clear and concise message about the flipping action for display in the user interface.
 *
 * @author ucgdi
 */
public class FlipOutputFormatter extends AbstractOutputFormatter<EntityOnPositionSnapshot> {

    private static final String COMMAND_OUTPUT_FORMAT = "%s was flipped on %s!";

    /**
     * Creates a new FlipOutputFormatter.
     * @param entityFormatter the EntityFormatter
     */
    public FlipOutputFormatter(EntityFormatter entityFormatter) {
        super(entityFormatter);
    }



    @Override
    public String format(EntityOnPositionSnapshot snapshot) {
        return String.format(COMMAND_OUTPUT_FORMAT, entityFormatter.formatEntitySummary(snapshot.getSnapshot()),
                snapshot.getSelectedField());
    }
}
