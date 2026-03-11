package edu.kit.kastel.crownoffarmland.ui.renderer;

import edu.kit.kastel.crownoffarmland.gameplay.snapshots.BoardSnapshot;
import edu.kit.kastel.crownoffarmland.gameplay.snapshots.EntitySnapshot;
import edu.kit.kastel.crownoffarmland.gameplay.snapshots.PlaceStepSnapshot;
import edu.kit.kastel.crownoffarmland.gameplay.snapshots.TeamStateSnapshot;
import edu.kit.kastel.crownoffarmland.gameplay.snapshots.movesnapshot.EntityOnPositionSnapshot;
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

    public String formatMove(MoveSnapshot moveSnapshot) {
        return moveOutputFormatter.format(moveSnapshot);
    }

    public String formatBoard(BoardSnapshot boardSnapshot) {
        return renderer.renderBoard(boardSnapshot);
    }

    public String formatShow(EntitySnapshot entitySnapshot) {
        return showOutputFormatter.format(entitySnapshot);
    }

    public String formatBlock(EntityOnPositionSnapshot entitySnapshot) {
        return blockOutputFormatter.format(entitySnapshot);
    }

    public String formatFlip(EntityOnPositionSnapshot entitySnapshot) {
        return flipOutputFormatter.format(entitySnapshot);
    }

    public String formatHand(List<EntitySnapshot> handEntries) {
        return handOutputFormatter.format(handEntries);
    }

    public String formatState(List<TeamStateSnapshot> stateSnapshots) {
        return stateOutputFormatter.format(stateSnapshots);
    }

    public String formatPlace(List<PlaceStepSnapshot> placeStepSnapshots) {
        return placeOutputFormatter.format(placeStepSnapshots);
    }

    public String formatYield(EntitySnapshot entitySnapshot) {
        return yieldOutputFormatter.format(entitySnapshot);
    }
}
