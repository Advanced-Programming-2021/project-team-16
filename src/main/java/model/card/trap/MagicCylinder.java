package model.card.trap;

import model.Game;

public class MagicCylinder extends Trap {
    public MagicCylinder() {
        super("MagicCylinder", "Trap", TrapType.NORMAL, "When an opponent's monster declares an attack: Target the attacking monster; negate the attack, and if you do, inflict damage to your opponent equal to its ATK.", "Unlimited", 2000);
    }


    public void action(Game game) {

    }
}
