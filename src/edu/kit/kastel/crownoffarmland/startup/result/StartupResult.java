package edu.kit.kastel.crownoffarmland.startup.result;

/**
 * Represents the result of a startup operation.
 *
 * @param <T> the result value type
 *
 * @author ucgdi
 */
public final class StartupResult<T> {
    private final T value;
    private final String errorMessage;

    private StartupResult(T value, String errorMessage) {
        this.value = value;
        this.errorMessage = errorMessage;
    }

    /**
     * Returns a successful result.
     *
     * @param value the result value
     * @param <T> the result value type
     * @return the successful result
     */
    public static <T> StartupResult<T> success(T value) {
        return new StartupResult<>(value, null);
    }

    /**
     * Returns an error result.
     *
     * @param errorMessage the error message
     * @param <T> the result value type
     * @return the error result
     */
    public static <T> StartupResult<T> error(String errorMessage) {
        return new StartupResult<>(null, errorMessage);
    }

    /**
     * Returns whether this result is successful.
     *
     * @return {@code true} if this result is successful, otherwise {@code false}
     */
    public boolean isSuccess() {
        return errorMessage == null;
    }

    /**
     * Returns whether this result is an error.
     *
     * @return {@code true} if this result is an error, otherwise {@code false}
     */
    public boolean isError() {
        return errorMessage != null;
    }

    /**
     * Returns the result value.
     *
     * @return the result value
     */
    public T getValue() {
        return value;
    }

    /**
     * Returns the error message.
     *
     * @return the error message
     */
    public String getErrorMessage() {
        return errorMessage;
    }
}