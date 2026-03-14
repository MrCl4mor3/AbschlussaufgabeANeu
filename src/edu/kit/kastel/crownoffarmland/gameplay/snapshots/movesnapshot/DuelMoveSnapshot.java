package edu.kit.kastel.crownoffarmland.gameplay.snapshots.movesnapshot;

import edu.kit.kastel.crownoffarmland.gameplay.combat.DuelResult;
import edu.kit.kastel.crownoffarmland.gameplay.snapshots.EntitySnapshot;

/**
 * Represents a snapshot of a duel move.
 *
 * @author ucgdi
 */
public final class DuelMoveSnapshot extends MoveSnapshot {
    private static final MoveType MOVE_TYPE = MoveType.DUEL;
    private static final int GOT_DAMAGE_THRESHOLD = 1;

    private final String fromPositionName;
    private final DuelResult result;
    private final EntitySnapshot targetEntity;
    private final String loserName;

    /**
     * Creates a new duel move snapshot.
     *
     * @param movedEntity the moved entity
     * @param targetEntity the target entity
     * @param fromPositionName the source position name
     * @param toPositionName the target position name
     * @param wasBlocked whether the entity was blocked before the move
     * @param result the duel result
     * @param loserName the losing team name, or {@code null} if the game is not over
     */
    public DuelMoveSnapshot(EntitySnapshot movedEntity, EntitySnapshot targetEntity, String fromPositionName,
            String toPositionName, boolean wasBlocked, DuelResult result, String loserName) {
        super(movedEntity, toPositionName, wasBlocked, MOVE_TYPE);
        this.result = result;
        this.targetEntity = targetEntity;
        this.fromPositionName = fromPositionName;
        this.loserName = loserName;
    }

    /**
     * Returns whether the game ended with this duel.
     *
     * @return {@code true} if the game is over
     */
    public boolean isGameOver() {
        return loserName != null;
    }

    /**
     * Returns the losing team name.
     *
     * @return the losing team name
     */
    public String getLoserName() {
        return loserName;
    }

    /**
     * Returns the source position name.
     *
     * @return the source position name
     */
    public String getFromPositionName() {
        return fromPositionName;
    }

    /**
     * Returns the target entity.
     *
     * @return the target entity
     */
    public EntitySnapshot getTargetEntity() {
        return targetEntity;
    }

    /**
     * Returns whether the attacker was flipped.
     *
     * @return {@code true} if the attacker was flipped
     */
    public boolean attackerWasFlipped() {
        return getMovedEntity().isHidden();
    }

    /**
     * Returns whether the defender was flipped.
     *
     * @return {@code true} if the defender was flipped
     */
    public boolean defenderWasFlipped() {
        return targetEntity.isHidden();
    }

    /**
     * Returns whether the attacker was eliminated.
     *
     * @return {@code true} if the attacker was eliminated
     */
    public boolean attackerWasEliminated() {
        return result.isAttackerEliminated();
    }

    /**
     * Returns whether the defender was eliminated.
     *
     * @return {@code true} if the defender was eliminated
     */
    public boolean defenderWasEliminated() {
        return result.isDefenderEliminated();
    }

    /**
     * Returns whether the duel caused damage.
     *
     * @return {@code true} if damage was dealt
     */
    public boolean hasDamage() {
        return result.getDamageToAttackerTeam() >= GOT_DAMAGE_THRESHOLD
                || result.getDamageToDefenderTeam() >= GOT_DAMAGE_THRESHOLD;
    }

    /**
     * Returns the damage amount.
     *
     * @return the damage amount
     */
    public int getDamageAmount() {
        if (result.getDamageToAttackerTeam() >= GOT_DAMAGE_THRESHOLD) {
            return result.getDamageToAttackerTeam();
        }
        return result.getDamageToDefenderTeam();
    }

    /**
     * Returns the name of the damaged team.
     *
     * @return the damaged team name, or {@code null} if no damage was dealt
     */
    public String getDamagedTeamName() {
        if (result.getDamageToAttackerTeam() >= GOT_DAMAGE_THRESHOLD) {
            return getMovedEntity().getTeamName();
        }
        if (result.getDamageToDefenderTeam() >= GOT_DAMAGE_THRESHOLD) {
            return targetEntity.getTeamName();
        }
        return null;
    }

    /**
     * Returns whether the attacker moves to the target position.
     *
     * @return {@code true} if the attacker moves to the target position
     */
    public boolean attackerMovesToTarget() {
        return !result.isAttackerEliminated()
                && result.isDefenderEliminated()
                && !targetEntity.isFarmerKing();
    }
}