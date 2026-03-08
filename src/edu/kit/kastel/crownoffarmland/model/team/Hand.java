package edu.kit.kastel.crownoffarmland.model.team;

import edu.kit.kastel.crownoffarmland.model.units.Unit;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the hand of a player, which can hold a limited number of units (cards).
 * The hand allows adding units, checking if it's full, retrieving units by index, and removing units.
 * The maximum hand size is defined as a constant, and the hand is implemented using a list to store the units.
 * This class provides methods to manage the player's hand of units, ensuring that the number of units does not exceed the defined limit.
 *
 * @author ucgdi
 */
public class Hand {
    private static final int MAX_HAND_SIZE = 5;

    private final List<Unit> cards;


    /**
     * Constructs a new Hand instance with an empty list of units. The hand is initialized to hold a maximum of MAX_HAND_SIZE units, and
     * the cards list is created to store the units added to the hand.
     */
    public Hand() {
        cards = new ArrayList<>();
    }

    /**
     * Returns the number of units currently in the hand.
     *
     * @return the size of the hand
     */
    public int size() {
        return cards.size();
    }

    /**
     * Adds a unit to the hand if it is not full.
     *
     * @param unit the unit to be added
     * @return true if the unit was added successfully, false if the hand is full
     */
    public boolean add(Unit unit) {
        if (!isFull()) {
            cards.add(unit);
            return  true;
        }
        return false;
    }

    /**
     * Checks if the hand has reached its maximum capacity.
     *
     * @return true if the hand is full, false otherwise
     */
    public boolean isFull() {
        return cards.size() == MAX_HAND_SIZE;
    }

    /**
     * Retrieves the unit at the specified index in the hand.
     *
     * @param index the index of the unit to retrieve
     * @return the unit at the specified index
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    public Unit getCardAt(int index) {
        return cards.get(index);
    }

    /**
     * Removes and returns the unit at the specified index in the hand.
     *
     * @param index the index of the unit to remove
     * @return the removed unit
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    public Unit removeCardAt(int index) {
        return cards.remove(index);
    }
}