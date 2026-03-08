package edu.kit.kastel.crownoffarmland.gameplay.snapshots;

public class PlaceStepSnapshot {
    private final String teamName;
    private final String placedUnitName;
    private final String existingUnitName;
    private final String eliminatedUnitName;
    private final String targetPosition;


    public PlaceStepSnapshot(String teamName, String placedUnitName, String existingUnitName, String eliminatedUnitName, String targetPosition) {
        this.teamName = teamName;
        this.placedUnitName = placedUnitName;
        this.existingUnitName = existingUnitName;
        this.eliminatedUnitName = eliminatedUnitName;
        this.targetPosition = targetPosition;
    }

    public String getTeamName() {
        return teamName;
    }

    public String getPlacedUnitName() {
        return placedUnitName;
    }

    public String getExistingUnitName() {
        return existingUnitName;
    }

    public String getEliminatedUnitName() {
        return eliminatedUnitName;
    }

    public String getTargetPosition() {
        return targetPosition;
    }
}
