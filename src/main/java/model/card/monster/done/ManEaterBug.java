package model.card.monster.done;

import model.Board;
import model.Game;
import model.card.monster.Monster;

public class ManEaterBug extends Monster {
    public ManEaterBug() {
        super("Man-Eater Bug", "FLIP: Target 1 monster on the field; destroy that target.", 800,
                MonsterType.INSECT, 2, 800, 600);
    }

    public void action(int monsterZoneIndex, Game game) {
        Board board = game.getRival().getBoard();
        Monster monster = board.getMonsterZone()[monsterZoneIndex];
        if (monster != null) {
            game.putCardInZone(monster, Board.Zone.GRAVE, null, board);
            game.removeCardFromZone(monster, Board.Zone.MONSTER, monsterZoneIndex, board);
        }
    }
}
