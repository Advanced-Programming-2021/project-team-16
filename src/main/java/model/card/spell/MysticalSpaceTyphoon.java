package model.card.spell;

import model.Game;

public class MysticalSpaceTyphoon extends Spell {
    public MysticalSpaceTyphoon() {
        super("Mystical space typhoon", "Spell", SpellType.QUICK_PLAY
                , "Target 1 Spell/Trap on the field; destroy that target.", "Unlimited", 3500);
    }

    public void action(Game game) {
    }

}
