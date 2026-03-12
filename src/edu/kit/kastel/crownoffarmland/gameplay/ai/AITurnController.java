package edu.kit.kastel.crownoffarmland.gameplay.ai;

import edu.kit.kastel.crownoffarmland.exceptions.CrownOfFarmlandException;
import edu.kit.kastel.crownoffarmland.gameplay.GameHandler;
import edu.kit.kastel.crownoffarmland.gameplay.snapshots.EndTurnSnapshot;
import edu.kit.kastel.crownoffarmland.gameplay.snapshots.EntitySnapshot;
import edu.kit.kastel.crownoffarmland.gameplay.snapshots.PlaceStepSnapshot;
import edu.kit.kastel.crownoffarmland.gameplay.snapshots.boardsnapshot.BoardSnapshot;
import edu.kit.kastel.crownoffarmland.gameplay.snapshots.EntityOnPositionSnapshot;
import edu.kit.kastel.crownoffarmland.gameplay.snapshots.movesnapshot.MoveSnapshot;
import edu.kit.kastel.crownoffarmland.model.Game;
import edu.kit.kastel.crownoffarmland.model.board.Position;
import edu.kit.kastel.crownoffarmland.ui.renderer.GameOutputPrinter;

import java.util.List;

/**
 * Controlls the AI turn.
 *
 * @author ucgdi
 */
public final class AITurnController {
    private final Game game;
    private final GameHandler gameHandler;
    private final AIDecisionService aiDecisionService;
    private final GameOutputPrinter printer;


    /**
     * Creates a new Controller.
     * @param gameHandler the gameHandler
     * @param game the model
     * @param aiDecisionService to decide the AI's actions
     * @param printer to generate a output
     */
    public AITurnController(GameHandler gameHandler, Game game, AIDecisionService aiDecisionService, GameOutputPrinter printer) {
        this.gameHandler = gameHandler;
        this.aiDecisionService = aiDecisionService;
        this.game = game;
        this.printer = printer;
    }

    /**
     * Execute the AI turn.
     * @throws CrownOfFarmlandException if a invalid Move triggert.
     */
    public void executeTurn() throws CrownOfFarmlandException {
        System.out.println(printer.formatHand(gameHandler.createHandSnapshot()));
        executeKingMove();

        if (gameHandler.isGameOver()) {
            return;
        }

        executePlacementIfPossible();

        if (gameHandler.isGameOver()) {
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
        List<PlaceStepSnapshot> placeStepSnapshots = gameHandler.placeUnits(new int[]{aiDecisionService.choosePlacementHandIndex()});

        System.out.print(printer.formatPlace(placeStepSnapshots));
        printBoardAndShow();
    }

    private void executeUnitAction() throws CrownOfFarmlandException {
        UnitActionDecision decision = aiDecisionService.chooseNextUnitAction();

        while (decision != null) {
            gameHandler.setSelected(decision.getSource());

            switch (decision.getActionType()) {
                case MOVE:
                    MoveSnapshot moveSnapshot = gameHandler.moveUnit(decision.getTarget());
                    printMoveOutput(moveSnapshot);

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
                    gameHandler.moveUnit(decision.getTarget());
                    break;
                default:
                    throw new CrownOfFarmlandException("Invalid action type");
            }

            decision = aiDecisionService.chooseNextUnitAction();
        }
    }


    private void executeYield() throws CrownOfFarmlandException {

        EndTurnSnapshot endTurnSnapshot;
        if (game.isHandFull(game.getCurrentTeamID())) {
            int discardIndex = aiDecisionService.chooseDiscardIndex();
            endTurnSnapshot = gameHandler.tryEndTurnWithDiscard(discardIndex);
        } else {
            endTurnSnapshot = gameHandler.tryEndTurn();
        }

        System.out.println(printer.formatYield(endTurnSnapshot));
    }


    private void printMoveOutput(MoveSnapshot moveSnapshot) throws CrownOfFarmlandException {
        System.out.print(printer.formatMove(moveSnapshot));

        if (!gameHandler.isGameOver()) {
            printBoardAndShow();
        }
    }

    private void printBoardAndShow() throws CrownOfFarmlandException {
        BoardSnapshot boardSnapshot = gameHandler.createBoardSnapshot();
        EntitySnapshot showSnapshot = gameHandler.createEntitySnapshot();

        System.out.println(printer.formatBoard(boardSnapshot));
        System.out.println(printer.formatShow(showSnapshot));
    }
}