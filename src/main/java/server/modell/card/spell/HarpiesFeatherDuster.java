package server.modell.card.spell;

import model.Board;
import server.modell.card.UtilActions;

public class HarpiesFeatherDuster extends Spell {

    public HarpiesFeatherDuster() {
        super("HarpiesFeatherDuster", "Spell", SpellType.NORMAL
                , "Destroy all Spells and Traps your opponent controls.", "Limited", 2500);
    }

    public String action() {
        UtilActions.removeRivalCards(Board.Zone.SPELL_AND_TRAP);
        UtilActions.removeRivalCards(Board.Zone.FIELD_SPELL);
        return super.action() + "and all rival's spells and traps are destroyed";
    }

}
