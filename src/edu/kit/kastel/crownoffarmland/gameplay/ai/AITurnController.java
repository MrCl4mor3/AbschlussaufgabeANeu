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


public final class AITurnController {
    private final Game game;
    private final GameHandler gameHandler;
    private final AIDecisionService AIDecisionService;
    private final GameOutputPrinter printer;

    public AITurnController(GameHandler gameHandler, Game game, AIDecisionService AIDecisionService, GameOutputPrinter printer) {
        this.gameHandler = gameHandler;
        this.AIDecisionService = AIDecisionService;
        this.game = game;
        this.printer = printer;
    }

    public void executeTurn() throws CrownOfFarmlandException {
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
        Position target = AIDecisionService.chooseKingMove();

        gameHandler.setSelected(kingPosition);
        MoveSnapshot moveSnapshot = gameHandler.moveUnit(target);

        printMoveOutput(moveSnapshot);
    }

    private void executePlacementIfPossible() throws CrownOfFarmlandException {
        Position target = AIDecisionService.choosePlacementPosition();

        if (target == null) {
            return;
        }

        int handIndex = AIDecisionService.choosePlacementHandIndex();

        gameHandler.setSelected(target);
        List<PlaceStepSnapshot> placeStepSnapshots = gameHandler.placeUnits(new int[]{AIDecisionService.choosePlacementHandIndex()});

        System.out.print(printer.formatPlace(placeStepSnapshots));
        printBoardAndShow();
    }

    private void executeUnitAction() throws CrownOfFarmlandException {
        UnitActionDecision decision = AIDecisionService.chooseNextUnitAction();

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
                    gameHandler.markSelectedUnitAsActed();
                    break;
                default:
                    throw new CrownOfFarmlandException("Invalid action type");
            }

            decision = AIDecisionService.chooseNextUnitAction();
        }
    }


    private void executeYield() throws CrownOfFarmlandException {

        EndTurnSnapshot endTurnSnapshot;
        if (game.isHandFull(game.getCurrentTeamID())) {
            int discardIndex = AIDecisionService.chooseDiscardIndex();
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