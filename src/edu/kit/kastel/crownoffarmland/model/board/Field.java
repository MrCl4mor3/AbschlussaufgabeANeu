package edu.kit.kastel.crownoffarmland.model.board;


import edu.kit.kastel.crownoffarmland.model.units.BoardEntity;

public class Field {
    private final Position position;
    private BoardEntity occupant;


    public Field(Position position) {
        this.position = position;
    }

    public BoardEntity getOccupant() {
        return occupant;
    }

    public void setOccupant(BoardEntity occupant) {
        this.occupant = occupant;
    }

    public boolean isEmpty() {
        return occupant == null;
    }

    public Position getPosition() {
        return position;
    }

    public BoardEntity clearOccupant() {
        BoardEntity old = occupant;
        occupant = null;
        return old;
    }
}