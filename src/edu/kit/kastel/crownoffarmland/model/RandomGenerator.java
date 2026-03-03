package edu.kit.kastel.crownoffarmland.model;

import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * A wrapper around java.util.Random to provide a more convenient interface for our use case.
 * This class is immutable and thread-safe.
 * It provides methods to generate random integers within a specified range and to shuffle lists.
 * The random generator is initialized with a seed to ensure reproducibility of the random sequences.
 *
 * @author ucgdi
 */
public final class RandomGenerator {
    private final Random rnd;

    /**
     * Constructor for creating a RandomGenerator object with the specified seed.
     * @param seed the seed for the random generator, used to ensure reproducibility of the random sequences
     */
    public RandomGenerator(long seed) {
        this.rnd = new Random(seed);
    }

    /**
     * Generates a random integer between the specified origin (inclusive) and bound (exclusive).
     * @param origin the lower bound (inclusive) of the random integer to be generated
     * @param bound the upper bound (exclusive) of the random integer to be generated
     * @return a random integer between the specified origin (inclusive) and bound (exclusive)
     */
    public int nextInt(int origin, int bound) {
        return rnd.nextInt(origin, bound);
    }

    /**
     * Shuffles the specified list using the random generator. The list is modified in place.
     * @param list the list to be shuffled
     */
    public void shuffle(List<?> list) {
        Collections.shuffle(list, rnd);
    }

}
