package edu.kit.kastel.crownoffarmland.gameplay.snapshots.movesnapshot;

import edu.kit.kastel.crownoffarmland.gameplay.combat.DuelResult;
import edu.kit.kastel.crownoffarmland.gameplay.snapshots.EntitySnapshot;

/**
 * This class represents a snapshot of a duel move in the game. It contains information about the moved entity, the target entity,
 * the positions involved in the move, whether the move was blocked, and the result of the duel.
 *
 * @author ucgdi
 */
public final class DuelMoveSnapshot extends  MoveSnapshot {
    private static final MoveType MOVE_TYPE = MoveType.DUEL;

    private final String fromPositionName;
    private final DuelResult result;
    private final EntitySnapshot targetEntity;


    /**
     * Creates a new DuelMoveSnapshot with the given parameters.
     * @param movedEntity the entity that was moved in the duel
     * @param targetEntity the entity that was targeted in the duel
     * @param fromPositionName the name of the position from which the entity was moved
     * @param toPositionName the name of the position to which the entity was moved
     * @param wasBlocked indicates whether the move was blocked by another entity
     * @param result the result of the duel, including information about damage dealt and whether any entities were eliminated
     */
    public DuelMoveSnapshot(EntitySnapshot movedEntity, EntitySnapshot targetEntity, String fromPositionName, String toPositionName,
                            boolean wasBlocked,
                            DuelResult result) {
        super(movedEntity, toPositionName, wasBlocked, MOVE_TYPE);
        this.result = result;
        this.targetEntity = targetEntity;
        this.fromPositionName = fromPositionName;
    }


    /**
     * Returns the name of the position from which the entity was moved.
     * @return the name of the position from which the entity was moved
     */
    public String getFromPositionName() {
        return fromPositionName;
    }


    /**
     * Returns the target entity involved in the duel.
     * @return the target entity involved in the duel
     */
    public EntitySnapshot getTargetEntity() {
        return targetEntity;
    }

    /**
     * Returns whether the attacker was flipped (i.e., hidden) as a result of the duel.
     * @return true if the attacker was flipped, false otherwise
     */
    public boolean attackerWasFlipped() {
        return getMovingEntitySnapshot().isHidden();
    }

    /**
     * Returns whether the defender was flipped (i.e., hidden) as a result of the duel.
     * @return true if the defender was flipped, false otherwise
     */
    public boolean defenderWasFlipped() {
        return targetEntity.isHidden();
    }

    /**
     * Returns whether the attacker was eliminated as a result of the duel.
     * @return true if the attacker was eliminated, false otherwise
     */
    public boolean attackerWasEliminated() {
        return result.isAttackerEliminated();
    }

    /**
     * Returns whether the defender was eliminated as a result of the duel.
     * @return true if the defender was eliminated, false otherwise
     */
    public boolean defenderWasEliminated() {
        return result.isDefenderEliminated();
    }

    /**
     * Returns whether any damage was dealt to either the attacker or the defender as a result of the duel.
     * @return true if damage was dealt to either the attacker or the defender, false otherwise
     */
    public boolean hasDamage() {
        return result.getDamageToAttackerTeam() > 0 || result.getDamageToDefenderTeam() > 0;
    }

    /**
     * Returns the amount of damage dealt to either the attacker or the defender as a result of the duel. If damage was dealt to both
     * teams, the damage to the attacker team is returned.
     * @return the amount of damage dealt to either the attacker or the defender, or 0 if no damage was dealt
     */
    public int getDamageAmount() {
        if (result.getDamageToAttackerTeam() > 0) {
            return result.getDamageToAttackerTeam();
        }
        return result.getDamageToDefenderTeam();
    }

    /**
     * Returns the name of the team that was damaged as a result of the duel. If damage was dealt to both teams, the name of the
     * attacker's team is returned.
     * @return the name of the team that was damaged as a result of the duel, or null if no damage was dealt
     */
    public String getDamagedTeamName() {
        if (result.getDamageToAttackerTeam() > 0) {
            return getMovedEntity().getTeamName();
        }
        if (result.getDamageToDefenderTeam() > 0) {
            return targetEntity.getTeamName();
        }
        return null;
    }

    /**
     * Returns whether the attacker moves to the target's position as a result of the duel. This is the case if the attacker is not
     * eliminated, the defender is eliminated, and the target entity is not a Farmer King.
     * @return true if the attacker moves to the target's position as a result of the duel, false otherwise
     */
    public boolean attackerMovesToTarget() {
        return !result.isAttackerEliminated()
                && result.isDefenderEliminated()
                && !targetEntity.isFarmerKing();
    }
}
