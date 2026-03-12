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

    private static final String ENTITY_SUMMARY_FORMAT = "%s (%d/%d)";


    /**
     * Formats a summary of the given EntitySnapshot, including its name, attack, and defense values, in a concise format suitable for
     * display in a list or overview.
     * @param entitySnapshot the EntitySnapshot to format, which contains information about the entity's name, attack, and defense values
     * @return a string representation of the EntitySnapshot summary, which includes the entity's name followed by its attack and defense
     *      values in a concise format (e.g., "EntityName (ATK/DEF)")
     */
    public String formatEntitySummary(EntitySnapshot entitySnapshot) {
        if (entitySnapshot.isFarmerKing()) {
            return entitySnapshot.getEntityName();
        }
        return String.format(ENTITY_SUMMARY_FORMAT, entitySnapshot.getEntityName(), entitySnapshot.getAttack(),
                entitySnapshot.getDefense());
    }
}
