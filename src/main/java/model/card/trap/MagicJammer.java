package model.card.trap;

import model.Board;
import model.Game;
import model.card.Card;

public class MagicJammer extends Trap {
    public MagicJammer() {
        super("MagicJammer", "Trap", TrapType.COUNTER, "When a Spell Card is activated: Discard 1 card; negate the activation, and if you do, destroy it.", "Unlimited", 3000);
    }

    boolean spellIsActivate = true;

    public String action(Game game, int i) {     // activeEffect method
        Board board1 = game.getRival().getBoard();
        Card card1 = board1.getSpellAndTrapZone()[i];
        Board board = game.getCurrentPlayer().getBoard();
        int index = random.nextInt(4);
        Card card = board.getHand()[index];
        if (spellIsActivate) {
            game.removeCardFromZone(card, Board.Zone.HAND, index, board);
            game.putCardInZone(card, Board.Zone.GRAVE, null, board);
        }
        spellIsActivate = false;
        game.removeCardFromZone(card1, Board.Zone.SPELL_AND_TRAP, i, board1);
        game.putCardInZone(card1, Board.Zone.GRAVE, null, board1);
        return "done!";
    }
}








