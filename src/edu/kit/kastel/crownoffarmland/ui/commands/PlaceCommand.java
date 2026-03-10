package edu.kit.kastel.crownoffarmland.ui.commands;

import edu.kit.kastel.crownoffarmland.exceptions.CrownOfFarmlandException;
import edu.kit.kastel.crownoffarmland.exceptions.InvalidCommandArgumentException;
import edu.kit.kastel.crownoffarmland.exceptions.InvalidHandException;
import edu.kit.kastel.crownoffarmland.gameplay.GameHandler;
import edu.kit.kastel.crownoffarmland.gameplay.snapshots.PlaceStepSnapshot;

import java.util.List;


/**
 * Implements the place command.
 * Command for placing units on the board.
 * This command allows players to place units from their hand onto the board, potentially merging with existing units.
 * The command takes one or more indices as arguments, which correspond to the units in the player's hand that they wish to place. The
 * command will handle the placement logic, including any merging of units and the resulting changes to the game state. After execution,
 * it will print the results of the placement and update the board and show displays.
 *
 * @author ucgdi
 */
public class PlaceCommand extends Command {
    private static final String COMMAND_NAME = "place";
    private static final int MIN_ARGS = 1;
    private static final String PLACE_MESSAGE = "%s places %s on %s.%n";
    private static final String MERGING_MESSAGE = "%s and %s on %s join forces!%n";
    private static final String MERGING_UNIT_SUCCESS_MESSAGE = "Success!%n";
    private static final String MERGING_UNIT_FAILURE_MESSAGE = "Union failed. %s was eliminated.%n";


    /**
     * Constructs a new PlaceCommand with the specified CommandHandler and GameHandler.
     *
     * @param commandHandler the CommandHandler to use for executing the command
     * @param gameHandler    the GameHandler to use for accessing and modifying the game state
     */
    public PlaceCommand(CommandHandler commandHandler, GameHandler gameHandler) {
        super(COMMAND_NAME, commandHandler, gameHandler);
    }

    @Override
    public void execute(String[] commandArgs) throws CrownOfFarmlandException {
        if (commandArgs.length < MIN_ARGS) {
            throw new InvalidCommandArgumentException(MIN_ARGS, commandArgs.length);
        }

        int[] userIndices = new int[commandArgs.length];
        for (int i = 0; i < commandArgs.length; i++) {
            try {
                userIndices[i] = Integer.parseInt(commandArgs[i]);
            } catch (NumberFormatException e) {
                throw new InvalidHandException(commandArgs[i]);
            }
        }

        List<PlaceStepSnapshot> results = this.gameHandler.placeUnits(userIndices);

        for (PlaceStepSnapshot result : results) {
            printPlaceStep(result);
        }
        commandHandler.printBoard();
        commandHandler.printShow();
    }

    private void printPlaceStep(PlaceStepSnapshot snapshot) {
        System.out.printf(PLACE_MESSAGE, snapshot.getTeamName(), snapshot.getPlacedUnitName(), snapshot.getTargetPosition());

        if (snapshot.getExistingUnitName() != null) {
            System.out.printf(MERGING_MESSAGE, snapshot.getExistingUnitName(), snapshot.getPlacedUnitName(), snapshot.getTargetPosition());
            if (snapshot.getEliminatedUnitName() == null) {
                System.out.printf(MERGING_UNIT_SUCCESS_MESSAGE);
            } else {
                System.out.printf(MERGING_UNIT_FAILURE_MESSAGE, snapshot.getEliminatedUnitName());
            }
        }
    }
}
