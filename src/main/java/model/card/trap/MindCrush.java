package model.card.trap;

import model.Board;
import model.Game;
import model.card.Card;
import model.card.monster.Monster;
import view.CommandProcessor;
import view.Show;


public class MindCrush extends Trap {
    public MindCrush() {
        super("MindCrush", "Trap", TrapType.NORMAL, "Declare 1 card name; if that card is in your opponent's hand, they must discard all copies of it, otherwise you discard 1 random card.", "Unlimited", 2000);
    }


    public String action(Game game, Card card1, int i) {
        Board boardC = game.getCurrentPlayer().getBoard();
        Board boardR = game.getRival().getBoard();
        Show.showImportantGameMessage("say a card name to remove frome ");
        Card[] cardR = game.getRival().getBoard().getHand();
        Monster monster = (Monster) Monster.getCardByName(CommandProcessor.getCardName());
        if (monster != null) {
            for (Card rivalCard : cardR) {
               /* if ((Monster) rivalCard instanceof monster) {

                }*??????*/ //TODO
            }
        }
        return "";//????}
    }
}
