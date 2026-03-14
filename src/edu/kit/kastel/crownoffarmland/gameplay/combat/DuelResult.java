package edu.kit.kastel.crownoffarmland.gameplay.combat;

/**
 * Represents the result of a duel.
 *
 * @author ucgdi
 */
public class DuelResult {
    private final boolean attackerEliminated;
    private final boolean defenderEliminated;
    private final int damageToAttackerTeam;
    private final int damageToDefenderTeam;

    /**
     * Creates a new duel result.
     *
     * @param attackerEliminated whether the attacker was eliminated
     * @param defenderEliminated whether the defender was eliminated
     * @param damageToAttackerTeam the damage dealt to the attacker's team
     * @param damageToDefenderTeam the damage dealt to the defender's team
     */
    public DuelResult(boolean attackerEliminated, boolean defenderEliminated,
            int damageToAttackerTeam, int damageToDefenderTeam) {
        this.attackerEliminated = attackerEliminated;
        this.defenderEliminated = defenderEliminated;
        this.damageToAttackerTeam = damageToAttackerTeam;
        this.damageToDefenderTeam = damageToDefenderTeam;
    }
    /**
     * Returns whether the attacker was eliminated.
     *
     * @return {@code true} if the attacker was eliminated
     */
    public boolean isAttackerEliminated() {
        return attackerEliminated;
    }

    /**
     * Returns whether the defender was eliminated.
     *
     * @return {@code true} if the defender was eliminated
     */
    public boolean isDefenderEliminated() {
        return defenderEliminated;
    }

    /**
     * Returns the damage dealt to the attacker's team.
     *
     * @return the damage dealt to the attacker's team
     */
    public int getDamageToAttackerTeam() {
        return damageToAttackerTeam;
    }

    /**
     * Returns the damage dealt to the defender's team.
     *
     * @return the damage dealt to the defender's team
     */
    public int getDamageToDefenderTeam() {
        return damageToDefenderTeam;
    }
}