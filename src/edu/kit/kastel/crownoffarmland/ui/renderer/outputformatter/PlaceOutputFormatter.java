package edu.kit.kastel.crownoffarmland.ui.renderer.outputformatter;

import edu.kit.kastel.crownoffarmland.gameplay.snapshots.PlaceStepSnapshot;

import java.util.List;

/**
 * Formats.
 *
 * @author ucgdi
 */
public class PlaceOutputFormatter implements OutputFormatter<List<PlaceStepSnapshot>> {

    private static final String PLACE_MESSAGE = "%s places %s on %s.";
    private static final String MERGING_MESSAGE = "%s and %s on %s join forces!%n";
    private static final String MERGING_UNIT_SUCCESS_MESSAGE = "Success!";
    private static final String MERGING_UNIT_FAILURE_MESSAGE = "Union failed. %s was eliminated.";


    @Override
    public String format(List<PlaceStepSnapshot> snapshots) {
        StringBuilder output = new StringBuilder();
        for (PlaceStepSnapshot snapshot : snapshots) {
            formatPlaceStep(output, snapshot);
        }
        return output.toString();
    }

    private void formatPlaceStep(StringBuilder output, PlaceStepSnapshot snapshot) {
        output.append(String.format(PLACE_MESSAGE, snapshot.getTeamName(), snapshot.getPlacedUnitName(), snapshot.getTargetPosition()));

        if (snapshot.getExistingUnitName() != null) {
            output.append(System.lineSeparator()).append(String.format(MERGING_MESSAGE, snapshot.getPlacedUnitName(),
                    snapshot.getExistingUnitName(), snapshot.getTargetPosition()));
            if (snapshot.getEliminatedUnitName() == null) {
                output.append(MERGING_UNIT_SUCCESS_MESSAGE);
            } else {
                output.append(String.format(MERGING_UNIT_FAILURE_MESSAGE, snapshot.getExistingUnitName()));
            }
        }
    }
}
