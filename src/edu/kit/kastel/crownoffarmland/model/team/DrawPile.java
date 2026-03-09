package edu.kit.kastel.crownoffarmland.model.team;

import edu.kit.kastel.crownoffarmland.model.RandomGenerator;
import edu.kit.kastel.crownoffarmland.model.units.Unit;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.List;

/**
 * Represents the draw pile of a team, which contains the units that can be drawn during the game.
 * The draw pile is initialized with a collection of units and provides methods to draw the top unit and shuffle the pile.
 *
 *
 * @author ucgdi
 */
public final class DrawPile {
    private final Deque<Unit> deck;

    /**
     * Initializes the draw pile with a collection of units. The units are added to the pile in the order they are provided in the
     * collection.
     * @param initialCards the collection of units to initialize the draw pile with
     */
    public DrawPile(Collection<Unit> initialCards) {
        this.deck = new ArrayDeque<>(initialCards);
    }

    /**
     * Returns the number of units currently in the draw pile.
     * @return the size of the draw pile
     */
    public int size() {
        return deck.size();
    }

    /**
     * Draws the top unit from the draw pile. If the pile is empty, it returns null.
     * @return the unit drawn from the top of the pile, or null if the pile is empty
     */
    public Unit drawTop() {
        return  deck.pollFirst();
    }

    /**
     * Checks if the draw pile is empty.
     * @return true if the draw pile is empty, false otherwise
     */
    public boolean isEmpty() {
        return deck.isEmpty();
    }

    /**
     * Shuffles the draw pile by randomizing the order of the units in the pile.
     * This method creates a temporary list of the units, shuffles it, and then repopulates the draw pile with the shuffled units.
     * @param generator the random generator used to shuffle the units in the draw pile
     */
    public void shuffle(RandomGenerator generator) {
        List<Unit> toShuffle = new ArrayList<>(deck);
        generator.shuffle(toShuffle);
        deck.clear();
        deck.addAll(toShuffle);
    }
}