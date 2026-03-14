package edu.kit.kastel.crownoffarmland.model.team;

import edu.kit.kastel.crownoffarmland.model.units.Unit;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a team's hand of unit cards.
 * A hand can hold up to a fixed maximum number of cards.
 *
 * @author ucgdi
 */
public class Hand {
    private static final int MAX_HAND_SIZE = 5;

    private final List<Unit> cards;

    /**
     * Constructs an empty hand.
     */
    public Hand() {
        cards = new ArrayList<>();
    }

    /**
     * Returns the number of cards currently in the hand.
     *
     * @return the current hand size
     */
    public int size() {
        return cards.size();
    }

    /**
     * Adds the given card to the hand if it is not full.
     *
     * @param unit the card to add
     * @return {@code true} if the card was added, otherwise {@code false}
     */
    public boolean add(Unit unit) {
        if (!isFull()) {
            cards.add(unit);
            return true;
        }
        return false;
    }

    /**
     * Checks whether the hand is full.
     *
     * @return {@code true} if the hand is full, otherwise {@code false}
     */
    public boolean isFull() {
        return cards.size() == MAX_HAND_SIZE;
    }

    /**
     * Returns the card at the given index.
     *
     * @param index the card index
     * @return the card at the given index
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    public Unit getCardAt(int index) {
        return cards.get(index);
    }

    /**
     * Removes and returns the card at the given index.
     *
     * @param index the card index
     * @return the removed card
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    public Unit removeCardAt(int index) {
        return cards.remove(index);
    }
}