package edu.kit.kastel.crownoffarmland.model.team;

import edu.kit.kastel.crownoffarmland.model.units.FarmerKing;

public class Team {
    private static final int START_LP = 8000;

    private final String name;
    private final TeamID teamID;

    private int lifePoints;
    private final Hand hand;
    private final DrawPile drawPile;
    private final FarmerKing king;


    public Team(String name, TeamID teamId, Hand hand, DrawPile drawPile) {
        this.name = name;
        this.teamID = teamId;
        this.hand = hand;
        this.drawPile = drawPile;
        this.lifePoints = START_LP;
        this.king = new FarmerKing(this.teamID);
    }

    public String getName() {
        return name;
    }

    public TeamID getTeamID() {
        return teamID;
    }

    public int getLifePoints() {
        return lifePoints;
    }

    public void getDamage(int amount) {
        this.lifePoints = lifePoints - amount;
    }
}
