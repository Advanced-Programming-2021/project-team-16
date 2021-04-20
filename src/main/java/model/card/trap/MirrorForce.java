package model.card.trap;

import model.Game;

public class MirrorForce extends Trap {
    public MirrorForce() {
        super("MirrorForce",
                "Trap",
                TrapType.NORMAL,
                "When an opponent's monster declares an attack: Destroy all your opponent's Attack Position monsters.",
                "Unlimited",
                2000
        );
    }

    @Override
    public String action() {
        Game game = Game.getInstance();
        return action();
    }
}