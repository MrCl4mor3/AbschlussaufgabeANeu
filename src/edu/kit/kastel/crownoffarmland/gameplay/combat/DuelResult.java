package edu.kit.kastel.crownoffarmland.gameplay.combat;

public class DuelResult {
    private final DuelType duelType;
    private final boolean attackerEliminated;
    private final boolean defenderEliminated;
    private final int damageToAttackerTeam;
    private final int damageToDefenderTeam;


    public DuelResult(DuelType duelType, boolean attackerEliminated, boolean defenderEliminated, int damageToAttackerTeam, int damageToDefenderTeam) {
        this.duelType = duelType;
        this.attackerEliminated = attackerEliminated;
        this.defenderEliminated = defenderEliminated;
        this.damageToAttackerTeam = damageToAttackerTeam;
        this.damageToDefenderTeam = damageToDefenderTeam;
    }

    public DuelType getDuelType() {
        return duelType;
    }

    public boolean isAttackerEliminated() {
        return attackerEliminated;
    }

    public  boolean isDefenderEliminated() {
        return defenderEliminated;
    }

    public int getDamageToAttackerTeam() {
        return damageToAttackerTeam;
    }

    public int getDamageToDefenderTeam() {
        return damageToDefenderTeam;
    }
}