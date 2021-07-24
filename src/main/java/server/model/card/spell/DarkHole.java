package server.model.card.spell;

import server.model.card.UtilActions;

public class DarkHole extends Spell {

    public DarkHole() {
        super("DarkHole", "Spell", SpellType.NORMAL
                , "Destroy all monsters on the field.", "Unlimited", 2500);
    }

    public String action() {
        UtilActions.removeAllMonsters();
        return super.action() + "and all monsters are destroyed";

    }

}
