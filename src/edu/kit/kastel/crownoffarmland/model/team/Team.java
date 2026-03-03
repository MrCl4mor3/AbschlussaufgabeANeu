package edu.kit.kastel.crownoffarmland.model.team;

import edu.kit.kastel.crownoffarmland.model.units.FarmerKing;

/**
 * Represents a team in the game, containing information about the team's name, ID, life points, hand of cards, draw pile, and the Farmer
 * King unit. The team starts with a predefined amount of life points and can take damage, which reduces its life points accordingly.
 * The team also has a hand of cards and a draw pile, which are used for gameplay mechanics, and a Farmer King unit that represents the
 * team's leader.
 *
 * @author ucgdi
 */
public class Team {
    private static final int START_LP = 8000;

    private final String name;
    private final TeamID teamID;

    private int lifePoints;
    private final Hand hand;
    private final DrawPile drawPile;
    private final FarmerKing king;


    /**
     * Constructor for creating a Team object with the specified name, team ID, hand of cards, and draw pile. The team starts with a
     * predefined amount of life points and a Farmer King unit that represents the team's leader.
     * @param name the name of the team
     * @param teamId the unique identifier of the team
     * @param hand the hand of cards that the team has at the start of the game
     * @param drawPile the draw pile from which the team can draw cards during the game
     */
    public Team(String name, TeamID teamId, Hand hand, DrawPile drawPile) {
        this.name = name;
        this.teamID = teamId;
        this.hand = hand;
        this.drawPile = drawPile;
        this.lifePoints = START_LP;
        this.king = new FarmerKing(this.teamID);
    }

    /**
     * Returns the name of the team.
     * @return the name of the team
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the unique identifier of the team.
     * @return the team ID
     */
    public TeamID getTeamID() {
        return teamID;
    }

    /**
     * Returns the current life points of the team.
     * @return the current life points
     */
    public int getLifePoints() {
        return lifePoints;
    }

    /**
     * Reduces the team's life points by the specified amount of damage.
     * @param amount the amount of damage to inflict on the team
     */
    public void getDamage(int amount) {
        this.lifePoints = lifePoints - amount;
    }
}
