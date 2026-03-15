package edu.kit.kastel.crownoffarmland.gameplay.ai.decision;

import edu.kit.kastel.crownoffarmland.gameplay.TurnState;
import edu.kit.kastel.crownoffarmland.gameplay.ai.decision.model.UnitActionDecision;
import edu.kit.kastel.crownoffarmland.gameplay.unitmerge.UnitMerger;
import edu.kit.kastel.crownoffarmland.model.Game;
import edu.kit.kastel.crownoffarmland.model.board.Position;
import edu.kit.kastel.crownoffarmland.model.team.TeamID;
import edu.kit.kastel.crownoffarmland.model.units.Unit;

import java.util.ArrayList;
import java.util.List;

/**
 * Provides decision logic for AI actions.
 *
 * @author ucgdi
 */
public final class AIDecisionService {
    private static final int HAND_INDEX_OFFSET = 1;

    private final Game game;
    private final WeightedRandomSelector weightedRandomSelector;
    private final KingMoveDecider kingMoveDecider;
    private final PlacementDecider placementDecider;
    private final UnitActionDecider unitActionDecider;

    /**
     * Creates a new AI decision service.
     *
     * @param game the current game
     * @param turnState the current turn state
     * @param unitMerger the unit merger
     * @param weightedRandomSelector the weighted random selector
     */
    public AIDecisionService(Game game, TurnState turnState, UnitMerger unitMerger, WeightedRandomSelector weightedRandomSelector) {
        this.game = game;
        this.weightedRandomSelector = weightedRandomSelector;

        BoardAnalysisService boardAnalysisService = new BoardAnalysisService(game);
        this.kingMoveDecider = new KingMoveDecider(game, boardAnalysisService, weightedRandomSelector);
        this.placementDecider = new PlacementDecider(game, boardAnalysisService, weightedRandomSelector);
        this.unitActionDecider = new UnitActionDecider(game, turnState, unitMerger, boardAnalysisService, weightedRandomSelector
        );
    }

    /**
     * Chooses the king's next position.
     *
     * @return the selected target position
     */
    public Position chooseKingMove() {
        return kingMoveDecider.chooseKingMove();
    }

    /**
     * Chooses a position for placing a unit.
     *
     * @return the selected position, or {@code null} if none is available
     */
    public Position choosePlacementPosition() {
        return placementDecider.choosePlacementPosition();
    }

    /**
     * Chooses the hand card to place.
     *
     * @return the selected hand index, starting at 1
     */
    public int choosePlacementHandIndex() {
        TeamID currentTeam = game.getCurrentTeamID();
        List<Integer> atkWeights = new ArrayList<>();

        for (int handIndex = 0; handIndex < game.teamView(currentTeam).getHandSize(); handIndex++) {
            atkWeights.add(game.getHandCardAt(currentTeam, handIndex).getAtk());
        }

        int selectedIndex = weightedRandomSelector.selectWeightedRandom(atkWeights);
        return selectedIndex + HAND_INDEX_OFFSET;
    }

    /**
     * Chooses the next action for a unit.
     *
     * @return the selected unit action, or {@code null} if no action is possible
     */
    public UnitActionDecision chooseNextUnitAction() {
        return unitActionDecider.chooseNextUnitAction();
    }

    /**
     * Chooses the card to discard.
     *
     * @return the selected hand index, starting at 1
     */
    public int chooseDiscardIndex() {
        List<Integer> discardWeights = new ArrayList<>();
        TeamID currentTeam = game.getCurrentTeamID();

        for (int handIndex = 0; handIndex < game.teamView(currentTeam).getHandSize(); handIndex++) {
            Unit handCard = game.getHandCardAt(currentTeam, handIndex);
            discardWeights.add(handCard.getAtk() + handCard.getDef());
        }

        int selectedIndex = weightedRandomSelector.selectInverseWeightedRandom(discardWeights);
        return selectedIndex + HAND_INDEX_OFFSET;
    }
}