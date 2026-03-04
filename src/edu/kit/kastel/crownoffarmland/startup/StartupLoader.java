package edu.kit.kastel.crownoffarmland.startup;

import edu.kit.kastel.crownoffarmland.gameplay.GameHandler;
import edu.kit.kastel.crownoffarmland.startup.config.StartupKey;
import edu.kit.kastel.crownoffarmland.startup.parser.RawArgsParser;

import java.util.Map;

/**
 * Test.
 *
 * @author ucgdi
 */
public final class StartupLoader {

    /**
     * This method is responsible for creating a GameHandler instance based on the provided command-line arguments. It follows a
     * multi-step process:
     * @param args The raw command-line arguments provided at the start of the program. These arguments are expected to contain key-value
     *     pairs that configure the game.
     * @return A StartupResult containing either a GameHandler instance if the creation was successful, or an error message if there was
     *     an issue during the parsing or validation of the arguments. The method first parses the raw arguments into a structured format,
     *     then validates the required keys and their values, and finally creates a GameHandler instance based on the validated
     *     argument. If any step fails, it returns an appropriate error message.
     */
    public StartupResult<GameHandler> createGameHandler(String[] args) {

        // 1. Parse raw arguments into a structured format (map of StartupKey to String)
        RawArgsParser parser = new RawArgsParser();
        StartupResult<Map<StartupKey, String>> parseResult = parser.parseRawArgs(args);
        if (parseResult.isError()) {
            return StartupError.error(parseResult.getErrorMessage());
        }
        Map<StartupKey, String> arguments = parseResult.getValue();


        // 2. Validate required keys and their values

        return StartupError.error("Not implemented yet");
    }
}
