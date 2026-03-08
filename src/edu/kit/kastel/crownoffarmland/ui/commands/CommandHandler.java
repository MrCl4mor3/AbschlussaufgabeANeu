package edu.kit.kastel.crownoffarmland.ui.commands;

import edu.kit.kastel.crownoffarmland.exceptions.CrownOfFarmlandException;
import edu.kit.kastel.crownoffarmland.exceptions.InvalidCommandArgumentException;
import edu.kit.kastel.crownoffarmland.exceptions.InvalidGameStateException;
import edu.kit.kastel.crownoffarmland.gameplay.GameHandler;
import edu.kit.kastel.crownoffarmland.ui.renderer.board.BoardRenderer;
import edu.kit.kastel.crownoffarmland.ui.renderer.entity.EntityFormatter;
import edu.kit.kastel.crownoffarmland.ui.snapshots.BoardSnapshot;
import edu.kit.kastel.crownoffarmland.ui.snapshots.EntitySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
/**
 * Implement the command handler in a similar fashion as on earlier tasks.
 *
 * @author Programmieren-Team
 */
public class CommandHandler {
    private static final String COMMAND_ERROR_PREFIX = "ERROR: ";
    private static final String COMMAND_NOT_FOUND_ERROR = "Command '%s' not found%n";
    private static final String COMMAND_DELIMITER_REGEX = "\\s+";
    private static final String COMMAND_DELIMITER_REPLACEMENT = " ";
    private static final String HELP_COMMAND = "Use one of the following commands: %s.";
    private static final String COMMAND_NOT_ALLOWED_AFTER_YIELD = "Cannot execute the command '%s', you must discard!%n";


    private final Map<String, Command> commands;
    private final GameHandler gameHandler;
    private final BoardRenderer boardRenderer;
    private final EntityFormatter entityFormatter;
    private boolean running = false;

    /**
     * Creates a new command handler object and initisalizes its program.commands.
     * @param gameHandler the game handler to execute the commands on
     * @param boardRenderer the board renderer to print the board when the board command is executed
     */
    public CommandHandler(GameHandler gameHandler, BoardRenderer boardRenderer) {
        this.gameHandler = gameHandler;
        this.boardRenderer = boardRenderer;
        this.entityFormatter = new EntityFormatter();
        commands = new HashMap<>();
        initCommands();
        this.running = true;
    }


    /**
     * This method handles the input of the user.
     * The input is taken so long, as this (command handler) was not stopped by the quit command.
     */
    public void handleUserInput() {
        this.running = true;
        System.out.println(startHelpMessage());
        try (Scanner scanner = new Scanner(System.in)) {
            while (this.running) {
                executeCommand(scanner.nextLine());
            }
        }
    }
    /**
     * Ends the program.
     */
    public void quit() {
        this.running = false;
    }

    public void printBoard() throws  InvalidGameStateException {
        BoardSnapshot boardSnapshot = gameHandler.createBoardSnapshot();
        System.out.println(boardRenderer.renderBoard(boardSnapshot));
    }

    public void printShow() throws InvalidGameStateException {
        EntitySnapshot snapshot = gameHandler.createEntitySnapshotAtSelected();
        System.out.println(entityFormatter.format(snapshot));
    }




    public EntityFormatter getEntityFormatter() {
        return this.entityFormatter;
    }

    private void executeCommand(String inputString) {

        String strippedInput = inputString.strip().replaceAll(COMMAND_DELIMITER_REGEX, COMMAND_DELIMITER_REPLACEMENT);
        String[] splitCommand = strippedInput.split(COMMAND_DELIMITER_REGEX);
        String commandName = splitCommand[0].toLowerCase();
        String[] commandArguments = Arrays.copyOfRange(splitCommand, 1, splitCommand.length);

        Command command = commands.get(commandName);
        if (command == null) {
            System.err.printf(COMMAND_ERROR_PREFIX + COMMAND_NOT_FOUND_ERROR, commandName);
            return;
        }

        // After tried yield command, only allow yield, hand and quit command until the next turn starts
        if (gameHandler.isYieldRestrictionActive() && command.isAllowedDuringYieldRestriction()) {
            System.err.printf(COMMAND_ERROR_PREFIX + COMMAND_NOT_ALLOWED_AFTER_YIELD, commandName);
            return;
        }


        try {
            command.execute(commandArguments);


            if (gameHandler.isGameOver()) {
                quit();
            }

        } catch (CrownOfFarmlandException e) {
            System.err.println(COMMAND_ERROR_PREFIX + e.getMessage());
        }
    }

    private String startHelpMessage() {
        return String.format(HELP_COMMAND, String.join(", ", getCommandNames()));
    }


    private List<String> getCommandNames() {
        List<String> commandNames = new ArrayList<>();
        for (Command command : commands.values()) {
            commandNames.add(command.getCommandName());
        }
        return commandNames;
    }



    private void initCommands() {
        addCommand(new BlockCommand(this, gameHandler));
        addCommand(new BoardCommand(this, gameHandler));
        addCommand(new FlipCommand(this, gameHandler));
        addCommand(new HandCommand(this, gameHandler));
        addCommand(new MoveCommand(this, gameHandler));
        addCommand(new PlaceCommand(this, gameHandler));
        addCommand(new QuitCommand(this, gameHandler));
        addCommand(new SelectCommand(this, gameHandler));
        addCommand(new ShowCommand(this, gameHandler));
        addCommand(new StateCommand(this, gameHandler));
        addCommand(new YieldCommand(this, gameHandler));
    }



    private void addCommand(Command command) {
        this.commands.put(command.getCommandName(), command);
    }
}
