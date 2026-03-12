package edu.kit.kastel.crownoffarmland.ui.renderer;

import edu.kit.kastel.crownoffarmland.gameplay.snapshots.EndTurnSnapshot;
import edu.kit.kastel.crownoffarmland.gameplay.snapshots.boardsnapshot.BoardSnapshot;
import edu.kit.kastel.crownoffarmland.gameplay.snapshots.EntitySnapshot;
import edu.kit.kastel.crownoffarmland.gameplay.snapshots.PlaceStepSnapshot;
import edu.kit.kastel.crownoffarmland.gameplay.snapshots.TeamStateSnapshot;
import edu.kit.kastel.crownoffarmland.gameplay.snapshots.EntityOnPositionSnapshot;
import edu.kit.kastel.crownoffarmland.gameplay.snapshots.movesnapshot.MoveSnapshot;
import edu.kit.kastel.crownoffarmland.ui.renderer.board.BoardRenderer;
import edu.kit.kastel.crownoffarmland.ui.renderer.entity.EntityFormatter;
import edu.kit.kastel.crownoffarmland.ui.renderer.outputformatter.BlockOutputFormatter;
import edu.kit.kastel.crownoffarmland.ui.renderer.outputformatter.FlipOutputFormatter;
import edu.kit.kastel.crownoffarmland.ui.renderer.outputformatter.HandOutputFormatter;
import edu.kit.kastel.crownoffarmland.ui.renderer.outputformatter.MoveOutputFormatter;
import edu.kit.kastel.crownoffarmland.ui.renderer.outputformatter.PlaceOutputFormatter;
import edu.kit.kastel.crownoffarmland.ui.renderer.outputformatter.ShowOutputFormatter;
import edu.kit.kastel.crownoffarmland.ui.renderer.outputformatter.StateOutputFormatter;
import edu.kit.kastel.crownoffarmland.ui.renderer.outputformatter.YieldOutputFormatter;

import java.util.List;

/**
 * Formats all UserOutputs.
 *
 * @author ucgdi
 */
public final class GameOutputPrinter {
    private final BoardRenderer renderer;

    private final MoveOutputFormatter  moveOutputFormatter;
    private final BlockOutputFormatter blockOutputFormatter;
    private final FlipOutputFormatter flipOutputFormatter;
    private final ShowOutputFormatter showOutputFormatter;
    private final HandOutputFormatter handOutputFormatter;
    private final StateOutputFormatter stateOutputFormatter;
    private final PlaceOutputFormatter placeOutputFormatter;
    private final YieldOutputFormatter yieldOutputFormatter;

    /**
     * Creates a new Formatter.
     * @param renderer for rendering the board
     * @param entityFormatter for formatting entities
     */
    public GameOutputPrinter(BoardRenderer renderer,  EntityFormatter entityFormatter) {
        this.renderer = renderer;
        this.moveOutputFormatter = new MoveOutputFormatter(entityFormatter);
        this.flipOutputFormatter = new FlipOutputFormatter(entityFormatter);
        this.showOutputFormatter = new ShowOutputFormatter(entityFormatter);
        this.handOutputFormatter = new HandOutputFormatter(entityFormatter);
        this.yieldOutputFormatter = new YieldOutputFormatter(entityFormatter);

        this.placeOutputFormatter = new PlaceOutputFormatter();
        this.stateOutputFormatter = new StateOutputFormatter();
        this.blockOutputFormatter = new BlockOutputFormatter();

    }

    /**
     * Formats the output of the move command.
     * @param moveSnapshot the snapshot of the move command
     * @return a String representing the output of the move command
     */
    public String formatMove(MoveSnapshot moveSnapshot) {
        return moveOutputFormatter.format(moveSnapshot);
    }

    /**
     * Formats the output of the board.
     * @param boardSnapshot the snapshot of the board
     * @return a String representing the output of the move command
     */
    public String formatBoard(BoardSnapshot boardSnapshot) {
        return renderer.renderBoard(boardSnapshot);
    }

    /**
     * Formats the output of the Show Command.
     * @param entitySnapshot the snapshot to show
     * @return a String representing the output of the show command
     */
    public String formatShow(EntitySnapshot entitySnapshot) {
        return showOutputFormatter.format(entitySnapshot);
    }

    /**
     * Formats the output of the block command.
     * @param entitySnapshot the snapshot of the block command
     * @return a String representing the output of the block command
     */
    public String formatBlock(EntityOnPositionSnapshot entitySnapshot) {
        return blockOutputFormatter.format(entitySnapshot);
    }

    /**
     * Formats the output of the block command.
     * @param entitySnapshot the snapshot of the block command
     * @return a String representing the output of the block command
     */
    public String formatFlip(EntityOnPositionSnapshot entitySnapshot) {
        return flipOutputFormatter.format(entitySnapshot);
    }

    /**
     * Formats the output of the block command.
     * @param handEntries the snapshot of the block command
     * @return a String representing the output of the block command
     */
    public String formatHand(List<EntitySnapshot> handEntries) {
        return handOutputFormatter.format(handEntries);
    }

    /**
     * Formats the output of the block command.
     * @param stateSnapshots the snapshot of the block command
     * @return a String representing the output of the block command
     */
    public String formatState(List<TeamStateSnapshot> stateSnapshots) {
        return stateOutputFormatter.format(stateSnapshots);
    }

    /**
     * Formats the output of the block command.
     * @param placeStepSnapshots the snapshot of the block command
     * @return a String representing the output of the block command
     */
    public String formatPlace(List<PlaceStepSnapshot> placeStepSnapshots) {
        return placeOutputFormatter.format(placeStepSnapshots);
    }

    /**
     * Formats the output of the block command.
     * @param endTurnSnapshot the snapshot of the block command
     * @return a String representing the output of the block command
     */
    public String formatYield(EndTurnSnapshot endTurnSnapshot) {
        return yieldOutputFormatter.format(endTurnSnapshot);
    }
}
