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

    /**
     * Constructs a new UnitTemplate with the specified UnitName and StatusValue. The constructor initializes the fields of the UnitTemplate
     * based on the provided parameters. The UnitName represents the name of the unit, while the StatusValue represents the stats of the
     * unit. This class is immutable, meaning that once an instance is created, its state cannot be changed. The UnitTemplate can be used
     * as a blueprint for creating units in the game, allowing for consistent and standardized unit creation based on predefined templates.
     * @param unitName the UnitName representing the name of the unit that this template will create. The UnitName is a key component of
     *                 the UnitTemplate, as it defines the identity of the unit that will be created based on this template. The UnitName
     *                 can include information such as the type of unit, its role, or any other relevant characteristics that help to
     *                 define the unit's identity within the game. The UnitName is used to differentiate between different types of units
     *                 and to provide a clear and descriptive name for the unit that will be created based on this template.
     * @param stats the StatusValue representing the stats of the unit that this template will create. The StatusValue is a key component
     *             of the UnitTemplate, as it defines the attributes and capabilities of the unit that will be created based on this
     *              template. The StatusValue can include information such as the unit's health, attack power, defense, speed, or any
     *              other relevant stats that help to define the unit's performance and effectiveness in the game. The StatusValue is
     *              used to determine how the unit will perform in combat, how it will interact with other units, and how it will
     *              contribute to the overall strategy of the player's team. The stats defined in the StatusValue can influence the
     *              unit's strengths and weaknesses, as well as its role and utility within the game.
     */
    public UnitTemplate(UnitName unitName, StatusValue stats) {
        this.unit = unitName;
        this.stats = stats;
    }

    /**
     * Returns the StatusValue representing the stats of the unit that this template will create. The StatusValue is a key component of the
     * UnitTemplate, as it defines the attributes and capabilities of the unit that will be created based on this template. The
     * StatusValue can include information such as the unit's health.
     * @return wewdwd
     */
    public StatusValue getStats() {
        return stats;
    }

    /**
     * winwnd.
     * @return wdwodmowd
     */
    public UnitName getName() {
        return unit;
    }
}