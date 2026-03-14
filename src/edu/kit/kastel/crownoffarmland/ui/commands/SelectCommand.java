package edu.kit.kastel.crownoffarmland.ui.commands;

import edu.kit.kastel.crownoffarmland.exceptions.CrownOfFarmlandException;
import edu.kit.kastel.crownoffarmland.gameplay.GameHandler;
import edu.kit.kastel.crownoffarmland.model.board.Position;
import edu.kit.kastel.crownoffarmland.ui.renderer.GameOutputPrinter;

/**
 * Command for selecting a field.
 *
 * @author ucgdi
 */
public class SelectCommand extends Command {
    private static final String COMMAND_NAME = "select";

    /**
     * Creates a new select command.
     *
     * @param commandHandler the command handler
     * @param gameHandler the game handler
     * @param gameOutputPrinter the game output printer
     */
    public SelectCommand(CommandHandler commandHandler, GameHandler gameHandler, GameOutputPrinter gameOutputPrinter) {
        super(COMMAND_NAME, commandHandler, gameHandler, gameOutputPrinter);
    }

    @Override
    public void execute(String[] commandArguments) throws CrownOfFarmlandException {
        ensureOneArguments(commandArguments);

        Position selectedPosition = Position.fromString(commandArguments[0]);
        gameHandler.setSelected(selectedPosition);

        System.out.println(gameOutputPrinter.formatBoard(gameHandler.snapshots().createBoardSnapshot()));
        System.out.println(gameOutputPrinter.formatShow(gameHandler.snapshots().createEntitySnapshot()));
    }
}