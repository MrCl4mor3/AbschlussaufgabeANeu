package edu.kit.kastel.crownoffarmland.model.units;

/**
 * The UnitName class represents the name of a unit in the game. It consists of a qualificator and a role.
 * The qualificator describes the type or category of the unit, while the role describes its function or purpose.
 * For example, a unit with the qualificator "Elite" and the role "Warrior" would be represented as "Elite Warrior".
 *
 * @author ucgdi
 */
public class UnitName {
    private static final String REPRESENTATION_FORMAT = "%s %s";
    private final String qualificator;
    private final String role;

    /**
     * Constructs a new UnitName with the specified qualificator and role. The constructor initializes the fields of the UnitName based
     * on the provided parameters. The qualificator describes the type or category of the unit, while the role describes its function or
     * purpose. This class is immutable, meaning that once an instance is created, its state cannot be changed. The UnitName can be used
     * to identify and differentiate between different types of units in the game, providing a clear and descriptive name for each unit
     * based on its characteristics and role within the game.
     * @param qualificator the qualificator describing the type or category of the unit. The qualificator is a key component of the
     *                     UnitName, as it helps to define the identity of the unit by providing information about its type or category.
     *                     The qualificator can include information such as whether the unit is a standard unit, an elite unit, a support
     *                     unit, or any other relevant classification that helps to differentiate between different types of units in the
     *                     game. The qualificator is used in conjunction with the role to create a clear and descriptive name for the
     *                     unit, allowing players to easily identify and understand the characteristics and capabilities of the unit
     *                     based on its name. The qualificator can also influence the unit's stats, abilities, and overall performance in
     *                     the game, making it an important aspect of the unit's identity and role within the game.
     * @param role the role describing the function or purpose of the unit. The role is a key component of the UnitName, as it helps to
     *             define the identity of the unit by providing information about its function or purpose within the game. The role can
     *             include information such as whether the unit is a warrior, a defender, a healer, a scout, or any other relevant
     *             classification that helps to differentiate between different types of units based on their function or purpose in the
     *             game. The role is used in conjunction with the qualificator to create a clear and descriptive name for the unit,
     *             allowing players to easily identify and understand the characteristic ...
     */
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
        return String.format(REPRESENTATION_FORMAT, qualificator, role);
    }
}