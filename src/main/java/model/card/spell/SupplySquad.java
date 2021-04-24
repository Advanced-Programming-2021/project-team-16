package model.card.spell;

import model.Board;
import model.Deck;
import model.Game;
import model.card.Card;

public class SupplySquad extends Spell {
    public SupplySquad() {
        super("Supply Squad", "Spell", SpellType.CONTINUES
                , "Once per turn, if a monster(s) you control is destroyed by battle or card effect: Draw 1 card.", "Unlimited", 4000);
    }

    public void action(Game game) {
        Board board = game.getCurrentPlayer().getBoard();
        Deck deck = game.getCurrentPlayer().getUser().getActiveDeck();
        Card card = deck.drawOneCard(game, board);
    }

}
