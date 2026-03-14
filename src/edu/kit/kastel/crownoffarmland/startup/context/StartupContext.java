package edu.kit.kastel.crownoffarmland.startup.context;

import edu.kit.kastel.crownoffarmland.model.RandomGenerator;
import edu.kit.kastel.crownoffarmland.model.units.UnitTemplate;

import java.util.List;

/**
 * Stores validated startup data.
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
     * @return the empty startup context
     */
    public static StartupContext empty() {
        return new StartupContext(null, null, StartupDecks.empty(), StartupTeams.empty(), StartupOutput.empty());
    }

    /**
     * Returns the random generator.
     *
     * @return the random generator
     */
    public RandomGenerator getRandomGenerator() {
        return randomGenerator;
    }

    /**
     * Returns the unit templates.
     *
     * @return the unit templates
     */
    public List<UnitTemplate> getUnitTemplates() {
        return unitTemplates;
    }

    /**
     * Returns the deck configuration.
     *
     * @return the deck configuration
     */
    public StartupDecks getDecks() {
        return decks;
    }

    /**
     * Returns the team configuration.
     *
     * @return the team configuration
     */
    public StartupTeams getTeams() {
        return teams;
    }

    /**
     * Returns the output configuration.
     *
     * @return the output configuration
     */
    public StartupOutput getOutput() {
        return output;
    }

    /**
     * Returns a copy with the given random generator.
     *
     * @param newRandomGenerator the random generator
     * @return the updated startup context
     */
    public StartupContext withRandomGenerator(RandomGenerator newRandomGenerator) {
        return new StartupContext(newRandomGenerator, unitTemplates, decks, teams, output);
    }

    /**
     * Returns a copy with the given unit templates.
     *
     * @param newUnitTemplates the unit templates
     * @return the updated startup context
     */
    public StartupContext withUnitTemplates(List<UnitTemplate> newUnitTemplates) {
        return new StartupContext(randomGenerator, newUnitTemplates, decks, teams, output);
    }

    /**
     * Returns a copy with the given deck configuration.
     *
     * @param newDecks the deck configuration
     * @return the updated startup context
     */
    public StartupContext withDecks(StartupDecks newDecks) {
        return new StartupContext(randomGenerator, unitTemplates, newDecks, teams, output);
    }

    /**
     * Returns a copy with the given team configuration.
     *
     * @param newTeams the team configuration
     * @return the updated startup context
     */
    public StartupContext withTeams(StartupTeams newTeams) {
        return new StartupContext(randomGenerator, unitTemplates, decks, newTeams, output);
    }

    /**
     * Returns a copy with the given output configuration.
     *
     * @param newOutput the output configuration
     * @return the updated startup context
     */
    public StartupContext withOutput(StartupOutput newOutput) {
        return new StartupContext(randomGenerator, unitTemplates, decks, teams, newOutput);
    }
}