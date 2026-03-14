package edu.kit.kastel.crownoffarmland.gameplay.combat;

import edu.kit.kastel.crownoffarmland.model.units.BoardEntity;
import edu.kit.kastel.crownoffarmland.model.units.Unit;

/**
 * Resolves duels between units.
 *
 * @author ucgdi
 */
public class DuelManager {
    private static final boolean WAS_ELIMINATED = true;
    private static final boolean WAS_NOT_ELIMINATED = false;
    private static final int NO_DAMAGE_TO_ATTACKER_TEAM = 0;
    private static final int NO_DAMAGE_TO_DEFENDER_TEAM = 1;

    /**
     * Creates a new duel manager.
     */
    public DuelManager() {
    }

    /**
     * Resolves a duel between an attacker and a defender.
     *
     * @param attacker the attacking unit
     * @param defender the defending entity
     * @return the duel result
     */
    public DuelResult resolveDuel(Unit attacker, BoardEntity defender) {
        if (defender.isFarmerKing()) {
            return new DuelResult(WAS_NOT_ELIMINATED, WAS_NOT_ELIMINATED,
                    NO_DAMAGE_TO_ATTACKER_TEAM, attacker.getAtk());
        }

        Unit def = (Unit) defender;

        if (def.isBlocked()) {
            return resolveBlockade(attacker, def);
        }
        return resolveStandard(attacker, def);
    }

    private DuelResult resolveBlockade(Unit attacker, Unit defender) {
        int atkA = attacker.getAtk();
        int defB = defender.getDef();

        if (atkA > defB) {
            return new DuelResult(WAS_NOT_ELIMINATED, WAS_ELIMINATED,
                    NO_DAMAGE_TO_ATTACKER_TEAM, NO_DAMAGE_TO_DEFENDER_TEAM);
        } else if (defB > atkA) {
            int damage = defB - atkA;
            return new DuelResult(WAS_ELIMINATED, WAS_NOT_ELIMINATED,
                    damage, NO_DAMAGE_TO_DEFENDER_TEAM);
        } else {
            return new DuelResult(WAS_NOT_ELIMINATED, WAS_ELIMINATED,
                    NO_DAMAGE_TO_ATTACKER_TEAM, NO_DAMAGE_TO_DEFENDER_TEAM);
        }
    }

    private DuelResult resolveStandard(Unit attacker, Unit defender) {
        int atkA = attacker.getAtk();
        int atkB = defender.getAtk();

        if (atkA > atkB) {
            return new DuelResult(WAS_NOT_ELIMINATED, WAS_ELIMINATED,
                    NO_DAMAGE_TO_ATTACKER_TEAM, atkA - atkB);
        } else if (atkB > atkA) {
            return new DuelResult(WAS_ELIMINATED, WAS_NOT_ELIMINATED,
                    atkB - atkA, NO_DAMAGE_TO_DEFENDER_TEAM);
        } else {
            return new DuelResult(WAS_ELIMINATED, WAS_ELIMINATED,
                    NO_DAMAGE_TO_ATTACKER_TEAM, NO_DAMAGE_TO_DEFENDER_TEAM);
        }
    }
}