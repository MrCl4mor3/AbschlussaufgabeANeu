package edu.kit.kastel.crownoffarmland.gameplay.snapshots;


/**
 * Creates a snapshot of an entity on his position.
 *
 * @author ucgdi
 */
public final class EntityOnPositionSnapshot {
    private final EntitySnapshot snapshot;
    private final String selectedField;

    /**
     * Creates a new EntityOnPositionSnapshot.
     * @param snapshot the snapshot of the entity
     * @param selectedField the FieldName of the field on which the entity is located
     */
    public EntityOnPositionSnapshot(EntitySnapshot snapshot, String selectedField) {
        this.snapshot = snapshot;
        this.selectedField = selectedField;
    }

    /**
     * Getter for the snapshot of the entity.
     * @return the EntitySnapshot
     */
    public EntitySnapshot getSnapshot() {
        return snapshot;
    }

    /**
     * Getter for the selected Field.
     * @return the selected FieldName
     */
    public String getSelectedField() {
        return selectedField;
    }
}
