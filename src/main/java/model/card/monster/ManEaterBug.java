package model.card.monster;

import controller.GameMenu;
import model.Board;
import model.Game;
import model.card.Card;
import view.CommandProcessor;

import java.util.ArrayList;
import java.util.HashMap;

public class ManEaterBug extends Monster {
    public ManEaterBug() {
        super("Man-Eater Bug", "FLIP: Target 1 monster on the field; destroy that target.", 800,
                MonsterType.INSECT, 2, 800, 600);
    }

    public String action() {
        Game game = GameMenu.getCurrentGame();
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
            game.putCardInZone(monster, Board.Zone.GRAVE, null, board);
            game.removeCardFromZone(monster, Board.Zone.MONSTER, monsterZoneIndex, board);
        }
        return this.getName() + "activated successfully";
    }
}
