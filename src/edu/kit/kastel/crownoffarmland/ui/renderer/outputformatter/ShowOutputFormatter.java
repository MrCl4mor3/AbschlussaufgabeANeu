package edu.kit.kastel.crownoffarmland.ui.renderer.outputformatter;

import edu.kit.kastel.crownoffarmland.gameplay.snapshots.EntitySnapshot;
import edu.kit.kastel.crownoffarmland.ui.renderer.entity.EntityFormatter;

/**
 * Formats entity details for UI output.
 *
 * @author ucgdi
 */
public class ShowOutputFormatter extends AbstractOutputFormatter<EntitySnapshot> {
    private static final String OUTPUT_NO_UNIT = "<no unit>";
    private static final String OUTPUT_HIDDEN = "???";

    private static final String FARMER_KING_SUFFIX = "'s Farmer King";
    private static final String TEAM_PREFIX = " (Team ";
    private static final String TEAM_SUFFIX = ")";
    private static final String ATK_PREFIX = "ATK: ";
    private static final String DEF_PREFIX = "DEF: ";

    /**
     * Creates a new show output formatter.
     *
     * @param entityFormatter formatter for entity output
     */
    public ShowOutputFormatter(EntityFormatter entityFormatter) {
        super(entityFormatter);
    }

    @Override
    public String format(EntitySnapshot snapshot) {
        if (!snapshot.hasEntity()) {
            return OUTPUT_NO_UNIT;
        }
        if (snapshot.isFarmerKing()) {
            return snapshot.getTeamName() + FARMER_KING_SUFFIX;
        }

        return resolveDisplayName(snapshot) + TEAM_PREFIX + snapshot.getTeamName() + TEAM_SUFFIX
                + System.lineSeparator()
                + ATK_PREFIX + resolveDisplayAttack(snapshot) + System.lineSeparator()
                + DEF_PREFIX + resolveDisplayDefense(snapshot);
    }

    private String resolveDisplayName(EntitySnapshot snapshot) {
        if (snapshot.isHidden()) {
            return OUTPUT_HIDDEN;
        }
        return snapshot.getEntityName();
    }

    private String resolveDisplayAttack(EntitySnapshot snapshot) {
        if (snapshot.isHidden()) {
            return OUTPUT_HIDDEN;
        }
        return String.valueOf(snapshot.getAttack());
    }

    private String resolveDisplayDefense(EntitySnapshot snapshot) {
        if (snapshot.isHidden()) {
            return OUTPUT_HIDDEN;
        }
        return String.valueOf(snapshot.getDefense());
    }
}