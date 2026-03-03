package edu.kit.kastel.crownoffarmland.model;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public final class RandomGenerator {
    private final Random rnd;

    public RandomGenerator(long seed) {
        this.rnd = new Random(seed);
    }

    public int nextInt(int origin, int bound) {
        return rnd.nextInt(origin, bound);
    }

    public void shuffle(List<?> list) {
        Collections.shuffle(list, rnd);
    }

}
