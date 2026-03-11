package edu.kit.kastel.crownoffarmland.ui.renderer.outputformatter;


import edu.kit.kastel.crownoffarmland.ui.renderer.entity.EntityFormatter;

public abstract class AbstractOutputFormatter<T> implements OutputFormatter<T> {
    protected final EntityFormatter entityFormatter;


    protected AbstractOutputFormatter(EntityFormatter entityFormatter) {
        this.entityFormatter = entityFormatter;
    }
}
