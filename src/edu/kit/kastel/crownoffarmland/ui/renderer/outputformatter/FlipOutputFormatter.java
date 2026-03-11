package edu.kit.kastel.crownoffarmland.ui.renderer.outputformatter;


import edu.kit.kastel.crownoffarmland.gameplay.snapshots.movesnapshot.EntityOnPositionSnapshot;
import edu.kit.kastel.crownoffarmland.ui.renderer.entity.EntityFormatter;

public class FlipOutputFormatter extends AbstractOutputFormatter<EntityOnPositionSnapshot> {

    private static final String COMMAND_OUTPUT_FORMAT = "%s was flipped on %s!%n";

    public FlipOutputFormatter(EntityFormatter entityFormatter) {
        super(entityFormatter);
    }



    @Override
    public String format(EntityOnPositionSnapshot snapshot) {
        return String.format(COMMAND_OUTPUT_FORMAT, entityFormatter.formatEntitySummary(snapshot.getSnapshot()),
                snapshot.getSelectedField());
    }
}
