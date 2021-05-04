package model.card.spell;

import controller.GameMenu;
import model.Board;
import model.Game;
import model.card.monster.Monster;

public class DarkHole extends Spell {
    private boolean isAtivated = false;

    public DarkHole() {
        super("Dark Hole", "Spell", SpellType.NORMAL
                , "Destroy all monsters on the field.", "Unlimited", 2500);
    }

    public String action() {
        Game game = GameMenu.getCurrentGame();
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
        isAtivated = true;
        super.action();
        return null;
    }

    public boolean isActivated() {
        return isAtivated;
    }
}
