package edu.kit.kastel.crownoffarmland.startup.context;

import java.util.Arrays;

/**
 * Immutable deck configuration for both teams during startup.
 *
 * @author ucgdi
 */
public final class StartupDecks {
    private final int[] deckCountsTeam1;
    private final int[] deckCountsTeam2;

    private StartupDecks(int[] deckCountsTeam1, int[] deckCountsTeam2) {
        this.deckCountsTeam1 = copyArray(deckCountsTeam1);
        this.deckCountsTeam2 = copyArray(deckCountsTeam2);
    }

    /**
     * Returns an empty deck configuration.
     *
     * @return empty deck configuration
     */
    public static StartupDecks empty() {
        return new StartupDecks(null, null);
    }

    /**
     * Returns a deck configuration for both teams.
     *
     * @param deckCountsTeam1 the deck counts of team 1
     * @param deckCountsTeam2 the deck counts of team 2
     * @return deck configuration for both teams
     */
    public static StartupDecks of(int[] deckCountsTeam1, int[] deckCountsTeam2) {
        return new StartupDecks(deckCountsTeam1, deckCountsTeam2);
    }

    /**
     * Returns a deck configuration that uses the same deck for both teams.
     *
     * @param deckCounts the deck counts used for both teams
     * @return mirrored deck configuration
     */
    public static StartupDecks mirrored(int[] deckCounts) {
        return new StartupDecks(deckCounts, deckCounts);
    }

    /**
     * Returns the deck counts of team 1.
     *
     * @return copy of the deck counts of team 1
     */
    public int[] getDeckCountsTeam1() {
        return copyArray(deckCountsTeam1);
    }

    /**
     * Returns the deck counts of team 2.
     *
     * @return copy of the deck counts of team 2
     */
    public int[] getDeckCountsTeam2() {
        return copyArray(deckCountsTeam2);
    }

    private static int[] copyArray(int[] source) {
        return source == null ? null : Arrays.copyOf(source, source.length);
    }
}