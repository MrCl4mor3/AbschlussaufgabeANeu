package edu.kit.kastel.crownoffarmland.gameplay;

import edu.kit.kastel.crownoffarmland.model.board.Position;
import edu.kit.kastel.crownoffarmland.model.units.BoardEntity;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Stores the state of the current turn.
 *
 * @author ucgdi
 */
public class TurnState {
    private Position selectedPos;
    private boolean yieldRestrictionActive;
    private final Set<BoardEntity> movedEntities;
    private boolean placedThisTurn;

    /**
     * Creates a new turn state.
     */
    public TurnState() {
        this.movedEntities = new HashSet<>();
        resetForNewTurn();
    }

    /**
     * Resets the state for a new turn.
     */
    public void resetForNewTurn() {
        selectedPos = null;
        yieldRestrictionActive = false;
        movedEntities.clear();
        placedThisTurn = false;
    }

    /**
     * Returns the selected position.
     *
     * @return the selected position, or {@code null} if none is selected
     */
    public Position getSelectedPos() {
        return selectedPos;
    }

    /**
     * Sets the selected position.
     *
     * @param selectedPos the selected position
     */
    public void setSelectedPos(Position selectedPos) {
        this.selectedPos = selectedPos;
    }

    /**
     * Returns whether the yield restriction is active.
     *
     * @return {@code true} if the yield restriction is active
     */
    public boolean isYieldRestrictionActive() {
        return yieldRestrictionActive;
    }

    /**
     * Activates the yield restriction.
     */
    public void activateYieldRestriction() {
        this.yieldRestrictionActive = true;
    }

    /**
     * Returns whether a unit was placed this turn.
     *
     * @return {@code true} if a unit was placed this turn
     */
    public boolean hasPlacedThisTurn() {
        return placedThisTurn;
    }

    /**
     * Marks that a unit was placed this turn.
     */
    public void markPlacedThisTurn() {
        this.placedThisTurn = true;
    }

    /**
     * Returns whether the given entity has already moved.
     *
     * @param entity the entity to check
     * @return {@code true} if the entity has already moved
     */
    public boolean hasMoved(BoardEntity entity) {
        return movedEntities.contains(entity);
    }

    /**
     * Marks the given entity as moved.
     *
     * @param entity the moved entity
     */
    public void markMoved(BoardEntity entity) {
        movedEntities.add(entity);
    }

    /**
     * Returns the moved entities of this turn.
     *
     * @return an unmodifiable set of moved entities
     */
    public Set<BoardEntity> getMovedEntities() {
        return Collections.unmodifiableSet(movedEntities);
    }
}