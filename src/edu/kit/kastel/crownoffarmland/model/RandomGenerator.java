package edu.kit.kastel.crownoffarmland.model;

import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Wraps a random number generator used by the game.
 *
 * @author ucgdi
 */
public final class RandomGenerator {
    private final Random rnd;

    /**
     * Constructs a random generator with the given seed.
     *
     * @param seed the seed of the random generator
     */
    public RandomGenerator(long seed) {
        this.rnd = new Random(seed);
    }

    /**
     * Returns a random integer in the given range.
     *
     * @param origin the lower bound, inclusive
     * @param bound the upper bound, exclusive
     * @return a random integer between {@code origin} and {@code bound}
     */
    public int nextInt(int origin, int bound) {
        return rnd.nextInt(origin, bound);
    }

    /**
     * Shuffles the given list.
     *
     * @param list the list to shuffle
     */
    public void shuffle(List<?> list) {
        Collections.shuffle(list, rnd);
    }
}