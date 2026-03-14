package edu.kit.kastel.crownoffarmland.gameplay.snapshots.boardsnapshot;

import edu.kit.kastel.crownoffarmland.model.board.Position;

/**
 * Represents a snapshot of the board.
 *
 * @author ucgdi
 */
public final class BoardSnapshot {
    private static final int FIRST_INDEX = 0;

    private final BoardCellSnapshot[][] cells;
    private final Position selected;

    /**
     * Creates a new board snapshot.
     *
     * @param cells the cell snapshots
     * @param selected the selected position, or {@code null} if none is selected
     */
    public BoardSnapshot(BoardCellSnapshot[][] cells, Position selected) {
        this.cells = copyCells(cells);
        this.selected = selected;
    }

    /**
     * Returns the board size.
     *
     * @return the board size
     */
    public int getBoardSize() {
        return cells.length;
    }

    /**
     * Returns the cell snapshot at the given position.
     *
     * @param rowIndex the row index
     * @param columnIndex the column index
     * @return the cell snapshot
     */
    public BoardCellSnapshot getCell(int rowIndex, int columnIndex) {
        return cells[rowIndex][columnIndex];
    }

    /**
     * Returns the selected position.
     *
     * @return the selected position, or {@code null} if none is selected
     */
    public Position getSelected() {
        return selected;
    }

    private static BoardCellSnapshot[][] copyCells(BoardCellSnapshot[][] source) {
        BoardCellSnapshot[][] copy = new BoardCellSnapshot[source.length][];

        for (int rowIndex = FIRST_INDEX; rowIndex < source.length; rowIndex++) {
            copy[rowIndex] = new BoardCellSnapshot[source[rowIndex].length];
            System.arraycopy(source[rowIndex], FIRST_INDEX, copy[rowIndex], FIRST_INDEX, source[rowIndex].length);
        }

        return copy;
    }
}