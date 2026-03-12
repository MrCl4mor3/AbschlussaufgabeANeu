package edu.kit.kastel.crownoffarmland.ui.renderer.outputformatter;


import edu.kit.kastel.crownoffarmland.ui.renderer.entity.EntityFormatter;

/**
 * Abstract base class for output formatters that provides common functionality and dependencies for formatting snapshots into string
 * representations.
 * @param <T> the type of snapshot that this formatter can handle, which contains the data and state information that needs to be
 *           represented as a string for display purposes
 *
 *
 * @author ucgdi
 */
public abstract class AbstractOutputFormatter<T> implements OutputFormatter<T> {
    protected final EntityFormatter entityFormatter;


    /**
     * Creates a new instance of the AbstractOutputFormatter class with the specified EntityFormatter dependency. The EntityFormatter is
     * used to format entities within the snapshots being formatted by this output formatter.
     * @param entityFormatter w
     */
    protected AbstractOutputFormatter(EntityFormatter entityFormatter) {
        this.entityFormatter = entityFormatter;
    }
}
