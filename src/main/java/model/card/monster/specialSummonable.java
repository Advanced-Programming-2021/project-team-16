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
    //  static String tribute(int monsterZoneIndex, int handZoneIndex, Game game) {
    //      Board board = game.getCurrentPlayer().getBoard();
    //      // for (int monsterZoneIndex : monsterZoneIndexes) {
    //      Monster monster = board.getMonsterZone()[monsterZoneIndex];
    //      Monster specialMonster = (Monster) board.getHand()[handZoneIndex];
    //      if (board.getMonsterZone()[monsterZoneIndex] == null) {
    //          return "there no monsters one this address";
    //      }
    //      game.putCardInZone(monster, Board.Zone.GRAVE, null, board);
    //      game.removeCardFromZone(monster, Board.Zone.MONSTER, monsterZoneIndex, board);
//
    //      game.removeCardFromZone(specialMonster, Board.Zone.HAND, handZoneIndex, board);
//
    //      game.putCardInZone(specialMonster,/*board.getFirstEmptyIndexOfZone() */ Board.Zone.MONSTER, Board.CardPosition.ATK, board);/////////
    //      return "summoned successfully";
    //  }

//    static String specialSummon(int monsterZoneIndex, int handZoneIndex, Game game) {
//        ArrayList<Monster> monsters = new ArrayList<>();
//        Board board = game.getCurrentPlayer().getBoard();
//        Monster monster = board.getMonsterZone()[monsterZoneIndex];
//        Monster specialMonster = (Monster) board.getHand()[handZoneIndex];
//        if (board.howManyMonsters() < 3) {
//            return "there are not enough cards for tribute";
//        }
//        for (int i = 0; i < 3; i++) {
//            monsters.add(monster);
//        }
//        if (monsters.size() == 3) {
//            for (int i = 0; i < 3; i++) {
//                game.removeCardFromZone(monster, Board.Zone.MONSTER, monsterZoneIndex, board);
//                game.putCardInZone(monster, Board.Zone.GRAVE, null, board);
//            }
//        }else  {
//            return "there is no monster on one of these addresses";
//        }
//
//        game.removeCardFromZone(specialMonster, Board.Zone.HAND, handZoneIndex, board);
//        game.putCardInZone(specialMonster, Board.Zone.MONSTER, Board.CardPosition.ATK, board);
//        return "summoned successfully";
//    }
}
