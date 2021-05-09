package model.card.monster;


import model.Board;
import model.Game;

import java.util.ArrayList;


public class GateGuardian extends Monster implements specialSummonable {
    public GateGuardian() {
        super("Gate Guardian", "Cannot be Normal Summoned/Set. Must first be Special Summoned (from your " +
                        "hand) by Tributing 1 \"Sanga of the Thunder\", \"Kazejin\", and \"Suijin\".", 20000,
                MonsterType.WARRIOR, 11, 3750, 3400);
    }

    public String action(int monsterZoneIndex, int handZoneIndex, Game game) {
        ArrayList<Monster> monsters = new ArrayList<>();
        Board board = game.getCurrentPlayer().getBoard();
        Monster monster = board.getMonsterZone()[monsterZoneIndex];
        Monster specialMonster = (Monster) board.getHand()[handZoneIndex];
        if (board.howManyMonsters() < 3) {
            return "there are not enough cards for tribute";
        }
        for (int i = 0; i < 3; i++) {
            monsters.add(monster);
        }
        if (monsters.size() == 3) {
            for (int i = 0; i < 3; i++) {
                game.removeCardFromZone(monster, Board.Zone.MONSTER, monsterZoneIndex, board);
                game.putCardInZone(monster, Board.Zone.GRAVE, null, board);
            }
        } else {
            return "there is no monster on one of these addresses";
        }
        game.removeCardFromZone(specialMonster, Board.Zone.HAND, handZoneIndex, board);
        game.putCardInZone(specialMonster, Board.Zone.MONSTER, Board.CardPosition.ATK, board);
        return "summoned successfully";
        // specialSummonable.specialSummon(monsterZoneIndex, handZoneIndexOfThis, game);
    }
}