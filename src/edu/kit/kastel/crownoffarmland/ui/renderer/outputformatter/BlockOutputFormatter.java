package edu.kit.kastel.crownoffarmland.ui.renderer.outputformatter;


import edu.kit.kastel.crownoffarmland.gameplay.snapshots.movesnapshot.EntityOnPositionSnapshot;

public class BlockOutputFormatter implements OutputFormatter<EntityOnPositionSnapshot> {

    private static final String BLOCK_OUTPUT_FORMAT = "%s (%s) blocks!%n";


    @Override
    public String format(EntityOnPositionSnapshot snapshot) {
        return String.format(BLOCK_OUTPUT_FORMAT, snapshot.getSnapshot().getEntityName(), snapshot.getSelectedField());
    }
}
