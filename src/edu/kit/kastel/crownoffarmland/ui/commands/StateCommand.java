package edu.kit.kastel.crownoffarmland.ui.commands;

import edu.kit.kastel.crownoffarmland.exceptions.CrownOfFarmlandException;

import edu.kit.kastel.crownoffarmland.gameplay.GameHandler;
import edu.kit.kastel.crownoffarmland.model.team.TeamID;
import edu.kit.kastel.crownoffarmland.gameplay.snapshots.TeamStateSnapshot;
import edu.kit.kastel.crownoffarmland.ui.renderer.GameOutputPrinter;

import java.util.List;

/**
 * Implements the state command.
 * This command allows the player to view the current state of both teams, including their life points, remaining deck cards, and placed
 * units. The output is formatted in a clear and organized manner for easy comparison between the two teams.
 *
 * @author ucgdi
 */
public class StateCommand extends  Command {
    private static final String COMMAND_NAME = "state";



    /**
     * Constructs a new StateCommand with the specified CommandHandler and GameHandler.
     *
     * @param commandHandler the CommandHandler to use for executing the command
     * @param gameHandler    the GameHandler to use for accessing and modifying the game state
     * @param gameOutputPrinter the GameOutputPrinter to use for formatting the output of the command
     */
    public StateCommand(CommandHandler commandHandler, GameHandler gameHandler, GameOutputPrinter gameOutputPrinter)  {
        super(COMMAND_NAME, commandHandler, gameHandler, gameOutputPrinter);
    }

    @Override
    public void execute(String[] commandArguments) throws CrownOfFarmlandException {
        ensureNoArguments(commandArguments);

        TeamStateSnapshot team1 = gameHandler.snapshots().createTeamStateSnapshot(TeamID.TEAM_1);
        TeamStateSnapshot team2 = gameHandler.snapshots().createTeamStateSnapshot(TeamID.TEAM_2);
        System.out.println(gameOutputPrinter.formatState(List.of(team1, team2)));
        System.out.println(gameOutputPrinter.formatBoard(gameHandler.snapshots().createBoardSnapshot()));
        if (gameHandler.getSelectedPos() != null) {
            System.out.println(gameOutputPrinter.formatShow(gameHandler.snapshots().createEntitySnapshot()));
        }
    }

}
