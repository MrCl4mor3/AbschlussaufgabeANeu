package edu.kit.kastel.crownoffarmland.ui.renderer.entity;

import edu.kit.kastel.crownoffarmland.ui.snapshots.EntitySnapshot;

public class EntityFormatter {
    private static final String OUTPUT_NO_UNIT = "no unit";
    private static final String OUTPUT_HIDDEN = "???";
    private static final String FARMER_KING_SUFFIX = "'s Farmer King";
    private static final String TEAM_PREFIX = " (Team ";
    private static final String TEAM_SUFFIX = ")";
    private static final String ATK_PREFIX = "ATK: ";
    private static final String DEF_PREFIX = "DEF: ";


    private static final String ENTITY_SUMMARY_FORMAT = "%s (%d/%d)";

    public String format(EntitySnapshot entitySnapshot) {
        if (!entitySnapshot.hasEntity()) {
            return OUTPUT_NO_UNIT;
        }

        if (entitySnapshot.isFarmerKing()) {
            return entitySnapshot.getTeamName() + FARMER_KING_SUFFIX;
        }

        return resolveDisplayName(entitySnapshot) + TEAM_PREFIX + entitySnapshot.getTeamName() + TEAM_SUFFIX + System.lineSeparator()
                + ATK_PREFIX + resolveDisplayAttack(entitySnapshot) + System.lineSeparator()
                + DEF_PREFIX + resolveDisplayDefense(entitySnapshot);
    }


    private String resolveDisplayName(EntitySnapshot entitySnapshot) {
        if (entitySnapshot.isHidden()) {
            return OUTPUT_HIDDEN;
        }

        return entitySnapshot.getEntityName();
    }

    private String resolveDisplayAttack(EntitySnapshot entitySnapshot) {
        if (entitySnapshot.isHidden()) {
            return OUTPUT_HIDDEN;
        }
        return String.valueOf(entitySnapshot.getAttack());
    }

    private String resolveDisplayDefense(EntitySnapshot entitySnapshot) {
        if (entitySnapshot.isHidden()) {
            return OUTPUT_HIDDEN;
        }
        return String.valueOf(entitySnapshot.getDefense());
    }

    public String formatEntitySummary(EntitySnapshot entitySnapshot) {
        return String.format(ENTITY_SUMMARY_FORMAT, entitySnapshot.getEntityName(), entitySnapshot.getAttack(),
                entitySnapshot.getDefense());
    }
}
