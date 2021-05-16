package model.card.spell;

import controller.GameMenu;
import model.Board;
import model.Game;
import model.card.Card;

public class UtilActions {
    public static String drawCardsForCurrentPlayer(int numberOfCards) {
        Game game = GameMenu.getCurrentGame();
        Board board = game.getCurrentPlayer().getBoard();
        int freeSpaceInHand = 0;
        for (Card card : board.getHand()) if (card == null) freeSpaceInHand++;
        if (freeSpaceInHand < numberOfCards) return "you can't activate this. (not enough free space in your hand)";
        if (board.getDeck().size() < numberOfCards) return "you can't activate this. (not enough deck cards)";
        for (int i = 0; i < numberOfCards; i++) {
            Card drewCard = board.getCardByIndexAndZone(board.getDeck().size() - 1, Board.Zone.DECK);
            game.removeCardFromZone(drewCard, Board.Zone.DECK, 0, board);
            game.putCardInZone(drewCard, Board.Zone.HAND, null, board);
        }
        return null;
    }
}
