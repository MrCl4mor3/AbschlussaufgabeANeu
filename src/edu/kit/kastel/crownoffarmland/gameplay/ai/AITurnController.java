package edu.kit.kastel.crownoffarmland.gameplay.ai;

import edu.kit.kastel.crownoffarmland.exceptions.CrownOfFarmlandException;
import edu.kit.kastel.crownoffarmland.gameplay.GameHandler;
import edu.kit.kastel.crownoffarmland.gameplay.snapshots.EndTurnSnapshot;
import edu.kit.kastel.crownoffarmland.gameplay.snapshots.EntityOnPositionSnapshot;
import edu.kit.kastel.crownoffarmland.gameplay.snapshots.EntitySnapshot;
import edu.kit.kastel.crownoffarmland.gameplay.snapshots.PlaceStepSnapshot;
import edu.kit.kastel.crownoffarmland.gameplay.snapshots.boardsnapshot.BoardSnapshot;
import edu.kit.kastel.crownoffarmland.gameplay.snapshots.movesnapshot.MoveSnapshot;
import edu.kit.kastel.crownoffarmland.model.Game;
import edu.kit.kastel.crownoffarmland.model.board.Position;
import edu.kit.kastel.crownoffarmland.ui.renderer.GameOutputPrinter;

import java.util.List;

/**
 * Controls the AI turn flow.
 *
 * @author ucgdi
 */
public final class AITurnController {
    private static final String UNEXPECTED_AI_ACTION_TYPE = "Unexpected AI action type";
    private static final String WINNER_MESSAGE = "%s wins!%n";

    private final Game game;
    private final GameHandler gameHandler;
    private final AIDecisionService aiDecisionService;
    private final GameOutputPrinter printer;

    /**
     * Creates a new AI turn controller.
     *
     * @param gameHandler the game handler
     * @param game the game model
     * @param aiDecisionService the AI decision service
     * @param printer the output printer
     */
    public AITurnController(GameHandler gameHandler, Game game, AIDecisionService aiDecisionService,
                            GameOutputPrinter printer) {
        this.gameHandler = gameHandler;
        this.aiDecisionService = aiDecisionService;
        this.game = game;
        this.printer = printer;
    }

    /**
     * Executes the complete AI turn.
     *
     * @throws CrownOfFarmlandException if an invalid action occurs
     */
    public void executeTurn() throws CrownOfFarmlandException {
        executeKingMove();

        if (gameHandler.isGameOver()) {
            System.out.printf(WINNER_MESSAGE, gameHandler.getWinner());
            return;
        }

        executePlacementIfPossible();

        if (gameHandler.isGameOver()) {
            System.out.printf(WINNER_MESSAGE, gameHandler.getWinner());
            return;
        }

        executeUnitAction();

        if (gameHandler.isGameOver()) {
            return;
        }

        executeYield();
    }

    private void executeKingMove() throws CrownOfFarmlandException {
        Position kingPosition = game.getKingPosition(game.getCurrentTeamID());
        Position target = aiDecisionService.chooseKingMove();

        gameHandler.setSelected(kingPosition);
        MoveSnapshot moveSnapshot = gameHandler.moveUnit(target);

        printMoveOutput(moveSnapshot);
    }

    private void executePlacementIfPossible() throws CrownOfFarmlandException {
        Position target = aiDecisionService.choosePlacementPosition();

        if (target == null) {
            return;
        }

        gameHandler.setSelected(target);
        List<PlaceStepSnapshot> placeStepSnapshots = gameHandler.placeUnits(
                new int[]{aiDecisionService.choosePlacementHandIndex()}
        );

        System.out.print(printer.formatPlace(placeStepSnapshots));
        printBoardAndShow();
    }

    private void executeUnitAction() throws CrownOfFarmlandException {
        UnitActionDecision decision = aiDecisionService.chooseNextUnitAction();

        while (decision != null) {
            gameHandler.setSelected(decision.getSource());

            switch (decision.getActionType()) {
                case MOVE:
                    printMoveOutput(gameHandler.moveUnit(decision.getTarget()));
                    if (gameHandler.isGameOver()) {
                        return;
                    }
                    break;
                case BLOCK:
                    EntityOnPositionSnapshot blockSnapshot = gameHandler.blockSelected();
                    System.out.println(printer.formatBlock(blockSnapshot));
                    printBoardAndShow();
                    break;
                case STAY:
                    printMoveOutput(gameHandler.moveUnit(decision.getTarget()));
                    break;
                default:
                    throw new IllegalStateException(UNEXPECTED_AI_ACTION_TYPE);
            }

            decision = aiDecisionService.chooseNextUnitAction();
        }
    }

    private void executeYield() throws CrownOfFarmlandException {
        EndTurnSnapshot endTurnSnapshot;
        if (game.teamView(game.getCurrentTeamID()).isHandFull()) {
            int discardIndex = aiDecisionService.chooseDiscardIndex();
            endTurnSnapshot = gameHandler.endTurnWithDiscard(discardIndex);
        } else {
            endTurnSnapshot = gameHandler.endTurn();
        }

        System.out.println(printer.formatYield(endTurnSnapshot));
    }

    private void printMoveOutput(MoveSnapshot moveSnapshot) throws CrownOfFarmlandException {
        System.out.print(printer.formatMove(moveSnapshot));
        if (gameHandler.isGameOver()) {
            System.out.printf(WINNER_MESSAGE, gameHandler.getWinner());
        }
        printBoardAndShow();
    }

    private void printBoardAndShow() throws CrownOfFarmlandException {
        BoardSnapshot boardSnapshot = gameHandler.snapshots().createBoardSnapshot();
        EntitySnapshot showSnapshot = gameHandler.snapshots().createEntitySnapshot();

        System.out.println(printer.formatBoard(boardSnapshot));
        System.out.println(printer.formatShow(showSnapshot));
    }
}