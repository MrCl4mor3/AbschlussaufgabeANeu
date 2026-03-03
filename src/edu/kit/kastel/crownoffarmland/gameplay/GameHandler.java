package edu.kit.kastel.crownoffarmland.gameplay;

import edu.kit.kastel.crownoffarmland.gameplay.combat.DuelManager;
import edu.kit.kastel.crownoffarmland.gameplay.unitmerge.UnitMerger;
import edu.kit.kastel.crownoffarmland.model.Game;
import edu.kit.kastel.crownoffarmland.model.board.Position;

public class GameHandler {
    private static final String HELP_COMMAND = "Use one of the following commands: select, board, move, flip, block, hand, place, show, " +
            "yield, state, quit";
    private static final Position TEAM1_KING_START = new Position(1,'D');
    private static final Position TEAM2_KING_START = new Position(7,'D');

    private final Game game;
    private final DuelManager duelManager;
    private final UnitMerger unitMerger;

    private Position selected;
    private boolean placedThisTurn;

    public GameHandler(Game game) {
        this(game, new DuelManager(), new UnitMerger());
    }

    private GameHandler(Game game, DuelManager duelManager, UnitMerger unitMerger) {
        this.game = game;
        this.duelManager = duelManager;
        this.unitMerger = unitMerger;

        initializeGame();
    }

    private void initializeGame() {

    }
}