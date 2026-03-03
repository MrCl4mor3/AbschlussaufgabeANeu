package edu.kit.kastel.crownoffarmland.model.board;



public class Position {
    private final int row;
    private final char column;

    public Position(int row, char column) {
        this.row = row;
        this.column = column;
    }

    public char getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }
}
