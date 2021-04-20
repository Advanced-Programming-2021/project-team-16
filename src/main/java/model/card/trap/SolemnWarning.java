package model.card.trap;

import model.Game;

public class SolemnWarning extends Trap {
    public SolemnWarning() {
        super("SolemnWarning", "Trap", TrapType.COUNTER, "When a monster(s) would be Summoned, OR when a Spell/Trap Card, or monster effect, is activated that includes an effect that Special Summons a monster(s): Pay 2000 LP; negate the Summon or activation, and if you do, destroy it.", "Unlimited", 3000);
    }


    @Override
    public String action() {
        Game game = Game.getInstance();
        return action();
    }
}