package edu.kit.kastel.crownoffarmland.startup.context;

import edu.kit.kastel.crownoffarmland.model.RandomGenerator;
import edu.kit.kastel.crownoffarmland.model.units.UnitTemplate;
import java.util.List;

/**
 * Immutable container for all validated startup data.
 *
 * @author ucgdi
 */
public final class StartupContext {
    private final RandomGenerator randomGenerator;
    private final List<UnitTemplate> unitTemplates;
    private final StartupDecks decks;
    private final StartupTeams teams;
    private final StartupOutput output;

    private StartupContext(RandomGenerator randomGenerator, List<UnitTemplate> unitTemplates, StartupDecks decks, StartupTeams teams,
        StartupOutput output) {
        this.randomGenerator = randomGenerator;
        this.unitTemplates = unitTemplates == null ? null : List.copyOf(unitTemplates);
        this.decks = decks;
        this.teams = teams;
        this.output = output;
    }

    /**
     * Returns an empty startup context.
     *
     * @return empty startup context
     */
    public static StartupContext empty() {
        return new StartupContext(null, null, StartupDecks.empty(), StartupTeams.empty(), StartupOutput.empty());
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
     * Returns the configured decks.
     *
     * @return deck configuration
     */
    public StartupDecks getDecks() {
        return decks;
    }

    /**
     * Returns the configured team names.
     *
     * @return team configuration
     */
    public StartupTeams getTeams() {
        return teams;
    }

    /**
     * Returns the configured output settings.
     *
     * @return output configuration
     */
    public StartupOutput getOutput() {
        return output;
    }

    /**
     * Returns a copy of this context with the given random generator.
     *
     * @param newRandomGenerator the new random generator
     * @return updated startup context
     */
    public StartupContext withRandomGenerator(RandomGenerator newRandomGenerator) {
        return new StartupContext(newRandomGenerator, unitTemplates, decks, teams, output);
    }

    /**
     * Returns a copy of this context with the given unit templates.
     *
     * @param newUnitTemplates the new unit templates
     * @return updated startup context
     */
    public StartupContext withUnitTemplates(List<UnitTemplate> newUnitTemplates) {
        return new StartupContext(randomGenerator, newUnitTemplates, decks, teams, output);
    }

    /**
     * Returns a copy of this context with the given decks.
     *
     * @param newDecks the new deck configuration
     * @return updated startup context
     */
    public StartupContext withDecks(StartupDecks newDecks) {
        return new StartupContext(randomGenerator, unitTemplates, newDecks, teams, output);
    }

    /**
     * Returns a copy of this context with the given team names.
     *
     * @param newTeams the new team configuration
     * @return updated startup context
     */
    public StartupContext withTeams(StartupTeams newTeams) {
        return new StartupContext(randomGenerator, unitTemplates, decks, newTeams, output);
    }

    /**
     * Returns a copy of this context with the given output settings.
     *
     * @param newOutput the new output configuration
     * @return updated startup context
     */
    public StartupContext withOutput(StartupOutput newOutput) {
        return new StartupContext(randomGenerator, unitTemplates, decks, teams, newOutput);
    }
}