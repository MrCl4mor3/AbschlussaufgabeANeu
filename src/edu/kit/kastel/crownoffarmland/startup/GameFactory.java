package edu.kit.kastel.crownoffarmland.startup;

import edu.kit.kastel.crownoffarmland.gameplay.GameHandler;
import edu.kit.kastel.crownoffarmland.model.Game;
import edu.kit.kastel.crownoffarmland.model.team.Team;
import edu.kit.kastel.crownoffarmland.model.team.TeamID;
import edu.kit.kastel.crownoffarmland.model.units.Unit;
import edu.kit.kastel.crownoffarmland.model.units.UnitTemplate;
import edu.kit.kastel.crownoffarmland.startup.context.StartupContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Factory for creating the initialized game handler from the validated startup context.
 *
 * @author ucgdi
 */
public final class GameFactory {
    private final StartupContext context;

    /**
     * Constructs a new GameFactory with the given startup context.
     *
     * @param context the validated startup context
     */
    public GameFactory(StartupContext context) {
        this.context = context;
    }

    /**
     * Creates and initializes the game handler.
     *
     * @return the initialized game handler
     */
    public GameHandler createGameHandler() {
        List<Unit> team1Deck = createDeck(
                TeamID.TEAM_1,
                context.getUnitTemplates(),
                context.getDeckCountsTeam1()
        );
        List<Unit> team2Deck = createDeck(
                TeamID.TEAM_2,
                context.getUnitTemplates(),
                context.getDeckCountsTeam2()
        );

        Team team1 = new Team(context.getTeam1Name(), TeamID.TEAM_1, team1Deck);
        Team team2 = new Team(context.getTeam2Name(), TeamID.TEAM_2, team2Deck);

        Game game = new Game(team1, team2, context.getRandomGenerator());
        GameHandler gameHandler = new GameHandler(game);
        gameHandler.initializeGame();
        return gameHandler;
    }

    private List<Unit> createDeck(TeamID teamID, List<UnitTemplate> unitTemplates, int[] deckCounts) {
        List<Unit> deck = new ArrayList<>();

        for (int templateIndex = 0; templateIndex < unitTemplates.size(); templateIndex++) {
            UnitTemplate template = unitTemplates.get(templateIndex);
            int count = deckCounts[templateIndex];

            for (int copyIndex = 0; copyIndex < count; copyIndex++) {
                deck.add(new Unit(teamID, template));
            }
        }

        return deck;
    }
}