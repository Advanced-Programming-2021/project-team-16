package model.card.spell;

import model.Game;

public class UnitedWeStand extends Spell {
    public UnitedWeStand() {
        super("United We Stand", "Spell", SpellType.EQUIP
                , "The equipped monster gains 800 ATK/DEF for each face-up monster you control.", "Unlimited", 4300);
    }

    public void action(Game game) {
    }
}
