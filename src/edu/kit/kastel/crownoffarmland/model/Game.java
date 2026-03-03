package edu.kit.kastel.crownoffarmland.model;

import edu.kit.kastel.crownoffarmland.model.board.Board;
import edu.kit.kastel.crownoffarmland.model.team.Team;
import edu.kit.kastel.crownoffarmland.model.team.TeamID;

import java.util.HashMap;
import java.util.Map;

/**
 * The Game class represents the main game logic and state. It manages the game board, teams, and turn order. It also provides methods for
 * switching turns and accessing the random generator. The Game class is responsible for maintaining the overall state of the game and
 * coordinating interactions between the different components such as the board and teams. It keeps track of which team's turn it is and
 * provides access to the random generator for any random events that may occur during the game. The class also has a boolean variable to
 * indicate if there is a winner, which can be used to determine when the game has ended.
 *
 * @author ucgdi
 */
public class Game {
    private final RandomGenerator generator;
    private final Board board;
    private final Map<TeamID, Team> teams;
    private TeamID currentTeamID;


    private boolean winner;


    public Game(Team team1, Team team2, RandomGenerator generator) {
        board = new Board();
        teams = new HashMap<>();
        teams.put(TeamID.TEAM_1, team1);
        teams.put(TeamID.TEAM_2, team2);
        currentTeamID = TeamID.TEAM_1;
        this.generator = generator;
    }

    /**
     * Returns the game board.
     * @return the game board
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Returns the ID of the current team whose turn it is.
     * @return the ID of the current team
     */
    public TeamID getCurrentTeam() {
        return currentTeamID;
    }

    /**
     * Switches the turn to the next team. This method updates the currentTeamID to the next team in the turn order.
     */
    public void switchTurn() {
        currentTeamID = currentTeamID.getNext();
    }

    /**
     * Returns the random generator used in the game. This generator can be used for any random events that may occur during the game,
     * such as dice rolls or random unit spawns.
     * @return the random generator used in the game
     */
    public RandomGenerator getRandomGenerator() {
        return generator;
    }
}