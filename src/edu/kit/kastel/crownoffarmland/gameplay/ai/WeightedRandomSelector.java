package edu.kit.kastel.crownoffarmland.gameplay.ai;

import edu.kit.kastel.crownoffarmland.model.RandomGenerator;

import java.util.ArrayList;
import java.util.List;

/**
 * Selects random indices based on weighted probabilities.
 *
 * @author ucgdi
 */
public final class WeightedRandomSelector {
    private static final int MIN_WEIGHT = 0;
    private static final int DEFAULT_WEIGHT = 1;
    private static final int INTERVAL_START = 1;
    private static final int OFFSET = 1;
    private static final int INDEX_NOT_FOUND = -1;

    private final RandomGenerator random;

    /**
     * Creates a new weighted random selector.
     *
     * @param random the random generator
     */
    public WeightedRandomSelector(RandomGenerator random) {
        this.random = random;
    }

    /**
     * Selects an index based on the given weights.
     *
     * @param weights the weights to use
     * @return the selected index
     */
    public int selectWeightedRandom(List<Integer> weights) {
        List<Integer> normalizedWeights = normalizeWeights(weights);
        int totalWeight = getTotalWeight(normalizedWeights);
        int roll = random.nextInt(INTERVAL_START, totalWeight + OFFSET);

        return getIndexForRoll(normalizedWeights, roll);
    }

    /**
     * Selects an index based on the inverse of the given weights.
     *
     * @param weights the weights to invert
     * @return the selected index
     */
    public int selectInverseWeightedRandom(List<Integer> weights) {
        List<Integer> normalizedWeights = normalizeWeights(weights);
        int maxWeight = getMaxWeight(normalizedWeights);

        List<Integer> inverseWeights = new ArrayList<>(normalizedWeights.size());
        for (int weight : normalizedWeights) {
            inverseWeights.add(maxWeight - weight);
        }

        if (getTotalWeight(inverseWeights) == 0) {
            setAllWeightsToDefault(inverseWeights);
        }

        return selectWeightedRandom(inverseWeights);
    }

    private List<Integer> normalizeWeights(List<Integer> weights) {
        List<Integer> normalizedWeights = new ArrayList<>(weights.size());
        for (int weight : weights) {
            normalizedWeights.add(Math.max(weight, MIN_WEIGHT));
        }

        if (getTotalWeight(normalizedWeights) == 0) {
            setAllWeightsToDefault(normalizedWeights);
        }

        return normalizedWeights;
    }

    private void setAllWeightsToDefault(List<Integer> weights) {
        weights.replaceAll(ignored -> DEFAULT_WEIGHT);
    }

    private int getTotalWeight(List<Integer> weights) {
        int totalWeight = 0;
        for (int weight : weights) {
            totalWeight += weight;
        }
        return totalWeight;
    }

    private int getIndexForRoll(List<Integer> weights, int roll) {
        int prefixSum = 0;
        for (int i = 0; i < weights.size(); i++) {
            prefixSum += weights.get(i);
            if (roll <= prefixSum) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }

    private int getMaxWeight(List<Integer> weights) {
        int maxWeight = MIN_WEIGHT;
        for (int weight : weights) {
            if (weight > maxWeight) {
                maxWeight = weight;
            }
        }
        return maxWeight;
    }
}