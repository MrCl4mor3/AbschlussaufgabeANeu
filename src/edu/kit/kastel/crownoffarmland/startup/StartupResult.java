package edu.kit.kastel.crownoffarmland.startup;

public class StartupResult<T> {

    private final boolean success;
    private final T value;
    private final String errorMessage;


    public static <T> StartupResult<T> success(T value) {
        return new StartupResult<>(true, value, null);
    }

    public static <T> StartupResult<T> error(String errorMessage) {
        return new StartupResult<>(false, null, errorMessage);
    }


    private StartupResult(boolean success, T value, String errorMessage) {
        this.success = success;
        this.value = value;
        this.errorMessage = errorMessage;
    }

    public boolean isSuccess() {
        return success;
    }

    public T getValue() {
        return value;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}