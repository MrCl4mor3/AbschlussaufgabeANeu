package edu.kit.kastel.crownoffarmland.ui.commands;

import edu.kit.kastel.crownoffarmland.exceptions.CrownOfFarmlandException;

import edu.kit.kastel.crownoffarmland.gameplay.GameHandler;
import edu.kit.kastel.crownoffarmland.gameplay.snapshots.movesnapshot.MoveSnapshot;

import edu.kit.kastel.crownoffarmland.model.board.Position;
import edu.kit.kastel.crownoffarmland.ui.renderer.GameOutputPrinter;

/**
 * Implements the move command.
 * Command to move an entity to a new position.
 *
 * @author ucgdi
 */
public class MoveCommand extends  Command {
    private static final String COMMAND_NAME = "move";
    private static final String WINNER_MESSAGE = "%s wins!%n";


    /**
     * Creates a new MoveCommand.
     *
     * @param commandHandler    the CommandHandler to handle this command
     * @param gameHandler       the GameHandler to execute this command
     * @param gameOutputPrinter the GameOutputPrinter to format the Output
     */
    public MoveCommand(CommandHandler commandHandler, GameHandler gameHandler, GameOutputPrinter gameOutputPrinter) {
        super(COMMAND_NAME, commandHandler, gameHandler, gameOutputPrinter);
    }

    @Override
    public void execute(String[] commandArgs) throws CrownOfFarmlandException {
        ensureOneArguments(commandArgs);

        Position targetPosition = Position.fromString(commandArgs[0]);
        MoveSnapshot result = gameHandler.moveUnit(targetPosition);

        System.out.print(gameOutputPrinter.formatMove(result));

        if (gameHandler.isGameOver()) {
            System.out.printf(WINNER_MESSAGE, gameHandler.getWinner());
        }
        System.out.println(gameOutputPrinter.formatBoard(gameHandler.createBoardSnapshot()));
        System.out.println(gameOutputPrinter.formatShow(gameHandler.createEntitySnapshot()));
    }
}
