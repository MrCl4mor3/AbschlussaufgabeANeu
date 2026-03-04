package edu.kit.kastel.crownoffarmland.startup;


import edu.kit.kastel.crownoffarmland.model.RandomGenerator;
import edu.kit.kastel.crownoffarmland.model.units.UnitTemplate;
import edu.kit.kastel.crownoffarmland.startup.config.DeckConfigMode;
import edu.kit.kastel.crownoffarmland.startup.config.Verbosity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    private StartupContext(
            long seed,
            RandomGenerator rng,
            String boardSymbols,
            List<UnitTemplate> unitTemplates,
            DeckConfigMode deckMode,
            int[] deckCountsTeam1,
            int[] deckCountsTeam2,
            String team1Name,
            String team2Name,
            Verbosity verbosity
    ) {
        this.seed = seed;
        this.rng = rng;
        this.boardSymbols = boardSymbols;

        // defensive copy (und unveränderlich machen)
        this.unitTemplates = unitTemplates == null
                ? null
                : Collections.unmodifiableList(new ArrayList<UnitTemplate>(unitTemplates));

        this.deckMode = deckMode;

        // defensive copies für Arrays
        this.deckCountsTeam1 = copyArray(deckCountsTeam1);
        this.deckCountsTeam2 = copyArray(deckCountsTeam2);

        this.team1Name = team1Name;
        this.team2Name = team2Name;
        this.verbosity = verbosity;
    }

    public static StartupContext empty() {
        return new StartupContext(0L, null, null, null, null, null, null, null, null, null);
    }

    private static int[] copyArray(int[] a) {
        if (a == null) return null;
        int[] c = new int[a.length];
        for (int i = 0; i < a.length; i++) c[i] = a[i];
        return c;
    }

    // --- "with"-Methoden: geben immer eine neue Instanz zurück ---

    public StartupContext withSeed(long seed, RandomGenerator rng) {
        return new StartupContext(seed, rng, boardSymbols, unitTemplates, deckMode,
                deckCountsTeam1, deckCountsTeam2, team1Name, team2Name, verbosity);
    }

    public StartupContext withBoardSymbols(String boardSymbols) {
        return new StartupContext(seed, rng, boardSymbols, unitTemplates, deckMode,
                deckCountsTeam1, deckCountsTeam2, team1Name, team2Name, verbosity);
    }

    public StartupContext withUnitTemplates(List<UnitTemplate> unitTemplates) {
        return new StartupContext(seed, rng, boardSymbols, unitTemplates, deckMode,
                deckCountsTeam1, deckCountsTeam2, team1Name, team2Name, verbosity);
    }

    public StartupContext withSharedDeck(int[] sharedCounts) {
        return new StartupContext(seed, rng, boardSymbols, unitTemplates, DeckConfigMode.SHARED_DECK,
                sharedCounts, sharedCounts, team1Name, team2Name, verbosity);
    }

    public StartupContext withSplitDecks(int[] team1Counts, int[] team2Counts) {
        return new StartupContext(seed, rng, boardSymbols, unitTemplates, DeckConfigMode.SPLIT_DECKS,
                team1Counts, team2Counts, team1Name, team2Name, verbosity);
    }

    public StartupContext withTeams(String team1Name, String team2Name) {
        return new StartupContext(seed, rng, boardSymbols, unitTemplates, deckMode,
                deckCountsTeam1, deckCountsTeam2, team1Name, team2Name, verbosity);
    }

    public StartupContext withVerbosity(Verbosity verbosity) {
        return new StartupContext(seed, rng, boardSymbols, unitTemplates, deckMode,
                deckCountsTeam1, deckCountsTeam2, team1Name, team2Name, verbosity);
    }

    // --- Getter ---

    public long getSeed() {
        return seed;
    }
    public RandomGenerator getRandomGenerator() {
        return rng;
    }
    public String getBoardSymbols() {
        return boardSymbols;
    }
    public List<UnitTemplate> getUnitTemplates() {
        return unitTemplates;
    }
    public DeckConfigMode getDeckMode() {
        return deckMode;
    }
    public int[] getDeckCountsTeam1() {
        return copyArray(deckCountsTeam1);
    }
    public int[] getDeckCountsTeam2() {
        return copyArray(deckCountsTeam2);
    }
    public String getTeam1Name() {
        return team1Name;
    }
    public String getTeam2Name() {
        return team2Name;
    }
    public Verbosity getVerbosity() {
        return verbosity;
    }
}