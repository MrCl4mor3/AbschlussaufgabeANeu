package edu.kit.kastel.crownoffarmland.gameplay.ai;

import edu.kit.kastel.crownoffarmland.gameplay.TurnState;
import edu.kit.kastel.crownoffarmland.gameplay.unitmerge.UnitMerger;
import edu.kit.kastel.crownoffarmland.model.Game;
import edu.kit.kastel.crownoffarmland.model.board.Position;
import edu.kit.kastel.crownoffarmland.model.team.TeamID;
import edu.kit.kastel.crownoffarmland.model.units.BoardEntity;
import edu.kit.kastel.crownoffarmland.model.units.Unit;

import java.util.ArrayList;
import java.util.List;

/**
 * Test.
 *
 * @author ucgdi
 */
public final class AIDecisionService {

    private static final int KING_ENEMY_WEIGHT_FACTOR = 2;
    private static final int KING_FELLOW_PRESENT_FACTOR = 3;
    private static final int KING_FELLOW_ON_FIELD_VALUE = 1;
    private static final int KING_NO_FELLOW_ON_FIELD_VALUE = 0;
    private static final int TIE_WEIGHT_VALUE = 1;

    private static final int PLACEMENT_ENEMY_WEIGHT_FACTOR = 2;
    private static final int PLACEMENT_TIE_WEIGHT = 1;
    private static final int HAND_INDEX_OFFSET = 1;


    private static final int BLOCK_MIN_SCORE = 1;
    private static final int STAY_MIN_SCORE = 0;
    private static final int DIVISOR_100 = 100;
    private static final int HIDDEN_ENEMY_PENALTY = 500;
    private static final int ADVANCE_STEPS_FACTOR = 10;
    private static final int DUEL_FACTOR = 2;

    private final UnitMerger unitMerger;
    private final TurnState turnState;
    private final Game game;
    private final WeightedRandomSelector weightedRandomSelector;

    /**
     * Constructor for creating an EnemyDecisionService object with the specified game state, turn state, and weighted random selector.
     * @param game the current game state, used to evaluate potential moves, place units, and other game-related information
     * @param weightedRandomSelector the weighted random selector, used to select a choice among multiple options with the same score
     */
    public AIDecisionService(Game game, TurnState turnState, UnitMerger unitMerger, WeightedRandomSelector weightedRandomSelector) {
        this.game = game;
        this.turnState = turnState;
        this.unitMerger = unitMerger;
        this.weightedRandomSelector = weightedRandomSelector;
    }

    /**
     * Determines the best move for the enemy's king based on the current game state. The method evaluates all valid moves for the king,
     * including staying in place, and assigns a score to each potential move. The scoring is based on the number of adjacent friendly
     * and enemy units, the distance from the current position, and whether there is a friendly unit on the target position. If multiple
     * moves have the same highest score, one of them is selected randomly using weighted random selection.
     * @return the position to which the enemy's king should move, or the current position if staying in place is the best option
     */
    public Position chooseKingMove() {
        TeamID currentTeam = game.getCurrentTeamID();
        Position kingPosition = game.getKingPosition(currentTeam);

        List<Position> candidates = new ArrayList<>();
        for (Position candidate : game.getOrthogonalNeighbors(kingPosition)) {
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
            return bestPositions.get(0);
        } else {
            List<Integer> tieWeights = createTieWeights(bestPositions.size(), TIE_WEIGHT_VALUE);
            int selectedIndex = weightedRandomSelector.selectWeightedRandom(tieWeights);
            return bestPositions.get(selectedIndex);
        }
    }

    public Position choosePlacementPosition() {
        TeamID currentTeam = game.getCurrentTeamID();
        Position kingPosition = game.getKingPosition(currentTeam);

        List<Position> candidates = game.getOrthogonalNeighbors(kingPosition);

        if (candidates.isEmpty()) {
            return null; // No valid placement positions available
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
            return bestPositions.get(0);
        }

        List<Integer> tieWeights = createTieWeights(bestPositions.size(), PLACEMENT_TIE_WEIGHT);
        int selectedIndex = weightedRandomSelector.selectWeightedRandom(tieWeights);
        return bestPositions.get(selectedIndex);
    }

    public int choosePlacementHandIndex() {
        TeamID currentTeam = game.getCurrentTeamID();
        List<Integer> atkWeights = new ArrayList<>();

        for (int handIndex = 0; handIndex < game.getHandSize(currentTeam); handIndex++) {
            atkWeights.add(game.getHandCardAt(currentTeam, handIndex).getAtk());
        }

        int selectedIndex = weightedRandomSelector.selectWeightedRandom(atkWeights);
        return selectedIndex + HAND_INDEX_OFFSET;
    }


    public UnitActionDecision chooseNextUnitAction() {
        TeamID currentTeam = game.getCurrentTeamID();
        List<UnitCandidate> candidates = getMoveableUnitCandidates(currentTeam);
        return null;
    }

    public int chooseDiscardIndex() {
        return 0;
    }






    private boolean isValidKingTarget(Position candidate, TeamID currentTeam) {
        BoardEntity occupant = game.getOccupant(candidate);
        return occupant == null || occupant.getOwner().equals(currentTeam);
    }

    private int scoreKingTarget(Position candidate, Position kingPosition, TeamID currentTeam) {
        int distance = manhattanDistance(candidate, kingPosition);
        int enemies = countAdjacentEntitiesFromTeam(candidate, game.getEnemyTeamID(), true);
        int fellows = countAdjacentEntitiesFromTeam(candidate, currentTeam, false);
        int fellowPresent = hasOwnUnitOnField(candidate, currentTeam) ? KING_FELLOW_ON_FIELD_VALUE : KING_NO_FELLOW_ON_FIELD_VALUE;
        return fellows - KING_ENEMY_WEIGHT_FACTOR * enemies - distance - KING_FELLOW_PRESENT_FACTOR * fellowPresent;
    }

    private int scorePlacementPosition(Position candidate, TeamID currentTeam) {
        Position enemyKingPosition = game.getKingPosition(game.getEnemyTeamID());
        int steps = manhattanDistance(candidate, enemyKingPosition);
        int enemies = countAdjacentEntitiesFromTeam(candidate, currentTeam, true);
        int fellows = countAdjacentEntitiesFromTeam(candidate, currentTeam, false);
        return -steps + PLACEMENT_ENEMY_WEIGHT_FACTOR * enemies - fellows;
    }


    private int manhattanDistance(Position a, Position b) {
        return Math.abs(a.getRow() - b.getRow()) + Math.abs(a.getColumn() - b.getColumn());
    }

    private int countAdjacentEntitiesFromTeam(Position center, TeamID team, boolean includeKing) {
        int count = 0;

        for (Position neighbor : game.getSurroundingPositions(center)) {
            BoardEntity occupant = game.getOccupant(neighbor);
            if (occupant != null && occupant.getOwner().equals(team)) {
                if (includeKing || !occupant.isFarmerKing()) {
                    count++;
                }
            }
        }
        return count;
    }

    private boolean hasOwnUnitOnField(Position candidate, TeamID team) {
        BoardEntity occupant = game.getOccupant(candidate);
        return occupant != null && occupant.getOwner().equals(team) && !occupant.isFarmerKing();
    }

    private List<Integer> createTieWeights(int size, int score) {
        List<Integer> weights = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            weights.add(score);
        }
        return weights;
    }


    private List<UnitCandidate> getMoveableUnitCandidates(TeamID currentTeam) {
        List<UnitCandidate> candidates = new ArrayList<>();

        for (int row = 0; row < game.getBoardSize(); row++) {
            for (int column = 0; column < game.getBoardSize(); column++) {
                Position source = game.getPositionAt(row, column);
                BoardEntity occupant = game.getOccupant(source);

                if (occupant != null && occupant.getOwner().equals(currentTeam) &&  !occupant.isFarmerKing() && !turnState.hasMoved(occupant)) {
                    Unit unit = (Unit) occupant;
                    candidates.add(evaluateUnit(source, unit, currentTeam));
                }
            }
        }
        return candidates;
    }

    private UnitCandidate evaluateUnit(Position source, Unit unit, TeamID currentTeam) {
        List<ActionScore> actionScores = evaluatePossibleActions(source, unit, currentTeam);
        int totalScore = 0;
        for (ActionScore actionScore : actionScores) {
            totalScore += actionScore.getScore();
        }

        return new UnitCandidate(source, actionScores, totalScore);
    }

    private List<ActionScore> evaluatePossibleActions(Position source, Unit unit, TeamID team) {
        List<ActionScore> actionScores = new ArrayList<>();
        return null;
    }


}
