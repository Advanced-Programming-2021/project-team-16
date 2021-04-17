package model.card.spell;

import model.Game;

public class Yami extends Spell {
    public Yami() {
        super("Yami", "Spell", SpellType.FIELD, "All Fiend and Spellcaster monsters on the field gain 200 ATK/DEF, also all Fairy monsters on the field lose 200 ATK/DEF.", "Unlimited", 4300);
    }

    public void action(Game game) {

    }
}
