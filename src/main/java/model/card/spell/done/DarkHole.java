package model.card.spell.done;

import model.Board;
import model.Game;
import model.card.monster.Monster;
import model.card.spell.Spell;

public class DarkHole extends Spell {
    public DarkHole() {
        super("Dark Hole", "Spell", SpellType.NORMAL
                , "Destroy all monsters on the field.", "Unlimited", 2500);
    }

    public void action(Game game) {
        Board board = game.getCurrentPlayer().getBoard();
        for (int index = 0; index < board.getMonsterZone().length; index++) {
            Monster monster = board.getMonsterZone()[index];
            if (monster != null) game.putCardInZone(monster, Board.Zone.GRAVE, null, board);
            game.removeCardFromZone(monster, Board.Zone.MONSTER, index, board);
        }
        board = game.getRival().getBoard();
        for (int index = 0; index < board.getMonsterZone().length; index++) {
            Monster monster = board.getMonsterZone()[index];
            if (monster != null) game.putCardInZone(monster, Board.Zone.GRAVE, null, board);
            game.removeCardFromZone(monster, Board.Zone.MONSTER, index, board);
        }
    }

}
