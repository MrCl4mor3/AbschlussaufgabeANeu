package edu.kit.kastel.crownoffarmland.ui.renderer.outputformatter;




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
