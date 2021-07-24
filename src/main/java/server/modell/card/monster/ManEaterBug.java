package server.modell.card.monster;

import controller.GameMenu;
import model.Board;
import server.controller.GameServer;
import server.modell.Game;
import server.modell.card.Card;
import view.CommandProcessor;

import java.util.ArrayList;
import java.util.HashMap;

public class ManEaterBug extends Monster {
    public ManEaterBug() {
        super("Man-EaterBug", "FLIP: Target 1 monster on the field; destroy that target.", 100,
                MonsterType.INSECT, 2, 800, 600);
    }

    public String action() {
        Game game = GameServer.getCurrentGame();
        Board board = game.getRival().getBoard();
        if (board.getNumberOfMonsters() == 0 || !CommandProcessor.yesNoQuestion("do you want to active " + this.getName() + " and destroy one of rival's monsters?"))
            return "cancelled";
        Monster[] monsterZone = board.getMonsterZone();
        ArrayList<Card> monsters = new ArrayList<>();
        HashMap<Card, Integer> monstersWithIndex = new HashMap<>();
        for (int i = 0; i < monsterZone.length; i++) {
            if (monsterZone[i] != null) {
                monsters.add(monsterZone[i]);
                monstersWithIndex.put(monsterZone[i], i);
            }
        }
        int index = CommandProcessor.getIndexOfCardArray(monsters, "(rival monster for destroying)");
        if (index == -1) return "cancelled";
        int monsterZoneIndex = monstersWithIndex.get(monsters.get(index));
        Monster monster = board.getMonsterZone()[monsterZoneIndex];
        if (monster != null) {
            game.removeCardFromZone(monster, Board.Zone.MONSTER, monsterZoneIndex, board);
            game.putCardInZone(monster, Board.Zone.GRAVE, null, board);
        }
        return this.getName() + "activated successfully";
    }
}
