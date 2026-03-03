package edu.kit.kastel.crownoffarmland.model.units;

public final class UnitTemplate {
    private final UnitName unit;
    private final StatusValue stats;

    public UnitTemplate(UnitName unitName, StatusValue stats) {
        this.unit = unitName;
        this.stats = stats;
    }
}