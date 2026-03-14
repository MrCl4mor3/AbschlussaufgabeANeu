package edu.kit.kastel.crownoffarmland.ui.renderer.outputformatter;

import edu.kit.kastel.crownoffarmland.ui.renderer.entity.EntityFormatter;

/**
 * Abstract base class for output formatters.
 *
 * @param <T> the type of snapshot to format
 *
 * @author ucgdi
 */
public abstract class AbstractOutputFormatter<T> implements OutputFormatter<T> {
    protected final EntityFormatter entityFormatter;

    /**
     * Creates a new output formatter.
     *
     * @param entityFormatter formatter for contained entities
     */
    protected AbstractOutputFormatter(EntityFormatter entityFormatter) {
        this.entityFormatter = entityFormatter;
    }
}