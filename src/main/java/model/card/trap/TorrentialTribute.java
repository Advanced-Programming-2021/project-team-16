package model.card.trap;

import model.Game;

public class TorrentialTribute extends Trap {
    public TorrentialTribute() {
        super("TorrentialTribute", "Trap", TrapType.NORMAL, "When a monster(s) is Summoned: Destroy all monsters on the field.", "Unlimited", 2000);
    }

    public void action(Game game) {

    }
}
