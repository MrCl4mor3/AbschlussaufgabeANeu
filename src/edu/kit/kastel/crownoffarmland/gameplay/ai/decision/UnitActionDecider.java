package edu.kit.kastel.crownoffarmland.gameplay.ai.decision;

import edu.kit.kastel.crownoffarmland.gameplay.TurnState;
import edu.kit.kastel.crownoffarmland.gameplay.ai.decision.model.ActionScore;
import edu.kit.kastel.crownoffarmland.gameplay.ai.decision.model.UnitActionDecision;
import edu.kit.kastel.crownoffarmland.gameplay.ai.decision.model.UnitActionType;
import edu.kit.kastel.crownoffarmland.gameplay.ai.decision.model.UnitCandidate;
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
 * Decides the next action for movable units.
 *
 * @author ucgdi
 */
public final class UnitActionDecider extends AbstractAIDecider {
    private static final int BLOCK_MIN_SCORE = 1;
    private static final int STAY_MIN_SCORE = 0;
    private static final int DIVISOR = 100;
    private static final int HIDDEN_ENEMY_PENALTY = 500;
    private static final int ADVANCE_STEPS_FACTOR = 10;
    private static final int DUEL_FACTOR = 2;


    private final TurnState turnState;
    private final UnitMerger unitMerger;

    /**
     * Creates a new unit action decider.
     *
     * @param game the current game
     * @param turnState the current turn state
     * @param unitMerger the unit merger
     * @param boardAnalysisService the board analysis service
     * @param weightedRandomSelector the weighted random selector
     */
    public UnitActionDecider(Game game, TurnState turnState, UnitMerger unitMerger, BoardAnalysisService boardAnalysisService,
            WeightedRandomSelector weightedRandomSelector) {
        super(game, boardAnalysisService, weightedRandomSelector);
        this.unitMerger = unitMerger;
        this.turnState = turnState;
    }

    /**
     * Chooses the next action for a unit.
     *
     * @return the selected unit action, or {@code null} if no action is possible
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

        if (!hasPositiveMovementOption(bestCandidate.getActionScores())) {
            return new UnitActionDecision(bestCandidate.getSource(), UnitActionType.BLOCK, bestCandidate.getSource());
        }

        List<Integer> weights = new ArrayList<>();
        for (ActionScore actionScore : bestCandidate.getActionScores()) {
            weights.add(actionScore.getScore());
        }

        int selectedIndex = weightedRandomSelector.selectWeightedRandom(weights);
        ActionScore selectedAction = bestCandidate.getActionScores().get(selectedIndex);
        return new UnitActionDecision(
                bestCandidate.getSource(),
                selectedAction.getActionType(),
                selectedAction.getTarget()
        );
    }

    private List<UnitCandidate> getMoveableUnitCandidates(TeamID currentTeam) {
        List<UnitCandidate> candidates = new ArrayList<>();

        for (int row = 0; row < game.boardView().getBoardSize(); row++) {
            for (int column = 0; column < game.boardView().getBoardSize(); column++) {
                Position source = game.boardView().getPositionAt(row, column);
                BoardEntity occupant = game.boardView().getOccupant(source);

                if (occupant != null
                        && occupant.getOwner().equals(currentTeam)
                        && !occupant.isFarmerKing()
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

    private List<ActionScore> evaluatePossibleActions(Position source, Unit unit, TeamID currentTeam) {
        List<ActionScore> actionScores = new ArrayList<>();

        for (Position target : game.boardView().getOrthogonalNeighbors(source)) {
            addDirectionalAction(actionScores, target, unit, currentTeam);
        }

        int blockScore = scoreBlockAction(source, unit, currentTeam);
        int stayScore = scoreStayAction(source, unit, currentTeam);

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

    private int scoreDirectionalAction(Position target, Unit unit, TeamID currentTeam, BoardEntity targetEntity) {
        if (targetEntity == null) {
            Position enemyKingPosition = game.getKingPosition(game.getEnemyTeamID());
            int steps = boardAnalysisService.manhattanDistance(target, enemyKingPosition);
            int enemies = boardAnalysisService.countOrthogonalEntitiesFromTeam(target, game.getEnemyTeamID());
            return ADVANCE_STEPS_FACTOR * steps - enemies;
        }

        if (targetEntity.getOwner().equals(currentTeam)) {
            Unit targetUnit = (Unit) targetEntity;
            MergeResult mergeResult = unitMerger.tryMerge(unit, targetUnit);

            if (mergeResult.isSuccessful()) {
                return mergeResult.getUnit().getAtk() + mergeResult.getUnit().getDef()
                        - unit.getAtk() - unit.getDef();
            }

            return -targetUnit.getAtk() - targetUnit.getDef();
        }

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

    private int scoreBlockAction(Position source, Unit unit, TeamID currentTeam) {
        int strongestEnemyAtk = boardAnalysisService.getStrongestEnemyAtkInStraightLine(source, currentTeam.getNext());
        return Math.max(BLOCK_MIN_SCORE, (unit.getDef() - strongestEnemyAtk) / DIVISOR);
    }

    private int scoreStayAction(Position source, Unit unit, TeamID currentTeam) {
        int strongestEnemyAtk = boardAnalysisService.getStrongestEnemyAtkInStraightLine(source, currentTeam.getNext());
        return Math.max(STAY_MIN_SCORE, (unit.getAtk() - strongestEnemyAtk) / DIVISOR);
    }

    private boolean hasPositiveMovementOption(List<ActionScore> actionScores) {
        for (ActionScore actionScore : actionScores) {
            if (actionScore.getActionType() != UnitActionType.BLOCK && actionScore.getScore() > 0) {
                return true;
            }
        }
        return false;
    }
}