package edu.kit.kastel.crownoffarmland.ui.renderer.outputformatter;

import edu.kit.kastel.crownoffarmland.gameplay.snapshots.EntitySnapshot;
import edu.kit.kastel.crownoffarmland.ui.renderer.entity.EntityFormatter;

public class YieldOutputFormatter extends AbstractOutputFormatter<EntitySnapshot> {

    private static final String SUCCESSFULLY_ENDED_TURN_MESSAGE = "It is %s's turn!%n";
    private static final String DISCARDING_CARD_MESSAGE = "%s discarded %s. ";
    private static final String NO_CARD_LEFT_MESSAGE = "%s has no cards left in the deck!%n";

    public YieldOutputFormatter(EntityFormatter entityFormatter) {
        super(entityFormatter);
    }

    @Override
    public String format(EntitySnapshot snapshot) {
        StringBuilder output = new StringBuilder();

        return output.toString();
    }
}
