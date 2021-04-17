package model.card.trap;

import model.Game;

public class MindCrush extends Trap {
    public MindCrush() {
        super("MindCrush", "Trap", TrapType.NORMAL, "Declare 1 card name; if that card is in your opponent's hand, they must discard all copies of it, otherwise you discard 1 random card.", "Unlimited", 2000);
    }


    public void action(Game game) {

    }
}
