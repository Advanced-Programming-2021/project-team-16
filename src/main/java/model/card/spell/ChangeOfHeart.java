package model.card.spell;

import model.Board;
import model.Game;
import model.card.monster.Monster;

public class ChangeOfHeart extends Spell {
    public ChangeOfHeart() {
        super("Change of Heart", "Spell", SpellType.NORMAL
                , "Target 1 monster your opponent controls; take control of it until the End Phase.", "Limited", 2500);
    }

    public void action(Game game, Monster monster, int index) {

        Board board = game.getRival().getBoard();
        Monster[] monsters = board.getMonsterZone();
//        for (int i = 0; i < monsters.length; i++) {
//            if (monsters[index] == monster) {
//
//            }
//        }
    }
}
