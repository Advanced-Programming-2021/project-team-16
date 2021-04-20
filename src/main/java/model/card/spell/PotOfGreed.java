package model.card.spell;

import model.Game;

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
