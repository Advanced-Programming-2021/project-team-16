package model.card.spell;

import model.Game;

public class RingOfDefense extends Spell {
    public RingOfDefense() {
        super("Ring of defense", "Spell", SpellType.QUICK_PLAY
                , "When a Trap effect that inflicts damage is activated: Make that effect damage 0.", "Unlimited", 3500);
    }

    public void action(Game game) {
    }
}
