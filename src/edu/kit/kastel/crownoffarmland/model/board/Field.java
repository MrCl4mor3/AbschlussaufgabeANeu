package edu.kit.kastel.crownoffarmland.model.board;


import edu.kit.kastel.crownoffarmland.model.units.BoardEntity;


/**
 * Represents a single field on the game board. It can be occupied by a BoardEntity (Unit or FarmerKing) or be empty.
 * Each field has a fixed position on the board, represented by a Position object.
 *
 * @author ucgdi
 */
public class Field {
    private final Position position;
    private BoardEntity occupant;


    /**
     * Constructs a new Field with the specified position. The field is initialized as empty (no occupant).
     * @param position the Position object representing the location of this field on the game board. The position is fixed and cannot be
     *               changed after construction.
     */
    public Field(Position position) {
        this.position = position;
    }

    /**
     * Returns the BoardEntity currently occupying this field, or null if the field is empty.
     * @return the BoardEntity occupying this field, or null if empty
     */
    public BoardEntity getOccupant() {
        return occupant;
    }

    /**
     * Sets the BoardEntity occupying this field. This can be a Unit, FarmerKing, or null to indicate the field is empty.
     * @param occupant the BoardEntity to occupy this field, or null to clear the occupant
     */
    public void setOccupant(BoardEntity occupant) {
        this.occupant = occupant;
    }

    /**
     * Checks if the field is currently empty (i.e., has no occupant).
     * @return true if the field is empty, false if it is occupied by a BoardEntity
     */
    public boolean isEmpty() {
        return occupant == null;
    }

    /**
     * Returns the fixed position of this field on the game board.
     * @return the Position object representing this field's location on the board
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Clears the occupant of this field, setting it to null. Returns the previous occupant that was cleared.
     * @return the BoardEntity that was previously occupying this field, or null if it was already empty
     */
    public BoardEntity clearOccupant() {
        BoardEntity old = occupant;
        occupant = null;
        return old;
    }
}