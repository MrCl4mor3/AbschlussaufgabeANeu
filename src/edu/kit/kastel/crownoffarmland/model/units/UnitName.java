package edu.kit.kastel.crownoffarmland.model.units;

public class UnitName {
    private final static String RepresentationFormat = "%s %s";
    private final String qualificator;
    private final String role;


    public UnitName(String qualificator, String role) {
        this.qualificator = qualificator;
        this.role = role;
    }

    public  String getQualificator() {
        return qualificator;
    }
    public String getRole() {
        return  role;
    }


    @Override
    public String toString() {
        return String.format(RepresentationFormat, qualificator, role);
    }
}