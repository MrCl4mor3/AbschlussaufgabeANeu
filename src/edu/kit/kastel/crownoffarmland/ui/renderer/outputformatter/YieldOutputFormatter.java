package edu.kit.kastel.crownoffarmland.ui.renderer.outputformatter;

import edu.kit.kastel.crownoffarmland.gameplay.snapshots.EndTurnSnapshot;
import edu.kit.kastel.crownoffarmland.ui.renderer.entity.EntityFormatter;

/**
 * Formats the output for yielding a turn, including discarding a card and ending the turn.
 *
 * @author ucgdi
 */
public class YieldOutputFormatter extends AbstractOutputFormatter<EndTurnSnapshot> {

    private static final String SUCCESSFULLY_ENDED_TURN_MESSAGE = "It is %s's turn!";
    private static final String DISCARDING_CARD_MESSAGE = "%s discarded %s.";
    private static final String NO_CARD_LEFT_MESSAGE = "%s has no cards left in the deck!";

    /**
     * Creates.
     * @param entityFormatter for formatting entities in the output
     */
    public YieldOutputFormatter(EntityFormatter entityFormatter) {
        super(entityFormatter);
    }

    @Override
    public String format(EndTurnSnapshot snapshot) {
        StringBuilder output = new StringBuilder();

        if (snapshot.getDiscardedCard() != null) {
            output.append(String.format(DISCARDING_CARD_MESSAGE, snapshot.getDiscardedCard().getTeamName(),
                    entityFormatter.formatEntitySummary(snapshot.getDiscardedCard())));
        }
        output.append(String.format(SUCCESSFULLY_ENDED_TURN_MESSAGE, snapshot.getNextTeamName()));

        if (snapshot.isGameOver()) {
            output.append(System.lineSeparator()).append(NO_CARD_LEFT_MESSAGE.formatted(snapshot.getNextTeamName()));
        }

        return output.toString();
    }
}
