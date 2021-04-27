package model.card.spell.done;

import model.Board;
import model.Game;
import model.card.monster.Monster;
import model.card.spell.Spell;

public class Raigeki extends Spell {
    public Raigeki() {
        super("Raigeki", "Spell", SpellType.NORMAL
                , "Destroy all monsters your opponent controls.", "Limited", 2500);
    }

    public String action(Game game) {

        Board board = game.getRival().getBoard();
        for (int index = 0; index < board.getMonsterZone().length; index++) {
            Monster monster = board.getMonsterZone()[index];
            if (monster != null)
                game.putCardInZone(monster, Board.Zone.GRAVE, null, board);
            game.removeCardFromZone(monster, Board.Zone.MONSTER, index, board);
            super.action(game);
        }
        return null;
    }
}
