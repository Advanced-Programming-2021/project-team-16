package server.modell.card.monster;

import server.modell.Board;
import server.controller.GameServer;
import server.modell.Game;
import server.modell.card.UtilActions;

public class BeastKingBarbaros extends Monster implements specialSummonable {
    public BeastKingBarbaros() {
        super("BeastKingBarbaros", "You can Normal Summon/Set this card without Tributing, but its original " +
                "ATK becomes 1900. You can Tribute 3 monsters to Tribute Summon (but not Set) this card. If Summoned this" +
                " way: Destroy all cards your opponent controls.", 100, MonsterType.BEAST_WARRIOR, 8, 3000, 1200);
    }

    public void normalSummonOrSet() {
        decreaseATK(1100);
    }

    public String specialSummon(int[] monsterZoneIndex, int handZoneIndexOfThis) {
        if (monsterZoneIndex == null) return "special summon cancelled";
        Game game = GameServer.getCurrentGame();
        specialSummonable.tribute(monsterZoneIndex, game);
        Board board = game.getCurrentPlayer().getBoard();
        game.removeCardFromZone(this, Board.Zone.HAND, handZoneIndexOfThis, board);
        game.putCardInZone(this, Board.Zone.MONSTER, Board.CardPosition.ATK, board);
        UtilActions.removeRivalCards(Board.Zone.MONSTER);
        UtilActions.removeRivalCards(Board.Zone.SPELL_AND_TRAP);
        UtilActions.removeRivalCards(Board.Zone.FIELD_SPELL);
        return this.name + " special summoned successfully";
    }
}

