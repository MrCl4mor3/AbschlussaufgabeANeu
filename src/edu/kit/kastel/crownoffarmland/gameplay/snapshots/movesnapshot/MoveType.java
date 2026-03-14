package edu.kit.kastel.crownoffarmland.gameplay.snapshots.movesnapshot;

/**
 * Defines the possible types of moves.
 *
 * @author ucgdi
 */
public enum MoveType {
    /**
     * A regular move.
     */
    SIMPLE,

    /**
     * A move that merges units.
     */
    MERGE,

    /**
     * A move that starts a duel.
     */
    DUEL
}