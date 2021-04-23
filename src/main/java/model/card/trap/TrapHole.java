package model.card.trap;

import model.Game;

public class TrapHole extends Trap {
    public TrapHole() {
        super("TrapHole", "Trap", TrapType.NORMAL, "When your opponent Normal or Flip Summons 1 monster with 1000 or more ATK: Target that monster; destroy that target.", "Unlimited", 2000
        );
    }


    public String action(Game game) {

    }
}