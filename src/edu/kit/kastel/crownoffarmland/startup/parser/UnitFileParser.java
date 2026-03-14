package edu.kit.kastel.crownoffarmland.startup.parser;

import edu.kit.kastel.crownoffarmland.model.units.StatusValue;
import edu.kit.kastel.crownoffarmland.model.units.UnitName;
import edu.kit.kastel.crownoffarmland.model.units.UnitTemplate;
import edu.kit.kastel.crownoffarmland.startup.result.StartupError;
import edu.kit.kastel.crownoffarmland.startup.result.StartupResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Parses unit file content.
 *
 * @author ucgdi
 */
public final class UnitFileParser implements ContentParser<List<UnitTemplate>> {
    private static final int MAX_UNITS = 80;
    private static final int PARTS_PER_LINE = 4;

    private static final String DELIMITER = ";";

    private static final String EMPTY_FILE_ERROR = "The Unit file is empty.";
    private static final String TOO_MANY_UNITS_ERROR = "The Unit file contains more than " + MAX_UNITS + " units: %d";
    private static final String INVALID_LINE_ERROR = "The Unit file contains a line with an invalid format: %s";
    private static final String INVALID_INTEGER_ERROR = "The Unit file contains a line with an invalid integer value: %s";
    private static final String NEGATIVE_INTEGER_ERROR = "The Unit file contains a line with a negative integer value: %s";


    private static final int QUALIFICATOR_INDEX = 0;
    private static final int ROLE_INDEX = 1;
    private static final int ATK_INDEX = 2;
    private static final int DEF_INDEX = 3;


    @Override
    public StartupResult<List<UnitTemplate>> parse(String content) {
        String normalizedContent = StartupError.removeTrailingLineBreaks(content);

        if (normalizedContent == null || normalizedContent.isEmpty()) {
            return StartupError.error(EMPTY_FILE_ERROR);
        }

        String[] lines = normalizedContent.split(System.lineSeparator());
        if (lines.length > MAX_UNITS) {
            return StartupError.error(TOO_MANY_UNITS_ERROR, lines.length);
        }

        List<UnitTemplate> units = new ArrayList<>();

        for (String line : lines) {
            String[] parts = line.split(DELIMITER, -1);
            if (parts.length != PARTS_PER_LINE) {
                return StartupError.error(INVALID_LINE_ERROR, line);
            }

            String qualificator = parts[QUALIFICATOR_INDEX].trim();
            String role = parts[ROLE_INDEX].trim();

            StartupResult<Integer> atkRes = parseNonNegativeInt(parts[ATK_INDEX].trim());
            if (atkRes.isError()) {
                return StartupError.error(INVALID_LINE_ERROR, line);
            }

            StartupResult<Integer> defRes = parseNonNegativeInt(parts[DEF_INDEX].trim());
            if (defRes.isError()) {
                return StartupError.error(INVALID_LINE_ERROR, line);
            }

            UnitName name = new UnitName(qualificator, role);
            StatusValue stats = new StatusValue(atkRes.getValue(), defRes.getValue());
            units.add(new UnitTemplate(name, stats));
        }

        return StartupResult.success(units);
    }

    private StartupResult<Integer> parseNonNegativeInt(String rawContent) {
        try {
            int value = Integer.parseInt(rawContent);
            if (value < 0) {
                return StartupError.error(NEGATIVE_INTEGER_ERROR, rawContent);
            }
            return StartupResult.success(value);
        } catch (NumberFormatException e) {
            return StartupError.error(INVALID_INTEGER_ERROR, rawContent);
        }
    }
}