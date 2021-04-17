package model.card.monster.done;

import model.Board;
import model.Game;
import model.card.monster.Monster;

public class TheCalculator extends Monster {
    public TheCalculator() {
        super("The Calculator", "The ATK of this card is the combined Levels of all face-up monsters " +
                "you control x 300.", 0, MonsterType.THUNDER, 2, 0, 0);
    }

    public void action(Game game) {
        int sumOfLevels = 0;
        Monster[] monsterZone = game.getCurrentPlayer().getBoard().getMonsterZone();
        Board.CardPosition[][] cardPositions = game.getCurrentPlayer().getBoard().getCardPositions();
        for (int i = 0; i < 5; i++) {
            if (monsterZone[i] != null && cardPositions[0][i] != Board.CardPosition.HIDE_DEF)
                sumOfLevels += monsterZone[i].getLevel();
        }
        this.ATK = 300 * sumOfLevels;
    }
}
