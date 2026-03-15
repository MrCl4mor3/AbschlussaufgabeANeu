package edu.kit.kastel.crownoffarmland.gameplay;

import edu.kit.kastel.crownoffarmland.exceptions.InvalidGameStateException;
import edu.kit.kastel.crownoffarmland.exceptions.gamestateexceptions.EmptySelectedFieldException;
import edu.kit.kastel.crownoffarmland.exceptions.gamestateexceptions.EnemyUnitSelectedException;
import edu.kit.kastel.crownoffarmland.exceptions.gamestateexceptions.MovementException;
import edu.kit.kastel.crownoffarmland.exceptions.gamestateexceptions.NoSelectionException;
import edu.kit.kastel.crownoffarmland.exceptions.gamestateexceptions.EntityAlreadyActedException;
import edu.kit.kastel.crownoffarmland.gameplay.combat.DuelManager;
import edu.kit.kastel.crownoffarmland.gameplay.combat.DuelResult;
import edu.kit.kastel.crownoffarmland.gameplay.snapshots.EntitySnapshot;
import edu.kit.kastel.crownoffarmland.gameplay.snapshots.movesnapshot.DuelMoveSnapshot;
import edu.kit.kastel.crownoffarmland.gameplay.snapshots.movesnapshot.MergeMoveSnapshot;
import edu.kit.kastel.crownoffarmland.gameplay.snapshots.movesnapshot.MoveSnapshot;
import edu.kit.kastel.crownoffarmland.gameplay.snapshots.movesnapshot.SimpleMoveSnapshot;
import edu.kit.kastel.crownoffarmland.gameplay.unitmerge.MergeResult;
import edu.kit.kastel.crownoffarmland.gameplay.unitmerge.UnitMerger;
import edu.kit.kastel.crownoffarmland.model.Game;
import edu.kit.kastel.crownoffarmland.model.board.Position;
import edu.kit.kastel.crownoffarmland.model.team.TeamID;
import edu.kit.kastel.crownoffarmland.model.units.BoardEntity;
import edu.kit.kastel.crownoffarmland.model.units.Unit;

/**
 * Handles unit movement.
 *
 * @author ucgdi
 */
public final class MovementService {
    private static final int MAX_MOVE_DISTANCE = 1;

    private final DuelManager duelManager;
    private final UnitMerger unitMerger;
    private final Game game;
    private final TurnState turnState;

    /**
     * Creates a new movement service.
     *
     * @param game the current game
     * @param unitMerger the unit merger
     * @param turnState the current turn state
     * @param duelManager the duel manager
     */
    public MovementService(Game game, UnitMerger unitMerger, TurnState turnState, DuelManager duelManager) {
        this.game = game;
        this.unitMerger = unitMerger;
        this.turnState = turnState;
        this.duelManager = duelManager;
    }

    /**
     * Moves the selected unit to the given target position.
     *
     * @param target the target position
     * @param currentTeam the current team
     * @return the move snapshot
     * @throws InvalidGameStateException if the move is not allowed
     */
    public MoveSnapshot moveUnit(Position target, TeamID currentTeam) throws InvalidGameStateException {
        Position source = requireSelectedPosition();
        BoardEntity selectedEntity = requireSelectedEntity(source, currentTeam);

        if (source.equals(target)) {
            boolean wasBlocked = unblockIfNecessary(selectedEntity);
            turnState.markMoved(selectedEntity);
            return new SimpleMoveSnapshot(createEntitySnapshot(selectedEntity), target.toString(), wasBlocked);
        }

        validateMove(source, target, selectedEntity);

        boolean wasBlocked = unblockIfNecessary(selectedEntity);

        if (selectedEntity.isFarmerKing()) {
            return resolveFarmerKingMove(source, target, selectedEntity, wasBlocked);
        }

        return resolveUnitMove(source, target, selectedEntity, wasBlocked);
    }

    private Position requireSelectedPosition() throws NoSelectionException {
        Position selectedPosition = turnState.getSelectedPos();
        if (selectedPosition == null) {
            throw new NoSelectionException();
        }
        return selectedPosition;
    }

    private BoardEntity requireSelectedEntity(Position position, TeamID currentTeam) throws InvalidGameStateException {
        BoardEntity entity = game.boardView().getOccupant(position);
        if (entity == null) {
            throw new EmptySelectedFieldException(position.toString());
        }

        if (entity.getOwner() != currentTeam) {
            throw new EnemyUnitSelectedException();
        }

        if (turnState.hasMoved(entity)) {
            throw new EntityAlreadyActedException(entity.getName().toString());
        }

        return entity;
    }

    private void validateMove(Position source, Position target, BoardEntity selectedEntity) throws MovementException {
        if (!isAdjacentTo(source, target, MAX_MOVE_DISTANCE)) {
            throw MovementException.targetTooFar(source.toString(), target.toString(), MAX_MOVE_DISTANCE);
        }

        BoardEntity targetEntity = game.boardView().getOccupant(target);
        if (targetEntity == null) {
            return;
        }

        if (selectedEntity.isFarmerKing() && !targetEntity.getOwner().equals(selectedEntity.getOwner())) {
            throw MovementException.farmerKingOntoEnemy(target.toString());
        }

        if (!selectedEntity.isFarmerKing() && targetEntity.getOwner().equals(selectedEntity.getOwner())
                && targetEntity.isFarmerKing()) {
            throw MovementException.ontoOwnFarmerKing(target.toString());
        }
    }

    private MoveSnapshot resolveFarmerKingMove(Position source, Position target, BoardEntity selectedEntity, boolean wasBlocked) {
        game.removeOccupant(source);
        game.setOccupant(target, selectedEntity);
        turnState.markMoved(selectedEntity);
        turnState.setSelectedPos(target);

        return new SimpleMoveSnapshot(createEntitySnapshot(selectedEntity), target.toString(), wasBlocked);
    }

    private MoveSnapshot resolveUnitMove(Position source, Position target, BoardEntity selectedEntity, boolean wasBlocked) {
        BoardEntity targetEntity = game.boardView().getOccupant(target);

        if (targetEntity == null) {
            return executeSimpleMove(source, target, selectedEntity, wasBlocked);
        } else if (targetEntity.getOwner().equals(selectedEntity.getOwner())) {
            return resolveMergeMove(source, target, (Unit) selectedEntity, (Unit) targetEntity, wasBlocked);
        } else {
            return resolveDuelMove(source, target, (Unit) selectedEntity, targetEntity, wasBlocked);
        }
    }

    private MoveSnapshot executeSimpleMove(Position source, Position target, BoardEntity selectedEntity, boolean wasBlocked) {
        game.removeOccupant(source);
        game.setOccupant(target, selectedEntity);
        turnState.markMoved(selectedEntity);
        turnState.setSelectedPos(target);

        return new SimpleMoveSnapshot(createEntitySnapshot(selectedEntity), target.toString(), wasBlocked);
    }

    private MoveSnapshot resolveMergeMove(Position source, Position target, Unit selectedUnit, Unit targetUnit, boolean wasBlocked) {
        MergeResult mergeResult = unitMerger.tryMerge(selectedUnit, targetUnit);

        turnState.setSelectedPos(target);
        game.removeOccupant(source);
        if (mergeResult.isSuccessful()) {
            game.setOccupant(target, mergeResult.getUnit());
        } else {
            game.setOccupant(target, selectedUnit);
            turnState.markMoved(selectedUnit);
        }

        return new MergeMoveSnapshot(createEntitySnapshot(selectedUnit), target.toString(), wasBlocked,
                mergeResult.isSuccessful(), targetUnit.getName().toString());
    }

    private MoveSnapshot resolveDuelMove(Position source, Position target, Unit attacker, BoardEntity defender, boolean wasBlocked) {
        EntitySnapshot targetEntitySnapshot = createEntitySnapshot(defender);
        EntitySnapshot sourceEntitySnapshot = createEntitySnapshot(attacker);

        revealIfHidden(defender);
        revealIfHidden(attacker);

        DuelResult duelResult = duelManager.resolveDuel(attacker, defender);
        updateGameStateAfterDuel(source, target, attacker, defender, duelResult);

        String loserName;
        if (game.getWinnerID() != null) {
            loserName = game.teamView(game.getWinnerID().getNext()).getName();
        } else {
            loserName = null;
        }

        return new DuelMoveSnapshot(sourceEntitySnapshot, targetEntitySnapshot, source.toString(), target.toString(),
                wasBlocked, duelResult, loserName);
    }

    private void updateGameStateAfterDuel(Position source, Position target, Unit attacker, BoardEntity defender, DuelResult duelResult) {
        if (duelResult.getDamageToAttackerTeam() > 0) {
            game.dealDamage(attacker.getOwner(), duelResult.getDamageToAttackerTeam());
        }

        if (duelResult.getDamageToDefenderTeam() > 0) {
            game.dealDamage(defender.getOwner(), duelResult.getDamageToDefenderTeam());
        }

        updateWinnerAfterDuel(attacker, defender);

        if (duelResult.isAttackerEliminated()) {
            game.removeOccupant(source);
        }

        if (duelResult.isDefenderEliminated()) {
            game.removeOccupant(target);
        }

        if (!duelResult.isAttackerEliminated() && duelResult.isDefenderEliminated()) {
            game.setOccupant(target, attacker);
            game.removeOccupant(source);
            turnState.setSelectedPos(target);
        }

        turnState.markMoved(attacker);
    }

    private void updateWinnerAfterDuel(Unit attacker, BoardEntity defender) {
        TeamID attackerTeam = attacker.getOwner();
        TeamID defenderTeam = defender.getOwner();

        if (game.teamView(attackerTeam).getLifePoints() <= 0) {
            game.setWinner(defenderTeam);
        } else if (game.teamView(defenderTeam).getLifePoints() <= 0) {
            game.setWinner(attackerTeam);
        }
    }

    private void revealIfHidden(BoardEntity entity) {
        if (!entity.isRevealed()) {
            entity.reveal();
        }
    }

    private boolean unblockIfNecessary(BoardEntity entity) {
        if (entity.isFarmerKing() || !entity.isBlocked()) {
            return false;
        }

        Unit unit = (Unit) entity;
        unit.unblock();
        return true;
    }

    private boolean isAdjacentTo(Position source, Position target, int distance) {
        int rowDiff = Math.abs(source.getRow() - target.getRow());
        int colDiff = Math.abs(source.getColumn() - target.getColumn());
        return (rowDiff + colDiff) <= distance;
    }

    private EntitySnapshot createEntitySnapshot(BoardEntity entity) {
        return new EntitySnapshot(
                entity,
                game.teamView(entity.getOwner()).getName(),
                entity.isFarmerKing(),
                !entity.isRevealed()
        );
    }
}