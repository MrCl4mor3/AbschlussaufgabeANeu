package edu.kit.kastel.crownoffarmland.gameplay.ai;

import edu.kit.kastel.crownoffarmland.model.board.Position;

import java.util.List;

final class UnitCandidate {
    private final Position source;
    private final List<ActionScore> actionScores;
    private final int totalScore;

    UnitCandidate(Position source, List<ActionScore> actionScores, int totalScore) {
        this.source = source;
        this.actionScores = actionScores;
        this.totalScore = totalScore;
    }

    Position getSource() {
        return source;
    }

    List<ActionScore> getActionScores() {
        return actionScores;
    }

    int getTotalScore() {
        return totalScore;
    }
}