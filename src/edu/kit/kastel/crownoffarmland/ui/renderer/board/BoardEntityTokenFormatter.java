package edu.kit.kastel.crownoffarmland.ui.renderer.board;

import edu.kit.kastel.crownoffarmland.gameplay.snapshots.BoardCellSnapshot;

/**
 * Formats a {@link BoardCellSnapshot} into a string representation for display on the board.
 *
 * @author ucgdi
 */
public class BoardEntityTokenFormatter {
    private static final char TEAM_CURRENT_UNIT = 'x';
    private static final char TEAM_CURRENT_KING = 'X';
    private static final char TEAM_ENEMY_UNIT = 'y';
    private static final char TEAM_ENEMY_KING = 'Y';
    private static final char EMPTY_TOKEN = ' ';
    private static final String EMPTY_FIELD = "   ";
    private static final char BLOCK_SUFFIX = 'b';
    private static final char MOVEABLE_PREFIX = '*';

    /**
     * Formats the given board cell snapshot into a string representation based on its contents and state.
     *
     * @param cell the board cell snapshot to format
     * @return a string representation of the board cell snapshot
     */
    public String format(BoardCellSnapshot cell) {
        if (!cell.hasEntity()) {
            return EMPTY_FIELD;
        }

        char[] token = new char[] {EMPTY_TOKEN, resolveBaseSymbol(cell), EMPTY_TOKEN};

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
            return cell.isOwnTeam() ? TEAM_CURRENT_KING : TEAM_ENEMY_KING;
        } else {
            return cell.isOwnTeam() ? TEAM_CURRENT_UNIT : TEAM_ENEMY_UNIT;
        }
    }
}
