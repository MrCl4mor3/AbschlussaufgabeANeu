package edu.kit.kastel.crownoffarmland.model.units;

import edu.kit.kastel.crownoffarmland.model.team.TeamID;

/**
 * The Farmer King unit, which is a special unit in the game. It has unique properties and abilities compared to other units.
 *
 * @author ucgdi
 */
public class FarmerKing extends BoardEntity {
    private static final String QUALIFICATOR = "Farmer";
    private static final String ROLE = "King";


    /**
     * Constructs a new Farmer King unit with the specified team ID. The Farmer King is initialized with a unique unit name that combines
     * the qualifactor "Farmer" and the role "King". The Farmer King is also marked as a special unit by passing true to the superclass
     * constructor
     * @param teamId The team ID to which this Farmer King belongs.
     */
    public FarmerKing(TeamID teamId) {
        super(new UnitName(QUALIFICATOR, ROLE), teamId, true);
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
