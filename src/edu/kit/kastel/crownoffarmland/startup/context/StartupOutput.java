package edu.kit.kastel.crownoffarmland.startup.context;

import edu.kit.kastel.crownoffarmland.startup.config.Verbosity;
import edu.kit.kastel.crownoffarmland.ui.renderer.board.boardsymbols.BoardSymbolSet;

/**
 * Stores startup output configuration.
 *
 * @author ucgdi
 */
public final class StartupOutput {
    private final BoardSymbolSet boardSymbolSet;
    private final Verbosity verbosity;

    private StartupOutput(BoardSymbolSet boardSymbolSet, Verbosity verbosity) {
        this.boardSymbolSet = boardSymbolSet;
        this.verbosity = verbosity;
    }

    /**
     * Returns an empty output configuration.
     *
     * @return the empty output configuration
     */
    public static StartupOutput empty() {
        return new StartupOutput(null, null);
    }

    /**
     * Returns a new output configuration.
     *
     * @param boardSymbolSet the board symbol set
     * @param verbosity the verbosity
     * @return the output configuration
     */
    public static StartupOutput of(BoardSymbolSet boardSymbolSet, Verbosity verbosity) {
        return new StartupOutput(boardSymbolSet, verbosity);
    }

    /**
     * Returns the board symbol set.
     *
     * @return the board symbol set
     */
    public BoardSymbolSet getBoardSymbolSet() {
        return boardSymbolSet;
    }

    /**
     * Returns the verbosity.
     *
     * @return the verbosity
     */
    public Verbosity getVerbosity() {
        return verbosity;
    }

    /**
     * Returns a copy with the given board symbol set.
     *
     * @param newBoardSymbolSet the board symbol set
     * @return the updated output configuration
     */
    public StartupOutput withBoardSymbolSet(BoardSymbolSet newBoardSymbolSet) {
        return new StartupOutput(newBoardSymbolSet, verbosity);
    }

    /**
     * Returns a copy with the given verbosity.
     *
     * @param newVerbosity the verbosity
     * @return the updated output configuration
     */
    public StartupOutput withVerbosity(Verbosity newVerbosity) {
        return new StartupOutput(boardSymbolSet, newVerbosity);
    }
}