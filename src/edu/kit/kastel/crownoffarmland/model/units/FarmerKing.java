package edu.kit.kastel.crownoffarmland.model.units;

import edu.kit.kastel.crownoffarmland.model.team.TeamID;

public class FarmerKing extends BoardEntity {
    private static final String qualificator = "Farmer";
    private static final String role = "King";


    public FarmerKing(TeamID teamId) {
        super(new UnitName(qualificator, role), teamId, true);
    }

    @Override
    public boolean isFarmerKing() {
        return true;
    }
}
