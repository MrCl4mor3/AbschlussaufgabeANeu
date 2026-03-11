package edu.kit.kastel.crownoffarmland.gameplay.ai;

import edu.kit.kastel.crownoffarmland.exceptions.CrownOfFarmlandException;
import edu.kit.kastel.crownoffarmland.gameplay.GameHandler;
import edu.kit.kastel.crownoffarmland.gameplay.snapshots.movesnapshot.MoveSnapshot;
import edu.kit.kastel.crownoffarmland.model.Game;
import edu.kit.kastel.crownoffarmland.model.board.Position;


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
            System.out.printf(moveSnapshot.getToPositionName());
        } catch (CrownOfFarmlandException e) {
            System.out.printf("Enemy's king move failed: %s%n", e.getMessage());
            System.exit(1);
        }
    }



    public MoveSnapshot executeKingMove() throws CrownOfFarmlandException {
        Position target = AIDecisionService.chooseKingMove();
        gameHandler.setSelected(game.getKingPosition(game.getCurrentTeamID()));
        return gameHandler.moveUnit(target);
    }
}