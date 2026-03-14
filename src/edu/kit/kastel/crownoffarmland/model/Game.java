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

    /**
     * Returns the ID of the team whose turn it currently is.
     * @return the ID of the current team
     */
    public TeamID getCurrentTeamID() {
        return currentTeamID;
    }

    /**
     * Returns the ID of the team that is currently not active (the enemy team).
     * @return the ID of the enemy team
     */
    public TeamID getEnemyTeamID() {
        return currentTeamID.getNext();
    }

    /**
     * Advances the game to the next turn by switching the current team to the next team. This method updates the currentTeamID to the
     * next team in the sequence, allowing the game to alternate turns between the two teams.
     */
    public void nextTurn() {
        currentTeamID = currentTeamID.getNext();
    }

    /**
     * Returns the ID of the team that has won the game, or null if there is no winner yet.
     * @return the ID of the winning team, or null if there is no winner yet
     */
    public TeamID getWinnerID() {
        return winner;
    }
    /**
     * Sets the winner of the game to the specified team ID. This method is used to declare a team as the winner when certain conditions
     * are met during the game, such as when the opposing team's life points reach zero. Once a winner is set, the game can be considered
     * over, and no further actions should be taken.
     * @param winner the ID of the team that has won the game
     */
    public void setWinner(TeamID winner) {
        this.winner = winner;
    }


    /**
     * Wrapper for board.
     * @return the view of a board
     */
    public GameBoardView boardView() {
        return board;
    }

    /**
     * Wrapper for team.
     * @param teamID the selected team
     * @return the view of the selectet team
     */
    public GameTeamView teamView(TeamID teamID) {
        return teams.get(teamID);
    }

    /**
     * Removes and returns the BoardEntity that occupies the specified position on the game board.
     * @param position the Position object representing the location on the game board from which to remove and return the occupant
     * @return the BoardEntity that was removed from the specified position on the game board, or null if the position was unoccupied
     */
    public BoardEntity removeOccupant(Position position) {
        return board.removeOccupant(position);
    }

    /**
     * Sets the occupant of the specified position on the game board to the given BoardEntity. This method allows you to place a unit or
     * other entity on the board at a specific location.
     * @param position the Position object representing the location on the game board where the occupant should be set
     * @param entity the BoardEntity that should be placed at the specified position on the game board
     */
    public void setOccupant(Position position, BoardEntity entity) {
        board.setOccupant(position, entity);
    }
    /**
     * Deals damage to the team corresponding to the given TeamID by reducing its life points by the specified amount. If the team's life
     * points reach zero as a result of the damage, the method also checks if there is a winner and sets the winner to the opposing team
     * if there is no winner yet.
     * @param teamID the ID of the team to which damage should be dealt
     * @param amount the amount of damage to be dealt to the team, which will reduce its life points
     */
    public void dealDamage(TeamID teamID, int amount) {
        teams.get(teamID).takeDamage(amount);

        if (teams.get(teamID).getLifePoints() == 0 && winner == null) {
            winner = teamID.getNext();
        }
    }

    /**
     * Returns the FarmerKing unit that represents the king of the team corresponding to the given TeamID. The FarmerKing is a special
     * unit in the game.
     * @param teamID the ID of the team for which to retrieve the king unit
     * @return the FarmerKing unit that represents the king of the team corresponding to the given TeamID
     */
    public FarmerKing getKing(TeamID teamID) {
        return teams.get(teamID).getKing();
    }

    /**
     * Returns the Position object representing the location of the king unit for the team corresponding to the given TeamID on the game
     * board.
     * @param teamID the ID of the team for which to retrieve the position of the king unit
     * @return the Position object representing the location of the king unit for the team corresponding to the given TeamID on the game
     *      board, or null if the king is not found
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
     * Returns the Unit card at the specified index in the hand of the team corresponding to the given TeamID.
     * @param teamID the ID of the team for which to retrieve the hand card
     * @param index the index of the card in the hand of the team corresponding to the given TeamID for which to retrieve the Unit
     * @return the Unit card at the specified index in the hand of the team corresponding to the given TeamID, or null if the index is
     *      out of bounds
     */
    public Unit getHandCardAt(TeamID teamID, int index) {
        return teams.get(teamID).getHandCardAt(index);
    }

    /**
     * Removes and returns the Unit card at the specified index in the hand of the team corresponding to the given TeamID.
     * @param teamID the ID of the team for which to remove the hand card
     * @param index the index of the card in the hand of the team corresponding to the given TeamID for which to remove and return the Unit
     * @return the Unit card that was removed from the hand of the team corresponding to the given TeamID at the specified index, or null
     *      if the index is out of bounds
     */
    public Unit removeHandCardAt(TeamID teamID, int index) {
        return teams.get(teamID).removeHandCardAt(index);
    }
    /**
     * Draws the top card from the draw pile of the team corresponding to the given TeamID and adds it to their hand. This method removes
     * the top card from the draw pile and returns it, allowing the team to use the drawn card during their turn. If the draw pile is
     * empty, this method may return null or a specific value indicating that there are no more cards to draw.
     * @param teamID  the ID of the team for which to draw a card from the draw pile and add it to their hand
     * @return the Unit card that was drawn from the top of the draw pile and added to the hand of the team corresponding to the given
     *      TeamID, or null if the draw pile is empty and there are no more cards to draw
     */
    public Unit drawToHand(TeamID teamID) {
        return teams.get(teamID).drawToHand();
    }

    /**
     * Shuffles the draw pile of the team corresponding to the given TeamID using the random generator.
     * @param teamID the ID of the team for which to shuffle the draw pile
     */
    public void shuffleDrawPile(TeamID teamID) {
        teams.get(teamID).shuffleDrawPile(generator);
    }

    /**
     * Counts the number of units (excluding the Farmer King) that the specified team has placed on the board. This method iterates
     * through all positions on the board and counts the units that belong to the specified team, excluding any Farmer King units.
     * @param teamID The TeamID of the team for which to count the placed units on the board
     * @return The number of units (excluding the Farmer King) that the specified team has placed on the board
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
     * Getter for the Random Generator.
     * @return the Random Generator of the Game
     */
    public RandomGenerator getRandomGenerator() {
        return this.generator;
    }
}