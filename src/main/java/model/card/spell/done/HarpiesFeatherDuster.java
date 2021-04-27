package model.card.spell.done;

import model.Board;
import model.Game;
import model.card.Card;
import model.card.spell.Spell;

public class HarpiesFeatherDuster extends Spell {
    public HarpiesFeatherDuster() {
        super("Harpie's Feather Duster", "Spell", SpellType.NORMAL
                , "Destroy all Spells and Traps your opponent controls.", "Limited", 2500);
    }

    public String action(Game game) {

        Board board = game.getRival().getBoard();
        for (int index = 0; index < board.getSpellAndTrapZone().length; index++) {
            Card card = board.getMonsterZone()[index];
            if (card != null) game.putCardInZone(card, Board.Zone.GRAVE, null, board);
            game.removeCardFromZone(card, Board.Zone.SPELL_AND_TRAP, index, board);
        }
        Card fieldSpell = board.getFieldSpell();
        if (fieldSpell != null) game.putCardInZone(fieldSpell, Board.Zone.GRAVE, null, board);
        game.removeCardFromZone(fieldSpell, Board.Zone.FIELD_SPELL, 0, board);

        super.action(game);

        return null;
    }

}
