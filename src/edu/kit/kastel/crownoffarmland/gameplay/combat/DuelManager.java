package edu.kit.kastel.crownoffarmland.gameplay.combat;

import edu.kit.kastel.crownoffarmland.model.units.BoardEntity;
import edu.kit.kastel.crownoffarmland.model.units.Unit;

public class DuelManager {

    public DuelResult duel(Unit attacker, BoardEntity defender) {

        if (defender.isFarmerKing()) {
            return new DuelResult(DuelType.KING, false, false,0, attacker.getAtk());
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
            return new DuelResult(DuelType.BlOCKADE, false, true, 0,0);
        } else  if (defB > atkA) {
            int damage = defB - atkA;
            return new DuelResult(DuelType.BlOCKADE, true, false, damage,0);
        } else  {
            return new DuelResult(DuelType.BlOCKADE, false, false,0,0);
        }
    }

    private DuelResult resolveStandard(Unit attacker, Unit defender) {
        int atkA = attacker.getAtk();
        int atkB = defender.getAtk();

        if (atkA > atkB) {
            return new DuelResult(DuelType.STANDARD, false, true,0,0);
        } else  if (atkB > atkA) {
            return new DuelResult(DuelType.STANDARD, true, false,0,0);
        } else   {
            return new DuelResult(DuelType.STANDARD, true, true,0,0);
        }
    }
}