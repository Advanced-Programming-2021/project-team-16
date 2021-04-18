package model.card.monster.done;

import model.Board;
import model.Game;
import model.card.monster.Monster;

public class GateGuardian extends Monster {
    public GateGuardian() {
        super("Gate Guardian", "Cannot be Normal Summoned/Set. Must first be Special Summoned (from your " +
                        "hand) by Tributing 1 \"Sanga of the Thunder\", \"Kazejin\", and \"Suijin\".", 20000,
                MonsterType.WARRIOR, 11, 3750, 3400);
    }

    public void specialSummon(int[] monsterZoneIndexes, int handZoneIndex, Game game) {
        Board board = game.getCurrentPlayer().getBoard();
        for (int monsterZoneIndex : monsterZoneIndexes) {
            Monster monster = board.getMonsterZone()[monsterZoneIndex];
            game.putCardInZone(monster, Board.Zone.GRAVE, null, board);
            game.removeCardFromZone(monster, Board.Zone.MONSTER, monsterZoneIndex, board);
        }
        game.removeCardFromZone(this, Board.Zone.HAND, handZoneIndex, board);
        game.putCardInZone(this, Board.Zone.MONSTER, Board.CardPosition.ATK, board);
    }
}
