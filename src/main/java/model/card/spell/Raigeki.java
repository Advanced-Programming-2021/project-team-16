package model.card.spell;

import controller.GameMenu;
import model.Board;
import model.Game;
import model.card.monster.Monster;

public class Raigeki extends Spell {
    private boolean isAtivated = false;

    public Raigeki() {
        super("Raigeki", "Spell", SpellType.NORMAL
                , "Destroy all monsters your opponent controls.", "Limited", 2500);
    }

    public String action() {
        Game game = GameMenu.getCurrentGame();
        Board board = game.getRival().getBoard();
        for (int index = 0; index < board.getMonsterZone().length; index++) {
            Monster monster = board.getMonsterZone()[index];
            if (monster != null)
                game.putCardInZone(monster, Board.Zone.GRAVE, null, board);
            game.removeCardFromZone(monster, Board.Zone.MONSTER, index, board);
            isAtivated = true;
            super.action();
        }
        return null;
    }

    public boolean isActivated() {
        return isAtivated;
    }
}
