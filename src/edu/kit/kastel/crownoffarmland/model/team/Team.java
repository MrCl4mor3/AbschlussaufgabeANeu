package edu.kit.kastel.crownoffarmland.model.team;

import edu.kit.kastel.crownoffarmland.model.RandomGenerator;
import edu.kit.kastel.crownoffarmland.model.units.FarmerKing;
import edu.kit.kastel.crownoffarmland.model.units.Unit;

import java.util.List;

/**
 * Represents a team in the game, containing information about the team's name, ID, life points, hand of cards, draw pile, and the Farmer
 * King unit. The team starts with a predefined amount of life points and can take damage, which reduces its life points accordingly.
 * The team also has a hand of cards and a draw pile, which are used for gameplay mechanics, and a Farmer King unit that represents the
 * team's leader.
 *
 * @author ucgdi
 */
public class Team implements GameTeamView {
    private static final int START_LP = 8000;
    private static final int MAX_UNITS_ON_BOARD = 5;


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
     * @param drawPile the draw pile from which the team can draw cards during the game
     */
    public Team(String name, TeamID teamId, List<Unit> drawPile) {
        this.name = name;
        this.teamID = teamId;
        this.hand = new Hand();
        this.drawPile = new DrawPile(drawPile);
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
    public void takeDamage(int amount) {
        this.lifePoints = Math.max(0, this.lifePoints - amount);
    }

    /**
     * Returns the Farmer King unit that represents the team's leader.
     * @return the Farmer King unit of the team
     */
    public FarmerKing getKing() {
        return king;
    }

    /**
     * Shuffles the team's draw pile using the provided random generator.
     * @param generator the random generator to use for shuffling the draw pile
     */
    public void shuffleDrawPile(RandomGenerator generator) {
        this.drawPile.shuffle(generator);
    }

    /**
     * Returns the current number of cards in the team's hand.
     * @return the current hand size
     */
    public int getHandSize() {
        return this.hand.size();
    }

    /**
     * Checks if the team's hand is full, meaning it has reached the maximum allowed number of cards.
     * @return true if the hand is full, false otherwise
     */
    public boolean isHandFull() {
        return this.hand.isFull();
    }

    /**
     * Returns the card at the specified index in the team's hand.
     * @param index the index of the card to retrieve from the hand
     * @return the card at the specified index in the hand
     */
    public Unit getHandCardAt(int index) {
        return this.hand.getCardAt(index);
    }

    /**
     * Removes and returns the card at the specified index from the team's hand. This method is used when a card is played or discarded
     * from the hand.
     * @param index the index of the card to remove from the hand
     * @return the card that was removed from the hand at the specified index
     */
    public Unit removeHandCardAt(int index) {
        return this.hand.removeCardAt(index);
    }

    /**
     * Returns the current number of cards in the team's draw pile. This method is used to check how many cards are left in the draw pile.
     * @return the current size of the draw pile
     */
    public int getDrawPileSize() {
        return this.drawPile.size();
    }

    @Override
    public int getStartDeckSize() {
        return this.drawPile.getStartSize();
    }

    @Override
    public int getMaxUnitsOnBoard() {
        return MAX_UNITS_ON_BOARD;
    }

    /**
     * Checks if the team's draw pile is empty, meaning there are no more cards left to draw.
     * @return true if the draw pile is empty, false otherwise
     */
    public boolean isDrawPileEmpty() {
        return this.drawPile.isEmpty();
    }

    /**
     * Draws the top card from the team's draw pile and adds it to the team's hand. This method is used when the team needs to draw a
     * card during the game. If the hand is full or the draw pile is empty, the method returns null, indicating that no card was drawn.
     * @return the card that was drawn and added to the hand, or null if the hand is full or the draw pile is empty
     */
    public Unit drawToHand() {
        if (hand.isFull() || drawPile.isEmpty()) {
            return null;
        }

        Unit drawnCard = drawPile.drawTop();
        hand.add(drawnCard);
        return drawnCard;
    }
}
