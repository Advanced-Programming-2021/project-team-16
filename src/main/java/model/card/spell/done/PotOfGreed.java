package model.card.spell.done;

import model.Board;
import model.Deck;
import model.Game;
import model.card.Card;
import model.card.spell.Spell;

public class PotOfGreed extends Spell {
    public PotOfGreed() {
        super("Pot of Greed", "Spell", SpellType.NORMAL,
                "Draw 2 cards.", "Limited", 2500);
    }

    public String action(Game game) {
        for (int i = 0; i < 2; i++) {
            Board board = game.getCurrentPlayer().getBoard();
            Deck deck = game.getCurrentPlayer().getUser().getActiveDeck();
            Card card = deck.drawOneCard(game, board);
            if (card == null)
                return null;
            super.action(game);
            game.putCardInZone(card, Board.Zone.HAND, null, board);

        }
        return null;
    }

}

