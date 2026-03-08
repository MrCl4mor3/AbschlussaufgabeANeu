package edu.kit.kastel.crownoffarmland.ui.snapshots;

public final class TeamStateSnapshot {
    private final String teamName;
    private final int lifePoints;
    private final int remainingDeckCards;
    private final int placedUnits;


    public TeamStateSnapshot(String teamName, int lifePoints, int remainingDeckCards, int placedUnits) {
        this.teamName = teamName;
        this.lifePoints = lifePoints;
        this.remainingDeckCards = remainingDeckCards;
        this.placedUnits = placedUnits;
    }

    public String getTeamName() {
        return teamName;
    }

    public int getLifePoints() {
        return lifePoints;
    }

    public int getRemainingDeckCards() {
        return remainingDeckCards;
    }

    public int getPlacedUnits() {
        return placedUnits;
    }
}
