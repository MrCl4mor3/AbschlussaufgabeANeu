package edu.kit.kastel.crownoffarmland.gameplay.unitmerge;

import edu.kit.kastel.crownoffarmland.model.units.StatusValue;
import edu.kit.kastel.crownoffarmland.model.units.Unit;
import edu.kit.kastel.crownoffarmland.model.units.UnitName;


/**
 * The UnitMerger class is responsible for determining whether two units can be merged based on their attributes and, if so, creating a
 * new merged unit with combined attributes. The merging process considers various conditions such as symbiosis, alignment, and prime
 * compatibility, which are determined by the attack and defense values of the units. The result of the merging attempt is encapsulated
 * in a MergeResult object, which indicates the type of merge that occurred (if any) and the resulting merged unit.
 *
 * @author ucgdi
 */
public class UnitMerger {

    private static final String QUALIFICATOR_DELIMITER = " ";
    private static final int G3T_PRIME_THRESHOLD = 100;
    private static final int PRIME_DIVISOR = 100;


    /**
     * Tries to merge two units based on their attributes. The method checks for various merging conditions such as symbiosis, alignment,
     * and prime compatibility, and returns a MergeResult indicating the outcome of the merging attempt. If the units are incompatible,
     * the result will indicate that as well. The merged unit, if created, will have combined attributes based on the merging rules
     * defined in the method.
     * @param incoming the unit that is attempting to merge with the target unit
     * @param target the unit that is being targeted for merging with the incoming unit
     * @return a MergeResult object that indicates the type of merge that occurred (if any) and the resulting merged unit, or null if the
     *     units are incompatible
     */
    public MergeResult tryMerge(Unit incoming, Unit target) {

        if (incoming == null || target == null) {
            return new MergeResult(MergeType.INCOMPATIBLE, null);
        }

        if (incoming.getName().equals(target.getName())) {
            return new MergeResult(MergeType.INCOMPATIBLE, null);
        }

        int atkA = incoming.getAtk();
        int defA = incoming.getDef();
        int defB = target.getDef();
        int atkB = target.getAtk();


        // Check for Symbiosis first
        if (isSymbiosis(atkA, defA, atkB, defB)) {
            Unit mergedUnit = buildMergedUnit(incoming, target, atkA, defB);
            return new MergeResult(MergeType.SYMBIOSIS, mergedUnit);
        }


        int g3t = computeG3t(atkA, defA, atkB, defB);


        // Check for Alignment if g3t is greater than the threshold
        if (g3t > G3T_PRIME_THRESHOLD) {
            int mergedAtk = atkA + atkB - g3t;
            int mergedDef = defA + defB - g3t;
            Unit mergedUnit = buildMergedUnit(incoming, target, mergedAtk, mergedDef);
            return new MergeResult(MergeType.PRIME, mergedUnit);
        }

        // Check for Prime Compatibility if g3t is equal to the threshold
        if (g3t == G3T_PRIME_THRESHOLD && isPrimeCompatible(atkA, defA, atkB, defB)) {
            int mergedAtk = (atkA + atkB) / PRIME_DIVISOR;
            int mergedDef = (defA + defB) / PRIME_DIVISOR;
            Unit mergedUnit = buildMergedUnit(incoming, target, mergedAtk, mergedDef);
            return new MergeResult(MergeType.ALIGNMENT, mergedUnit);
        }
        // if none of the above conditions are met, the units are incompatible
        return new MergeResult(MergeType.INCOMPATIBLE, null);
    }


    private boolean isSymbiosis(int atkA, int defA, int atkB, int defB) {
        return (atkA > atkB && atkA == defB && defA == atkB);
    }

    private Unit buildMergedUnit(Unit incoming, Unit resident, int atk, int def) {
        String mergedQualificator = mergeQualificator(incoming.getQualificator(), resident.getQualificator());
        String mergedRole = mergeRole(incoming.getRole(), resident.getRole());
        return new Unit(incoming.getTeamID(), new UnitName(mergedRole, mergedQualificator), new StatusValue(atk, def));
    }

    private String mergeQualificator(String qualificatorA, String qualificatorB) {
        return qualificatorB + QUALIFICATOR_DELIMITER + qualificatorA;
    }

    private String mergeRole(String roleA, String roleB) {
        return roleB;
    }

    private int computeG3t(int atkA, int defA, int atkB, int defB) {
        return Math.max(ggT(atkA, atkB), ggT(defA, defB));
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

    private boolean isPrimeCompatible(int atkA, int defA, int atkB, int defB) {
        boolean atkSide = divisibleByDivisorAndQuotientPrime(atkA) && divisibleByDivisorAndQuotientPrime(atkB);
        boolean defSide = divisibleByDivisorAndQuotientPrime(defA) && divisibleByDivisorAndQuotientPrime(defB);
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