package server.modell.card.monster;


import controller.GameMenu;
import model.Board;
import server.controller.GameServer;
import server.modell.Game;


public class GateGuardian extends Monster implements specialSummonable {
    public GateGuardian() {
        super("GateGuardian", "Cannot be Normal Summoned/Set. Must first be Special Summoned (from your " +
                        "hand) by Tributing 1 \"Sanga of the Thunder\", \"Kazejin\", and \"Suijin\".", 20000,
                MonsterType.WARRIOR, 11, 3750, 3400);
    }

    public String specialSummon(int[] tributes, int handZoneIndexOfThis) {
        Game game = GameServer.getCurrentGame();
        Board board = game.getCurrentPlayer().getBoard();
        if (tributes == null) return "special summon cancelled";
        specialSummonable.tribute(tributes, game);
        game.removeCardFromZone(this, Board.Zone.HAND, handZoneIndexOfThis, board);
        game.putCardInZone(this, Board.Zone.MONSTER, Board.CardPosition.ATK, board);
        return this.getName() + " special summoned successfully";

    }
}