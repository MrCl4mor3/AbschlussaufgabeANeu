package edu.kit.kastel.crownoffarmland.gameplay.ai.decision;

import edu.kit.kastel.crownoffarmland.model.Game;
import edu.kit.kastel.crownoffarmland.model.board.Position;
import edu.kit.kastel.crownoffarmland.model.team.TeamID;
import edu.kit.kastel.crownoffarmland.model.units.BoardEntity;
import edu.kit.kastel.crownoffarmland.model.units.Unit;

/**
 * Provides reusable board analysis logic for AI decisions.
 *
 * @author ucgdi
 */
public final class BoardAnalysisService {
    private static final int ROW_DELTA_INDEX = 0;
    private static final int COLUMN_DELTA_INDEX = 1;
    private static final int INITIAL_STRONGEST_ATK = 0;

    private static final int[][] ORTHOGONAL_DELTAS = {
            {1, 0},
            {0, 1},
            {-1, 0},
            {0, -1}
    };

    private final Game game;

    /**
     * Creates a new board analysis service.
     *
     * @param game the current game
     */
    public BoardAnalysisService(Game game) {
        this.game = game;
    }

    /**
     * Calculates the Manhattan distance between two positions.
     *
     * @param a the first position
     * @param b the second position
     * @return the Manhattan distance
     */
    public int manhattanDistance(Position a, Position b) {
        return Math.abs(a.getRow() - b.getRow()) + Math.abs(a.getColumn() - b.getColumn());
    }

    /**
     * Counts all adjacent entities from the given team.
     *
     * @param center the center position
     * @param team the team to count
     * @param includeKing whether the king should be included
     * @return the number of adjacent matching entities
     */
    public int countAdjacentEntitiesFromTeam(Position center, TeamID team, boolean includeKing) {
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

    /**
     * Counts all orthogonally adjacent entities from the given team.
     *
     * @param center the center position
     * @param team the team to count
     * @return the number of orthogonally adjacent matching entities
     */
    public int countOrthogonalEntitiesFromTeam(Position center, TeamID team) {
        int count = 0;

        for (Position neighbor : game.boardView().getOrthogonalNeighbors(center)) {
            BoardEntity occupant = game.boardView().getOccupant(neighbor);
            if (occupant != null && occupant.getOwner().equals(team)) {
                count++;
            }
        }
        return count;
    }

    /**
     * Checks whether the given field currently contains one of the team's own non-king units.
     *
     * @param candidate the position to check
     * @param team the team
     * @return {@code true} if the field contains an own unit, otherwise {@code false}
     */
    public boolean hasOwnUnitOnField(Position candidate, TeamID team) {
        BoardEntity occupant = game.boardView().getOccupant(candidate);
        return occupant != null && occupant.getOwner().equals(team) && !occupant.isFarmerKing();
    }

    /**
     * Determines the strongest blocked enemy attack value reachable in a straight line.
     *
     * @param source the start position
     * @param enemyTeam the enemy team
     * @return the strongest enemy attack value found
     */
    public int getStrongestEnemyAtkInStraightLine(Position source, TeamID enemyTeam) {
        int strongestAtk = INITIAL_STRONGEST_ATK;

        for (int[] delta : ORTHOGONAL_DELTAS) {
            int atkOnRay = getStrongestEnemyAtkAlongRay(
                    source,
                    delta[ROW_DELTA_INDEX],
                    delta[COLUMN_DELTA_INDEX],
                    enemyTeam
            );
            strongestAtk = Math.max(strongestAtk, atkOnRay);
        }
        return strongestAtk;
    }

    private int getStrongestEnemyAtkAlongRay(Position source, int rowDelta, int columnDelta, TeamID enemyTeam) {
        int strongestAtk = INITIAL_STRONGEST_ATK;
        Position current = new Position(source.getRow() + rowDelta, (char) (source.getColumn() + columnDelta));

        while (game.boardView().isValidPosition(current)) {
            BoardEntity occupant = game.boardView().getOccupant(current);
            if (occupant != null
                    && occupant.getOwner().equals(enemyTeam)
                    && !occupant.isFarmerKing()
                    && occupant.isBlocked()) {
                Unit enemyUnit = (Unit) occupant;
                strongestAtk = Math.max(strongestAtk, enemyUnit.getAtk());
            }
            current = new Position(current.getRow() + rowDelta, (char) (current.getColumn() + columnDelta));
        }

        return strongestAtk;
    }
}