package edu.kit.kastel.crownoffarmland.ui.renderer.outputformatter;

/**
 * Formats snapshots for UI output.
 *
 * @param <T> the snapshot type
 *
 * @author ucgdi
 */
public interface OutputFormatter<T> {

    /**
     * Formats the given snapshot.
     *
     * @param snapshot the snapshot to format
     * @return the formatted output
     */
    String format(T snapshot);
}
