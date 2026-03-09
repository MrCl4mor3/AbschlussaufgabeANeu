package edu.kit.kastel.crownoffarmland.gameplay.snapshots;

/**
 * Snapshot for a place step in the game. It captures the state of the game after a unit has been placed on the board.
 * This includes the team that placed the unit, the name of the placed unit, any existing unit that was replaced, any unit that was
 * eliminated as a result of the placement, and the target position on the board where the unit was placed.
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
     * Constructor for PlaceStepSnapshot. It initializes all the fields with the provided values.
     * @param teamName The name of the team that placed the unit.
     * @param placedUnitName The name of the unit that was placed on the board.
     * @param existingUnitName The name of any existing unit that was replaced by the placed unit (if applicable).
     * @param eliminatedUnitName The name of any unit that was eliminated as a result of the placement (if applicable).
     * @param targetPosition The position on the board where the unit was placed.
     */
    public PlaceStepSnapshot(String teamName, String placedUnitName, String existingUnitName, String eliminatedUnitName, String targetPosition) {
        this.teamName = teamName;
        this.placedUnitName = placedUnitName;
        this.existingUnitName = existingUnitName;
        this.eliminatedUnitName = eliminatedUnitName;
        this.targetPosition = targetPosition;
    }

    /**
     * Getter for the name of the team that placed the unit.
     * @return The name of the team that placed the unit.
     */
    public String getTeamName() {
        return teamName;
    }

    /**
     * Getter for the name of the unit that was placed on the board.
     * @return The name of the unit that was placed on the board.
     */
    public String getPlacedUnitName() {
        return placedUnitName;
    }

    /**
     * Getter for the name of any existing unit that was replaced by the placed unit (if applicable).
     * @return The name of any existing unit that was replaced by the placed unit, or null if no unit was replaced.
     */
    public String getExistingUnitName() {
        return existingUnitName;
    }

    /**
     * Getter for the name of any unit that was eliminated as a result of the placement (if applicable).
     * @return The name of any unit that was eliminated as a result of the placement, or null if no unit was eliminated.
     */
    public String getEliminatedUnitName() {
        return eliminatedUnitName;
    }

    /**
     * Getter for the position on the board where the unit was placed.
     * @return The position on the board where the unit was placed.
     */
    public String getTargetPosition() {
        return targetPosition;
    }
}
