package edu.kit.kastel.crownoffarmland.model.units;

import edu.kit.kastel.crownoffarmland.model.team.TeamID;

/**
 * The Farmer King unit, which is a special unit in the game. It has unique properties and abilities compared to other units.
 *
 * @author ucgdi
 */
public class FarmerKing extends BoardEntity {
    private static final String qualificator = "Farmer";
    private static final String role = "King";


    public FarmerKing(TeamID teamId) {
        super(new UnitName(qualificator, role), teamId, true);
    }

    /**
     * Indicates that this unit is the Farmer King.
     *
     * @return true, as this unit is the Farmer King.
     */
    @Override
    public boolean isFarmerKing() {
        return true;
    }
}
