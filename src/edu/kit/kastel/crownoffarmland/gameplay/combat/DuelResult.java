package edu.kit.kastel.crownoffarmland.gameplay.combat;

/**
 * The DuelResult class encapsulates the outcome of a duel between two units in the game. It contains information about the type of duel
 * (standard or blockade), whether the attacker or defender was eliminated, and the amount of damage dealt to each team as a result of the
 * combat. The DuelResult is used to communicate the results of a duel back to the game logic, allowing for appropriate updates to the game
 * state based on the outcome of the combat.
 *
 * @author ucgdi
 */
public class DuelResult {
    private final DuelType duelType;
    private final boolean attackerEliminated;
    private final boolean defenderEliminated;
    private final int damageToAttackerTeam;
    private final int damageToDefenderTeam;


    /**
     * Constructs a new DuelResult with the specified parameters, representing the outcome of a duel between two units. The constructor
     * initializes the fields of the DuelResult based on the type of duel, whether the attacker or defender was eliminated, and the
     * amount of damage dealt to each team. This information is used to communicate the results of the duel back to the game logic,
     * allowing for appropriate updates to the game state based on the outcome of the combat.
     * @param duelType the type of duel that occurred (standard or blockade)
     * @param attackerEliminated indicates whether the attacking unit was eliminated as a result of the duel
     * @param defenderEliminated indicates whether the defending unit was eliminated as a result of the duel
     * @param damageToAttackerTeam the amount of damage dealt to the attacker's team as a result of the duel
     * @param damageToDefenderTeam the amount of damage dealt to the defender's team as a result of the duel
     */
    public DuelResult(DuelType duelType, boolean attackerEliminated, boolean defenderEliminated, int damageToAttackerTeam,
        int damageToDefenderTeam) {
        this.duelType = duelType;
        this.attackerEliminated = attackerEliminated;
        this.defenderEliminated = defenderEliminated;
        this.damageToAttackerTeam = damageToAttackerTeam;
        this.damageToDefenderTeam = damageToDefenderTeam;
    }

    /**
     * Returns the type of duel that occurred (standard or blockade) as part of the result of a combat between two units. This
     * information is used to communicate the nature of the combat back to the game logic, allowing for appropriate updates to the game
     * state based on the outcome of the duel. The duel type can influence how the game processes the results of the combat, such as
     * determining which units are affected by the damage dealt and how the game state is updated based on the outcome of the duel.
     * @return the type of duel that occurred (standard or blockade) as part of the result of a combat between two units
     */
    public DuelType getDuelType() {
        return duelType;
    }

    /**
     * Returns whether the attacking unit was eliminated as a result of the duel between two units in the game. This information is used to
     * communicate the outcome of the combat back to the game logic, allowing for appropriate updates to the game state based on the
     * results of the duel. If the attacker was eliminated, it may affect the game state by removing the attacking unit from the board
     * and potentially impacting the attacker's team in terms of resources or strategic position. The elimination of the attacker can
     * also influence the morale and future actions of the attacker's team in subsequent turns of the game.
     * @return whether the attacking unit was eliminated as a result of the duel between two units in the game
     */
    public boolean isAttackerEliminated() {
        return attackerEliminated;
    }

    /**
     * Returns whether the defending unit was eliminated as a result of the duel between two units in the game. This information is used to
     * communicate the outcome of the combat back to the game logic, allowing for appropriate updates to the game state based on the
     * results of the duel. If the defender was eliminated, it may affect the game state by removing the defending unit from the board
     * and potentially impacting the defender's team in terms of resources or strategic position. The elimination of the defender can
     * also influence the morale and future actions of the defender's team in subsequent turns of the game. Additionally, the elimination
     * of the defender may have specific implications for the game state if the defender was a key unit, such as a Farmer King, which
     * could lead to a significant shift in the balance of power between the teams.
     * @return whether the defending unit was eliminated as a result of the duel between two units in the game
     */
    public  boolean isDefenderEliminated() {
        return defenderEliminated;
    }

    /**
     * Returns the amount of damage dealt to the attacker's team as a result of the duel between two units in the game. This information
     * is used to communicate the consequences of the combat back to the game logic, allowing for appropriate updates to the game state
     * based on the results of the duel. The damage dealt to the attacker's team can affect the overall strength and resources of the
     * attacker's team, potentially impacting their ability to take future actions
     * @return the amount of damage dealt to the attacker's team as a result of the duel between two units in the game
     */
    public int getDamageToAttackerTeam() {
        return damageToAttackerTeam;
    }

    /**
     * Returns the amount of damage dealt to the defender's team as a result of the duel between two units in the game. This information
     * is used to communicate the consequences of the combat back to the game logic, allowing for appropriate updates to the game state
     * based on the results of the duel. The damage dealt to the defender's team can affect the overall strength and resources of the
     * defender's team, potentially impacting their ability to take future actions and defend against subsequent attacks. Additionally,
     * the damage dealt to the defender's team may have specific implications for the game state if the defender was a key unit, such as
     * a Farmer King, which could lead to a significant shift in the balance of power between the teams. The damage to the defender's
     * team can also influence the morale and future actions of the defender's team in subsequent turns of the game, as they may need to
     * adjust their strategy based on the losses incurred from the duel.
     * @return the amount of damage dealt to the defender's team as a result of the duel between two units in the game
     */
    public int getDamageToDefenderTeam() {
        return damageToDefenderTeam;
    }
}