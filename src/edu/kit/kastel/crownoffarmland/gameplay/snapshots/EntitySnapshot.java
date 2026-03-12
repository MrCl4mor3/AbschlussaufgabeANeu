package edu.kit.kastel.crownoffarmland.gameplay.snapshots;

import edu.kit.kastel.crownoffarmland.model.units.BoardEntity;
import edu.kit.kastel.crownoffarmland.model.units.FarmerKing;
import edu.kit.kastel.crownoffarmland.model.units.Unit;

/**
 * Immutable snapshot of a single entity for rendering purposes.
 *
 * @author ucgdi
 */
public final class EntitySnapshot {
    private static final EntitySnapshot NO_UNIT = new EntitySnapshot(false, false, false, null, null, 0, 0);

    private final boolean hasEntity;
    private final boolean farmerKing;
    private final boolean hidden;
    private final String teamName;
    private final String entityName;
    private final int attack;
    private final int defense;




    private EntitySnapshot(boolean hasEntity, boolean farmerKing, boolean hidden, String teamName, String entityName, int attack,
                        int defense) {
        this.hasEntity = hasEntity;
        this.farmerKing = farmerKing;
        this.hidden = hidden;
        this.teamName = teamName;
        this.entityName = entityName;
        this.attack = attack;
        this.defense = defense;
    }

    /**
     * Creates a new immutable entity snapshot for the given unit.
     * @param unit the unit to create the snapshot for
     * @param teamName the name of the team the unit belongs to
     */
    public EntitySnapshot(Unit unit, String teamName) {
        this(true, false, !unit.isRevealed(), teamName, unit.getName().toString(), unit.getAtk(), unit.getDef());
    }

    /**
     * Creates a new immutable entity snapshot for the given farmer king.
     * @param farmerKing the farmer king to create the snapshot for
     * @param teamName the name of the team the farmer king belongs to
     */
    public EntitySnapshot(FarmerKing farmerKing, String teamName) {
        this(true, true, !farmerKing.isRevealed(), teamName, farmerKing.getName().toString(), 0, 0);
    }
    /**
     * Creates a new immutable entity snapshot for the given entity.
     * @param entity the entity to create the snapshot for
     * @param teamName the name of the team the entity belongs to
     * @param isKing true if the entity is a farmer king, false otherwise
     * @param isRevealed true if the entity is revealed, false if it is hidden
     */
    public EntitySnapshot(BoardEntity entity, String teamName, boolean isKing, boolean isRevealed) {
        this(true,
                isKing,
                !isRevealed,
                teamName,
                entity.getName().toString(),
                !isKing ? ((Unit) entity).getAtk() : 0,
                !isKing ? ((Unit) entity).getDef() : 0);
    }


    /**
     * Returns a shared snapshot representing the absence of an entity.
     * @return the no-unit snapshot
     */
    public static EntitySnapshot noUnit() {
        return NO_UNIT;
    }

    /**
     * Returns whether this snapshot represents an entity.
     * @return true if this snapshot represents an entity, false if it represents the absence of an entity
     */
    public boolean hasEntity() {
        return hasEntity;
    }

    /**
     * Returns whether the entity represented by this snapshot is a farmer king.
     * @return true if the entity is a farmer king, false otherwise
     */
    public boolean isFarmerKing() {
        return farmerKing;
    }

    /**
     * Returns whether the entity represented by this snapshot is hidden.
     * @return true if the entity is hidden, false if it is revealed
     */
    public boolean isHidden() {
        return hidden;
    }

    /**
     * Returns the name of the team the entity represented by this snapshot belongs to.
     * @return the name of the team, or null if this snapshot represents the absence of an entity
     */
    public String getTeamName() {
        return teamName;
    }

    /**
     * Returns the name of the entity represented by this snapshot.
     * @return the name of the entity, or null if this snapshot represents the absence of an entity
     */
    public String getEntityName() {
        return entityName;
    }

    /**
     * Returns the attack value of the entity represented by this snapshot.
     * @return the attack value, or 0 if this snapshot represents the absence of an entity or a farmer king
     */
    public int getAttack() {
        return attack;
    }

    /**
     * Returns the defense value of the entity represented by this snapshot.
     * @return the defense value, or 0 if this snapshot represents the absence of an entity or a farmer king
     */
    public int getDefense() {
        return defense;
    }
}
