package model.card.spell;

import model.Game;

public class TwinTwisters extends Spell {
    public TwinTwisters() {
        super("Twin Twisters", "Spell", SpellType.QUICK_PLAY
                , "Discard 1 card, then target up to 2 Spells/Traps on the field; destroy them.", "Unlimited", 3500);
    }

    public void action(Game game) {
    }

}
