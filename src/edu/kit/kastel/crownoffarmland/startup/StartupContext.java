package edu.kit.kastel.crownoffarmland.startup;

import edu.kit.kastel.crownoffarmland.model.RandomGenerator;
import edu.kit.kastel.crownoffarmland.model.units.UnitTemplate;
import edu.kit.kastel.crownoffarmland.startup.config.Verbosity;
import edu.kit.kastel.crownoffarmland.ui.renderer.board.boardsymbols.BoardSymbolSet;

import java.util.Arrays;
import java.util.List;

/**
 * Immutable container for all validated startup data.
 *
 * @author ucgdi
 */
public final class StartupContext {
    private final RandomGenerator randomGenerator;
    private final List<UnitTemplate> unitTemplates;
    private final int[] deckCountsTeam1;
    private final int[] deckCountsTeam2;
    private final String team1Name;
    private final String team2Name;
    private final BoardSymbolSet boardSymbolSet;
    private final Verbosity verbosity;

    private StartupContext(RandomGenerator randomGenerator, List<UnitTemplate> unitTemplates,
                           int[] deckCountsTeam1, int[] deckCountsTeam2,
                           String team1Name, String team2Name,
                           BoardSymbolSet boardSymbolSet, Verbosity verbosity) {
        this.randomGenerator = randomGenerator;
        this.unitTemplates = unitTemplates == null ? null : List.copyOf(unitTemplates);
        this.deckCountsTeam1 = copyArray(deckCountsTeam1);
        this.deckCountsTeam2 = copyArray(deckCountsTeam2);
        this.team1Name = team1Name;
        this.team2Name = team2Name;
        this.boardSymbolSet = boardSymbolSet;
        this.verbosity = verbosity;
    }

    /**
     * Returns an empty startup context.
     *
     * @return empty startup context
     */
    public static StartupContext empty() {
        return new StartupContext(null, null, null, null, null, null, null, null);
    }

    /**
     * Returns the configured random generator.
     *
     * @return random generator
     */
    public RandomGenerator getRandomGenerator() {
        return randomGenerator;
    }

    /**
     * Returns the configured unit templates.
     *
     * @return immutable list of unit templates
     */
    public List<UnitTemplate> getUnitTemplates() {
        return unitTemplates;
    }

    /**
     * Returns the configured deck counts for team 1.
     *
     * @return copy of deck counts for team 1
     */
    public int[] getDeckCountsTeam1() {
        return copyArray(deckCountsTeam1);
    }

    /**
     * Returns the configured deck counts for team 2.
     *
     * @return copy of deck counts for team 2
     */
    public int[] getDeckCountsTeam2() {
        return copyArray(deckCountsTeam2);
    }

    /**
     * Returns the configured name of team 1.
     *
     * @return team 1 name
     */
    public String getTeam1Name() {
        return team1Name;
    }

    /**
     * Returns the configured name of team 2.
     *
     * @return team 2 name
     */
    public String getTeam2Name() {
        return team2Name;
    }

    /**
     * Returns the configured board symbol set.
     *
     * @return board symbol set
     */
    public BoardSymbolSet getBoardSymbolSet() {
        return boardSymbolSet;
    }

    /**
     * Returns the configured verbosity.
     *
     * @return verbosity
     */
    public Verbosity getVerbosity() {
        return verbosity;
    }

    /**
     * Returns a copy of this context with the given random generator.
     *
     * @param newRandomGenerator the new random generator
     * @return updated startup context
     */
    public StartupContext withRandomGenerator(RandomGenerator newRandomGenerator) {
        return new StartupContext(newRandomGenerator, unitTemplates, deckCountsTeam1, deckCountsTeam2,
                team1Name, team2Name, boardSymbolSet, verbosity);
    }

    /**
     * Returns a copy of this context with the given unit templates.
     *
     * @param newUnitTemplates the new unit templates
     * @return updated startup context
     */
    public StartupContext withUnitTemplates(List<UnitTemplate> newUnitTemplates) {
        return new StartupContext(randomGenerator, newUnitTemplates, deckCountsTeam1, deckCountsTeam2,
                team1Name, team2Name, boardSymbolSet, verbosity);
    }

    /**
     * Returns a copy of this context with the given deck counts for team 1.
     *
     * @param newDeckCountsTeam1 the new deck counts for team 1
     * @return updated startup context
     */
    public StartupContext withDeckCountsTeam1(int[] newDeckCountsTeam1) {
        return new StartupContext(randomGenerator, unitTemplates, newDeckCountsTeam1, deckCountsTeam2,
                team1Name, team2Name, boardSymbolSet, verbosity);
    }

    /**
     * Returns a copy of this context with the given deck counts for team 2.
     *
     * @param newDeckCountsTeam2 the new deck counts for team 2
     * @return updated startup context
     */
    public StartupContext withDeckCountsTeam2(int[] newDeckCountsTeam2) {
        return new StartupContext(randomGenerator, unitTemplates, deckCountsTeam1, newDeckCountsTeam2,
                team1Name, team2Name, boardSymbolSet, verbosity);
    }

    /**
     * Returns a copy of this context with the given team 1 name.
     *
     * @param newTeam1Name the new team 1 name
     * @return updated startup context
     */
    public StartupContext withTeam1Name(String newTeam1Name) {
        return new StartupContext(randomGenerator, unitTemplates, deckCountsTeam1, deckCountsTeam2,
                newTeam1Name, team2Name, boardSymbolSet, verbosity);
    }

    /**
     * Returns a copy of this context with the given team 2 name.
     *
     * @param newTeam2Name the new team 2 name
     * @return updated startup context
     */
    public StartupContext withTeam2Name(String newTeam2Name) {
        return new StartupContext(randomGenerator, unitTemplates, deckCountsTeam1, deckCountsTeam2,
                team1Name, newTeam2Name, boardSymbolSet, verbosity);
    }

    /**
     * Returns a copy of this context with the given board symbol set.
     *
     * @param newBoardSymbolSet the new board symbol set
     * @return updated startup context
     */
    public StartupContext withBoardSymbolSet(BoardSymbolSet newBoardSymbolSet) {
        return new StartupContext(randomGenerator, unitTemplates, deckCountsTeam1, deckCountsTeam2,
                team1Name, team2Name, newBoardSymbolSet, verbosity);
    }

    /**
     * Returns a copy of this context with the given verbosity.
     *
     * @param newVerbosity the new verbosity
     * @return updated startup context
     */
    public StartupContext withVerbosity(Verbosity newVerbosity) {
        return new StartupContext(randomGenerator, unitTemplates, deckCountsTeam1, deckCountsTeam2,
                team1Name, team2Name, boardSymbolSet, newVerbosity);
    }

    private static int[] copyArray(int[] source) {
        return source == null ? null : Arrays.copyOf(source, source.length);
    }
}