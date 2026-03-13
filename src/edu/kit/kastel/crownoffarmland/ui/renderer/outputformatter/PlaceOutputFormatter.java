package edu.kit.kastel.crownoffarmland.ui.renderer.outputformatter;

import edu.kit.kastel.crownoffarmland.gameplay.snapshots.PlaceStepSnapshot;

import java.util.List;

/**
 * Formats.
 *
 * @author ucgdi
 */
public class PlaceOutputFormatter implements OutputFormatter<List<PlaceStepSnapshot>> {

    private static final String PLACE_MESSAGE = "%s places %s on %s.%n";
    private static final String ELIMINATION_MESSAGE = "%s was eliminated!%n";

    private final MergeOutputFormatter mergeOutputFormatter;


    /**
     * Creates a new PlaceOutputFormatter with the given MergeOutputFormatter.
     * @param mergeOutputFormatter to format the merge results in the output
     */
    public PlaceOutputFormatter(MergeOutputFormatter mergeOutputFormatter) {
        this.mergeOutputFormatter = mergeOutputFormatter;
    }


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
            boolean mergeSuccess = snapshot.getEliminatedUnitName() == null;
            output.append(mergeOutputFormatter.formatMergeOutput(mergeSuccess, snapshot.getExistingUnitName(),
                    snapshot.getPlacedUnitName(), snapshot.getTargetPosition()));
        } else if (snapshot.getEliminatedUnitName() != null) {
            output.append(String.format(ELIMINATION_MESSAGE, snapshot.getEliminatedUnitName()));
        }
    }
}
