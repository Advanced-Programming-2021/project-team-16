package model.card.trap;


public class NegateAttack extends Trap {
    public NegateAttack() {
        super("NegateAttack", "Trap", TrapType.COUNTER, "When an opponent's monster declares an attack: Target the attacking monster; negate the attack, then end the Battle Phase.", "Unlimited", 3000);
    }

    @Override
    public String action(int myIndex) {
        return super.action(myIndex) + "and battle phase has ended";
    }


}