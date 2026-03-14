package edu.kit.kastel.crownoffarmland.model.team;

import edu.kit.kastel.crownoffarmland.model.RandomGenerator;
import edu.kit.kastel.crownoffarmland.model.units.FarmerKing;
import edu.kit.kastel.crownoffarmland.model.units.Unit;

import java.util.List;

/**
 * Represents a team in the game.
 * A team has a name, life points, a hand, a draw pile, and a farmer king.
 *
 * @author ucgdi
 */
public class Team implements GameTeamView {
    private static final int START_LP = 8000;
    private static final int MAX_UNITS_ON_BOARD = 5;

    private final String name;
    private final Hand hand;
    private final DrawPile drawPile;
    private final FarmerKing king;

    private int lifePoints;

    /**
     * Constructs a team with the given name, id, and draw pile.
     *
     * @param name the team name
     * @param teamId the team id
     * @param drawPile the initial draw pile cards
     */
    public Team(String name, TeamID teamId, List<Unit> drawPile) {
        this.name = name;
        this.hand = new Hand();
        this.drawPile = new DrawPile(drawPile);
        this.lifePoints = START_LP;
        this.king = new FarmerKing(teamId);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getLifePoints() {
        return lifePoints;
    }

    @Override
    public int getHandSize() {
        return hand.size();
    }

    @Override
    public boolean isHandFull() {
        return hand.isFull();
    }

    @Override
    public int getDrawPileSize() {
        return drawPile.size();
    }

    @Override
    public int getStartDeckSize() {
        return drawPile.getStartSize();
    }

    @Override
    public boolean isDrawPileEmpty() {
        return drawPile.isEmpty();
    }

    @Override
    public int getMaxUnitsOnBoard() {
        return MAX_UNITS_ON_BOARD;
    }

    /**
     * Reduces the team's life points by the given amount.
     *
     * @param amount the damage to deal
     */
    public void takeDamage(int amount) {
        this.lifePoints = Math.max(0, this.lifePoints - amount);
    }

    /**
     * Returns the farmer king of this team.
     *
     * @return the farmer king
     */
    public FarmerKing getKing() {
        return king;
    }

    /**
     * Shuffles the team's draw pile.
     *
     * @param generator the random generator used for shuffling
     */
    public void shuffleDrawPile(RandomGenerator generator) {
        drawPile.shuffle(generator);
    }

    /**
     * Returns the hand card at the given index.
     *
     * @param index the card index
     * @return the card at the given index
     */
    public Unit getHandCardAt(int index) {
        return hand.getCardAt(index);
    }

    /**
     * Removes and returns the hand card at the given index.
     *
     * @param index the card index
     * @return the removed card
     */
    public Unit removeHandCardAt(int index) {
        return hand.removeCardAt(index);
    }

    /**
     * Draws the top card from the draw pile to the hand.
     *
     * @return the drawn card, or {@code null} if the hand is full or the draw pile is empty
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