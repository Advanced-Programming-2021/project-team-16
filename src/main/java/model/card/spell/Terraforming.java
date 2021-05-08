package model.card.spell;

import controller.GameMenu;
import model.Board;
import model.Game;
import model.card.Card;

import java.util.ArrayList;

public class Terraforming extends Spell {
    private boolean isAtivated = false;

    public Terraforming() {
        super("Terraforming", "Spell", SpellType.NORMAL
                , "Add 1 Field Spell from your Deck to your hand.", "Limited", 2500);
    }

    public String action(String spellname) {
        Game game = GameMenu.getCurrentGame();
        Card card = getCardByName(spellname);
        Board board = game.getCurrentPlayer().getBoard();
        ArrayList<Card> deck = board.getDeck();

        if (!deck.contains(card)) return "Your deck doesn't contain this card!";
        assert card != null;
        if (((Spell) card).getSpellType() != SpellType.FIELD) return "This is not a field spell card!";
        game.putCardInZone(card, Board.Zone.HAND, null, board);
        game.removeCardFromZone(card, Board.Zone.DECK, 0, board);
        isAtivated = true;
        super.action();
        return "Card was added to your hand successfully!";
    }

    public boolean isActivated() {
        return isAtivated;
    }
}
