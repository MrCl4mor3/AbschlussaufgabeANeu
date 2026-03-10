package edu.kit.kastel.crownoffarmland.gameplay.snapshots.movesnapshot;

/**
 * This enum represents the different types of moves that can be performed in the game. It is used to categorize move snapshots and
 * provide information about the type of move that was performed.
 *
 * @author ucgdi
 */
public enum MoveType {
    /**
     * Represents a simple move, where an entity moves from one position to another without any special interactions or consequences.
     */
    SIMPLE,
    /**
     * Represents a merge move, where an entity attempts to merge with another entity at the target position. The success of the merge
     * may depend on various factors, such as the types of entities involved and their current states.
     */
    MERGE,
    /**
     * Represents a duel move, where an entity engages in combat with another entity at the target position. The outcome of the duel may
     * result in damage to one or both entities, and may also affect their positions on the game board.
     */
    DUEL;
}
