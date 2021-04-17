package model.card.spell;

import model.Game;

public class Terraforming extends Spell {
    public Terraforming() {
        super("Terraforming", "Spell", SpellType.NORMAL
                , "Add 1 Field Spell from your Deck to your hand.", "Limited", 2500);
    }

    public void action(Game game) {
    }
}
