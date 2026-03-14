package edu.kit.kastel.crownoffarmland.gameplay.snapshots;

/**
 * Represents a single placement step.
 *
 * @author ucgdi
 */
public class PlaceStepSnapshot {
    private final String teamName;
    private final String placedUnitName;
    private final String existingUnitName;
    private final String eliminatedUnitName;
    private final String targetPosition;

    /**
     * Creates a new place step snapshot.
     *
     * @param teamName the name of the placing team
     * @param placedUnitName the name of the placed unit
     * @param existingUnitName the name of the replaced unit, or {@code null} if none was replaced
     * @param eliminatedUnitName the name of the eliminated unit, or {@code null} if none was eliminated
     * @param targetPosition the target position
     */
    public PlaceStepSnapshot(String teamName, String placedUnitName, String existingUnitName, String eliminatedUnitName,
            String targetPosition) {
        this.teamName = teamName;
        this.placedUnitName = placedUnitName;
        this.existingUnitName = existingUnitName;
        this.eliminatedUnitName = eliminatedUnitName;
        this.targetPosition = targetPosition;
    }

    /**
     * Returns the team name.
     *
     * @return the team name
     */
    public String getTeamName() {
        return teamName;
    }

    /**
     * Returns the placed unit name.
     *
     * @return the placed unit name
     */
    public String getPlacedUnitName() {
        return placedUnitName;
    }

    /**
     * Returns the replaced unit name.
     *
     * @return the replaced unit name, or {@code null} if none was replaced
     */
    public String getExistingUnitName() {
        return existingUnitName;
    }

    /**
     * Returns the eliminated unit name.
     *
     * @return the eliminated unit name, or {@code null} if none was eliminated
     */
    public String getEliminatedUnitName() {
        return eliminatedUnitName;
    }

    /**
     * Returns the target position.
     *
     * @return the target position
     */
    public String getTargetPosition() {
        return targetPosition;
    }
}