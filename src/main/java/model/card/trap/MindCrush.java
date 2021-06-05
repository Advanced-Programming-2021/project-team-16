package model.card.trap;

import controller.GameMenu;
import model.Board;
import model.Game;
import model.card.Activatable;
import model.card.Card;
import view.CommandProcessor;

import java.util.Random;

public class MindCrush extends Trap implements Activatable {
    public MindCrush() {
        super("MindCrush", "Trap", TrapType.NORMAL, "Declare 1 card name; if that card is in your opponent's hand, they must discard all copies of it, otherwise you discard 1 random card.", "Unlimited", 2000);
    }
    private Random random = new Random();

    public String action() {
        Game game = GameMenu.getCurrentGame();
        Board myBoard = game.getCurrentPlayer().getBoard();
        Board rivalBoard = game.getRival().getBoard();
        if (myBoard.getNumberOfHandCards() == 0) return "no card in hand. can't activate this";
        super.action(game.getSelectedZoneIndex());
        String cardName = CommandProcessor.getCardName("guess rival's card");
        Card[] hand = rivalBoard.getHand();
        int numberOfGuessedCard = 0;
        for (int i = 0; i < hand.length; i++) {
            Card card = hand[i];
            if (card.getName().equals(cardName)) {
                game.removeCardFromZone(card, Board.Zone.HAND, i, rivalBoard);
                game.putCardInZone(card, Board.Zone.GRAVE, null, rivalBoard);
                numberOfGuessedCard++;
            }
        }
        if (numberOfGuessedCard == 0) {
            hand = myBoard.getHand();
            int randomIndex = random.nextInt(6);
            while (hand[randomIndex] == null) randomIndex = random.nextInt(6);
            game.removeCardFromZone(hand[randomIndex], Board.Zone.HAND, randomIndex, myBoard);
            game.putCardInZone(hand[randomIndex], Board.Zone.GRAVE, null, myBoard);
            return "wrong guess. you lost one of your hand cards randomly";
        }
        return "well done! " + numberOfGuessedCard + " cards where removed from your rival's hand";
    }

//
//    public String action(Game game, Card card1, int i) {
//        Board boardC = game.getCurrentPlayer().getBoard();
//        Board boardR = game.getRival().getBoard();
//        Card card = Card.getCardByName(CommandProcessor.getCardName());
//        int int_random = random.nextInt(4);
//        Card[] hand = boardC.getHand();
//        Card randomCard = hand[int_random];
//        for (Card rivalCard : boardR.getHand()) {
//            if (card != null && card.equals(rivalCard)) {
//                game.removeCardFromZone(card, Board.Zone.HAND, CommandProcessor.getCardIndex(), boardR);
//                game.putCardInZone(card, Board.Zone.GRAVE, null, boardR);
//                for (Card rivalCard1 : boardR.getMonsterZone()) {
//                    if (card.equals(rivalCard1)) {
//                        game.removeCardFromZone(card, Board.Zone.MONSTER, CommandProcessor.getCardIndex(), boardR);
//                        game.putCardInZone(card, Board.Zone.GRAVE, null, boardR);
//                    }
//                }
//                for (Card rivalCard2 : boardR.getDeck()) {
//                    if (card.equals(rivalCard2)) {
//                        game.removeCardFromZone(card, Board.Zone.DECK, CommandProcessor.getCardIndex(), boardR);
//                        game.putCardInZone(card, Board.Zone.GRAVE, null, boardR);
//                    }
//                }
//                for (Card rivalCard3 : boardR.getSpellAndTrapZone()) {
//                    if (card.equals(rivalCard3)) {
//                        game.removeCardFromZone(card, Board.Zone.SPELL_AND_TRAP, CommandProcessor.getCardIndex(), boardR);
//                        game.putCardInZone(card, Board.Zone.GRAVE, null, boardR);
//                    }
//                }
//            }
//        }
//        for (Card rivalCard : boardR.getHand()) {
//            if (card != null && !card.equals(rivalCard)) {
//                game.removeCardFromZone(randomCard, Board.Zone.HAND, int_random, boardC);
//                game.putCardInZone(randomCard, Board.Zone.GRAVE, null, boardC);
//            }
//
//        }
//        return "done!";
//    }
}