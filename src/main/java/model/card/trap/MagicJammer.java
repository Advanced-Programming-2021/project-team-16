package model.card.trap;

import controller.GameMenu;
import model.Board;
import model.Game;
import model.card.Card;
import view.CommandProcessor;

public class MagicJammer extends Trap {
    public MagicJammer() {
        super("MagicJammer", "Trap", TrapType.COUNTER, "When a Spell Card is activated: Discard 1 card; negate the activation, and if you do, destroy it.", "Unlimited", 3000);
    }


    public String action(int myIndex) {
        Game game = GameMenu.getCurrentGame();
        Board myBoard = game.getCurrentPlayer().getBoard();
        if (myBoard.getNumberOfHandCards() == 0) return "can't activate. no hand cards for tribute";
        int[] tributes = CommandProcessor.getTribute(1, false);
        if (tributes == null) return "cancelled";
        Card tribute = myBoard.getHand()[tributes[0]];
        game.removeCardFromZone(tribute, Board.Zone.HAND, tributes[0], myBoard);
        game.putCardInZone(tribute, Board.Zone.GRAVE, null, myBoard);
        Board rivalBoard = game.getRival().getBoard();
        game.removeCardFromZone(game.getSelectedCard(), game.getSelectedZone(), game.getSelectedZoneIndex(), rivalBoard);
        game.putCardInZone(game.getSelectedCard(), Board.Zone.GRAVE, null, rivalBoard);
        return super.action(myIndex) + "and spell activation was cancelled and spell was destroyed";
    }
}








