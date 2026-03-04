package edu.kit.kastel.crownoffarmland.startup;

/**
 * This class represents the result of a startup operation, which can either be successful or contain an error message.
 * It is a generic class that can hold a value of any type in case of success, or an error message in case of failure.
 * The class provides static factory methods for creating success and error results, and getter methods to retrieve the success status,
 * value, and error message.
 * @param <T> the type of the value held in case of a successful startup result
 * @author ucgdi
 */
public final class StartupResult<T> {

    private final T value;
    private final String errorMessage;


    private StartupResult(boolean success, T value, String errorMessage) {
        this.value = value;
        this.errorMessage = errorMessage;
    }
    /**
     * Creates a successful StartupResult with the given value.
     * @param value the value to be held in the successful result
     * @return a StartupResult instance representing a successful startup operation with the provided value
     * @param <T> the type of the value held in the successful result
     */
    public static <T> StartupResult<T> success(T value) {
        return new StartupResult<>(true, value, null);
    }

    /**
     * Creates an error StartupResult with the given error message.
     * @param errorMessage the error message to be held in the error result
     * @return a StartupResult instance representing a failed startup operation with the provided error message
     * @param <T> the type of the value that would have been held in case of a successful result, which is not applicable in this case
     *           since it's an error result
     */
    public static <T> StartupResult<T> error(String errorMessage) {
        return new StartupResult<>(false, null, errorMessage);
    }


    /**
     * Indicates whether the startup operation was successful.
     * @return true if the startup operation was successful, false otherwise
     */
    public boolean isSuccess() {
        return errorMessage == null;
    }
    /**
     * Indicates whether the startup operation resulted in an error.
     * @return true if the startup operation resulted in an error, false otherwise
     */
    public boolean isError() {
        return errorMessage != null;
    }

    /**
     * Retrieves the value held in this StartupResult if it represents a successful startup operation.
     * @return the value held in this StartupResult if it is successful, or null if it is an error result
     */
    public T getValue() {
        return value;
    }

    /**
     * Retrieves the error message held in this StartupResult if it represents a failed startup operation.
     * @return the error message held in this StartupResult if it is an error result, or null if it is a successful result
     */
    public String getErrorMessage() {
        return errorMessage;
    }
}