package model.card.trap;


import model.Board;
import model.Game;
import model.card.Card;
import model.card.monster.Monster;

public class NegateAttack extends Trap {
    public NegateAttack() {
        super("NegateAttack", "Trap", TrapType.COUNTER, "When an opponent's monster declares an attack: Target the attacking monster; negate the attack, then end the Battle Phase.", "Unlimited", 3000);
    }

    boolean trapIsActivate = false;

    public String action(Game game, int MonsterIndex, int trapIndex) { //attack method & Phase
        Board board = game.getRival().getBoard();
        Board board1 = game.getCurrentPlayer().getBoard();
        Card card1 = board1.getSpellAndTrapZone()[trapIndex];
        Monster RivalMonster = board.getMonsterZone()[MonsterIndex];
        trapIsActivate = true;

        return "done!";
    }
}