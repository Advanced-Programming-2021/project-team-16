package server.modell.card.monster;

import server.modell.Board;
import server.modell.Game;

public interface specialSummonable {
    static void tribute(int[] monsterZoneIndexes, Game game) {
        Board board = game.getCurrentPlayer().getBoard();
        for (int monsterZoneIndex : monsterZoneIndexes) {
            Monster monster = board.getMonsterZone()[monsterZoneIndex];
            game.removeCardFromZone(monster, Board.Zone.MONSTER, monsterZoneIndex, board);
            game.putCardInZone(monster, Board.Zone.GRAVE, null, board);
        }
    }
}
