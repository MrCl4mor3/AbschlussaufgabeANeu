package edu.kit.kastel.crownoffarmland.ui.renderer.outputformatter;

import edu.kit.kastel.crownoffarmland.gameplay.snapshots.movesnapshot.DuelMoveSnapshot;
import edu.kit.kastel.crownoffarmland.gameplay.snapshots.movesnapshot.MergeMoveSnapshot;
import edu.kit.kastel.crownoffarmland.gameplay.snapshots.movesnapshot.MoveSnapshot;
import edu.kit.kastel.crownoffarmland.gameplay.snapshots.movesnapshot.MoveType;
import edu.kit.kastel.crownoffarmland.ui.renderer.entity.EntityFormatter;

/**
 * Formats move actions for UI output.
 *
 * @author ucgdi
 */
public class MoveOutputFormatter extends AbstractOutputFormatter<MoveSnapshot> {
    private static final String MOVE_MESSAGE = "%s moves to %s.%n";
    private static final String REMOVE_BLOCK_MESSAGE = "%s no longer blocks.%n";

    private static final String ATTACK_MESSAGE = "%s attacks %s on %s!%n";
    private static final String FLIP_MESSAGE = "%s was flipped on %s!%n";
    private static final String ELIMINATION_MESSAGE = "%s was eliminated!%n";
    private static final String DAMAGE_MESSAGE = "%s takes %d damage!%n";
    private static final String LIFE_ZERO_MESSAGE = "%s's life points dropped to 0!%n";
    private static final String NOT_REVEALED_UNIT = "???";

    private final MergeOutputFormatter mergeOutputFormatter;

    /**
     * Creates a new move output formatter.
     *
     * @param entityFormatter formatter for entity output
     * @param mergeOutputFormatter formatter for merge output
     */
    public MoveOutputFormatter(EntityFormatter entityFormatter, MergeOutputFormatter mergeOutputFormatter) {
        super(entityFormatter);
        this.mergeOutputFormatter = mergeOutputFormatter;
    }

    @Override
    public String format(MoveSnapshot snapshot) {
        StringBuilder output = new StringBuilder();

        if (snapshot.wasBlocked()) {
            output.append(String.format(REMOVE_BLOCK_MESSAGE, snapshot.getMovedEntity().getEntityName()));
        }
        if (snapshot.getMoveType() != MoveType.DUEL) {
            output.append(String.format(MOVE_MESSAGE, snapshot.getMovedEntity().getEntityName(), snapshot.getToPositionName()));
        }

        switch (snapshot.getMoveType()) {
            case MERGE:
                formatMergeResult((MergeMoveSnapshot) snapshot, output);
                break;
            case DUEL:
                formatDuelMove((DuelMoveSnapshot) snapshot, output);
                break;
            default:
                break;
        }

        return output.toString();
    }

    private void formatMergeResult(MergeMoveSnapshot snapshot, StringBuilder output) {
        output.append(mergeOutputFormatter.formatMergeOutput(
                snapshot.isMergeSuccess(),
                snapshot.getTargetEntityName(),
                snapshot.getMovedEntity().getEntityName(),
                snapshot.getToPositionName()
        ));
    }

    private void formatDuelMove(DuelMoveSnapshot snapshot, StringBuilder output) {
        String attackerSummary = entityFormatter.formatEntitySummary(snapshot.getMovedEntity());
        String defenderSummary = entityFormatter.formatEntitySummary(snapshot.getTargetEntity());
        String defenderDisplay = snapshot.getTargetEntity().isHidden() ? NOT_REVEALED_UNIT : defenderSummary;

        output.append(String.format(ATTACK_MESSAGE, attackerSummary, defenderDisplay, snapshot.getToPositionName()));

        if (snapshot.attackerWasFlipped()) {
            output.append(String.format(FLIP_MESSAGE, attackerSummary, snapshot.getFromPositionName()));
        }
        if (snapshot.defenderWasFlipped()) {
            output.append(String.format(FLIP_MESSAGE, defenderSummary, snapshot.getToPositionName()));
        }
        if (snapshot.defenderWasEliminated()) {
            output.append(String.format(ELIMINATION_MESSAGE, snapshot.getTargetEntity().getEntityName()));
        }
        if (snapshot.attackerWasEliminated()) {
            output.append(String.format(ELIMINATION_MESSAGE, snapshot.getMovedEntity().getEntityName()));
        }
        if (snapshot.hasDamage()) {
            output.append(String.format(DAMAGE_MESSAGE, snapshot.getDamagedTeamName(), snapshot.getDamageAmount()));
        }
        if (snapshot.attackerMovesToTarget()) {
            output.append(String.format(MOVE_MESSAGE, snapshot.getMovedEntity().getEntityName(), snapshot.getToPositionName()));
        }
        if (snapshot.isGameOver()) {
            output.append(String.format(LIFE_ZERO_MESSAGE, snapshot.getLoserName()));
        }
    }
}