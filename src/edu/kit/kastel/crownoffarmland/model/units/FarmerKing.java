package edu.kit.kastel.crownoffarmland.model.units;

import edu.kit.kastel.crownoffarmland.model.team.TeamID;

/**
 * Represents the farmer king of a team.
 *
 * @author ucgdi
 */
public class FarmerKing extends BoardEntity {
    private static final String QUALIFICATOR = "Farmer";
    private static final String ROLE = "King";

    /**
     * Constructs a farmer king for the given team.
     *
     * @param teamId the owning team
     */
    public FarmerKing(TeamID teamId) {
        super(new UnitName(QUALIFICATOR, ROLE), teamId, true);
    }

    @Override
    public boolean isFarmerKing() {
        return true;
    }
}