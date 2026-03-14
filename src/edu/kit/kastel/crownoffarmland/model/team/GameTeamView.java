package edu.kit.kastel.crownoffarmland.model.team;



public interface GameTeamView {
    String getName();
    int getLifePoints();

    int getHandSize();
    boolean isHandFull();

    int getDrawPileSize();
    boolean isDrawPileEmpty();
}

