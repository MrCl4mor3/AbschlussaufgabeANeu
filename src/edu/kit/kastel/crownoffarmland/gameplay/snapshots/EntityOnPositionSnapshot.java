package edu.kit.kastel.crownoffarmland.gameplay.snapshots;

/**
 * Represents an entity snapshot together with its position.
 *
 * @author ucgdi
 */
public final class EntityOnPositionSnapshot {
    private final EntitySnapshot snapshot;
    private final String selectedField;

    /**
     * Creates a new entity-on-position snapshot.
     *
     * @param snapshot the entity snapshot
     * @param selectedField the field name of the entity position
     */
    public EntityOnPositionSnapshot(EntitySnapshot snapshot, String selectedField) {
        this.snapshot = snapshot;
        this.selectedField = selectedField;
    }

    /**
     * Returns the entity snapshot.
     *
     * @return the entity snapshot
     */
    public EntitySnapshot getSnapshot() {
        return snapshot;
    }

    /**
     * Returns the field name.
     *
     * @return the field name
     */
    public String getSelectedField() {
        return selectedField;
    }
}