package edu.kit.kastel.crownoffarmland.ui.commands;

import edu.kit.kastel.crownoffarmland.exceptions.CrownOfFarmlandException;
import edu.kit.kastel.crownoffarmland.gameplay.GameHandler;
import edu.kit.kastel.crownoffarmland.ui.renderer.GameOutputPrinter;

/**
 * Implements the show command.
 * This command allows the player to view detailed information about the currently selected field on the board.
 *
 * @author ucgdi
 */
public class ShowCommand extends  Command {
    private static final String COMMAND_NAME = "show";




    /**
     * Constructs a new ShowCommand with the specified CommandHandler and GameHandler.
     * @param commandHandler the CommandHandler to use for executing the command
     * @param gameHandler the GameHandler to use for accessing and modifying the game state
     */
    public ShowCommand(CommandHandler commandHandler, GameHandler gameHandler, GameOutputPrinter gameOutputPrinter)  {
        super(COMMAND_NAME, commandHandler, gameHandler,  gameOutputPrinter);
    }


    @Override
    public void execute(String[] commandArguments) throws CrownOfFarmlandException {
        ensureNoArguments(commandArguments);
        System.out.println(gameOutputPrinter.formatShow(gameHandler.createEntitySnapshot()));
    }


}
