package edu.kit.kastel.crownoffarmland.model.units;

public class StatusValue {
    private final int def;
    private final int atk;


    public StatusValue(int atk, int def) {
        this.def = Math.max(def, 0);
        this.atk = Math.max(atk, 0);
    }

    public int getDef() {
        return def;
    }

    public int getAtk() {
        return atk;
    }
}