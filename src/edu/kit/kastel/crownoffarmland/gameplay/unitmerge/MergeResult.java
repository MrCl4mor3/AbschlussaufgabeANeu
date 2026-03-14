package edu.kit.kastel.crownoffarmland.gameplay.unitmerge;

import edu.kit.kastel.crownoffarmland.model.units.Unit;

/**
 * Represents the result of a unit merge.
 *
 * @author ucgdi
 */
public class MergeResult {
    private final MergeType mergeType;
    private final Unit unit;

    /**
     * Creates a new merge result.
     *
     * @param mergeType the merge type
     * @param mergedUnit the resulting unit
     */
    public MergeResult(MergeType mergeType, Unit mergedUnit) {
        this.mergeType = mergeType;
        this.unit = mergedUnit;
    }

    /**
     * Returns the resulting unit.
     *
     * @return the resulting unit
     */
    public Unit getUnit() {
        return unit;
    }

    /**
     * Returns whether the merge was successful.
     *
     * @return {@code true} if the merge was successful
     */
    public boolean isSuccessful() {
        return this.mergeType != MergeType.INCOMPATIBLE;
    }
}