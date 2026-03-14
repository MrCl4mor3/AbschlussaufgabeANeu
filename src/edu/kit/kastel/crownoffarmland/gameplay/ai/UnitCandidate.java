package edu.kit.kastel.crownoffarmland.gameplay.ai;

import edu.kit.kastel.crownoffarmland.model.board.Position;

import java.util.List;

/**
 * Represents a unit together with its possible actions and total score.
 *
 * @author ucgdi
 */
public final class UnitCandidate {
    private final Position source;
    private final List<ActionScore> actionScores;
    private final int totalScore;

    /**
     * Creates a new unit candidate.
     *
     * @param source the source position of the unit
     * @param actionScores the scored possible actions
     * @param totalScore the total score of the unit
     */
    UnitCandidate(Position source, List<ActionScore> actionScores, int totalScore) {
        this.source = source;
        this.actionScores = actionScores;
        this.totalScore = totalScore;
    }

    /**
     * Returns the source position.
     *
     * @return the source position
     */
    Position getSource() {
        return source;
    }

    /**
     * Returns the scored possible actions.
     *
     * @return the action scores
     */
    List<ActionScore> getActionScores() {
        return actionScores;
    }

    /**
     * Returns the total score.
     *
     * @return the total score
     */
    int getTotalScore() {
        return totalScore;
    }
}