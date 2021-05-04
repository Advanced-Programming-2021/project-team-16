package model.card.spell;

import controller.GameMenu;
import model.Board;
import model.Deck;
import model.Game;
import model.card.Card;

public class PotOfGreed extends Spell {
    private boolean isAtivated = false;

    public PotOfGreed() {
        super("Pot of Greed", "Spell", SpellType.NORMAL,
                "Draw 2 cards.", "Limited", 2500);
    }

    public String action() {
        Game game = GameMenu.getCurrentGame();
        for (int i = 0; i < 2; i++) {
            Board board = game.getCurrentPlayer().getBoard();
            Deck deck = game.getCurrentPlayer().getUser().getActiveDeck();
            Card card = deck.drawOneCard(game, board);
            if (card == null)
                return null;
            game.putCardInZone(card, Board.Zone.HAND, null, board);
            isAtivated = true;
            super.action();
        }
        return null;
    }

    public boolean isActivated() {
        return isAtivated;
    }
}

