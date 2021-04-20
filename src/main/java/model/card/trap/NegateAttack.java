package model.card.trap;


import model.Game;

public class NegateAttack extends Trap {
    public NegateAttack() {
        super("NegateAttack", "Trap", TrapType.COUNTER, "When an opponent's monster declares an attack: Target the attacking monster; negate the attack, then end the Battle Phase.", "Unlimited", 3000);
    }


    @Override
    public String action() {
        Game game = Game.getInstance();
        return action();
    }
}