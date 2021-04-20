package model.card.trap;

import model.Game;

public class TimeSeal extends Trap {
    public TimeSeal() {
        super("TimeSeal", "Trap", TrapType.NORMAL, "Skip the Draw Phase of your opponent's next turn.", "Limited", 2000);
    }


    @Override
    public String action() {
        Game game = Game.getInstance();
        return action();
    }
}