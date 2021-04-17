package model.card.spell;

import model.Game;

public class ClosedForest extends Spell {
    public ClosedForest() {
        super("ClosedForest", "Spell", SpellType.FIELD, "All Beast-Type monsters you control gain 100 ATK for each monster in your Graveyard. Field Spell Cards cannot be activated. Field Spell Cards cannot be activated during the turn this card is destroyed.", "Unlimited", 4300);
    }


    public void action(Game game) {

    }
}
