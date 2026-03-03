package edu.kit.kastel.crownoffarmland.gameplay.unitmerge;

import edu.kit.kastel.crownoffarmland.model.units.Unit;

/**
 * The MergeResult class represents the result of a unit merge operation in the game. It contains information about the type of merge
 * that occurred and the resulting merged unit. The MergeType enum defines the different types of merges that can occur.
 *
 * @author ucgdi
 */
public class MergeResult {
    private final MergeType mergeType;
    private final Unit unit;

    /**
     * Constructs a new MergeResult instance with the specified merge type and merged unit. The mergeType parameter indicates the type of
     * merge that occurred, while the mergedUnit parameter represents the resulting unit after the merge operation. This class is used to
     * encapsulate the outcome of a unit merge, allowing other parts of the game to easily access the details of the merge and the
     * resulting unit. The MergeResult can be used to determine the effects of the merge on the game state and to update the player's
     * hand or the board accordingly based on the type of merge that occurred.
     * @param mergeType the type of merge that occurred, represented by the MergeType enum. This parameter indicates the specific type of
     *                 merge that took place, such as a successful merge, a failed merge, or any other defined merge type in the game.
     *                  The mergeType can be used to determine the outcome of the merge operation and to trigger any necessary game logic
     *                  based on the type of merge that occurred.
     * @param mergedUnit the resulting unit after the merge operation. This parameter represents the unit that was created as a result of
     *                  the merge, which may have different attributes or abilities compared to the original units that were merged. The
     *                   mergedUnit can be used to update the player's hand, the board, or any other relevant game components based on
     *                   the outcome of the merge operation.
     */
    public MergeResult(MergeType mergeType, Unit mergedUnit) {
        this.mergeType = mergeType;
        this.unit = mergedUnit;
    }
}
