package edu.kit.kastel.crownoffarmland.gameplay;

import edu.kit.kastel.crownoffarmland.gameplay.combat.DuelManager;
import edu.kit.kastel.crownoffarmland.gameplay.unitmerge.UnitMerger;
import edu.kit.kastel.crownoffarmland.model.Game;
import edu.kit.kastel.crownoffarmland.model.board.Position;

/**
 * The GameHandler class is responsible for managing the state and flow of the game. It interacts with the Game model to execute player
 * commands, manage turns, and handle game logic. The GameHandler uses a DuelManager to resolve combat between units and a UnitMerger to
 * handle unit merging mechanics. It maintains the currently selected position on the board and tracks whether a unit has been placed
 * during the current turn. The GameHandler provides methods for processing player commands, updating the game state, and determining the
 * outcome of the game based on player actions and interactions between units on the board.
 *
 * @author ucgdi
 */
public class GameHandler {
    private static final Position TEAM1_KING_START = new Position(1, 'D');
    private static final Position TEAM2_KING_START = new Position(7, 'D');

    private final Game game;
    private final DuelManager duelManager;
    private final UnitMerger unitMerger;

    private Position selected;
    private boolean placedThisTurn;

    /**
     * Constructs a new GameHandler instance with the specified Game model. The GameHandler initializes the DuelManager and UnitMerger,
     * and sets up the initial state of the game. The selected position is initially set to null, and the placedThisTurn flag is set to
     * false, indicating that no unit has been placed during the current turn. The GameHandler is responsible for managing the game state
     * and processing player commands based on the provided Game model.
     * @param game The Game model that represents the current state of the game. The GameHandler will interact with this model to execute
     *            player commands and manage the flow of the game. The Game model should be properly initialized with the necessary game
     *             components, such as the board, teams, and units, before being passed to the GameHandler constructor.
     */
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