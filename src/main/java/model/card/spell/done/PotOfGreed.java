package model.card.spell.done;

import model.Game;
import model.card.spell.Spell;

public class PotOfGreed extends Spell {
    public PotOfGreed() {
        super("Pot of Greed", "Spell", SpellType.NORMAL,
                "Draw 2 cards.", "Limited", 2500);
    }

    public void action() {
        Game game = Game.getInstance();
        game.addNCardsToHand(2);
    }

}
