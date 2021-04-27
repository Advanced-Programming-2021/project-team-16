package model.card.spell.done;

import model.Board;
import model.Deck;
import model.Game;
import model.card.Card;
import model.card.spell.Spell;

public class SupplySquad extends Spell {
    public SupplySquad() {
        super("Supply Squad", "Spell", SpellType.CONTINUES
                , "Once per turn, if a monster(s) you control is destroyed by battle or card effect: Draw 1 card.", "Unlimited", 4000);
    }


    public String action(Game game) {   //we need owner of the card!!


        Board board = game.getCurrentPlayer().getBoard();
        Deck deck = game.getCurrentPlayer().getUser().getActiveDeck();
        Card card = deck.drawOneCard(game, board);
        if (card == null)
            return null;
        game.putCardInZone(card, Board.Zone.HAND, null, board);
        super.action(game);
        return "Supply squad's effect was activated";
    }

}
//    TO PUT:
//    SupplySquad supplySquad = new SupplySquad();
//        supplySquad.action(game);