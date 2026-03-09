package edu.kit.kastel.crownoffarmland.ui.renderer.entity;

import edu.kit.kastel.crownoffarmland.gameplay.snapshots.EntitySnapshot;

/**
 * Formats an {@link EntitySnapshot} into a string representation for display in the user interface.
 * This class provides methods to format the entity's name, team affiliation, attack, and defense values,
 * as well as handling special cases such as hidden entities or the Farmer King.
 *
 * @author ucgdi
 */
public class EntityFormatter {
    private static final String OUTPUT_NO_UNIT = "no unit";
    private static final String OUTPUT_HIDDEN = "???";
    private static final String FARMER_KING_SUFFIX = "'s Farmer King";
    private static final String TEAM_PREFIX = " (Team ";
    private static final String TEAM_SUFFIX = ")";
    private static final String ATK_PREFIX = "ATK: ";
    private static final String DEF_PREFIX = "DEF: ";


    private static final String ENTITY_SUMMARY_FORMAT = "%s (%d/%d)";

    /**
     * Formats the given EntitySnapshot into a string representation based on its properties and state.
     * @param entitySnapshot the EntitySnapshot to format, which contains information about the entity's name, team affiliation, attack,
     *                       defense, and visibility
     * @return a string representation of the EntitySnapshot, which may include the entity's name, team affiliation, attack, and defense
     *      values, or special indicators for hidden entities or the Farmer King
     */
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


    /**
     * Formats a summary of the given EntitySnapshot, including its name, attack, and defense values, in a concise format suitable for
     * display in a list or overview.
     * @param entitySnapshot the EntitySnapshot to format, which contains information about the entity's name, attack, and defense values
     * @return a string representation of the EntitySnapshot summary, which includes the entity's name followed by its attack and defense
     *      values in a concise format (e.g., "EntityName (ATK/DEF)")
     */
    public String formatEntitySummary(EntitySnapshot entitySnapshot) {
        return String.format(ENTITY_SUMMARY_FORMAT, entitySnapshot.getEntityName(), entitySnapshot.getAttack(),
                entitySnapshot.getDefense());
    }
}
