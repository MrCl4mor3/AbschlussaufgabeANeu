package edu.kit.kastel.crownoffarmland.gameplay.snapshots;

import edu.kit.kastel.crownoffarmland.model.units.BoardEntity;
import edu.kit.kastel.crownoffarmland.model.units.FarmerKing;
import edu.kit.kastel.crownoffarmland.model.units.Unit;

/**
 * Represents a snapshot of a single entity.
 *
 * @author ucgdi
 */
public final class EntitySnapshot {
    private static final int NON_COMBAT_STAT = 0;
    private static final EntitySnapshot NO_UNIT = new EntitySnapshot();

    private final boolean hasEntity;
    private final boolean farmerKing;
    private final boolean hidden;
    private final String teamName;
    private final String entityName;
    private final int attack;
    private final int defense;

    /**
     * Creates a new entity snapshot for a unit.
     *
     * @param unit the unit
     * @param teamName the team name
     */
    public EntitySnapshot(Unit unit, String teamName) {
        this(true, false, !unit.isRevealed(), teamName, unit.getName().toString(), unit.getAtk(), unit.getDef());
    }

    /**
     * Creates a new entity snapshot for a farmer king.
     *
     * @param farmerKing the farmer king
     * @param teamName the team name
     */
    public EntitySnapshot(FarmerKing farmerKing, String teamName) {
        this(true, true, !farmerKing.isRevealed(), teamName, farmerKing.getName().toString(),
                NON_COMBAT_STAT, NON_COMBAT_STAT);
    }

    /**
     * Creates a new entity snapshot for a board entity.
     *
     * @param entity the entity
     * @param teamName the team name
     * @param isKing whether the entity is a farmer king
     * @param hidden whether the entity is hidden
     */
    public EntitySnapshot(BoardEntity entity, String teamName, boolean isKing, boolean hidden) {
        this(true,
                isKing,
                hidden,
                teamName,
                entity.getName().toString(),
                !isKing ? ((Unit) entity).getAtk() : NON_COMBAT_STAT,
                !isKing ? ((Unit) entity).getDef() : NON_COMBAT_STAT);
    }

    private EntitySnapshot(boolean hasEntity, boolean farmerKing, boolean hidden, String teamName, String entityName,
            int attack, int defense) {
        this.hasEntity = hasEntity;
        this.farmerKing = farmerKing;
        this.hidden = hidden;
        this.teamName = teamName;
        this.entityName = entityName;
        this.attack = attack;
        this.defense = defense;
    }

    private EntitySnapshot() {
        this.hasEntity = false;
        this.farmerKing = false;
        this.hidden = false;
        this.teamName = null;
        this.entityName = null;
        this.attack = NON_COMBAT_STAT;
        this.defense = NON_COMBAT_STAT;
    }

    /**
     * Returns a shared snapshot without an entity.
     *
     * @return the empty entity snapshot
     */
    public static EntitySnapshot noUnit() {
        return NO_UNIT;
    }

    /**
     * Returns whether an entity is present.
     *
     * @return {@code true} if an entity is present
     */
    public boolean hasEntity() {
        return hasEntity;
    }

    /**
     * Returns whether the entity is a farmer king.
     *
     * @return {@code true} if the entity is a farmer king
     */
    public boolean isFarmerKing() {
        return farmerKing;
    }

    /**
     * Returns whether the entity is hidden.
     *
     * @return {@code true} if the entity is hidden
     */
    public boolean isHidden() {
        return hidden;
    }

    /**
     * Returns the team name.
     *
     * @return the team name, or {@code null} if no entity is present
     */
    public String getTeamName() {
        return teamName;
    }

    /**
     * Returns the entity name.
     *
     * @return the entity name, or {@code null} if no entity is present
     */
    public String getEntityName() {
        return entityName;
    }

    /**
     * Returns the attack value.
     *
     * @return the attack value
     */
    public int getAttack() {
        return attack;
    }

    /**
     * Returns the defense value.
     *
     * @return the defense value
     */
    public int getDefense() {
        return defense;
    }
}