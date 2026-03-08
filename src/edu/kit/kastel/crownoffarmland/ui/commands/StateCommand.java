package edu.kit.kastel.crownoffarmland.ui.commands;

import edu.kit.kastel.crownoffarmland.exceptions.CrownOfFarmlandException;

import edu.kit.kastel.crownoffarmland.gameplay.GameHandler;
import edu.kit.kastel.crownoffarmland.model.team.TeamID;
import edu.kit.kastel.crownoffarmland.ui.snapshots.TeamStateSnapshot;


public class StateCommand extends  Command {
    private static final String COMMAND_NAME = "state";

    private static final String LIFE_POINTS_FORMAT = "%d/8000 LP";
    private static final String DECK_COUNT_FORMAT = "DC: %d/40";
    private static final String BOARD_COUNT_FORMAT = "BC: %d/5";
    private static final int TOTAL_LINE_LENGTH = 31;
    private static final String INDENT = "  ";
    private static final String PADDING = " ";


    public StateCommand(CommandHandler commandHandler, GameHandler gameHandler)  {
        super(COMMAND_NAME, commandHandler, gameHandler);
    }

    @Override
    public void execute(String[] commandArguments) throws CrownOfFarmlandException {
        ensureNoArguments(commandArguments);


        TeamStateSnapshot team1 = gameHandler.createTeamStateSnapshots(TeamID.TEAM_1);
        TeamStateSnapshot team2 = gameHandler.createTeamStateSnapshots(TeamID.TEAM_2);
        System.out.println(this.format(team1, team2));
        commandHandler.printBoard();
        if (gameHandler.getSelectedPos() != null) {
            commandHandler.printShow();
        }
    }


    private String format(TeamStateSnapshot team1, TeamStateSnapshot team2) {
        StringBuilder output = new StringBuilder();

        appendStateLine(output, team1.getTeamName(),  team2.getTeamName());
        output.append(System.lineSeparator());
        appendStateLine(output, String.format(LIFE_POINTS_FORMAT, team1.getLifePoints()), String.format(LIFE_POINTS_FORMAT,
                team2.getLifePoints()));
        output.append(System.lineSeparator());
        appendStateLine(output, String.format(DECK_COUNT_FORMAT, team1.getRemainingDeckCards()), String.format(DECK_COUNT_FORMAT,
                team2.getRemainingDeckCards()));
        output.append(System.lineSeparator());
        appendStateLine(output, String.format(BOARD_COUNT_FORMAT, team1.getPlacedUnits()), String.format(BOARD_COUNT_FORMAT,
                team2.getPlacedUnits()));

        return output.toString();
    }


    private void appendStateLine(StringBuilder output, String left, String right) {
        output.append(INDENT).append(left);
        appendPadding(output, TOTAL_LINE_LENGTH - INDENT.length() - left.length() - right.length());
        output.append(right);
    }

    private void appendPadding(StringBuilder output, int count) {
        output.append(PADDING.repeat(Math.max(0, count)));
    }







}
