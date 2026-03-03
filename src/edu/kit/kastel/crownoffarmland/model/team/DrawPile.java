package edu.kit.kastel.crownoffarmland.model.team;

import edu.kit.kastel.crownoffarmland.model.units.Unit;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.List;

public final class DrawPile {
    private final Deque<Unit> deck;

    public DrawPile(Collection<Unit> initialCards) {
        this.deck = new ArrayDeque<>(initialCards);
    }

    public int size() {
        return deck.size();
    }

    public Unit drawTop() {
        Unit unit = deck.pollFirst();
        return unit;
    }

    public void shuffle() {
        List<Unit> toShuffle = new ArrayList<>(deck);


        deck.clear();
        deck.addAll(toShuffle);
    }
}