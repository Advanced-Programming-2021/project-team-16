package model.card.spell.done;

import model.Game;
import model.card.spell.Spell;
import model.card.trap.MagicCylinder;

public class RingOfDefense extends Spell {
    public RingOfDefense() {
        super("Ring of defense", "Spell", SpellType.QUICK_PLAY
                , "When a Trap effect that inflicts damage is activated: Make that effect damage 0.", "Unlimited", 3500);
    }

    public String action(Game game) {
        MagicCylinder magicCylinder = new MagicCylinder();
        if (magicCylinder.getDamageAmount() != 0) {
            game.getRival().increaseLP(magicCylinder.getDamageAmount());              // OWNER!!!
        }
        super.action(game);
        return null;
    }
}
