package model.card.spell;

import model.Game;

public class DarkHole extends Spell {
    public DarkHole() {
        super("Dark Hole", "Spell", SpellType.NORMAL
                , "Destroy all monsters on the field.", "Unlimited", 2500);
    }

    public void action(Game game) {
    }

}
