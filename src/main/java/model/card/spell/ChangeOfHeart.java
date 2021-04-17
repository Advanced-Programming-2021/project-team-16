package model.card.spell;

import model.Game;

public class ChangeOfHeart extends Spell {
    public ChangeOfHeart() {
        super("Change of Heart", "Spell", SpellType.NORMAL
                , "Target 1 monster your opponent controls; take control of it until the End Phase.", "Limited", 2500);
    }

    public void action(Game game) {
    }
}
