package edu.kit.kastel.crownoffarmland.ui.renderer.outputformatter;

/**
 * Interface for formatting output in the user interface. This interface defines a method for formatting a snapshot of data into a string
 * representation that can be displayed in the user interface. Implementations of this interface will provide specific formatting logic
 * based on the type of snapshot being formatted and the desired output format. The generic type parameter T allows for flexibility in
 * the types of snapshots that can be formatted, enabling the interface to be used with various data structures and types of information
 * that may need to be displayed in the ui.
 * @param <T> the Snapshot
 *
 * @author ucgdi
 */
public interface OutputFormatter<T> {

    /**
     * Formats the given snapshot into a string representation for display in the user interface. The specific formatting logic will
     * depend on the type of snapshot being formatted and the desired output format.
     * @param snapshot the snapshot to format, which contains the data and state information that needs to be represented as a string for
     *                display purposes
     *
     * @return A String of output
     */
    String format(T snapshot);

}
