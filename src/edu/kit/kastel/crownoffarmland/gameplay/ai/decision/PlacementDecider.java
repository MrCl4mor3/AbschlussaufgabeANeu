package edu.kit.kastel.crownoffarmland.gameplay.ai.decision;

import edu.kit.kastel.crownoffarmland.model.Game;
import edu.kit.kastel.crownoffarmland.model.board.Position;
import edu.kit.kastel.crownoffarmland.model.team.TeamID;
import edu.kit.kastel.crownoffarmland.model.units.BoardEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Decides where the AI should place a unit.
 *
 * @author ucgdi
 */
public final class PlacementDecider extends AbstractAIDecider {
    private static final int PLACEMENT_ENEMY_WEIGHT_FACTOR = 2;
    private static final int PLACEMENT_TIE_WEIGHT = 1;


    /**
     * Creates a new placement decider.
     *
     * @param game the current game
     * @param boardAnalysisService the board analysis service
     * @param weightedRandomSelector the weighted random selector
     */
    public PlacementDecider(Game game, BoardAnalysisService boardAnalysisService, WeightedRandomSelector weightedRandomSelector) {
        super(game, boardAnalysisService, weightedRandomSelector);
    }

    /**
     * Chooses a position for placing a unit.
     *
     * @return the selected position, or {@code null} if none is available
     */
    public Position choosePlacementPosition() {
        TeamID currentTeam = game.getCurrentTeamID();
        Position kingPosition = game.getKingPosition(currentTeam);

        List<Position> candidates = new ArrayList<>();
        for (Position position : game.boardView().getSurroundingPositions(kingPosition)) {
            BoardEntity occupant = game.boardView().getOccupant(position);
            if (occupant == null || occupant.getOwner().equals(currentTeam)) {
                candidates.add(position);
            }
        }

        if (candidates.isEmpty()) {
            return null;
        }

        int bestScore = Integer.MIN_VALUE;
        List<Position> bestPositions = new ArrayList<>();

        for (Position candidate : candidates) {
            int score = scorePlacementPosition(candidate, currentTeam);

            if (score > bestScore) {
                bestScore = score;
                bestPositions.clear();
                bestPositions.add(candidate);
            } else if (score == bestScore) {
                bestPositions.add(candidate);
            }
        }

        if (bestPositions.size() == 1) {
            return bestPositions.getFirst();
        }

        List<Integer> tieWeights = createTieWeights(bestPositions.size(), PLACEMENT_TIE_WEIGHT);
        int selectedIndex = weightedRandomSelector.selectWeightedRandom(tieWeights);
        return bestPositions.get(selectedIndex);
    }

    private int scorePlacementPosition(Position candidate, TeamID currentTeam) {
        Position enemyKingPosition = game.getKingPosition(game.getEnemyTeamID());
        int steps = boardAnalysisService.manhattanDistance(candidate, enemyKingPosition);
        int enemies = boardAnalysisService.countOrthogonalEntitiesFromTeam(candidate, game.getEnemyTeamID());
        int fellows = boardAnalysisService.countOrthogonalEntitiesFromTeam(candidate, currentTeam);

        return -steps + PLACEMENT_ENEMY_WEIGHT_FACTOR * enemies - fellows;
    }

    private List<Integer> createTieWeights(int size, int score) {
        List<Integer> weights = new ArrayList<>();
        for (int index = 0; index < size; index++) {
            weights.add(score);
        }
        return weights;
    }
}