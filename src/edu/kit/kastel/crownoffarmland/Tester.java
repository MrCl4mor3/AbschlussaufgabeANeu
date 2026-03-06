package edu.kit.kastel.crownoffarmland;

import edu.kit.kastel.crownoffarmland.model.board.Board;
import edu.kit.kastel.crownoffarmland.model.board.Position;
import edu.kit.kastel.crownoffarmland.model.team.TeamID;
import edu.kit.kastel.crownoffarmland.model.units.BoardEntity;
import edu.kit.kastel.crownoffarmland.model.units.FarmerKing;
import edu.kit.kastel.crownoffarmland.model.units.StatusValue;
import edu.kit.kastel.crownoffarmland.model.units.Unit;
import edu.kit.kastel.crownoffarmland.model.units.UnitName;
import edu.kit.kastel.crownoffarmland.startup.config.Verbosity;
import edu.kit.kastel.crownoffarmland.ui.renderer.BoardEntityTokenFormatter;
import edu.kit.kastel.crownoffarmland.ui.renderer.BoardRenderer;
import edu.kit.kastel.crownoffarmland.ui.renderer.boardsymbols.CustomBoardSymbolSet;
import edu.kit.kastel.crownoffarmland.ui.renderer.boardsymbols.StandardBoardSymbolSet;

import java.util.HashSet;
import java.util.Set;

public class Tester {
    public static void main(String[] args) {
        Board board = new Board();

        FarmerKing king1 = new FarmerKing(TeamID.TEAM_1);
        FarmerKing king2 = new FarmerKing(TeamID.TEAM_2);
        board.setOccupant(new Position(1, 'D'), king1);
        board.setOccupant(new Position(7, 'D'), king2);

        Unit unit1 = new Unit(TeamID.TEAM_1, new UnitName("Test", "Bär"), new StatusValue(101, 100));
        Unit unit2 = new Unit(TeamID.TEAM_2, new UnitName("Test", "Bär"), new StatusValue(101, 100));
        board.setOccupant(new Position(2, 'B'), unit1);
        board.setOccupant(new Position(6, 'F'), unit2);


        Position pos = new Position(1, 'G');
        TeamID team = TeamID.TEAM_1;
        Verbosity verbosity = Verbosity.COMPACT;

        Set<BoardEntity> set = new HashSet<>();
        set.add(unit1);
        set.add(unit2);
        set.add(king1);

        String symbols = "abcdefghijklmnopqrstuvwxyzäöü";
        //BoardRenderer renderer = new BoardRenderer(new CustomBoardSymbolSet(symbols), new BoardEntityTokenFormatter(), verbosity);
        BoardRenderer renderer = new BoardRenderer(new StandardBoardSymbolSet(), new BoardEntityTokenFormatter(), verbosity);
        String output = renderer.renderBoard(board, pos, team, set);
        System.out.println(output);
    }
}
