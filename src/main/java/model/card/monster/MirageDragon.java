package model.card.monster;

import model.Board;
import model.Game;

public class MirageDragon extends Monster {
    public MirageDragon() {
        super("Mirage Dragon", "Your opponent cannot activate Trap Cards during the Battle Phase.",
                2500, MonsterType.DRAGON, 4, 1600, 600);
    }

    public boolean isOn(Game game) {
        Monster[] monsterZone = game.getRival().getBoard().getMonsterZone();
        Board.CardPosition[] monsterPositions = game.getRival().getBoard().getCardPositions()[0];
        for (int i = 0; i < monsterZone.length; i++) {
            if (monsterZone[i] != null)
                if (this.name.equals(monsterZone[i].getName()))
                    if (monsterPositions[i] != Board.CardPosition.HIDE_DEF)
                        return true;
        }
        return false;
    }
}
