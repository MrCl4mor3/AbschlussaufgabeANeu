package edu.kit.kastel.crownoffarmland.gameplay.unitmerge;

import edu.kit.kastel.crownoffarmland.model.units.Unit;

public class MergeResult {
    private final MergeType mergeType;
    private final Unit unit;

    public MergeResult(MergeType mergeType, Unit mergedUnit) {
        this.mergeType = mergeType;
        this.unit = mergedUnit;
    }
}
