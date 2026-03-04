package edu.kit.kastel.crownoffarmland.startup;

import edu.kit.kastel.crownoffarmland.gameplay.GameHandler;
import edu.kit.kastel.crownoffarmland.model.Game;
import edu.kit.kastel.crownoffarmland.model.team.Team;
import edu.kit.kastel.crownoffarmland.model.team.TeamID;
import edu.kit.kastel.crownoffarmland.model.units.Unit;
import edu.kit.kastel.crownoffarmland.model.units.UnitTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * The GameFactory class is responsible for creating and initializing the GameHandler instance based on the provided StartupContext. It
 * serves as a factory for constructing the GameHandler, which manages the state and flow of the game. The GameFactory takes in a
 * StartupContext during construction, which contains all the necessary configuration and parameters for setting up the game. The
 * initGame method is responsible for creating and configuring the GameHandler instance using the information from the StartupContext.
 * The getGameHandler method allows access to the initialized GameHandler instance, which can then be used to manage the game state and
 * process player commands.
 *
 * @author ucgdi
 */
public final class GameFactory {
    private final StartupContext container;
    private final GameHandler gameHandler;

    /**
     * Constructs a new GameFactory instance with the specified StartupContext. The constructor initializes the GameHandler by calling
     * the initGame method, which creates and configures the GameHandler based on the information provided in the StartupContext. The
     * GameFactory serves as a central point for creating and managing the GameHandler instance, allowing for a clean separation of
     * concerns between the startup configuration and the game logic. By passing the StartupContext to the GameFactory, it can access all
     * the necessary configuration parameters and settings required to properly initialize the GameHandler, ensuring that the game is set
     * up according to the specified configuration. The GameFactory provides a structured way to create and manage the GameHandler,
     * allowing for flexibility and modularity in the overall design of the game application.
     * @param container The StartupContext that contains all the necessary configuration and parameters for setting up the game. The
     *                  GameFactory will use this context to access the required information for initializing the GameHandler, such as
     *                  team configurations, deck settings, and verbosity levels. The StartupContext should be properly initialized with
     *                  the necessary configuration before being passed to the GameFactory constructor, as it will be essential for the
     *                  correct setup of the GameHandler and the overall game state. The GameFactory relies on the information provided
     *                  in the StartupContext to ensure that the GameHandler is initialized with the correct settings and parameters,
     *                  allowing for a smooth and consistent game experience based on the specified configuration.
     */
    public GameFactory(StartupContext container) {
        this.container = container;
        gameHandler = initGame();
    }

    /**
     * Returns the initialized GameHandler instance. This method allows access to the GameHandler that has been created and configured
     * based on the information provided in the StartupContext.
     * @return the initialized GameHandler instance that can be used to manage the game state and process player commands.
     */
    public GameHandler getGameHandler() {
        return gameHandler;
    }

    private GameHandler initGame() {
        List<UnitTemplate> unitTemplates = container.getUnitTemplates();
        int[] deckCounts1 = container.getDeckCountsTeam1();
        int[] deckCounts2 = container.getDeckCountsTeam2();
        List<Unit> team1Deck = createDeck(TeamID.TEAM_1, unitTemplates, deckCounts1);
        List<Unit> team2Deck = createDeck(TeamID.TEAM_2, unitTemplates, deckCounts2);

        Team team1 = new Team(container.getTeam1Name(), TeamID.TEAM_1, team1Deck);
        Team team2 = new Team(container.getTeam2Name(), TeamID.TEAM_2, team2Deck);

        Game game = new Game(team1, team2, container.getRandomGenerator());
        return new GameHandler(game);
        //ToDo: Verbosity muss noch weiter gegeben werden, evtl auch Deckconfig
    }


    private List<Unit> createDeck(TeamID teamID, List<UnitTemplate> unitTemplates, int[] deckCounts) {
        List<Unit> units = new ArrayList<>();
        for (int i = 0; i < unitTemplates.size(); i++) {
            UnitTemplate template = unitTemplates.get(i);
            int count = deckCounts[i];
            for (int j = 0; j < count; j++) {
                units.add(new Unit(teamID, template));
            }
        }
        return units;
    }
}
