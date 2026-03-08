package edu.kit.kastel.crownoffarmland.exceptions;

public class EnemyUnitSelectedException extends InvalidGameStateException {
    private static final String MESSAGE = "An enemy unit is selected. Please select a friendly unit to perform this action.";
    public EnemyUnitSelectedException() {
        super(MESSAGE);
    }
}
