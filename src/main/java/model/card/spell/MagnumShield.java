package model.card.spell;

import model.Game;

public class MagnumShield extends Spell {
    public MagnumShield() {
        super("Magnum Shield", "Spell", SpellType.EQUIP
                , "Equip only to a Warrior-Type monster. Apply this effect, depending on its battle position." +
                        "● Attack Position: It gains ATK equal to its original DEF." +
                        "● Defense Position: It gains DEF equal to its original ATK.", "Unlimited", 4300);
    }

    public void action(Game game) {
    }
}
