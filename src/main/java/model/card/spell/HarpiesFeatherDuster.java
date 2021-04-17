package model.card.spell;

import model.Game;

public class HarpiesFeatherDuster extends Spell {
    public HarpiesFeatherDuster() {
        super("Harpie's Feather Duster", "Spell", SpellType.NORMAL
                , "Destroy all Spells and Traps your opponent controls.", "Limited", 2500);
    }

    public void action(Game game) {
    }

}
