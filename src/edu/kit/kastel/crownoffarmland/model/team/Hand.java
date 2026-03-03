package edu.kit.kastel.crownoffarmland.model.team;

import edu.kit.kastel.crownoffarmland.model.units.Unit;

import java.util.ArrayList;
import java.util.List;

public class Hand {
    private static final int MAX_HAND_Size = 5;

    private final List<Unit> cards;


    public Hand() {
        cards = new ArrayList<>();
    }

    public int size() {
        return cards.size();
    }

    public boolean add(Unit unit) {
        if (!isFull()) {
            cards.add(unit);
            return  true;
        }
        return false;
    }

    public boolean isFull() {
        return cards.size() == MAX_HAND_Size;
    }

    public Unit getCardOnIndex(int index) {
        return cards.get(index);
    }

    public Unit remove(int index) {
        return cards.remove(index);
    }
}