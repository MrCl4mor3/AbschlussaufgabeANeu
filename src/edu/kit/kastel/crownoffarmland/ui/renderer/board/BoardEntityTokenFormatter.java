package edu.kit.kastel.crownoffarmland.ui.renderer.board;

import edu.kit.kastel.crownoffarmland.gameplay.snapshots.boardsnapshot.BoardCellSnapshot;

/**
 * Formats board cells as display tokens.
 *
 * @author ucgdi
 */
public class BoardEntityTokenFormatter {
    private static final char TEAM_ONE_UNIT_TOKEN = 'x';
    private static final char TEAM_ONE_KING_TOKEN = 'X';
    private static final char TEAM_TWO_UNIT_TOKEN = 'y';
    private static final char TEAM_TWO_KING_TOKEN = 'Y';
    private static final char EMPTY_TOKEN = ' ';
    private static final String EMPTY_FIELD = "   ";
    private static final char BLOCK_SUFFIX = 'b';
    private static final char MOVEABLE_PREFIX = '*';

    /**
     * Formats the given board cell.
     *
     * @param cell the board cell snapshot
     * @return the formatted token
     */
    public String format(BoardCellSnapshot cell) {
        if (!cell.hasEntity()) {
            return EMPTY_FIELD;
        }

        char[] token = new char[]{EMPTY_TOKEN, resolveBaseSymbol(cell), EMPTY_TOKEN};

        if (cell.isMoveable()) {
            token[0] = MOVEABLE_PREFIX;
        }

        if (cell.isBlocked()) {
            token[2] = BLOCK_SUFFIX;
        }

        return new String(token);
    }

    private char resolveBaseSymbol(BoardCellSnapshot cell) {
        if (cell.isFarmerKing()) {
            return cell.isTeamOne() ? TEAM_ONE_KING_TOKEN : TEAM_TWO_KING_TOKEN;
        } else {
            return cell.isTeamOne() ? TEAM_ONE_UNIT_TOKEN : TEAM_TWO_UNIT_TOKEN;
        }
    }
}