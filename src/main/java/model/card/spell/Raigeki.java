package model.card.spell;

import model.Game;

public class Raigeki extends Spell {
    public Raigeki() {
        super("Raigeki", "Spell", SpellType.NORMAL
                , "Destroy all monsters your opponent controls.", "Limited", 2500);
    }

    public void action(Game game) {
    }
}
