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

    public static void removeRivalCards(Board.Zone zone) {
        Card[] cards;
        Game game = GameMenu.getCurrentGame();
        Board board = game.getRival().getBoard();
        if (zone == Board.Zone.MONSTER) cards = board.getMonsterZone();
        else if (zone == Board.Zone.SPELL_AND_TRAP) cards = board.getSpellAndTrapZone();
        else if (zone == Board.Zone.FIELD_SPELL) {
            game.removeCardFromZone(board.getFieldSpell(), Board.Zone.FIELD_SPELL, 0, board);
            return;
        } else return;
        for (int index = 0; index < cards.length; index++) {
            Card card = cards[index];
            if (card != null) {
                game.putCardInZone(card, Board.Zone.GRAVE, null, board);
                game.removeCardFromZone(card, Board.Zone.MONSTER, index, board);
            }
        }
    }
}
