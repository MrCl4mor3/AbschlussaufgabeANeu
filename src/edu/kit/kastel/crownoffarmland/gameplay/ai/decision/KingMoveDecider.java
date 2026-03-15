package edu.kit.kastel.crownoffarmland.gameplay.ai.decision;

import edu.kit.kastel.crownoffarmland.model.Game;
import edu.kit.kastel.crownoffarmland.model.board.Position;
import edu.kit.kastel.crownoffarmland.model.team.TeamID;
import edu.kit.kastel.crownoffarmland.model.units.BoardEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Decides the king's next move.
 *
 * @author ucgdi
 */
public final class KingMoveDecider extends AbstractAIDecider {
    private static final int KING_ENEMY_WEIGHT_FACTOR = 2;
    private static final int KING_FELLOW_PRESENT_FACTOR = 3;
    private static final int KING_FELLOW_ON_FIELD_VALUE = 1;
    private static final int KING_NO_FELLOW_ON_FIELD_VALUE = 0;
    private static final int TIE_WEIGHT_VALUE = 1;


    /**
     * Creates a new king move decider.
     *
     * @param game the current game
     * @param boardAnalysisService the board analysis service
     * @param weightedRandomSelector the weighted random selector
     */
    public KingMoveDecider(Game game, BoardAnalysisService boardAnalysisService, WeightedRandomSelector weightedRandomSelector) {
        super(game, boardAnalysisService, weightedRandomSelector);
    }

    /**
     * Chooses the king's next position.
     *
     * @return the selected position
     */
    public Position chooseKingMove() {
        TeamID currentTeam = game.getCurrentTeamID();
        Position kingPosition = game.getKingPosition(currentTeam);

        List<Position> candidates = new ArrayList<>();
        for (Position candidate : game.boardView().getOrthogonalNeighbors(kingPosition)) {
            if (isValidKingTarget(candidate, currentTeam)) {
                candidates.add(candidate);
            }
        }
        candidates.add(kingPosition);

        int bestScore = Integer.MIN_VALUE;
        List<Position> bestPositions = new ArrayList<>();

        for (Position candidate : candidates) {
            int score = scoreKingTarget(candidate, kingPosition, currentTeam);

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

        List<Integer> tieWeights = createTieWeights(bestPositions.size(), TIE_WEIGHT_VALUE);
        int selectedIndex = weightedRandomSelector.selectWeightedRandom(tieWeights);
        return bestPositions.get(selectedIndex);
    }

    private boolean isValidKingTarget(Position candidate, TeamID currentTeam) {
        BoardEntity occupant = game.boardView().getOccupant(candidate);
        return occupant == null || occupant.getOwner().equals(currentTeam);
    }

    private int scoreKingTarget(Position candidate, Position kingPosition, TeamID currentTeam) {
        int distance = boardAnalysisService.manhattanDistance(candidate, kingPosition);
        int enemies = boardAnalysisService.countAdjacentEntitiesFromTeam(candidate, game.getEnemyTeamID(), true);
        int fellows = boardAnalysisService.countAdjacentEntitiesFromTeam(candidate, currentTeam, false);
        int fellowPresent = boardAnalysisService.hasOwnUnitOnField(candidate, currentTeam)
                ? KING_FELLOW_ON_FIELD_VALUE : KING_NO_FELLOW_ON_FIELD_VALUE;

        return fellows - KING_ENEMY_WEIGHT_FACTOR * enemies - distance - KING_FELLOW_PRESENT_FACTOR * fellowPresent;
    }

    private List<Integer> createTieWeights(int size, int score) {
        List<Integer> weights = new ArrayList<>();
        for (int index = 0; index < size; index++) {
            weights.add(score);
        }
        return weights;
    }
}