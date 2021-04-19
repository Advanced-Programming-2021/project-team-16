package model.card.monster;

import model.Board;
import model.Game;

public class Marshmallon extends Monster {
    public Marshmallon() {
        super("Marshmallon", "Cannot be destroyed by battle. After damage calculation, if this card was" +
                        " attacked, and was face-down at the start of the Damage Step: The attacking player takes 1000 damage.",
                700, MonsterType.FAIRY, 3, 300, 500);
    }

    public void action(int monsterIndexOfThis, Game game) {
        Board board = game.getRival().getBoard();
        if (board.getCardPositions()[0][monsterIndexOfThis] == Board.CardPosition.HIDE_DEF)
            game.getCurrentPlayer().decreaseLP(1000);
    }
}
