package edu.kit.kastel.crownoffarmland.gameplay;

import edu.kit.kastel.crownoffarmland.model.board.Position;
import edu.kit.kastel.crownoffarmland.model.units.BoardEntity;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * This class encapsulates the state of a player's turn, including the selected position, yield restriction status,
 * moved entities, and whether an entity has been placed this turn.
 *
 * @author ucgdi
 */
public class TurnState {
    private Position selectedPos;
    private boolean yieldRestrictionActive;
    private final Set<BoardEntity> movedEntities;
    private boolean placedThisTurn;

    /**
     * Initializes a new TurnState with default values.
     */
    public TurnState() {
        this.movedEntities = new HashSet<>();
        resetForNewTurn();
    }

    /**
     * Resets the turn state for a new turn, clearing the selected position, yield restriction status, moved entities, and placement
     * status.
     */
    public void resetForNewTurn() {
        selectedPos = null;
        yieldRestrictionActive = false;
        movedEntities.clear();
        placedThisTurn = false;
    }

    /**
     * Returns the currently selected position for this turn, or null if no position is selected.
     * @return the selected position, or null if none is selected
     */
    public Position getSelectedPos() {
        return selectedPos;
    }

    /**
     * Sets the selected position for this turn. This position may be used for various actions during the turn, such as moving or placing
     * entities.
     * @param selectedPos the position to set as the currently selected position for this turn
     */
    public void setSelectedPos(Position selectedPos) {
        this.selectedPos = selectedPos;
    }

    /**
     * Checks if the yield restriction is currently active for this turn. If the yield restriction is active, the player must discard
     * instead of performing other actions.
     * @return true if the yield restriction is active, false otherwise
     */
    public boolean isYieldRestrictionActive() {
        return yieldRestrictionActive;
    }

    /**
     * Activates the yield restriction for this turn, indicating that the player must discard instead of performing other actions. Once
     * activated, the yield restriction will remain active until the end of the turn, preventing the player from performing any actions
     * other than discarding.
     */
    public void activateYieldRestriction() {
        this.yieldRestrictionActive = true;
    }

    /**
     * Checks if the player has already placed an entity during this turn. If an entity has been placed, the player cannot place another
     * one during the same turn.
     * @return true if an entity has been placed this turn, false otherwise
     */
    public boolean hasPlacedThisTurn() {
        return placedThisTurn;
    }

    /**
     * Marks that the player has placed an entity during this turn, preventing them from placing another one until the next turn. This
     * method should be called whenever the player successfully places an entity on the board, ensuring that the turn state accurately
     * reflects the player's actions and enforces the rule that only one entity can be placed per turn.
     */
    public void markPlacedThisTurn() {
        this.placedThisTurn = true;
    }


    public boolean hasMoved(BoardEntity entity) {
        return movedEntities.contains(entity);
    }

    public void markMoved(BoardEntity entity) {
        movedEntities.add(entity);
    }

    public Set<BoardEntity> getMovedEntities() {
        return Collections.unmodifiableSet(movedEntities);
    }
}