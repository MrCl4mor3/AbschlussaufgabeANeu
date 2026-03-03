package edu.kit.kastel.crownoffarmland.gameplay.combat;

/**
 * The DuelType enum represents the different types of duels that can occur between units in the game. It defines three possible values:
 * KING, BLOCKADE, and STANDARD. Each duel type represents a specific combat scenario with its own rules and outcomes. The KING duel type
 * may involve a powerful unit or leader, while the BLOCKADE duel type may involve defensive tactics and fortifications. The STANDARD duel
 * type represents a typical combat scenario between two units without any special conditions. The DuelType enum is used to categorize and
 * differentiate between the various combat scenarios that can occur in the game, allowing for appropriate handling of combat outcomes
 * based on the specific type of duel that takes place.
 *
 * @author ucgdi
 */
public enum DuelType {
    /**
     * Represents a duel scenario involving a FarmerKing unit.
     */
    KING,
    /**
     * Represents a duel scenario involving a unit that is blockading state.
     */
    BlOCKADE,
    /**
     * Represents a standard duel scenario between two units without any special conditions.
     */
    STANDARD;
}
