package edu.kit.kastel.crownoffarmland.model.units;

/**
 * The UnitTemplate class represents a template for creating units in the game. It contains a UnitName and a StatusValue.
 * The UnitName represents the name of the unit, while the StatusValue represents the stats of the unit.
 * This class is immutable, meaning that once an instance is created, its state cannot be changed.
 *
 * @author ucgdi
 */
public final class UnitTemplate {
    private final UnitName unit;
    private final StatusValue stats;

    public UnitTemplate(UnitName unitName, StatusValue stats) {
        this.unit = unitName;
        this.stats = stats;
    }
}