package model.card.spell;

import model.Board;

public class HarpiesFeatherDuster extends Spell {

    public HarpiesFeatherDuster() {
        super("Harpie's Feather Duster", "Spell", SpellType.NORMAL
                , "Destroy all Spells and Traps your opponent controls.", "Limited", 2500);
    }

    public String action() {
        UtilActions.removeRivalCards(Board.Zone.SPELL_AND_TRAP);
        return super.action() + "and all rival's spells and traps are destroyed";
    }

}
