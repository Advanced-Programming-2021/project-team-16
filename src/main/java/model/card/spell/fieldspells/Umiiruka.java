package model.card.spell.fieldspells;

import model.Game;
import model.card.spell.Spell;

public class Umiiruka extends Spell {
    public Umiiruka() {
        super("Umiiruka", "Spell", SpellType.FIELD, "Increase the ATK of all WATER monsters by 500 points and decrease their DEF by 400 points.", "Unlimited", 4300);
    }

    public void action(Game game) {

    }
}