package edu.kit.kastel.crownoffarmland.gameplay.snapshots;


public final class EntityOnPositionSnapshot {
    private final EntitySnapshot snapshot;
    private final String selectedField;

    public EntityOnPositionSnapshot(EntitySnapshot snapshot, String selectedField) {
        this.snapshot = snapshot;
        this.selectedField = selectedField;
    }

    public EntitySnapshot getSnapshot() {
        return snapshot;
    }

    public String getSelectedField() {
        return selectedField;
    }
}
