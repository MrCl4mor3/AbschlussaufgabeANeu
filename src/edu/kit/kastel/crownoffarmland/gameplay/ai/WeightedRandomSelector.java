package edu.kit.kastel.crownoffarmland.gameplay.ai;

import edu.kit.kastel.crownoffarmland.model.RandomGenerator;

import java.util.ArrayList;
import java.util.List;

/**
 * A utility class for selecting a random index from a list of weights, where the probability of selecting each index is proportional to
 * its weight.
 * This class also provides a method for selecting an index based on inverse weights, where the probability of selecting each index is
 * inversely proportional to its weight.
 * Negative weights are treated as zero.
 *
 * @author ucgdi
 */
public final class WeightedRandomSelector {
    private static final int MIN_WEIGHT = 0;
    private static final int INTERVAL_START = 1;
    private static final int OFFSET = 1;
    private static final int INDEX_NOT_FOUND = -1;
    private final RandomGenerator random;

    /**
     * Constructor for creating a WeightedRandomSelector object with the specified random generator.
     * @param random the random generator to be used for selecting random indices based on weights
     */
    public WeightedRandomSelector(RandomGenerator random) {
        this.random = random;
    }

    /**
     * Selects a random index from the list of weights, where the probability of selecting each index is proportional to its weight.
     * @param weights the list of weights, where each weight corresponds to the probability of selecting the index at that position.
     *                Negative weights are treated as zero.
     * @return the selected index based on the weights, or -1 if the list of weights is empty or all weights are zero
     */
    public int selectWeightedRandom(List<Integer> weights) {
        List<Integer> normalizedWeights = setNegativeWeightsToZero(weights);


        int totalWeight = getTotalWeight(normalizedWeights);
        int roll = random.nextInt(INTERVAL_START, totalWeight + OFFSET);

        return getIndexForRoll(normalizedWeights, roll);
    }

    /**
     * Selects a random index from the list of weights, where the probability of selecting each index is inversely proportional to its
     * weight.
     * @param weights the list of weights, where each weight corresponds to the probability of selecting the index at that position.
     * @return the selected index based on the inverse weights, or -1 if the list of weights is empty or all weights are zero
     */
    public int selectInverseWeightedRandom(List<Integer> weights) {
        List<Integer> normalizedWeights = setNegativeWeightsToZero(weights);
        int maxWeight = getMaxWeight(normalizedWeights);

        List<Integer> inverseWeights = new ArrayList<>(normalizedWeights.size());
        for (Integer weight : normalizedWeights) {
            inverseWeights.add(maxWeight - weight);
        }
        return selectWeightedRandom(inverseWeights);
    }



    private int getTotalWeight(List<Integer> weights) {
        int totalWeight = 0;
        for (int weight : weights) {
            totalWeight += weight;
        }
        return totalWeight;
    }

    private int getIndexForRoll(List<Integer> weights, int roll) {
        System.out.println("Roll: " + roll);
        int prefixSum = 0;
        for (int i = 0; i < weights.size(); i++) {
            System.out.println(prefixSum);
            prefixSum += weights.get(i);
            if (roll <= prefixSum) {
                System.out.println(i);
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }


    private List<Integer> setNegativeWeightsToZero(List<Integer> weights) {
        for (int i = 0; i < weights.size(); i++) {
            if (weights.get(i) < 0) {
                weights.set(i, MIN_WEIGHT);
            }
        }
        return weights;
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
