package edu.kit.kastel.crownoffarmland.ui.renderer.outputformatter;

import edu.kit.kastel.crownoffarmland.gameplay.snapshots.EntityOnPositionSnapshot;
import edu.kit.kastel.crownoffarmland.ui.renderer.entity.EntityFormatter;

/**
 * Formats flip actions for UI output.
 *
 * @author ucgdi
 */
public class FlipOutputFormatter extends AbstractOutputFormatter<EntityOnPositionSnapshot> {

    private static final String COMMAND_OUTPUT_FORMAT = "%s was flipped on %s!";

    /**
     * Creates a new flip output formatter.
     *
     * @param entityFormatter formatter for entity output
     */
    public FlipOutputFormatter(EntityFormatter entityFormatter) {
        super(entityFormatter);
    }

    @Override
    public String format(EntityOnPositionSnapshot snapshot) {
        return String.format(COMMAND_OUTPUT_FORMAT,
                entityFormatter.formatEntitySummary(snapshot.getSnapshot()),
                snapshot.getSelectedField());
    }
}