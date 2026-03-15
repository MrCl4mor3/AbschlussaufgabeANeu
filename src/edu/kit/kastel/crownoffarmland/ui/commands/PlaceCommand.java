package edu.kit.kastel.crownoffarmland.ui.commands;

import edu.kit.kastel.crownoffarmland.exceptions.CrownOfFarmlandException;
import edu.kit.kastel.crownoffarmland.exceptions.InvalidCommandArgumentException;
import edu.kit.kastel.crownoffarmland.exceptions.gamestateexceptions.InvalidHandIndexException;
import edu.kit.kastel.crownoffarmland.gameplay.GameHandler;
import edu.kit.kastel.crownoffarmland.ui.renderer.GameOutputPrinter;

/**
 * Command for placing units on the board.
 *
 * @author ucgdi
 */
public class PlaceCommand extends Command {
    private static final String COMMAND_NAME = "place";
    private static final int MIN_ARGS = 1;

    /**
     * Creates a new place command.
     *
     * @param commandHandler the command handler
     * @param gameHandler the game handler
     * @param gameOutputPrinter the game output printer
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
                throw new InvalidHandIndexException(commandArgs[i]);
            }
        }

        System.out.print(gameOutputPrinter.formatPlace(gameHandler.placeUnits(userIndices)));
        System.out.println(gameOutputPrinter.formatBoard(gameHandler.snapshots().createBoardSnapshot()));
        System.out.println(gameOutputPrinter.formatShow(gameHandler.snapshots().createEntitySnapshot()));
    }
}