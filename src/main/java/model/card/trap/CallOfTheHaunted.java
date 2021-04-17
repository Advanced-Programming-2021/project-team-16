package model.card.trap;

import model.Game;

public class CallOfTheHaunted extends Trap {
    public CallOfTheHaunted() {
        super("CallOfTheHaunted", "Trap", TrapType.CONTINUOUS, "Activate this card by targeting 1 monster in your GY; Special Summon that target in Attack Position. When this card leaves the field, destroy that monster. When that monster is destroyed, destroy this card.", "Unlimited", 3500);
    }

    public void action(Game game) {

    }
}
