package edu.kit.kastel.crownoffarmland.ui.commands;

import edu.kit.kastel.crownoffarmland.exceptions.InvalidCommandArgumentException;
import edu.kit.kastel.crownoffarmland.gameplay.GameHandler;

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
    private static final String COMMAND_ERROR_PRAEFIX = "Error: ";
    private static final String COMMAND_NOT_FOUND_ERROR = "Command '%S' not recognised by any pattern%n";
    private static final String COMMAND_DELIMITER_REGEX = "\\s+";
    private static final String COMMAND_DELIMITER_REPLACEMENT = " ";
    private static final String HELP_COMMAND = "Use one of the following commands: %s.";

    private final Map<String, Command> commands;
    private final GameHandler gameHandler;
    private boolean running = false;


    /**
     * Creates a new command handler object and initisalizes its program.commands.
     * @param gameHandler the game handler to execute the commands on
     */
    public CommandHandler(GameHandler gameHandler) {
        this.gameHandler = gameHandler;
        commands = new HashMap<>();
        initCommands();
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

    private void executeCommand(String inputString) {

        String strippedInput = inputString.strip().replaceAll(COMMAND_DELIMITER_REGEX, COMMAND_DELIMITER_REPLACEMENT);
        String[] splitCommand = strippedInput.split(COMMAND_DELIMITER_REGEX);
        String[] commandArguments = Arrays.copyOfRange(splitCommand, 1, splitCommand.length);

        for (Command command : commands.values()) {
            if (strippedInput.matches(command.getCommandRegex())) {
                try {
                    command.execute(commandArguments);
                } catch (InvalidCommandArgumentException e) {
                    System.err.println(COMMAND_ERROR_PRAEFIX + COMMAND_NOT_FOUND_ERROR + e.getMessage());
                }
                return;
            }
        }
        System.out.printf(COMMAND_NOT_FOUND_ERROR, inputString);
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
        //addCommand(new BlockCommand(this, game));
        //addCommand(new BoardCommand(this, game));
        //addCommand(new FlipCommand(this, game));
        //addCommand(new HandCommand(this, game));
        //addCommand(new MoveCommand(this, game));
        //addCommand(new PlaceCommand(this, game));
        addCommand(new QuitCommand(this, gameHandler));
        //addCommand(new SelectCommand(this, game));
        //addCommand(new ShowCommand(this, game));
        //addCommand(new StateCommand(this, game));
        //addCommand(new YieldCommand(this, game));
    }



    private void addCommand(Command command) {
        this.commands.put(command.getCommandName(), command);
    }
}
