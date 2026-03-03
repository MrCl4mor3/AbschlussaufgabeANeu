package edu.kit.kastel.crownoffarmland.gameplay.unitmerge;

import edu.kit.kastel.crownoffarmland.model.units.StatusValue;
import edu.kit.kastel.crownoffarmland.model.units.Unit;
import edu.kit.kastel.crownoffarmland.model.units.UnitName;


public class UnitMerger {

    private static final String QUALIFICATOR_DELIMITER = " ";
    private static final int G3T_PRIME_THRESHOLD = 100;
    private static final int PRIME_DIVISOR = 100;


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
        return new Unit(incoming.getTeamId(), new UnitName(mergedRole, mergedQualificator), new StatusValue(atk, def));
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
        a = Math.abs(a);
        b = Math.abs(b);
        while (b != 0) {
            int temp = a % b;
            a = b;
            b = temp;
        }
        return a;
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