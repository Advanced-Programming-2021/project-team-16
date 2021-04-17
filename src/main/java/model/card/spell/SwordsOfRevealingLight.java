package model.card.spell;

import model.Game;

public class SwordsOfRevealingLight extends Spell {
    public SwordsOfRevealingLight() {
        super("Swords of Revealing Light", "Spell", SpellType.NORMAL
                , "After this card's activation, it remains on the field, but destroy it during the End Phase of your opponent's 3rd turn. When this card is activated: If your opponent controls a face-down monster, flip all monsters they control face-up. While this card is face-up on the field, your opponent's monsters cannot declare an attack."
                , "Unlimited", 2500);
    }

    public void action(Game game) {
    }
}
