package edu.kit.kastel.crownoffarmland.model.units;

/**
 * Represents a template for creating units.
 * A unit template consists of a unit name and its stats.
 *
 * @author ucgdi
 */
public final class UnitTemplate {
    private final UnitName unit;
    private final StatusValue stats;

    /**
     * Constructs a unit template with the given name and stats.
     *
     * @param unitName the unit name
     * @param stats the unit stats
     */
    public UnitTemplate(UnitName unitName, StatusValue stats) {
        this.unit = unitName;
        this.stats = stats;
    }

    /**
     * Returns the stats of this unit template.
     *
     * @return the unit stats
     */
    public StatusValue getStats() {
        return stats;
    }

    /**
     * Returns the name of this unit template.
     *
     * @return the unit name
     */
    public UnitName getName() {
        return unit;
    }
}