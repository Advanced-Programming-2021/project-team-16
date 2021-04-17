package model.card.monster.done;

import model.Board;
import model.Game;
import model.card.Card;
import model.card.monster.Monster;

public class TheTricky extends Monster {
    public TheTricky() {
    }

    public String action(int handIndex, Game game) {
        Board board = game.getCurrentPlayer().getBoard();
        Card card = board.getHand()[handIndex];
        if (card == null) return "There is no card in this hand index";
        game.putCardInZone(card, Board.Zone.GRAVE, null, board);
        game.removeCardFromZone(card, Board.Zone.HAND, handIndex, board);
        game.putCardInZone(this, Board.Zone.MONSTER, Board.CardPosition.ATK, board);
        game.removeCardFromZone(this, Board.Zone.HAND, handIndex, board);

    }
}
