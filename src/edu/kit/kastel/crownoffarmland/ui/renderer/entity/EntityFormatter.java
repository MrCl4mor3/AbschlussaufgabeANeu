package edu.kit.kastel.crownoffarmland.ui.renderer.entity;

import edu.kit.kastel.crownoffarmland.gameplay.snapshots.EntitySnapshot;

/**
 * Formats entity snapshots for output.
 *
 * @author ucgdi
 */
public class EntityFormatter {
    private static final String ENTITY_SUMMARY_FORMAT = "%s (%d/%d)";

    /**
     * Formats a short summary of the given entity.
     *
     * @param entitySnapshot the entity snapshot
     * @return the formatted entity summary
     */
    public String formatEntitySummary(EntitySnapshot entitySnapshot) {
        if (entitySnapshot.isFarmerKing()) {
            return entitySnapshot.getEntityName();
        }
        return String.format(
                ENTITY_SUMMARY_FORMAT,
                entitySnapshot.getEntityName(),
                entitySnapshot.getAttack(),
                entitySnapshot.getDefense()
        );
    }
}