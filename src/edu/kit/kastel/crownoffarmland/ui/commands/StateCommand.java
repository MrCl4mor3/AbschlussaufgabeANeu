package edu.kit.kastel.crownoffarmland.ui.commands;

import edu.kit.kastel.crownoffarmland.exceptions.CrownOfFarmlandException;
import edu.kit.kastel.crownoffarmland.gameplay.GameHandler;
import edu.kit.kastel.crownoffarmland.gameplay.snapshots.TeamStateSnapshot;
import edu.kit.kastel.crownoffarmland.model.team.TeamID;
import edu.kit.kastel.crownoffarmland.ui.renderer.GameOutputPrinter;

import java.util.List;

/**
 * Command for printing the game state.
 *
 * @author ucgdi
 */
public class StateCommand extends Command {
    private static final String COMMAND_NAME = "state";

    /**
     * Creates a new state command.
     *
     * @param commandHandler the command handler
     * @param gameHandler the game handler
     * @param gameOutputPrinter the game output printer
     */
    public StateCommand(CommandHandler commandHandler, GameHandler gameHandler, GameOutputPrinter gameOutputPrinter) {
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