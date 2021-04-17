package model.card.spell;

import model.Game;

public class Forest extends Spell {
    public Forest() {
        super("Forest", "Spell", SpellType.FIELD, "All Insect, Beast, Plant, and Beast-Warrior monsters on the field gain 200 ATK/DEF.", "Unlimited", 4300);
    }


    public void action(Game game) {

    }
}