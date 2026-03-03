package edu.kit.kastel.crownoffarmland.util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Utility class for loading files from the file system.
 * This class provides a method to read the contents of a file as a String.
 * It is designed as a utility class and cannot be instantiated.
 * The method readFileFromPath takes a file path as input and returns the contents of the file as a String.
 * If the file cannot be read, an IOException is thrown.
 *
 * @author ucgdi
 */
public final class FileLoader {

    private static final String UTILITY_CLASS_CONSTRUCTOR_MESSAGE = "Utility classes cannot be instantiated";

    private FileLoader() {
        throw new UnsupportedOperationException(UTILITY_CLASS_CONSTRUCTOR_MESSAGE);
    }


    /**
     * Reads the contents of a file from the specified path and returns it as a String.
     *
     * @param path the path to the file to be read
     * @return the contents of the file as a String
     * @throws IOException if an I/O error occurs reading from the file or a malformed or unmappable byte sequence is read
     */
    public static String readFileFromPath(String path) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, StandardCharsets.UTF_8);
    }
}
