package edu.kit.kastel.crownoffarmland.model.team;

import edu.kit.kastel.crownoffarmland.model.RandomGenerator;
import edu.kit.kastel.crownoffarmland.model.units.Unit;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.List;

/**
 * Represents a team's draw pile.
 * It stores the units that can be drawn during the game.
 *
 * @author ucgdi
 */
public final class DrawPile {
    private static final int START_SIZE_DEFAULT = 40;
    private final int startSize;
    private final Deque<Unit> deck;

    /**
     * Constructs a draw pile with the given initial cards.
     *
     * @param initialCards the cards to add to the draw pile
     */
    public DrawPile(Collection<Unit> initialCards) {
        this.deck = new ArrayDeque<>(initialCards);
        this.startSize = deck.size();
    }

    /**
     * Returns the number of cards currently in the draw pile.
     *
     * @return the current draw pile size
     */
    public int size() {
        return deck.size();
    }

    /**
     * Returns the initial size of the draw pile.
     *
     * @return the initial draw pile size
     */
    public int getStartSize() {
        return startSize;
    }

    /**
     * Draws and returns the top card of the draw pile.
     *
     * @return the top card, or {@code null} if the draw pile is empty
     */
    public Unit drawTop() {
        return deck.pollFirst();
    }

    /**
     * Checks whether the draw pile is empty.
     *
     * @return {@code true} if the draw pile is empty, otherwise {@code false}
     */
    public boolean isEmpty() {
        return deck.isEmpty();
    }

    /**
     * Shuffles the cards in the draw pile.
     *
     * @param generator the random generator used for shuffling
     */
    public void shuffle(RandomGenerator generator) {
        List<Unit> toShuffle = new ArrayList<>(deck);
        generator.shuffle(toShuffle);
        deck.clear();
        deck.addAll(toShuffle);
    }

    /**
     * Returns the default initial size of a draw pile.
     *
     * @return the default initial size
     */
    public static int getStartSizeDefault() {
        return START_SIZE_DEFAULT;
    }
}