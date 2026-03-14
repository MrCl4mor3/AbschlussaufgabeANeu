package edu.kit.kastel.crownoffarmland.model;

import edu.kit.kastel.crownoffarmland.model.board.Board;
import edu.kit.kastel.crownoffarmland.model.board.GameBoardView;
import edu.kit.kastel.crownoffarmland.model.board.Position;
import edu.kit.kastel.crownoffarmland.model.team.GameTeamView;
import edu.kit.kastel.crownoffarmland.model.team.Team;
import edu.kit.kastel.crownoffarmland.model.team.TeamID;
import edu.kit.kastel.crownoffarmland.model.units.BoardEntity;
import edu.kit.kastel.crownoffarmland.model.units.FarmerKing;
import edu.kit.kastel.crownoffarmland.model.units.Unit;

import java.util.EnumMap;
import java.util.Map;

/**
 * Represents the complete game state.
 * It provides access to the board, the teams, and turn-related information.
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
     * Constructs a game with two teams and a random generator.
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

    /**
     * Returns the id of the current team.
     *
     * @return the current team id
     */
    public TeamID getCurrentTeamID() {
        return currentTeamID;
    }

    /**
     * Returns the id of the enemy team.
     *
     * @return the enemy team id
     */
    public TeamID getEnemyTeamID() {
        return currentTeamID.getNext();
    }

    /**
     * Advances the game to the next turn.
     */
    public void nextTurn() {
        currentTeamID = currentTeamID.getNext();
    }

    /**
     * Returns the winner of the game.
     *
     * @return the winner id, or {@code null} if no winner is set
     */
    public TeamID getWinnerID() {
        return winner;
    }

    /**
     * Sets the winner of the game.
     *
     * @param winner the winning team id
     */
    public void setWinner(TeamID winner) {
        this.winner = winner;
    }

    /**
     * Returns a read-only view of the board.
     *
     * @return the board view
     */
    public GameBoardView boardView() {
        return board;
    }

    /**
     * Returns a read-only view of the given team.
     *
     * @param teamID the selected team
     * @return the team view
     */
    public GameTeamView teamView(TeamID teamID) {
        return teams.get(teamID);
    }

    /**
     * Removes and returns the occupant at the given position.
     *
     * @param position the position to clear
     * @return the removed occupant, or {@code null} if the field was empty
     */
    public BoardEntity removeOccupant(Position position) {
        return board.removeOccupant(position);
    }

    /**
     * Sets the occupant at the given position.
     *
     * @param position the target position
     * @param entity the new occupant
     */
    public void setOccupant(Position position, BoardEntity entity) {
        board.setOccupant(position, entity);
    }

    /**
     * Deals damage to the given team.
     * If its life points reach zero and no winner is set yet, the opposing team wins.
     *
     * @param teamID the damaged team
     * @param amount the damage amount
     */
    public void dealDamage(TeamID teamID, int amount) {
        teams.get(teamID).takeDamage(amount);

        if (teams.get(teamID).getLifePoints() == 0 && winner == null) {
            winner = teamID.getNext();
        }
    }

    /**
     * Returns the farmer king of the given team.
     *
     * @param teamID the selected team
     * @return the farmer king of that team
     */
    public FarmerKing getKing(TeamID teamID) {
        return teams.get(teamID).getKing();
    }

    /**
     * Returns the board position of the farmer king of the given team.
     *
     * @param teamID the selected team
     * @return the king position, or {@code null} if it is not on the board
     */
    public Position getKingPosition(TeamID teamID) {
        BoardEntity king = getKing(teamID);

        for (int rowIndex = 0; rowIndex < boardView().getBoardSize(); rowIndex++) {
            for (int columnIndex = 0; columnIndex < boardView().getBoardSize(); columnIndex++) {
                Position position = boardView().getPositionAt(rowIndex, columnIndex);
                if (boardView().getOccupant(position) == king) {
                    return position;
                }
            }
        }
        return null;
    }

    /**
     * Returns the hand card of the given team at the given index.
     *
     * @param teamID the selected team
     * @param index the card index
     * @return the card at the given index
     */
    public Unit getHandCardAt(TeamID teamID, int index) {
        return teams.get(teamID).getHandCardAt(index);
    }

    /**
     * Removes and returns the hand card of the given team at the given index.
     *
     * @param teamID the selected team
     * @param index the card index
     * @return the removed card
     */
    public Unit removeHandCardAt(TeamID teamID, int index) {
        return teams.get(teamID).removeHandCardAt(index);
    }

    /**
     * Draws the top card of the given team's draw pile to its hand.
     *
     * @param teamID the selected team
     * @return the drawn card, or {@code null} if drawing is not possible
     */
    public Unit drawToHand(TeamID teamID) {
        return teams.get(teamID).drawToHand();
    }

    /**
     * Shuffles the draw pile of the given team.
     *
     * @param teamID the selected team
     */
    public void shuffleDrawPile(TeamID teamID) {
        teams.get(teamID).shuffleDrawPile(generator);
    }

    /**
     * Returns the number of non-king units of the given team currently placed on the board.
     *
     * @param teamID the selected team
     * @return the number of placed units
     */
    public int getUnitsPlaced(TeamID teamID) {
        int count = 0;
        for (int rowIndex = 0; rowIndex < boardView().getBoardSize(); rowIndex++) {
            for (int columnIndex = 0; columnIndex < boardView().getBoardSize(); columnIndex++) {
                Position position = boardView().getPositionAt(rowIndex, columnIndex);
                BoardEntity entity = boardView().getOccupant(position);
                if (entity != null && entity.getOwner() == teamID && !entity.isFarmerKing()) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * Returns the random generator used by this game.
     *
     * @return the random generator
     */
    public RandomGenerator getRandomGenerator() {
        return generator;
    }
}