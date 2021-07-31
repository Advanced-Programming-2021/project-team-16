package model.card.trap;

import controller.GameMenu;
import model.Board;
import model.Game;
import model.card.Card;
import model.card.monster.Monster;

public class TrapHole extends Trap {
    public TrapHole() {
        super("TrapHole", "Trap", TrapType.NORMAL, "When your opponent Normal or Flip Summons 1 monster with 1000 or more ATK: Target that monster; destroy that target.", "Unlimited", 2000
        );
    }

    public String action(int myIndex) {
        Game game = GameMenu.getCurrentGame();
        Board board = game.getRival().getBoard();
        Card monster = game.getSelectedCard();
        Monster[] monsterZone = board.getMonsterZone();
        for (int i = 0, monsterZoneLength = monsterZone.length; i < monsterZoneLength; i++) {
            Monster monster1 = monsterZone[i];
            if (monster == monster1) {
                game.removeCardFromZone(monster, Board.Zone.MONSTER, i, board);
                game.putCardInZone(monster, Board.Zone.GRAVE, null, board);
                return super.action(myIndex) + "and rival's monster was destroyed";
            }
        }
        return "rival monster was not summoned";
    }
}