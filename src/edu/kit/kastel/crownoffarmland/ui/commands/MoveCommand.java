package edu.kit.kastel.crownoffarmland.ui.commands;

import edu.kit.kastel.crownoffarmland.exceptions.CrownOfFarmlandException;
import edu.kit.kastel.crownoffarmland.gameplay.GameHandler;
import edu.kit.kastel.crownoffarmland.gameplay.snapshots.movesnapshot.DuelMoveSnapshot;
import edu.kit.kastel.crownoffarmland.gameplay.snapshots.movesnapshot.MergeMoveSnapshot;
import edu.kit.kastel.crownoffarmland.gameplay.snapshots.movesnapshot.MoveSnapshot;
import edu.kit.kastel.crownoffarmland.gameplay.snapshots.movesnapshot.MoveType;

/**
 * Implements the move command.
 * Command to move an entity to a new position.
 *
 * @author ucgdi
 */
public class MoveCommand extends  Command {
    private static final String COMMAND_NAME = "move";
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


    /**
     * Creates a new MoveCommand.
     *
     * @param commandHandler the CommandHandler to handle this command
     * @param gameHandler the GameHandler to execute this command
     */
    public MoveCommand(CommandHandler commandHandler, GameHandler gameHandler) {
        super(COMMAND_NAME, commandHandler, gameHandler);
    }

    @Override
    public void execute(String[] commandArgs) throws CrownOfFarmlandException {
        ensureOneArguments(commandArgs);
        String targetPositionName = commandArgs[0];
        MoveSnapshot result = gameHandler.moveUnit(targetPositionName);

        printMoveResult(result);

        if (!gameHandler.isGameOver()) {
            commandHandler.printBoard();
            commandHandler.printShow();
        }
    }


    private void printMoveResult(MoveSnapshot result) {
        if (result.wasBlocked()) {
            System.out.printf(REMOVE_BLOCK_MESSAGE, result.getMovedEntity().getEntityName());
        }
        if (!(result.getMoveType() == MoveType.DUEL)) {
            System.out.printf(MOVE_MESSAGE, result.getMovedEntity().getEntityName(), result.getToPositionName());
        }

        switch (result.getMoveType()) {
            case MERGE:
                printMergeResult((MergeMoveSnapshot) result);
                break;
            case DUEL:
                printDuelResult((DuelMoveSnapshot) result);
                break;
            default:
                break;
        }
    }

    private void printMergeResult(MergeMoveSnapshot mergeResult) {
        System.out.printf(MERGING_MESSAGE, mergeResult.getMovedEntity().getEntityName(),
            mergeResult.getTargetEnityName(), mergeResult.getToPositionName());
        if (mergeResult.isMergeSuccess()) {
            System.out.printf(MERGING_UNIT_SUCCESS_MESSAGE);
        } else {
            System.out.printf(MERGING_UNIT_FAILURE_MESSAGE, mergeResult.getMovedEntity().getEntityName());
        }
    }

    private void printDuelResult(DuelMoveSnapshot duelMoveSnapshot) {
        String attackerSummary = commandHandler.getEntityFormatter().formatEntitySummary(duelMoveSnapshot.getMovedEntity());
        String defenderSummary = commandHandler.getEntityFormatter().formatEntitySummary(duelMoveSnapshot.getTargetEntity());

        System.out.printf(ATTACK_MESSAGE, attackerSummary, defenderSummary, duelMoveSnapshot.getToPositionName());

        if (duelMoveSnapshot.attackerWasFlipped()) {
            System.out.printf(FLIP_MESSAGE, attackerSummary, duelMoveSnapshot.getFromPositionName());
        }

        if (duelMoveSnapshot.defenderWasFlipped()) {
            System.out.printf(FLIP_MESSAGE, defenderSummary, duelMoveSnapshot.getToPositionName());
        }

        if (duelMoveSnapshot.defenderWasEliminated()) {
            System.out.printf(ELIMINATION_MESSAGE, duelMoveSnapshot.getTargetEntity().getEntityName());
        }

        if (duelMoveSnapshot.attackerWasEliminated()) {
            System.out.printf(ELIMINATION_MESSAGE, duelMoveSnapshot.getMovedEntity().getEntityName());
        }

        if (duelMoveSnapshot.hasDamage()) {
            System.out.printf(DAMAGE_MESSAGE, duelMoveSnapshot.getDamagedTeamName(), duelMoveSnapshot.getDamageAmount());
        }

        if (duelMoveSnapshot.attackerMovesToTarget()) {
            System.out.printf(MOVE_MESSAGE, attackerSummary, duelMoveSnapshot.getToPositionName());
        }

        if (gameHandler.isGameOver()) {
            String winner = gameHandler.getWinner();

            String attackerTeam = duelMoveSnapshot.getMovedEntity().getTeamName();
            String defenderTeam = duelMoveSnapshot.getTargetEntity().getTeamName();

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
