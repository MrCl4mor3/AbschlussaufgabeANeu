package edu.kit.kastel.crownoffarmland.ui.renderer.outputformatter;

import edu.kit.kastel.crownoffarmland.gameplay.snapshots.movesnapshot.DuelMoveSnapshot;
import edu.kit.kastel.crownoffarmland.gameplay.snapshots.movesnapshot.MergeMoveSnapshot;
import edu.kit.kastel.crownoffarmland.gameplay.snapshots.movesnapshot.MoveSnapshot;
import edu.kit.kastel.crownoffarmland.gameplay.snapshots.movesnapshot.MoveType;
import edu.kit.kastel.crownoffarmland.ui.renderer.entity.EntityFormatter;

/**
 * This class is responsible for formatting the output of move actions in the game. It takes a MoveSnapshot as input and generates a
 * string representation of the move, including details about the entities involved, the type of move, and the outcome of the move (e.g.,
 * whether it was blocked, whether a merge was successful, or the results of a duel). The output is designed to be informative and
 * engaging for the player, providing a clear narrative of the events that occurred during the move.
 *
 * @author ucgdi
 */
public class MoveOutputFormatter extends AbstractOutputFormatter<MoveSnapshot> {

    private static final String MERGING_MESSAGE = "%s and %s on %s join forces!%n";
    private static final String MERGING_UNIT_SUCCESS_MESSAGE = "Success!%n";
    private static final String MERGING_UNIT_FAILURE_MESSAGE = "Union failed. %s was eliminated.%n";
    private static final String MOVE_MESSAGE = "%s moves to %s.%n";
    private static final String REMOVE_BLOCK_MESSAGE = "%s no longer blocks.%n";
    private static final String ATTACK_MESSAGE = "%s attacks %s on %s!%n";
    private static final String FLIP_MESSAGE = "%s was flipped on %s!%n";
    private static final String ELIMINATION_MESSAGE = "%s was eliminated!%n";
    private static final String DAMAGE_MESSAGE = "%s takes %d damage!%n";
    private static final String LIFE_ZERO_MESSAGE = "%s's life points dropped to 0!%n";
    private static final String NOT_REVEALED_UNIT = "???";


    /**
     *  Creates a new MoveOutputFormatter with the given EntityFormatter.
     * @param entityFormatter the EntityFormatter used to format entity summaries in the output
     */
    public MoveOutputFormatter(EntityFormatter entityFormatter) {
        super(entityFormatter);
    }


    @Override
    public String format(MoveSnapshot snapshot) {
        StringBuilder output = new StringBuilder();

        if (snapshot.wasBlocked()) {
            output.append(String.format(REMOVE_BLOCK_MESSAGE, snapshot.getMovedEntity().getEntityName()));
        }
        if (!(snapshot.getMoveType() == MoveType.DUEL)) {
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
                // No additional output for normal moves
                break;
        }
        return output.toString();
    }


    private void formatMergeResult(MergeMoveSnapshot snapshot, StringBuilder output) {
        output.append(String.format(MERGING_MESSAGE, snapshot.getMovedEntity().getEntityName(),
                snapshot.getTargetEntityName(), snapshot.getToPositionName()));
        if (snapshot.isMergeSuccess()) {
            output.append(MERGING_UNIT_SUCCESS_MESSAGE);
        } else {
            output.append(String.format(MERGING_UNIT_FAILURE_MESSAGE, snapshot.getMovedEntity().getEntityName()));
        }
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

        //ToDo GameOver Message implemeniteren

        boolean isGameOver = false;
        if (isGameOver) {
            String winner = "";
            // //getWinner();

            String attackerTeam = snapshot.getMovedEntity().getTeamName();
            String defenderTeam = snapshot.getTargetEntity().getTeamName();

            String losingTeam;
            if (winner.equals(attackerTeam)) {
                losingTeam = defenderTeam;
            } else {
                losingTeam = attackerTeam;
            }

            System.out.printf(LIFE_ZERO_MESSAGE, losingTeam);
        }
    }
}
