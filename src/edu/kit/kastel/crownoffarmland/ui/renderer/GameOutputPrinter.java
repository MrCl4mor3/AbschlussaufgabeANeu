package edu.kit.kastel.crownoffarmland.ui.renderer;

import edu.kit.kastel.crownoffarmland.gameplay.snapshots.EndTurnSnapshot;
import edu.kit.kastel.crownoffarmland.gameplay.snapshots.EntityOnPositionSnapshot;
import edu.kit.kastel.crownoffarmland.gameplay.snapshots.EntitySnapshot;
import edu.kit.kastel.crownoffarmland.gameplay.snapshots.PlaceStepSnapshot;
import edu.kit.kastel.crownoffarmland.gameplay.snapshots.TeamStateSnapshot;
import edu.kit.kastel.crownoffarmland.gameplay.snapshots.boardsnapshot.BoardSnapshot;
import edu.kit.kastel.crownoffarmland.gameplay.snapshots.movesnapshot.MoveSnapshot;
import edu.kit.kastel.crownoffarmland.ui.renderer.board.BoardRenderer;
import edu.kit.kastel.crownoffarmland.ui.renderer.entity.EntityFormatter;
import edu.kit.kastel.crownoffarmland.ui.renderer.outputformatter.BlockOutputFormatter;
import edu.kit.kastel.crownoffarmland.ui.renderer.outputformatter.FlipOutputFormatter;
import edu.kit.kastel.crownoffarmland.ui.renderer.outputformatter.HandOutputFormatter;
import edu.kit.kastel.crownoffarmland.ui.renderer.outputformatter.MergeOutputFormatter;
import edu.kit.kastel.crownoffarmland.ui.renderer.outputformatter.MoveOutputFormatter;
import edu.kit.kastel.crownoffarmland.ui.renderer.outputformatter.PlaceOutputFormatter;
import edu.kit.kastel.crownoffarmland.ui.renderer.outputformatter.ShowOutputFormatter;
import edu.kit.kastel.crownoffarmland.ui.renderer.outputformatter.StateOutputFormatter;
import edu.kit.kastel.crownoffarmland.ui.renderer.outputformatter.YieldOutputFormatter;

import java.util.List;

/**
 * Formats game output for the UI.
 *
 * @author ucgdi
 */
public final class GameOutputPrinter {
    private final BoardRenderer renderer;

    private final MoveOutputFormatter moveOutputFormatter;
    private final BlockOutputFormatter blockOutputFormatter;
    private final FlipOutputFormatter flipOutputFormatter;
    private final ShowOutputFormatter showOutputFormatter;
    private final HandOutputFormatter handOutputFormatter;
    private final StateOutputFormatter stateOutputFormatter;
    private final PlaceOutputFormatter placeOutputFormatter;
    private final YieldOutputFormatter yieldOutputFormatter;

    /**
     * Creates a new game output printer.
     *
     * @param renderer renderer for board output
     * @param entityFormatter formatter for entity output
     */
    public GameOutputPrinter(BoardRenderer renderer, EntityFormatter entityFormatter) {
        this.renderer = renderer;

        this.flipOutputFormatter = new FlipOutputFormatter(entityFormatter);
        this.showOutputFormatter = new ShowOutputFormatter(entityFormatter);
        this.handOutputFormatter = new HandOutputFormatter(entityFormatter);
        this.yieldOutputFormatter = new YieldOutputFormatter(entityFormatter);

        MergeOutputFormatter mergeOutputFormatter = new MergeOutputFormatter();

        this.moveOutputFormatter = new MoveOutputFormatter(entityFormatter, mergeOutputFormatter);
        this.placeOutputFormatter = new PlaceOutputFormatter(mergeOutputFormatter);

        this.stateOutputFormatter = new StateOutputFormatter();
        this.blockOutputFormatter = new BlockOutputFormatter();
    }

    /**
     * Formats board output.
     *
     * @param boardSnapshot the board snapshot
     * @return the formatted board output
     */
    public String formatBoard(BoardSnapshot boardSnapshot) {
        return renderer.renderBoard(boardSnapshot);
    }

    /**
     * Formats show output.
     *
     * @param entitySnapshot the entity snapshot
     * @return the formatted show output
     */
    public String formatShow(EntitySnapshot entitySnapshot) {
        return showOutputFormatter.format(entitySnapshot);
    }

    /**
     * Formats hand output.
     *
     * @param handEntries the hand entries
     * @return the formatted hand output
     */
    public String formatHand(List<EntitySnapshot> handEntries) {
        return handOutputFormatter.format(handEntries);
    }

    /**
     * Formats state output.
     *
     * @param stateSnapshots the team state snapshots
     * @return the formatted state output
     */
    public String formatState(List<TeamStateSnapshot> stateSnapshots) {
        return stateOutputFormatter.format(stateSnapshots);
    }

    /**
     * Formats move output.
     *
     * @param moveSnapshot the move snapshot
     * @return the formatted move output
     */
    public String formatMove(MoveSnapshot moveSnapshot) {
        return moveOutputFormatter.format(moveSnapshot);
    }

    /**
     * Formats block output.
     *
     * @param entitySnapshot the block snapshot
     * @return the formatted block output
     */
    public String formatBlock(EntityOnPositionSnapshot entitySnapshot) {
        return blockOutputFormatter.format(entitySnapshot);
    }

    /**
     * Formats flip output.
     *
     * @param entitySnapshot the flip snapshot
     * @return the formatted flip output
     */
    public String formatFlip(EntityOnPositionSnapshot entitySnapshot) {
        return flipOutputFormatter.format(entitySnapshot);
    }

    /**
     * Formats place output.
     *
     * @param placeStepSnapshots the place step snapshots
     * @return the formatted place output
     */
    public String formatPlace(List<PlaceStepSnapshot> placeStepSnapshots) {
        return placeOutputFormatter.format(placeStepSnapshots);
    }

    /**
     * Formats yield output.
     *
     * @param endTurnSnapshot the end-turn snapshot
     * @return the formatted yield output
     */
    public String formatYield(EndTurnSnapshot endTurnSnapshot) {
        return yieldOutputFormatter.format(endTurnSnapshot);
    }
}