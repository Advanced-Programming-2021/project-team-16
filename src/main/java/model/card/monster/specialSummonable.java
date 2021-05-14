package model.card.monster;

import model.Board;
import model.Game;

public interface specialSummonable {
    static void tribute(int[] monsterZoneIndexes, Game game) {
        Board board = game.getCurrentPlayer().getBoard();
        for (int monsterZoneIndex : monsterZoneIndexes) {
            Monster monster = board.getMonsterZone()[monsterZoneIndex];
            game.putCardInZone(monster, Board.Zone.GRAVE, null, board);
            game.removeCardFromZone(monster, Board.Zone.MONSTER, monsterZoneIndex, board);
        }
    }
}
