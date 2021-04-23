package model.card.trap;

import model.Game;

public class MagicJammer extends Trap {
    public MagicJammer() {
        super("MagicJammer", "Trap", TrapType.COUNTER, "When a Spell Card is activated: Discard 1 card; negate the activation, and if you do, destroy it.", "Unlimited", 3000);
    }


    public String action(Game game) {

    }
}
