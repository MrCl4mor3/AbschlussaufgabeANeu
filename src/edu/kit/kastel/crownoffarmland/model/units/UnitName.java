package edu.kit.kastel.crownoffarmland.model.units;

/**
 * The UnitName class represents the name of a unit in the game. It consists of a qualificator and a role.
 * The qualificator describes the type or category of the unit, while the role describes its function or purpose.
 * For example, a unit with the qualificator "Elite" and the role "Warrior" would be represented as "Elite Warrior".
 *
 * @author ucgdi
 */
public class UnitName {
    private final static String RepresentationFormat = "%s %s";
    private final String qualificator;
    private final String role;


    public UnitName(String qualificator, String role) {
        this.qualificator = qualificator;
        this.role = role;
    }

    /**
     * Returns the qualificator of the unit name.
     * @return the qualificator of the unit name
     */
    public  String getQualificator() {
        return qualificator;
    }

    /**
     * Returns the role of the unit name.
     * @return the role of the unit name
     */
    public String getRole() {
        return  role;
    }


    /**
     * Returns a string representation of the unit name in the format "qualificator role".
     * @return a string representation of the unit name
     */
    @Override
    public String toString() {
        return String.format(RepresentationFormat, qualificator, role);
    }
}