package edu.kit.kastel.crownoffarmland.ui.commands;

import edu.kit.kastel.crownoffarmland.exceptions.InvalidCommandArgumentException;
import edu.kit.kastel.crownoffarmland.gameplay.GameHandler;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CommandHandler {
    private static final String COMMAND_ERROR_PRAEFIX = "Error: ";
    private static final String COMMAND_NOT_FOUND_ERROR = "Command '%S' not recognised by any pattern%n";
    private static final String COMMAND_DELIMITER_REGEX = "\\s+";
    private static final String COMMAND_DELIMITER_REPLACEMENT = " ";

    private final Map<String, Command> commands;
    private final GameHandler gameHandler;
    private boolean running = false;


    public CommandHandler(GameHandler gameHandler) {
        this.gameHandler = gameHandler;
        commands = new HashMap<>();
        initCommands();
    }


    public void handleUserInput() {
        this.running = true;

        try (Scanner scanner = new Scanner(System.in)) {
            while (this.running) {
                executeCommand(scanner.nextLine());
            }
        }
    }

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
