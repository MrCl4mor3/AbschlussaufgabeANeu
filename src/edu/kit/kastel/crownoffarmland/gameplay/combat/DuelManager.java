package edu.kit.kastel.crownoffarmland.gameplay.combat;

import edu.kit.kastel.crownoffarmland.model.units.BoardEntity;
import edu.kit.kastel.crownoffarmland.model.units.Unit;

/**
 * The DuelManager class is responsible for managing duels between units in the game. It provides methods to resolve duels based on the
 * type of combat (standard or blockade) and the attributes of the attacking and defending units. The duel results are encapsulated in a
 * DuelResult object, which contains information about the outcome of the duel, including whether the attacker or defender was defeated
 * and any damage dealt. The DuelManager handles the logic for determining the winner of a duel and calculating the consequences of the
 * combat based on the attributes of the involved units.
 *
 * @author ucgdi
 */
public class DuelManager {
    private static final boolean WAS_ELIMINATED = true;
    private static final boolean WAS_NOT_ELIMINATED = false;
    private static final int NO_DAMAGE_TO_ATTACKER_TEAM = 0;
    private static final int NO_DAMAGE_TO_DEFENDER_TEAM = 1;

    /**
     * Constructs a new DuelManager instance.
     */
    public DuelManager() {
    }

    /**
     * Resolves a duel between an attacker and a defender. The method checks if the defender is a Farmer King, in which case it returns a
     * DuelResult indicating a victory for the attacker. If the defender is not a Farmer King, it checks if the defender is blocked. If
     * the defender is blocked, it resolves the duel as a blockade; otherwise, it resolves it as a standard duel. The method returns a
     * DuelResult object that contains the outcome of the duel, including whether the attacker or defender was defeated and any damage
     * dealt.
     * @param attacker The unit that is attacking in the duel.
     * @param defender The board entity that is defending in the duel. This can be a unit or a Farmer King.
     * @return A DuelResult object that contains the outcome of the duel.
     */
    public DuelResult resolveDuel(Unit attacker, BoardEntity defender) {
        if (defender.isFarmerKing()) {
            return new DuelResult(DuelType.KING, WAS_NOT_ELIMINATED, WAS_NOT_ELIMINATED, NO_DAMAGE_TO_ATTACKER_TEAM, attacker.getAtk());
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
            return new DuelResult(DuelType.BlOCKADE, WAS_NOT_ELIMINATED, WAS_ELIMINATED, NO_DAMAGE_TO_ATTACKER_TEAM, NO_DAMAGE_TO_DEFENDER_TEAM);
        } else  if (defB > atkA) {
            int damage = defB - atkA;
            return new DuelResult(DuelType.BlOCKADE, WAS_ELIMINATED, WAS_NOT_ELIMINATED, damage, NO_DAMAGE_TO_DEFENDER_TEAM);
        } else  {
            return new DuelResult(DuelType.BlOCKADE, WAS_NOT_ELIMINATED, WAS_ELIMINATED, NO_DAMAGE_TO_ATTACKER_TEAM, NO_DAMAGE_TO_DEFENDER_TEAM);
        }
    }

    private DuelResult resolveStandard(Unit attacker, Unit defender) {
        int atkA = attacker.getAtk();
        int atkB = defender.getAtk();

        if (atkA > atkB) {
            return new DuelResult(DuelType.STANDARD, WAS_NOT_ELIMINATED, WAS_ELIMINATED, NO_DAMAGE_TO_ATTACKER_TEAM, atkA - atkB);
        } else  if (atkB > atkA) {
            return new DuelResult(DuelType.STANDARD, WAS_ELIMINATED, WAS_NOT_ELIMINATED, atkB - atkA, NO_DAMAGE_TO_DEFENDER_TEAM);
        } else   {
            return new DuelResult(DuelType.STANDARD, WAS_ELIMINATED, WAS_ELIMINATED, NO_DAMAGE_TO_ATTACKER_TEAM, NO_DAMAGE_TO_DEFENDER_TEAM);
        }
    }
}