package edu.kit.kastel.crownoffarmland.gameplay;

import edu.kit.kastel.crownoffarmland.exceptions.gamestateexceptions.EmptySelectedFieldException;
import edu.kit.kastel.crownoffarmland.exceptions.gamestateexceptions.EnemyUnitSelectedException;
import edu.kit.kastel.crownoffarmland.exceptions.InvalidGameStateException;
import edu.kit.kastel.crownoffarmland.exceptions.gamestateexceptions.NoSelectionException;
import edu.kit.kastel.crownoffarmland.exceptions.gamestateexceptions.UnitAlreadyActedException;
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
 * Service class responsible for handling unit movement logic in the game.
 * It validates move actions, updates the game state accordingly, and generates snapshots of the move for UI updates.
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
     * Creates a new instance of MovementService with the provided dependencies.
     * @param game the game instance to be manipulated by this service
     * @param unitMerger the UnitMerger instance used for merging units during movement
     * @param turnState the TurnState instance used for managing the current turn state during movement
     * @param duelManager the DuelManager instance used for handling combat interactions that may occur during movement
     */
    public MovementService(Game game, UnitMerger unitMerger, TurnState turnState, DuelManager duelManager) {
        this.game = game;
        this.unitMerger = unitMerger;
        this.turnState = turnState;
        this.duelManager = duelManager;
    }

    /**
     * Moves a unit from its current position to the specified target position, if the move is valid according to the game rules.
     * This method checks if the target position is within the allowed move distance, if it is occupied by an enemy unit, and if the move
     * is legal based on the current turn state.
     * @param target the target position to which the unit should be moved
     * @param currentTeam the team ID of the player attempting to move the unit, used for validating ownership and turn state
     * @throws InvalidGameStateException if the move is invalid due to game rules
     * @return a MoveSnapshot containing the details of the move, including the moved entity and the target position name, or null if the
     *      move is invalid
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


    private MoveSnapshot resolveFarmerKingMove(Position source, Position target, BoardEntity selectedEntity, boolean wasBlocked) {
        // ToDo evtl. zurückgeben, dass die Einheit in Target Pos ersetzt wurde, falls nicht leer!
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
        }

        return new MergeMoveSnapshot(createEntitySnapshot(selectedUnit), target.toString(), wasBlocked, mergeResult.isSuccessful(),
                targetUnit.getName().toString());
    }

    private MoveSnapshot resolveDuelMove(Position source, Position target, Unit attacker, BoardEntity defender, boolean wasBlocked) {
        EntitySnapshot targetEntitySnapshot = createEntitySnapshot(defender);
        EntitySnapshot sourceEntitySnapshot = createEntitySnapshot(attacker);

        revealIfHidden(defender);
        revealIfHidden(attacker);

        DuelResult duelResult = duelManager.resolveDuel(attacker, defender);
        updateGameStateAfterDuel(source, target, attacker, defender, duelResult);

        String loserName;

        if (!(game.getWinnerID() == null)) {
            loserName = game.teamView(game.getWinnerID().getNext()).getName();
        } else {
            loserName = null;
        }
        return new DuelMoveSnapshot(sourceEntitySnapshot, targetEntitySnapshot, source.toString(), target.toString(), wasBlocked,
                duelResult, loserName);
    }


    private void updateGameStateAfterDuel(Position source, Position target, Unit attacker, BoardEntity defender,
        DuelResult duelResult) {
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

    private void updateWinnerAfterDuel(Unit attacker, BoardEntity defender) {
        TeamID attackerTeam = attacker.getOwner();
        TeamID defenderTeam = defender.getOwner();

        if (game.teamView(attackerTeam).getLifePoints() <= 0) {
            game.setWinner(defenderTeam);
        } else if (game.teamView(defenderTeam).getLifePoints() <= 0) {
            game.setWinner(attackerTeam);
        }
    }

    private void validateMove(Position source, Position target, BoardEntity selectedEntity) throws InvalidGameStateException {
        if (!isAdjacentTo(source, target, MAX_MOVE_DISTANCE)) {
            throw new InvalidGameStateException("Target position is too far away for a move.");
        }

        // Selected Entity is not allowed to move to the Position of his own King
        BoardEntity targetEntity = game.boardView().getOccupant(target);

        if (!(targetEntity == null)) {
            // If the selected entity is a Farmer King, it cannot move onto a position occupied by an enemy unit
            if (selectedEntity.isFarmerKing() && !targetEntity.getOwner().equals(selectedEntity.getOwner())) {
                throw new InvalidGameStateException("Cannot move a Farmer King onto a position occupied by an enemy unit.");
            }
            // Selected Entity is not allowed to move to the Position of his own King
            if (!selectedEntity.isFarmerKing() && targetEntity.getOwner().equals(selectedEntity.getOwner())
                    && targetEntity.isFarmerKing()) {
                throw new InvalidGameStateException("Cannot move onto a position occupied by your own Farmer King.");
            }
        }
    }

    private boolean isAdjacentTo(Position source, Position target, int distance) {
        int rowDiff = Math.abs(source.getRow() - target.getRow());
        int colDiff = Math.abs(source.getColumn() - target.getColumn());
        return (rowDiff + colDiff) <= distance;
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

        if (turnState.getMovedEntities().contains(entity)) {
            throw new UnitAlreadyActedException(entity.getName().toString());
        }

        return entity;
    }

    private EntitySnapshot createEntitySnapshot(BoardEntity entity) {
        return new EntitySnapshot(entity, game.teamView(entity.getOwner()).getName(), entity.isFarmerKing(), !entity.isRevealed());
    }
}
