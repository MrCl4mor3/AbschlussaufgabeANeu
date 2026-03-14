package edu.kit.kastel.crownoffarmland.model.board;

import edu.kit.kastel.crownoffarmland.model.units.BoardEntity;

/**
 * Represents a single field on the game board.
 * A field has a fixed position and may be occupied by a board entity.
 *
 * @author ucgdi
 */
public class Field {
    private final Position position;
    private BoardEntity occupant;

    /**
     * Constructs a field at the given position.
     *
     * @param position the field position
     */
    public Field(Position position) {
        this.position = position;
    }

    /**
     * Returns the occupant of this field.
     *
     * @return the occupying entity, or {@code null} if the field is empty
     */
    public BoardEntity getOccupant() {
        return occupant;
    }

    /**
     * Sets the occupant of this field.
     *
     * @param occupant the new occupant, or {@code null} to clear the field
     */
    public void setOccupant(BoardEntity occupant) {
        this.occupant = occupant;
    }

    /**
     * Checks whether this field is empty.
     *
     * @return {@code true} if the field has no occupant, otherwise {@code false}
     */
    public boolean isEmpty() {
        return occupant == null;
    }

    /**
     * Returns the position of this field.
     *
     * @return the field position
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Clears the occupant of this field.
     *
     * @return the previous occupant, or {@code null} if the field was empty
     */
    public BoardEntity clearOccupant() {
        BoardEntity old = occupant;
        occupant = null;
        return old;
    }
}