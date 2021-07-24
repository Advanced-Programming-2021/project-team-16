package server.modell.card.trap;

import controller.GameMenu;
import model.Board;
import server.controller.GameServer;
import server.modell.Game;
import server.modell.card.Activatable;
import server.modell.card.Card;
import view.CommandProcessor;

import java.util.Random;

public class MindCrush extends Trap implements Activatable {
    public MindCrush() {
        super("MindCrush", "Trap", TrapType.NORMAL, "Declare 1 card name; if that card is in your opponent's hand, they must discard all copies of it, otherwise you discard 1 random card.", "Unlimited", 2000);
    }
    private final Random random = new Random();

    public String action() {
        Game game = GameServer.getCurrentGame();
        Board myBoard = game.getCurrentPlayer().getBoard();
        Board rivalBoard = game.getRival().getBoard();
        if (myBoard.getNumberOfHandCards() == 0) return "no card in hand. can't activate this";
        super.action(game.getSelectedZoneIndex());
        String cardName = CommandProcessor.getCardName("guess rival's card");
        Card[] hand = rivalBoard.getHand();
        int numberOfGuessedCard = 0;
        for (int i = hand.length - 1; i >= 0; i--) {
            Card card = hand[i];
            if (card != null && card.getName().equals(cardName)) {
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

}