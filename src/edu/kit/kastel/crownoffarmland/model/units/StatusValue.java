package edu.kit.kastel.crownoffarmland.model.units;

/**
 * Represents the attack and defense values of a unit.
 * Negative values are stored as {@code 0}.
 *
 * @author ucgdi
 */
public class StatusValue {
    private final int def;
    private final int atk;

    /**
     * Constructs status values with the given attack and defense.
     *
     * @param atk the attack value
     * @param def the defense value
     */
    public StatusValue(int atk, int def) {
        this.def = Math.max(def, 0);
        this.atk = Math.max(atk, 0);
    }

    /**
     * Returns the defense value.
     *
     * @return the defense value
     */
    public int getDef() {
        return def;
    }

    /**
     * Returns the attack value.
     *
     * @return the attack value
     */
    public int getAtk() {
        return atk;
    }
}