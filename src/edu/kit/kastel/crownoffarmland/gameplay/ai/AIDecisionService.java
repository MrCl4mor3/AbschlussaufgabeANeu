package edu.kit.kastel.crownoffarmland.gameplay.ai;

import edu.kit.kastel.crownoffarmland.gameplay.TurnState;
import edu.kit.kastel.crownoffarmland.gameplay.unitmerge.MergeResult;
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

    private static final int[][] ORTHOGONAL_DELTAS = {
            {1, 0},   // oben
            {0, 1},   // rechts
            {-1, 0},  // unten
            {0, -1}   // links
    };
    private static final int BLOCK_MIN_SCORE = 1;
    private static final int STAY_MIN_SCORE = 0;
    private static final int DIVISOR = 100;
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
     * @param turnState the current turn state, used to determine which units have already moved and which actions are still available
     * @param unitMerger the unit merger, used to evaluate potential merges when scoring moves and actions
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
        } else {
            List<Integer> tieWeights = createTieWeights(bestPositions.size(), TIE_WEIGHT_VALUE);
            int selectedIndex = weightedRandomSelector.selectWeightedRandom(tieWeights);
            return bestPositions.get(selectedIndex);
        }
    }

    /**
     * Choose a Placement Position.
     * @return a Position
     */
    public Position choosePlacementPosition() {
        TeamID currentTeam = game.getCurrentTeamID();
        Position kingPosition = game.getKingPosition(currentTeam);

        //List<Position> candidates = game.boardView().getSurroundingPositions(kingPosition);
        List<Position> candidates = new ArrayList<>();
        for (Position position : game.boardView().getSurroundingPositions(kingPosition)) {
            if (game.boardView().getOccupant(position).getOwner().equals(currentTeam)) {
                candidates.add(position);
            }
        }



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
            return bestPositions.getFirst();
        }

        List<Integer> tieWeights = createTieWeights(bestPositions.size(), PLACEMENT_TIE_WEIGHT);
        int selectedIndex = weightedRandomSelector.selectWeightedRandom(tieWeights);
        return bestPositions.get(selectedIndex);
    }
    /**
     * Choose a Handcard to place.
     * @return Index of the chosen Handcard, starting with 1 for the first card in hand
     */
    public int choosePlacementHandIndex() {
        TeamID currentTeam = game.getCurrentTeamID();
        List<Integer> atkWeights = new ArrayList<>();

        for (int handIndex = 0; handIndex < game.getHandSize(currentTeam); handIndex++) {
            atkWeights.add(game.getHandCardAt(currentTeam, handIndex).getAtk());
        }

        int selectedIndex = weightedRandomSelector.selectWeightedRandom(atkWeights);
        return selectedIndex + HAND_INDEX_OFFSET;
    }
    /**
     * Choose next Unit action.
     * @return the Unit action.
     */
    public UnitActionDecision chooseNextUnitAction() {
        TeamID currentTeam = game.getCurrentTeamID();
        List<UnitCandidate> candidates = getMoveableUnitCandidates(currentTeam);

        if (candidates.isEmpty()) {
            return null;
        }

        UnitCandidate bestCandidate = candidates.getFirst();
        for (UnitCandidate candidate : candidates) {
            if (candidate.getTotalScore() > bestCandidate.getTotalScore()) {
                bestCandidate = candidate;
            }
        }

        // Block, if no pos AktionScore
        if (!hasPositiveMovementOption(bestCandidate.getActionScores())) {
            return new UnitActionDecision(bestCandidate.getSource(), UnitActionType.BLOCK, bestCandidate.getSource());
        }

        List<Integer> weights = new ArrayList<>();
        for (ActionScore actionScore : bestCandidate.getActionScores()) {
            weights.add(actionScore.getScore());
        }

        int selectedIndex = weightedRandomSelector.selectWeightedRandom(weights);
        ActionScore selectedAction = bestCandidate.getActionScores().get(selectedIndex);
        return new UnitActionDecision(bestCandidate.getSource(), selectedAction.getActionType(), selectedAction.getTarget());
    }
    /**
     * Choose wich card should drop.
     * @return the index of the card to drop, starting with 1 for the first card in hand
     */
    public int chooseDiscardIndex() {
        List<Integer> discardWeights = new ArrayList<>();
        TeamID currentTeam = game.getCurrentTeamID();

        for (int handIndex = 0; handIndex < game.getHandSize(currentTeam); handIndex++) {
            Unit handCard = game.getHandCardAt(currentTeam, handIndex);
            discardWeights.add(handCard.getAtk() + handCard.getDef());
        }

        int selectedIndex = weightedRandomSelector.selectInverseWeightedRandom(discardWeights);
        return selectedIndex +  HAND_INDEX_OFFSET;
    }

    private boolean isValidKingTarget(Position candidate, TeamID currentTeam) {
        BoardEntity occupant = game.boardView().getOccupant(candidate);
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
        int enemies = countOrthogonalEntitiesFromTeam(candidate, game.getEnemyTeamID());
        int fellows = countOrthogonalEntitiesFromTeam(candidate, currentTeam);
        return -steps + PLACEMENT_ENEMY_WEIGHT_FACTOR * enemies - fellows;
    }
    private int manhattanDistance(Position a, Position b) {
        return Math.abs(a.getRow() - b.getRow()) + Math.abs(a.getColumn() - b.getColumn());
    }
    private int countAdjacentEntitiesFromTeam(Position center, TeamID team, boolean includeKing) {
        int count = 0;

        for (Position neighbor : game.boardView().getSurroundingPositions(center)) {
            BoardEntity occupant = game.boardView().getOccupant(neighbor);
            if (occupant != null && occupant.getOwner().equals(team)) {
                if (includeKing || !occupant.isFarmerKing()) {
                    count++;
                }
            }
        }
        return count;
    }
    private int countOrthogonalEntitiesFromTeam(Position center, TeamID team) {
        int count = 0;

        for (Position neighbor : game.boardView().getOrthogonalNeighbors(center)) {
            BoardEntity occupant = game.boardView().getOccupant(neighbor);
            if (occupant != null && occupant.getOwner().equals(team)) {
                count++;
            }
        }
        return count;
    }
    private boolean hasOwnUnitOnField(Position candidate, TeamID team) {
        BoardEntity occupant = game.boardView().getOccupant(candidate);
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

        for (int row = 0; row < game.boardView().getBoardSize(); row++) {
            for (int column = 0; column < game.boardView().getBoardSize(); column++) {
                Position source = game.boardView().getPositionAt(row, column);
                BoardEntity occupant = game.boardView().getOccupant(source);

                if (occupant != null && occupant.getOwner().equals(currentTeam) &&  !occupant.isFarmerKing()
                        && !turnState.hasMoved(occupant)) {
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

        for (Position target : game.boardView().getOrthogonalNeighbors(source)) {
            addDirectionalAction(actionScores, target, unit, team);
        }

        int blockScore = scoreBlockAction(source, unit, team);
        int stayScore = scoreStayAction(source, unit, team);

        actionScores.add(new ActionScore(UnitActionType.BLOCK, source, blockScore));
        actionScores.add(new ActionScore(UnitActionType.STAY, source, stayScore));

        return actionScores;
    }
    private void addDirectionalAction(List<ActionScore> actionScores, Position target, Unit unit, TeamID currentTeam) {
        if (!game.boardView().isValidPosition(target)) {
            return;
        }
        BoardEntity targetEntity = game.boardView().getOccupant(target);
        if (targetEntity != null && targetEntity.isFarmerKing() && targetEntity.getOwner().equals(currentTeam)) {
            return;
        }
        int score = scoreDirectionalAction(target, unit, currentTeam, targetEntity);
        actionScores.add(new ActionScore(UnitActionType.MOVE, target, score));
    }
    //TargetEntity kann leer sein, eigenes Team, oder Gegner, ABER: nicht eigener König!
    private int scoreDirectionalAction(Position target, Unit unit, TeamID currentTeam, BoardEntity targetEntity) {
        if (targetEntity == null) {
            Position enemyKingPosition = game.getKingPosition(game.getEnemyTeamID());
            int steps = manhattanDistance(target, enemyKingPosition);
            int enemies = countOrthogonalEntitiesFromTeam(target, game.getEnemyTeamID());
            return ADVANCE_STEPS_FACTOR * steps - enemies;
        }
        if (targetEntity.getOwner().equals(currentTeam)) {
            Unit targetUnit = (Unit) targetEntity;
            MergeResult mergeResult = unitMerger.tryMerge(unit, targetUnit);
            if (mergeResult.isSuccessful()) {
                return mergeResult.getUnit().getAtk() + mergeResult.getUnit().getDef() - unit.getAtk() - unit.getDef();
            } else {
                return -targetUnit.getAtk() - targetUnit.getDef();
            }
        }  else {
            if (targetEntity.isFarmerKing()) {
                return unit.getAtk();
            }
            Unit targetUnit = (Unit) targetEntity;

            if (!targetUnit.isRevealed()) {
                return unit.getAtk() - HIDDEN_ENEMY_PENALTY;
            }

            if (targetUnit.isBlocked()) {
                return unit.getAtk() - targetUnit.getDef();
            }
            return DUEL_FACTOR * (unit.getAtk() - targetUnit.getDef());
        }
    }
    private int scoreBlockAction(Position source, Unit unit, TeamID currentTeam) {
        int strongestEnemyAtk = getStrongestEnemyAtkInStraightLine(source, currentTeam.getNext());
        return Math.max(BLOCK_MIN_SCORE, (unit.getDef() - strongestEnemyAtk) / DIVISOR);
    }
    private int scoreStayAction(Position source, Unit unit, TeamID currentTeam) {
        int strongestEnemyAtk = getStrongestEnemyAtkInStraightLine(source, currentTeam.getNext());
        return Math.max(STAY_MIN_SCORE,  (unit.getAtk() - strongestEnemyAtk) / DIVISOR);
    }
    private int getStrongestEnemyAtkInStraightLine(Position source, TeamID enemyTeam) {
        int strongestAtk = 0;

        for (int[] delta : ORTHOGONAL_DELTAS) {
            int aktOnRay = getStrongestEnemyAtkAlongRay(source, delta[0], delta[1], enemyTeam);
            strongestAtk = Math.max(strongestAtk, aktOnRay);
        }
        return strongestAtk;
    }
    private int getStrongestEnemyAtkAlongRay(Position source, int rowDelta, int columnDelta, TeamID enemyTeam) {
        int strongestAtk = 0;
        Position current = new Position(source.getRow() + rowDelta, (char)  (source.getColumn() + columnDelta));
        while (game.boardView().isValidPosition(current)) {
            BoardEntity occupant = game.boardView().getOccupant(current);
            if (occupant != null && occupant.getOwner().equals(enemyTeam) && !occupant.isFarmerKing()) {
                Unit enemyUnit = (Unit) occupant;
                strongestAtk = Math.max(strongestAtk, enemyUnit.getAtk());
            }
            current = new Position(current.getRow() + rowDelta, (char) (current.getColumn() + columnDelta));
        }
        return strongestAtk;
    }
    private boolean hasPositiveMovementOption(List<ActionScore> actionScores) {
        for (ActionScore actionScore : actionScores) {
            if (actionScore.getActionType() != UnitActionType.BLOCK
                    && actionScore.getScore() > 0) {
                return true;
            }
        }
        return false;
    }
}