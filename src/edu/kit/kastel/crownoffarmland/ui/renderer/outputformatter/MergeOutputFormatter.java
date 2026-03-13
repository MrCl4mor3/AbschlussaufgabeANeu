package edu.kit.kastel.crownoffarmland.ui.renderer.outputformatter;

/**
 * Formats output for merging two units together.
 *
 * @author ucgdi
 */
public final class MergeOutputFormatter {
    private static final String MERGING_MESSAGE = "%s and %s on %s join forces!%n";
    private static final String MERGING_UNIT_SUCCESS_MESSAGE = "Success!";
    private static final String MERGING_UNIT_FAILURE_MESSAGE = "Union failed. %s was eliminated.%n";
    private static final String ELIMINATION_MESSAGE = "%s was eliminated!%n";


    /**
     * Returns the formatted output for a merge action.
     *
     * @param success whether the merge was successful
     * @param existingUnit the name of the unit already on the target field
     * @param movedUnit the name of the moved or placed unit
     * @param targetPosition the target position of the merge
     * @return the formatted merge output
     */
    public String formatMergeOutput(boolean success, String existingUnit, String movedUnit, String targetPosition) {
        StringBuilder output = new StringBuilder();
        output.append(String.format(MERGING_MESSAGE, movedUnit, existingUnit, targetPosition));
        if (success) {
            formatSuccess(output);
        } else {
            formatFailure(output, existingUnit);
        }
        return output.toString();
    }

    private void formatSuccess(StringBuilder output) {
        output.append(MERGING_UNIT_SUCCESS_MESSAGE).append(System.lineSeparator());

    }

    private void formatFailure(StringBuilder output, String existingUnit) {
        output.append(String.format(MERGING_UNIT_FAILURE_MESSAGE, existingUnit));
    };
}
