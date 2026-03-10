package edu.kit.kastel.crownoffarmland.gameplay;

import edu.kit.kastel.crownoffarmland.exceptions.InvalidGameStateException;
import edu.kit.kastel.crownoffarmland.exceptions.InvalidHandException;
import edu.kit.kastel.crownoffarmland.exceptions.NoSelectionException;
import edu.kit.kastel.crownoffarmland.gameplay.snapshots.PlaceStepSnapshot;
import edu.kit.kastel.crownoffarmland.gameplay.unitmerge.MergeResult;
import edu.kit.kastel.crownoffarmland.gameplay.unitmerge.UnitMerger;
import edu.kit.kastel.crownoffarmland.model.Game;
import edu.kit.kastel.crownoffarmland.model.board.Position;
import edu.kit.kastel.crownoffarmland.model.team.TeamID;
import edu.kit.kastel.crownoffarmland.model.units.BoardEntity;
import edu.kit.kastel.crownoffarmland.model.units.Unit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Handles all placement-related game logic.
 *
 * @author ucgdi
 */
public final class PlacementService {
    private static final int HAND_INDEX_OFFSET = 1;
    private static final int MAX_KING_DISTANCE = 1;
    private static final int MAX_UNITS_ON_BOARD = 5;

    private final Game game;
    private final UnitMerger unitMerger;
    private final TurnState turnState;

    /**
     * Creates a new placement service.
     *
     * @param game the current game
     * @param unitMerger merger used for placement unions
     * @param turnState current turn state
     */
    public PlacementService(Game game, UnitMerger unitMerger, TurnState turnState) {
        this.game = game;
        this.unitMerger = unitMerger;
        this.turnState = turnState;
    }

    /**
     * Places the given hand cards on the currently selected field.
     *
     * @param userIndices one-based indices of the cards in hand
     * @return snapshots for each placement step
     * @throws InvalidGameStateException if the current placement is not allowed
     */
    public List<PlaceStepSnapshot> placeUnits(int[] userIndices) throws InvalidGameStateException {
        validateTarget();
        List<Integer> internalIndices = parseUniqueHandIndices(userIndices);
        List<Unit> unitsToPlace = takeUnitsFromHand(internalIndices);
        List<PlaceStepSnapshot> snapshots = new ArrayList<>();

        for (Unit unit : unitsToPlace) {
            snapshots.add(placeSingleUnit(unit));
        }
        turnState.markPlacedThisTurn();
        return snapshots;
    }

    private void validateTarget() throws InvalidGameStateException {
        Position targetPosition = turnState.getSelectedPos();
        if (targetPosition == null) {
            throw new NoSelectionException();
        }
        if (turnState.hasPlacedThisTurn()) {
            throw new InvalidGameStateException("You have already placed a unit this turn.");
        }

        Position kingPosition = game.getKingPosition(game.getCurrentTeamID());
        if (!isAdjacentToKing(targetPosition, kingPosition)) {
            throw new InvalidGameStateException("You can only place a unit adjacent to your King.");
        }

        BoardEntity occupant = game.getOccupant(targetPosition);
        if (occupant != null && occupant.getOwner() != game.getCurrentTeamID()) {
            throw new InvalidGameStateException("You cannot place on an enemy occupied field.");
        }
    }

    private boolean isAdjacentToKing(Position targetPosition, Position kingPosition) {
        int rowDifference = Math.abs(targetPosition.getRow() - kingPosition.getRow());
        int columnDifference = Math.abs(targetPosition.getColumn() - kingPosition.getColumn());
        return Math.max(rowDifference, columnDifference) == MAX_KING_DISTANCE;
    }

    private List<Integer> parseUniqueHandIndices(int[] userIndices) throws InvalidGameStateException {
        List<Integer> internalIndices = new ArrayList<>();
        Set<Integer> seenIndices = new HashSet<>();
        int handSize = game.getHandSize(game.getCurrentTeamID());

        for (int userIndex : userIndices) {
            int internalIndex = userIndex - HAND_INDEX_OFFSET;
            if (internalIndex < 0 || internalIndex >= handSize) {
                throw new InvalidHandException(String.valueOf(userIndex));
            }
            if (!seenIndices.add(internalIndex)) {
                throw new InvalidGameStateException(
                        "Each hand index may only be used once per place command."
                );
            }
            internalIndices.add(internalIndex);
        }
        return internalIndices;
    }

    private List<Unit> takeUnitsFromHand(List<Integer> internalIndices) {
        List<Unit> units = new ArrayList<>();
        TeamID currentTeam = game.getCurrentTeamID();

        for (int internalIndex : internalIndices) {
            units.add(game.getHandCardAt(currentTeam, internalIndex));
        }

        List<Integer> descendingIndices = new ArrayList<>(internalIndices);
        descendingIndices.sort(Collections.reverseOrder());
        for (int internalIndex : descendingIndices) {
            game.removeHandCardAt(currentTeam, internalIndex);
        }
        return units;
    }

    private PlaceStepSnapshot placeSingleUnit(Unit incomingUnit) throws InvalidGameStateException {
        Position targetPosition = turnState.getSelectedPos();
        BoardEntity occupant = game.getOccupant(targetPosition);
        String teamName = game.getTeamName(game.getCurrentTeamID());
        String incomingUnitName = incomingUnit.getName().toString();

        if (occupant == null) {
            game.setOccupant(targetPosition, incomingUnit);
            if (countUnitsOnBoard(game.getCurrentTeamID()) > MAX_UNITS_ON_BOARD) {
                game.setOccupant(targetPosition, null);
            }
            return new PlaceStepSnapshot(
                    teamName,
                    incomingUnitName,
                    null,
                    null,
                    targetPosition.toString()
            );
        }

        if (occupant.isFarmerKing()) {
            throw new InvalidGameStateException("You cannot place a unit on top of a Farmer King.");
        }

        Unit existingUnit = (Unit) occupant;
        String existingUnitName = existingUnit.getName().toString();
        MergeResult mergeResult = unitMerger.tryMerge(incomingUnit, existingUnit);

        if (mergeResult.isSuccessful()) {
            game.setOccupant(targetPosition, mergeResult.getUnit());
            return new PlaceStepSnapshot(
                    teamName,
                    incomingUnitName,
                    existingUnitName,
                    null,
                    targetPosition.toString()
            );
        }

        game.setOccupant(targetPosition, incomingUnit);
        return new PlaceStepSnapshot(
                teamName,
                incomingUnitName,
                existingUnitName,
                existingUnitName,
                targetPosition.toString()
        );
    }

    private int countUnitsOnBoard(TeamID teamID) {
        int count = 0;
        for (int rowIndex = 0; rowIndex < game.getBoardSize(); rowIndex++) {
            for (int columnIndex = 0; columnIndex < game.getBoardSize(); columnIndex++) {
                Position position = game.getPositionAt(rowIndex, columnIndex);
                BoardEntity entity = game.getOccupant(position);
                if (entity != null && entity.getOwner() == teamID && !entity.isFarmerKing()) {
                    count++;
                }
            }
        }
        return count;
    }
}