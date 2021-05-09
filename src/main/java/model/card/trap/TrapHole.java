package model.card.trap;

import model.Board;
import model.Game;
import model.card.Card;

public class TrapHole extends Trap {
    public TrapHole() {
        super("TrapHole", "Trap", TrapType.NORMAL, "When your opponent Normal or Flip Summons 1 monster with 1000 or more ATK: Target that monster; destroy that target.", "Unlimited", 2000
        );
    }

    public String action(Card monster, int index, Game game) {
        Board board = game.getCurrentPlayer().getBoard();
        game.removeCardFromZone(monster, Board.Zone.MONSTER, index, board);
        game.putCardInZone(monster, Board.Zone.GRAVE, null, board);
        return "The monster is killed successfully!";
    }
}