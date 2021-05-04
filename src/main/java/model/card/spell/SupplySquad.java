package model.card.spell;

import controller.GameMenu;
import model.Board;
import model.Deck;
import model.Game;
import model.card.Card;

public class SupplySquad extends Spell {
    private boolean isAtivated = false;
    public SupplySquad() {
        super("Supply Squad", "Spell", SpellType.CONTINUES
                , "Once per turn, if a monster(s) you control is " +
                        "destroyed by battle or card effect: Draw 1 card.", "Unlimited", 4000);
    }


    public String action() {

        Game game = GameMenu.getCurrentGame();
        Board board = game.getCurrentPlayer().getBoard();
        Deck deck = game.getCurrentPlayer().getUser().getActiveDeck();
        Card card = deck.drawOneCard(game, board);
        if (card == null)
            return null;
        game.putCardInZone(card, Board.Zone.HAND, null, board);
        isAtivated = true;
        super.action();
        return "Supply squad's effect was activated";
    }

    public boolean isActivated() {
        return isAtivated;
    }
}
