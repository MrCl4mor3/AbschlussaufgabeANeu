package edu.kit.kastel.crownoffarmland.gameplay.unitmerge;

import edu.kit.kastel.crownoffarmland.model.units.StatusValue;
import edu.kit.kastel.crownoffarmland.model.units.Unit;
import edu.kit.kastel.crownoffarmland.model.units.UnitName;

/**
 * Handles merges between units.
 *
 * @author ucgdi
 */
public class UnitMerger {
    private static final String QUALIFICATOR_DELIMITER = " ";
    private static final int G3T_PRIME_THRESHOLD = 100;
    private static final int PRIME_DIVISOR = 100;

    /**
     * Tries to merge two units.
     *
     * @param incoming the incoming unit
     * @param target the target unit
     * @return the merge result
     */
    public MergeResult tryMerge(Unit incoming, Unit target) {
        if (incoming == null || target == null) {
            return new MergeResult(MergeType.INCOMPATIBLE, null);
        }

        if (incoming.getName().equals(target.getName())) {
            return new MergeResult(MergeType.INCOMPATIBLE, null);
        }

        Unit unitA = incoming.getAtk() >= target.getAtk() ? incoming : target;
        Unit unitB = unitA == incoming ? target : incoming;

        if (isSymbiosis(unitA, unitB)) {
            Unit mergedUnit = buildMergedUnit(incoming, target, unitA.getAtk(), unitB.getDef());
            return new MergeResult(MergeType.SYMBIOSIS, mergedUnit);
        }

        int g3t = computeG3t(unitA, unitB);

        if (g3t > G3T_PRIME_THRESHOLD) {
            int mergedAtk = unitA.getAtk() + unitB.getAtk() - g3t;
            int mergedDef = unitA.getDef() + unitB.getDef() - g3t;
            Unit mergedUnit = buildMergedUnit(incoming, target, mergedAtk, mergedDef);
            return new MergeResult(MergeType.PRIME, mergedUnit);
        }

        if (g3t == G3T_PRIME_THRESHOLD && isPrimeCompatible(unitA, unitB)) {
            int mergedAtk = unitA.getAtk() + unitB.getAtk();
            int mergedDef = unitA.getDef() + unitB.getDef();
            Unit mergedUnit = buildMergedUnit(incoming, target, mergedAtk, mergedDef);
            return new MergeResult(MergeType.ALIGNMENT, mergedUnit);
        }

        return new MergeResult(MergeType.INCOMPATIBLE, null);
    }

    private boolean isSymbiosis(Unit unitA, Unit unitB) {
        return unitA.getAtk() == unitB.getDef() && unitA.getDef() == unitB.getAtk();
    }

    private Unit buildMergedUnit(Unit incoming, Unit resident, int atk, int def) {
        String mergedQualificator = mergeQualificator(incoming.getQualificator(), resident.getQualificator());
        String mergedRole = resident.getRole();
        return new Unit(incoming.getOwner(), new UnitName(mergedQualificator, mergedRole), new StatusValue(atk, def));
    }

    private String mergeQualificator(String qualificatorA, String qualificatorB) {
        return qualificatorB + QUALIFICATOR_DELIMITER + qualificatorA;
    }

    private int computeG3t(Unit unitA, Unit unitB) {
        return Math.max(
                ggT(unitA.getAtk(), unitB.getAtk()),
                ggT(unitA.getDef(), unitB.getDef())
        );
    }

    private int ggT(int a, int b) {
        int absA = Math.abs(a);
        int absB = Math.abs(b);

        while (absB != 0) {
            int temp = absA % absB;
            absA = absB;
            absB = temp;
        }

        return absA;
    }

    private boolean isPrimeCompatible(Unit unitA, Unit unitB) {
        boolean atkSide = divisibleByDivisorAndQuotientPrime(unitA.getAtk())
                && divisibleByDivisorAndQuotientPrime(unitB.getAtk());
        boolean defSide = divisibleByDivisorAndQuotientPrime(unitA.getDef())
                && divisibleByDivisorAndQuotientPrime(unitB.getDef());
        return atkSide || defSide;
    }

    private boolean divisibleByDivisorAndQuotientPrime(int value) {
        return value % PRIME_DIVISOR == 0 && isPrime(value / PRIME_DIVISOR);
    }

    private boolean isPrime(int n) {
        if (n <= 1) {
            return false;
        }

        for (int i = 2; i * i <= n; i++) {
            if (n % i == 0) {
                return false;
            }
        }

        return true;
    }
}