package edu.kit.kastel.crownoffarmland.model;

import edu.kit.kastel.crownoffarmland.model.board.Board;
import edu.kit.kastel.crownoffarmland.model.team.Team;
import edu.kit.kastel.crownoffarmland.model.team.TeamID;

import java.util.HashMap;
import java.util.Map;

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

    public Board getBoard() {
        return board;
    }

    public TeamID getCurrentTeam() {
        return currentTeamID;
    }

    public void switchTurn() {
        currentTeamID = currentTeamID.getNext();
    }

    public RandomGenerator getRendomGenerator() {
        return generator;
    }
}