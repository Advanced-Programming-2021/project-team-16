package model.card.monster;

import model.Game;

public class GateGuardian extends Monster implements specialSummonable {
    public GateGuardian() {
        super("Gate Guardian", "Cannot be Normal Summoned/Set. Must first be Special Summoned (from your " +
                        "hand) by Tributing 1 \"Sanga of the Thunder\", \"Kazejin\", and \"Suijin\".", 20000,
                MonsterType.WARRIOR, 11, 3750, 3400);
    }

            public void specialSummon(int[] monsterZoneIndexes,/* int handZoneIndex,*/ Game game) {
                specialSummonable.tribute(monsterZoneIndexes, game);
                // Board board = game.getCurrentPlayer().getBoard();
                // game.removeCardFromZone(this, Board.Zone.HAND, handZoneIndex, board);
                // game.putCardInZone(this, Board.Zone.MONSTER, Board.CardPosition.ATK, board);
            }
}
