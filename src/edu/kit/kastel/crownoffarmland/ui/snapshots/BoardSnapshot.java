package edu.kit.kastel.crownoffarmland.ui.snapshots;


import edu.kit.kastel.crownoffarmland.model.board.Position;

/**
 * Immutable snapshot of the complete board for rendering purposes.
 *
 * @author ucgdi
 */
public final class BoardSnapshot {
    private static final int FIRST_INDEX = 0;

    private final BoardCellSnapshot[][] cells;
    private final Position selected;

    /**
     * Creates a new immutable board snapshot.
     *
     * @param cells    the cell snapshots of the board
     * @param selected the currently selected position, or null if no field is selected
     */
    public BoardSnapshot(BoardCellSnapshot[][] cells, Position selected) {
        this.cells = copyCells(cells);
        this.selected = selected;
    }

    /**
     * Returns the size of the board.
     *
     * @return the board size
     */
    public int getBoardSize() {
        return cells.length;
    }

    /**
     * Returns the snapshot of the cell at the given indices.
     *
     * @param rowIndex    the row index
     * @param columnIndex the column index
     * @return the cell snapshot at the given position
     */
    public BoardCellSnapshot getCell(int rowIndex, int columnIndex) {
        return cells[rowIndex][columnIndex];
    }

    /**
     * Returns the currently selected position, or null if no field is selected.
     *
     * @return the selected position or null
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

