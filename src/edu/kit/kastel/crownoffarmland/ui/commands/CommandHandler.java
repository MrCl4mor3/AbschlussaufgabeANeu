package edu.kit.kastel.crownoffarmland.ui.commands;

import edu.kit.kastel.crownoffarmland.exceptions.CrownOfFarmlandException;
import edu.kit.kastel.crownoffarmland.gameplay.GameHandler;
import edu.kit.kastel.crownoffarmland.ui.renderer.GameOutputPrinter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Handles user commands.
 *
 * @author Programmieren-Team
 * @author ucgdi
 */
public class CommandHandler {
    private static final String COMMAND_ERROR_PREFIX = "ERROR: ";
    private static final String COMMAND_NOT_FOUND_ERROR = "Command '%s' not found%n";
    private static final String COMMAND_DELIMITER_REGEX = "\\s+";
    private static final String COMMAND_DELIMITER_REPLACEMENT = " ";
    private static final String HELP_COMMAND = "Use one of the following commands: %s.";
    private static final String COMMAND_NOT_ALLOWED_AFTER_YIELD = "Cannot execute the command '%s', you must discard!%n";
    private static final String COMMAND_LIST_SEPARATOR = ", ";

    private final Map<String, Command> commands;
    private final GameHandler gameHandler;
    private final GameOutputPrinter gameOutputPrinter;

    private boolean running = false;

    /**
     * Creates a new command handler.
     *
     * @param gameHandler the game handler
     * @param gameOutputPrinter the game output printer
     */
    public CommandHandler(GameHandler gameHandler, GameOutputPrinter gameOutputPrinter) {
        this.gameHandler = gameHandler;
        this.gameOutputPrinter = gameOutputPrinter;
        this.commands = new LinkedHashMap<>();
        initCommands();
    }

    /**
     * Starts handling user input.
     */
    public void handleUserInput() {
        this.running = true;
        System.out.println(startHelpMessage());

        try (Scanner scanner = new Scanner(System.in)) {
            while (this.running) {
                while (this.running && gameHandler.isCurrentPlayerAI() && !gameHandler.isGameOver()) {
                    executeAITurn();
                }

                if (!this.running || gameHandler.isGameOver()) {
                    break;
                }

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

        if (gameHandler.isYieldRestrictionActive() && !command.isAllowedDuringYieldRestriction()) {
            System.err.printf(COMMAND_ERROR_PREFIX + COMMAND_NOT_ALLOWED_AFTER_YIELD, commandName);
            return;
        }

        try {
            command.execute(commandArguments);
        } catch (CrownOfFarmlandException e) {
            System.err.println(COMMAND_ERROR_PREFIX + e.getMessage());
        }
    }

    private void executeAITurn() {
        try {
            gameHandler.executeAITurn();

            if (gameHandler.isGameOver()) {
                quit();
            }
        } catch (CrownOfFarmlandException e) {
            System.err.println(COMMAND_ERROR_PREFIX + e.getMessage());
            quit();
        }
    }

    private String startHelpMessage() {
        return String.format(HELP_COMMAND, String.join(COMMAND_LIST_SEPARATOR, getCommandNames()));
    }

    private List<String> getCommandNames() {
        List<String> commandNames = new ArrayList<>();
        for (Command command : commands.values()) {
            commandNames.add(command.getCommandName());
        }
        return commandNames;
    }

    private void initCommands() {
        addCommand(new SelectCommand(this, gameHandler, gameOutputPrinter));
        addCommand(new BoardCommand(this, gameHandler, gameOutputPrinter));
        addCommand(new MoveCommand(this, gameHandler, gameOutputPrinter));
        addCommand(new FlipCommand(this, gameHandler, gameOutputPrinter));
        addCommand(new BlockCommand(this, gameHandler, gameOutputPrinter));
        addCommand(new HandCommand(this, gameHandler, gameOutputPrinter));
        addCommand(new PlaceCommand(this, gameHandler, gameOutputPrinter));
        addCommand(new ShowCommand(this, gameHandler, gameOutputPrinter));
        addCommand(new YieldCommand(this, gameHandler, gameOutputPrinter));
        addCommand(new StateCommand(this, gameHandler, gameOutputPrinter));
        addCommand(new QuitCommand(this, gameHandler, gameOutputPrinter));
    }

    private void addCommand(Command command) {
        this.commands.put(command.getCommandName(), command);
    }
}