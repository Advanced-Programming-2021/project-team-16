package model.card.spell;

import controller.GameMenu;
import model.Game;
import model.card.trap.MagicCylinder;

public class RingOfDefense extends Spell {
    private boolean isAtivated = false;

    public RingOfDefense() {
        super("Ring of defense", "Spell", SpellType.QUICK_PLAY
                , "When a Trap effect that inflicts damage is activated: Make that effect damage 0.", "Unlimited", 3500);
    }

    public String action() {
        Game game = GameMenu.getCurrentGame();
        MagicCylinder magicCylinder = new MagicCylinder();
        if (magicCylinder.getDamageAmount() != 0) {
            game.getRival().increaseLP(magicCylinder.getDamageAmount());
        }
        isAtivated = true;
        super.action();
        return null;
    }

    public boolean isActivated() {
        return isAtivated;
    }
}
