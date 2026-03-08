package edu.kit.kastel.crownoffarmland.ui.snapshots;

import edu.kit.kastel.crownoffarmland.model.units.BoardEntity;
import edu.kit.kastel.crownoffarmland.model.units.Unit;

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

    public EntitySnapshot(Unit unit, String teamName) {
        this(true, false, unit.isRevealed(), teamName, unit.getName().toString(), unit.getAtk(), unit.getDef());
    }

    public EntitySnapshot(BoardEntity entity, String teamName, boolean isKing, boolean isRevealed) {
        this(true,
                isKing,
                isRevealed,
                teamName,
                entity.getName().toString(),
                !isKing ? ((Unit) entity).getAtk() : 0,
                !isKing ? ((Unit) entity).getDef() : 0);
    }



    public static EntitySnapshot noUnit() {
        return NO_UNIT;
    }

    public boolean hasEntity() {
        return hasEntity;
    }

    public boolean isFarmerKing() {
        return farmerKing;
    }

    public boolean isHidden() {
        return hidden;
    }

    public String getTeamName() {
        return teamName;
    }

    public String getEntityName() {
        return entityName;
    }

    public int getAttack() {
        return attack;
    }

    public int getDefense() {
        return defense;
    }
}
