package edu.kit.kastel.crownoffarmland.startup;

import edu.kit.kastel.crownoffarmland.model.RandomGenerator;
import edu.kit.kastel.crownoffarmland.model.units.UnitTemplate;
import edu.kit.kastel.crownoffarmland.startup.config.DeckConfigMode;
import edu.kit.kastel.crownoffarmland.startup.config.Verbosity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Immutable container that holds all values collected during the startup phase.
 * <p>
 * Instances are created step by step by the {@link StartupLoader}. Each {@code with...} method returns a new
 * {@code StartupContext} instance with one value changed, leaving the original instance unchanged.
 * <p>
 * The class applies defensive copying for mutable data (lists and arrays) to preserve immutability.
 *
 * @author ucgdi
 */
public final class StartupContext {

    private final long seed;
    private final RandomGenerator rng;
    private final String boardSymbols;
    private final List<UnitTemplate> unitTemplates;
    private final DeckConfigMode deckMode;
    private final int[] deckCountsTeam1;
    private final int[] deckCountsTeam2;
    private final String team1Name;
    private final String team2Name;
    private final Verbosity verbosity;

    /**
     * Creates a new {@code StartupContext} from the given builder.
     * <p>
     * This constructor is private to enforce immutability and to keep object creation controlled by this class.
     *
     * @param b builder holding the state that will be copied into the new {@code StartupContext}
     */
    private StartupContext(Builder b) {
        this.seed = b.seed;
        this.rng = b.rng;
        this.boardSymbols = b.boardSymbols;

        this.unitTemplates = b.unitTemplates == null
                ? null
                : Collections.unmodifiableList(new ArrayList<UnitTemplate>(b.unitTemplates));

        this.deckMode = b.deckMode;
        this.deckCountsTeam1 = copyArray(b.deckCountsTeam1);
        this.deckCountsTeam2 = copyArray(b.deckCountsTeam2);

        this.team1Name = b.team1Name;
        this.team2Name = b.team2Name;
        this.verbosity = b.verbosity;
    }

    /**
     * Creates an empty {@code StartupContext}.
     * <p>
     * All values are set to their default "unset" state (e.g., {@code null} for references and {@code 0} for primitives).
     * The loader fills the context incrementally using the {@code with...} methods.
     *
     * @return a new empty {@code StartupContext}
     */
    public static StartupContext empty() {
        return new Builder().build();
    }

    /**
     * Creates a defensive copy of an {@code int} array.
     *
     * @param a array to copy (may be {@code null})
     * @return a new copied array or {@code null} if the input was {@code null}
     */
    private static int[] copyArray(int[] a) {
        if (a == null) {
            return null;
        }
        int[] c = new int[a.length];
        for (int i = 0; i < a.length; i++) {
            c[i] = a[i];
        }
        return c;
    }

    /**
     * Returns a new context with the given seed and random generator.
     *
     * @param seed the seed used to initialize randomness
     * @param rng the random generator created from the seed
     * @return a new {@code StartupContext} with the updated seed and generator
     */
    public StartupContext withSeed(long seed, RandomGenerator rng) {
        Builder b = new Builder(this);
        b.seed = seed;
        b.rng = rng;
        return b.build();
    }

    /**
     * Returns a new context with the given board symbols.
     * <p>
     * The symbols string may be {@code null} to signal that the default symbol set should be used.
     *
     * @param boardSymbols the board symbols string (may be {@code null})
     * @return a new {@code StartupContext} with updated board symbols
     */
    public StartupContext withBoardSymbols(String boardSymbols) {
        Builder b = new Builder(this);
        b.boardSymbols = boardSymbols;
        return b.build();
    }

    /**
     * Returns a new context with the given unit templates.
     *
     * @param unitTemplates list of parsed unit templates
     * @return a new {@code StartupContext} with updated unit templates
     */
    public StartupContext withUnitTemplates(List<UnitTemplate> unitTemplates) {
        Builder b = new Builder(this);
        b.unitTemplates = unitTemplates;
        return b.build();
    }

    /**
     * Returns a new context for a shared deck configuration.
     * <p>
     * Both teams will use the same deck counts array.
     *
     * @param sharedCounts array of card counts per unit template (shared by both teams)
     * @return a new {@code StartupContext} with shared deck configuration
     */
    public StartupContext withSharedDeck(int[] sharedCounts) {
        Builder b = new Builder(this);
        b.deckMode = DeckConfigMode.SHARED_DECK;
        b.deckCountsTeam1 = sharedCounts;
        b.deckCountsTeam2 = sharedCounts;
        return b.build();
    }

    /**
     * Returns a new context for split deck configuration.
     *
     * @param team1Counts array of card counts per unit template for team 1
     * @param team2Counts array of card counts per unit template for team 2
     * @return a new {@code StartupContext} with split deck configuration
     */
    public StartupContext withSplitDecks(int[] team1Counts, int[] team2Counts) {
        Builder b = new Builder(this);
        b.deckMode = DeckConfigMode.SPLIT_DECKS;
        b.deckCountsTeam1 = team1Counts;
        b.deckCountsTeam2 = team2Counts;
        return b.build();
    }

    /**
     * Returns a new context with the given team names.
     *
     * @param team1Name name of team 1
     * @param team2Name name of team 2
     * @return a new {@code StartupContext} with updated team names
     */
    public StartupContext withTeams(String team1Name, String team2Name) {
        Builder b = new Builder(this);
        b.team1Name = team1Name;
        b.team2Name = team2Name;
        return b.build();
    }

    /**
     * Returns a new context with the given verbosity.
     *
     * @param verbosity the verbosity configuration
     * @return a new {@code StartupContext} with updated verbosity
     */
    public StartupContext withVerbosity(Verbosity verbosity) {
        Builder b = new Builder(this);
        b.verbosity = verbosity;
        return b.build();
    }

    /**
     * Returns the configured seed.
     *
     * @return the seed value
     */
    public long getSeed() {
        return seed;
    }

    /**
     * Returns the random generator created from the seed.
     *
     * @return the random generator (may be {@code null} if not set yet)
     */
    public RandomGenerator getRandomGenerator() {
        return rng;
    }

    /**
     * Returns the board symbols string.
     *
     * @return the board symbols (may be {@code null} if default symbols should be used)
     */
    public String getBoardSymbols() {
        return boardSymbols;
    }

    /**
     * Returns the list of parsed unit templates.
     *
     * @return an unmodifiable list of unit templates (may be {@code null} if not set yet)
     */
    public List<UnitTemplate> getUnitTemplates() {
        return unitTemplates;
    }

    /**
     * Returns the deck configuration mode.
     *
     * @return the deck configuration mode (may be {@code null} if not set yet)
     */
    public DeckConfigMode getDeckMode() {
        return deckMode;
    }

    /**
     * Returns the deck counts for team 1.
     * <p>
     * A defensive copy is returned to preserve immutability.
     *
     * @return a copy of the deck counts array for team 1 (may be {@code null} if not set yet)
     */
    public int[] getDeckCountsTeam1() {
        return copyArray(deckCountsTeam1);
    }

    /**
     * Returns the deck counts for team 2.
     * <p>
     * A defensive copy is returned to preserve immutability.
     *
     * @return a copy of the deck counts array for team 2 (may be {@code null} if not set yet)
     */
    public int[] getDeckCountsTeam2() {
        return copyArray(deckCountsTeam2);
    }

    /**
     * Returns the name of team 1.
     *
     * @return the name of team 1 (may be {@code null} if not set yet)
     */
    public String getTeam1Name() {
        return team1Name;
    }

    /**
     * Returns the name of team 2.
     *
     * @return the name of team 2 (may be {@code null} if not set yet)
     */
    public String getTeam2Name() {
        return team2Name;
    }

    /**
     * Returns the configured verbosity level.
     *
     * @return the verbosity (may be {@code null} if not set yet)
     */
    public Verbosity getVerbosity() {
        return verbosity;
    }

    /**
     * Internal builder used to create immutable {@link StartupContext} instances without exceeding
     * the allowed maximum number of parameters per constructor.
     * <p>
     * This builder is intentionally private and only used internally by {@link StartupContext}.
     */
    private static final class Builder {
        long seed;
        RandomGenerator rng;
        String boardSymbols;
        List<UnitTemplate> unitTemplates;
        DeckConfigMode deckMode;
        int[] deckCountsTeam1;
        int[] deckCountsTeam2;
        String team1Name;
        String team2Name;
        Verbosity verbosity;

        /**
         * Creates a new empty builder.
         */
        Builder() {
            // default state
        }

        /**
         * Creates a builder initialized from an existing context.
         *
         * @param c the context to copy from
         */
        Builder(StartupContext c) {
            this.seed = c.seed;
            this.rng = c.rng;
            this.boardSymbols = c.boardSymbols;
            this.unitTemplates = c.unitTemplates;
            this.deckMode = c.deckMode;
            this.deckCountsTeam1 = c.deckCountsTeam1;
            this.deckCountsTeam2 = c.deckCountsTeam2;
            this.team1Name = c.team1Name;
            this.team2Name = c.team2Name;
            this.verbosity = c.verbosity;
        }

        /**
         * Builds a new immutable {@link StartupContext} from the current builder state.
         *
         * @return a new {@link StartupContext}
         */
        StartupContext build() {
            return new StartupContext(this);
        }
    }
}