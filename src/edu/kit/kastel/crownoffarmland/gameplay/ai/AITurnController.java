package edu.kit.kastel.crownoffarmland.gameplay.ai;

import edu.kit.kastel.crownoffarmland.exceptions.CrownOfFarmlandException;
import edu.kit.kastel.crownoffarmland.gameplay.GameHandler;
import edu.kit.kastel.crownoffarmland.gameplay.snapshots.PlaceStepSnapshot;
import edu.kit.kastel.crownoffarmland.gameplay.snapshots.movesnapshot.MoveSnapshot;
import edu.kit.kastel.crownoffarmland.model.Game;
import edu.kit.kastel.crownoffarmland.model.board.Position;

import java.sql.Array;
import java.util.List;


public final class AITurnController {
    private final Game game;
    private final GameHandler gameHandler;
    private final AIDecisionService AIDecisionService;

    public AITurnController(GameHandler gameHandler, Game game, AIDecisionService AIDecisionService) {
        this.gameHandler = gameHandler;
        this.AIDecisionService = AIDecisionService;
        this.game = game;
    }

    public void executeTurn() {
        try {
            MoveSnapshot moveSnapshot = executeKingMove();
            System.out.println(moveSnapshot.getToPositionName());
            List<PlaceStepSnapshot> placeStepSnapshots = executePlacement();
            System.out.println(placeStepSnapshots.get(0).getPlacedUnitName());
            System.out.println(placeStepSnapshots.get(0).getTargetPosition());
            gameHandler.endTurn();
        } catch (CrownOfFarmlandException e) {
            System.out.printf("Enemy's king move failed: %s%n", e.getMessage());
            System.exit(1);
        }
    }



    private MoveSnapshot executeKingMove() throws CrownOfFarmlandException {
        Position target = AIDecisionService.chooseKingMove();
        gameHandler.setSelected(game.getKingPosition(game.getCurrentTeamID()));
        return gameHandler.moveUnit(target);
    }

    private List<PlaceStepSnapshot> executePlacement() throws CrownOfFarmlandException {
        Position target = AIDecisionService.choosePlacementPosition();
        gameHandler.setSelected(target);
        return gameHandler.placeUnits(new int[] {AIDecisionService.choosePlacementHandIndex()});
    }

}