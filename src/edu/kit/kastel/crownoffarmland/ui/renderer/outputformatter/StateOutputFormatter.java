package edu.kit.kastel.crownoffarmland.ui.renderer.outputformatter;

import edu.kit.kastel.crownoffarmland.gameplay.snapshots.TeamStateSnapshot;

import java.util.List;

public class StateOutputFormatter implements OutputFormatter<List<TeamStateSnapshot>> {

    private static final String LIFE_POINTS_FORMAT = "%d/8000 LP";
    private static final String DECK_COUNT_FORMAT = "DC: %d/40";
    private static final String BOARD_COUNT_FORMAT = "BC: %d/5";
    private static final int TOTAL_LINE_LENGTH = 31;
    private static final String INDENT = "  ";
    private static final String PADDING = " ";


    @Override
    public String format(List<TeamStateSnapshot> teams) {
        StringBuilder output = new StringBuilder();

        TeamStateSnapshot team1 = teams.get(0);
        TeamStateSnapshot team2 = teams.get(1);


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
