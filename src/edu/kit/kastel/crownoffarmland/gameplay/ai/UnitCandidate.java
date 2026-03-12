package edu.kit.kastel.crownoffarmland.gameplay.ai;

import edu.kit.kastel.crownoffarmland.model.board.Position;

import java.util.List;

/**
 * Is a representation of a unit candidate, which consists of a source position, a list of action scores, and a total score.
 *
 * @author ucgdi
 */
public final class UnitCandidate {
    private final Position source;
    private final List<ActionScore> actionScores;
    private final int totalScore;

    /**
     * Creates.
     * @param source the actual position of the unit
     * @param actionScores a List of ActionScores for all possible moves of the unit
     * @param totalScore the total score of all possible moves of the unit
     */
    UnitCandidate(Position source, List<ActionScore> actionScores, int totalScore) {
        this.source = source;
        this.actionScores = actionScores;
        this.totalScore = totalScore;
    }


    /**
     * Getter for the source Position.
     * @return the source position
     */
    Position getSource() {
        return source;
    }

    /**
     * Getter for the ActionScores.
     * @return a List of ActionScores for all possible moves of the unit
     */
    List<ActionScore> getActionScores() {
        return actionScores;
    }

    /**
     * Getter for the total score of all possible moves of the unit.
     * @return the sum of all ActionScores for all possible moves of the unit
     */
    int getTotalScore() {
        return totalScore;
    }
}