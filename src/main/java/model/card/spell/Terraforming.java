package model.card.spell;

import controller.GameMenu;
import model.Board;
import model.Game;
import model.card.Card;
import model.card.spell.fieldspells.FieldSpell;
import view.CommandProcessor;

import java.util.ArrayList;

public class Terraforming extends Spell {

    public Terraforming() {
        super("Terraforming", "Spell", SpellType.NORMAL
                , "Add 1 Field Spell from your Deck to your hand.", "Limited", 2500);
    }

    public String action() {
        Game game = GameMenu.getCurrentGame();
        Board board = game.getCurrentPlayer().getBoard();
        if (board.isZoneFull(Board.Zone.HAND)) return "you can't activate this. (your hand is full)";
        ArrayList<Card> fieldSpells = new ArrayList<>();
        for (Card card : board.getDeck()) if (card instanceof FieldSpell) fieldSpells.add(card);
        if (fieldSpells.size() == 0) return "you can't activate this. (there is no field spell in your deck)";
        int fieldSpellIndex = CommandProcessor.getIndexOfCardArray(fieldSpells, "bring field spell from deck to hand");
        if (fieldSpellIndex == -1) return "cancelled";
        Card fieldSpell = fieldSpells.get(fieldSpellIndex);
        game.removeCardFromZone(fieldSpell, Board.Zone.DECK, 0, board);
        game.putCardInZone(fieldSpell, Board.Zone.HAND, null, board);
        super.action();
        return "spell activated and " + fieldSpell.getName() + " was added to your hand successfully!";
    }

}
