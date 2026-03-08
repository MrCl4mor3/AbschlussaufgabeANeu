package edu.kit.kastel.crownoffarmland.model;

import edu.kit.kastel.crownoffarmland.exceptions.InvalidPositionException;
import edu.kit.kastel.crownoffarmland.model.board.Board;
import edu.kit.kastel.crownoffarmland.model.board.Position;
import edu.kit.kastel.crownoffarmland.model.team.Team;
import edu.kit.kastel.crownoffarmland.model.team.TeamID;
import edu.kit.kastel.crownoffarmland.model.units.BoardEntity;
import edu.kit.kastel.crownoffarmland.model.units.FarmerKing;
import edu.kit.kastel.crownoffarmland.model.units.Unit;

import java.util.EnumMap;
import java.util.Map;

/**
 * Represents the complete game state.
 *
 * @author ucgdi
 */
public class Game {
    private final RandomGenerator generator;
    private final Board board;
    private final Map<TeamID, Team> teams;
    private TeamID currentTeamID;
    private TeamID winner;

    /**
     * Creates a new game with two teams and a random generator.
     *
     * @param team1 the first team
     * @param team2 the second team
     * @param generator the random generator
     */
    public Game(Team team1, Team team2, RandomGenerator generator) {
        this.board = new Board();
        this.teams = new EnumMap<>(TeamID.class);
        this.teams.put(TeamID.TEAM_1, team1);
        this.teams.put(TeamID.TEAM_2, team2);
        this.currentTeamID = TeamID.TEAM_1;
        this.generator = generator;
        this.winner = null;
    }

    public TeamID getCurrentTeamID() {
        return currentTeamID;
    }

    public TeamID getEnemyTeamID() {
        return currentTeamID.getNext();
    }

    public void nextTurn() {
        currentTeamID = currentTeamID.getNext();
    }

    public boolean isOver() {
        return winner != null;
    }

    public TeamID getWinner() {
        return winner;
    }

    public void setWinner(TeamID winner) {
        this.winner = winner;
    }

    public int getBoardSize() {
        return board.getBoardSize();
    }

    public Position getPositionAt(int rowIndex, int columnIndex) {
        return board.getPositionAt(rowIndex, columnIndex);
    }

    public int getRowIndex(Position position) {
        return board.rowIndex(position);
    }

    public int getColumnIndex(Position position) {
        return board.columnIndex(position);
    }

    public BoardEntity getOccupant(Position position) {
        return board.getOccupant(position);
    }

    public boolean isFieldEmpty(Position position) {
        return board.isFieldEmpty(position);
    }

    public void setOccupant(Position position, BoardEntity entity) {
        board.setOccupant(position, entity);
    }

    public String getTeamName(TeamID teamID) {
        return teams.get(teamID).getName();
    }

    public int getLifePoints(TeamID teamID) {
        return teams.get(teamID).getLifePoints();
    }

    public void dealDamage(TeamID teamID, int amount) {
        teams.get(teamID).takeDamage(amount);

        if (teams.get(teamID).getLifePoints() == 0 & winner == null) {
            winner = teamID.getNext();
        }
    }

    public FarmerKing getKing(TeamID teamID) {
        return teams.get(teamID).getKing();
    }

    public int getHandSize(TeamID teamID) {
        return teams.get(teamID).getHandSize();
    }

    public boolean isHandFull(TeamID teamID) {
        return teams.get(teamID).isHandFull();
    }

    public Unit getHandCardAt(TeamID teamID, int index) {
        return teams.get(teamID).getHandCardAt(index);
    }

    public Unit removeHandCardAt(TeamID teamID, int index) {
        return teams.get(teamID).removeHandCardAt(index);
    }

    public int getDrawPileSize(TeamID teamID) {
        return teams.get(teamID).getDrawPileSize();
    }

    public boolean isDrawPileEmpty(TeamID teamID) {
        return teams.get(teamID).isDrawPileEmpty();
    }

    public Unit drawToHand(TeamID teamID) {
        return teams.get(teamID).drawToHand();
    }

    public void shuffleDrawPile(TeamID teamID) {
        teams.get(teamID).shuffleDrawPile(generator);
    }

    public Position parsePosition(String rawPosition) throws InvalidPositionException {
        return board.parsePosition(rawPosition);
    }
}