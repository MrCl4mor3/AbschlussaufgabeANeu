package edu.kit.kastel.crownoffarmland.ui.renderer;


import edu.kit.kastel.crownoffarmland.model.team.TeamID;
import edu.kit.kastel.crownoffarmland.model.units.BoardEntity;


/**
 * Formats a {@link BoardEntity} into a string representation for display on the board.
 * The formatter uses specific characters to represent different types of entities and their states, such as whether they belong to the
 * current team, are moveable, or are blocked.
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
     * Formats the given {@link BoardEntity} into a string representation based on its type, team affiliation, and state (moveable or
     * blocked).
     * @param entity the BoardEntity to format
     * @param currentTeam the TeamID of the current team, used to determine the appropriate symbols for the entity
     * @param moveable a boolean indicating whether the entity is moveable, which affects the prefix of the token
     * @return a string representation of the BoardEntity, formatted according to its type, team affiliation, and state
     */
    public String format(BoardEntity entity, TeamID currentTeam, boolean moveable) {
        if (entity == null) {
            return EMPTY_FIELD;
        }

        char[] token = new char[] {EMPTY_TOKEN, resolveBaseSymbol(entity, currentTeam), EMPTY_TOKEN};

        if (moveable) {
            token[0] = MOVEABLE_PREFIX;
        }

        if (entity.isBlocked()) {
            token[2] = BLOCK_SUFFIX;
        }

        return new String(token);
    }


    private char resolveBaseSymbol(BoardEntity entity, TeamID currentTeam) {
        if (entity.isFarmerKing()) {
            return entity.getTeamID() == currentTeam ? TEAM_CURRENT_KING : TEAM_ENEMY_KING;
        } else {
            return entity.getTeamID() == currentTeam ? TEAM_CURRENT_UNIT : TEAM_ENEMY_UNIT;
        }
    }
}
