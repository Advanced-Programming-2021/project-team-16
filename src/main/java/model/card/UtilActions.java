package model.card;

import controller.GameMenu;
import model.Board;
import model.Game;
import model.card.monster.Monster;
import consoleView.CommandProcessor;

import java.util.ArrayList;
import java.util.HashMap;

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
            if (board.getFieldSpell() != null)
                game.removeCardFromZone(board.getFieldSpell(), Board.Zone.FIELD_SPELL, 0, board);
            return;
        } else return;
        for (int index = 0; index < cards.length; index++) {
            Card card = cards[index];
            if (card != null) {
                game.removeCardFromZone(card, zone, index, board);
                game.putCardInZone(card, Board.Zone.GRAVE, null, board);
            }
        }
    }

    public static void removeAllMonsters() {
        Game game = GameMenu.getCurrentGame();
        Board board = game.getCurrentPlayer().getBoard();
        for (int index = 0; index < board.getMonsterZone().length; index++) {
            Monster monster = board.getMonsterZone()[index];
            if (monster != null) {
                game.removeCardFromZone(monster, Board.Zone.MONSTER, index, board);
                game.putCardInZone(monster, Board.Zone.GRAVE, null, board);
            }
        }
        UtilActions.removeRivalCards(Board.Zone.MONSTER);
    }

    public static String getSpellIndexAndThrowAway(ArrayList<Card> spells, HashMap<Card, Integer> spellsWithIndex, int handIndexTribute) {
        Game game = GameMenu.getCurrentGame();
        Board rivalBoard = game.getRival().getBoard();
        Board myBoard = game.getCurrentPlayer().getBoard();
        int spellIndex;
        spellIndex = CommandProcessor.getIndexOfCardArray(spells, "throw away spell or trap from rival's board");
        if (spellIndex == -1) return "cancelled";
        if (handIndexTribute != -1) {
            game.removeCardFromZone(myBoard.getCardByIndexAndZone(handIndexTribute, Board.Zone.HAND), Board.Zone.HAND, handIndexTribute, myBoard);
            game.putCardInZone(myBoard.getCardByIndexAndZone(handIndexTribute, Board.Zone.HAND), Board.Zone.GRAVE, null, myBoard);
        }
        game.removeCardFromZone(spells.get(spellIndex), Board.Zone.SPELL_AND_TRAP, spellsWithIndex.get(spells.get(spellIndex)), rivalBoard);
        game.putCardInZone(spells.get(spellIndex), Board.Zone.GRAVE, null, rivalBoard);
        spellsWithIndex.remove(spells.get(spellIndex));
        spells.remove(spellIndex);
        return null;
    }
}
