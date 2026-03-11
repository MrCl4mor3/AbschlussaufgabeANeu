package edu.kit.kastel.crownoffarmland.ui.commands;

import edu.kit.kastel.crownoffarmland.exceptions.CrownOfFarmlandException;
import edu.kit.kastel.crownoffarmland.exceptions.InvalidCommandArgumentException;
import edu.kit.kastel.crownoffarmland.exceptions.InvalidHandException;
import edu.kit.kastel.crownoffarmland.gameplay.GameHandler;
import edu.kit.kastel.crownoffarmland.ui.renderer.GameOutputPrinter;




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


    /**
     * Constructs a new PlaceCommand with the specified CommandHandler and GameHandler.
     *
     * @param commandHandler the CommandHandler to use for executing the command
     * @param gameHandler    the GameHandler to use for accessing and modifying the game state
     */
    public PlaceCommand(CommandHandler commandHandler, GameHandler gameHandler, GameOutputPrinter gameOutputPrinter) {
        super(COMMAND_NAME, commandHandler, gameHandler, gameOutputPrinter);
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

        System.out.println(gameOutputPrinter.formatPlace(gameHandler.placeUnits(userIndices)));
        System.out.println(gameOutputPrinter.formatBoard(gameHandler.createBoardSnapshot()));
        System.out.println(gameOutputPrinter.formatShow(gameHandler.createEntitySnapshot()));
    }
}