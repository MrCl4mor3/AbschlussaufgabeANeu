package edu.kit.kastel.crownoffarmland.gameplay.ai.decision;

import edu.kit.kastel.crownoffarmland.model.Game;

/**
 * Base class for AI deciders that determine the actions of the AI player.
 *
 * @author ucgdi
 */
public abstract class AIDecider {
    protected final Game game;
    protected final BoardAnalysisService boardAnalysisService;
    protected final WeightedRandomSelector weightedRandomSelector;

    /**
     * Creates a new AI decider base with shared dependencies.
     *
     * @param game the current game
     * @param boardAnalysisService the board analysis service
     * @param weightedRandomSelector the weighted random selector
     */
    protected AIDecider(Game game, BoardAnalysisService boardAnalysisService, WeightedRandomSelector weightedRandomSelector) {
        this.game = game;
        this.boardAnalysisService = boardAnalysisService;
        this.weightedRandomSelector = weightedRandomSelector;
    }
}