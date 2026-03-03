package edu.kit.kastel.crownoffarmland.model.units;

/**
 * This class represents the status values of a unit, such as attack and defense.
 * It ensures that the values are non-negative and provides getter methods to access them.
 *
 * @author ucgdi
 */
public class StatusValue {
    private final int def;
    private final int atk;


    /**
     * Constructor for creating a StatusValue object with the specified attack and defense values.
     * @param atk the attack value of the unit, which will be set to 0 if a negative value is provided
     * @param def the defense value of the unit, which will be set to 0 if a negative value is provided
     */
    public StatusValue(int atk, int def) {
        this.def = Math.max(def, 0);
        this.atk = Math.max(atk, 0);
    }

    /**
     * Returns the defense value of the unit.
     * @return the defense value
     */
    public int getDef() {
        return def;
    }

    /**
     * Returns the attack value of the unit.
     * @return the attack value
     */
    public int getAtk() {
        return atk;
    }
}