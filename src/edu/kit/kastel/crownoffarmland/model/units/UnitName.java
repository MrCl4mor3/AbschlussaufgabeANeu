package edu.kit.kastel.crownoffarmland.model.units;

/**
 * Represents the name of a unit.
 * A unit name consists of a qualificator and a role.
 *
 * @author ucgdi
 */
public class UnitName {
    private static final String REPRESENTATION_FORMAT = "%s %s";
    private final String qualificator;
    private final String role;

    /**
     * Constructs a unit name with the given qualificator and role.
     *
     * @param qualificator the unit qualificator
     * @param role the unit role
     */
    public UnitName(String qualificator, String role) {
        this.qualificator = qualificator;
        this.role = role;
    }

    /**
     * Returns the qualificator of this unit name.
     *
     * @return the qualificator
     */
    public String getQualificator() {
        return qualificator;
    }

    /**
     * Returns the role of this unit name.
     *
     * @return the role
     */
    public String getRole() {
        return role;
    }

    /**
     * Returns this unit name as a string.
     *
     * @return the string representation of this unit name
     */
    @Override
    public String toString() {
        return String.format(REPRESENTATION_FORMAT, qualificator, role);
    }
}