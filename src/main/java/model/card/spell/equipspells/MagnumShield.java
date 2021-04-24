package model.card.spell.equipspells;

import model.Board;
import model.Game;
import model.card.Card;
import model.card.monster.Monster;
import model.card.spell.Spell;

public class MagnumShield extends Spell {
    boolean doesExist;

    public MagnumShield() {
        super("Magnum Shield", "Spell", SpellType.EQUIP
                , "Equip only to a Warrior-Type monster. Apply this effect, depending on its battle position." +
                        "● Attack Position: It gains ATK equal to its original DEF." +
                        "● Defense Position: It gains DEF equal to its original ATK.", "Unlimited", 4300);
    }

    public String action(Game game, Card givenMonster, int indexOfThisMonster) {
        Monster[] monsterZoneCurr = game.getCurrentPlayer().getBoard().getMonsterZone();
        Monster[] monsterZoneRiv = game.getRival().getBoard().getMonsterZone();
        Board.CardPosition[] currMonsterPositions = game.getCurrentPlayer().getBoard().getCardPositions()[0];
        for (Monster monster : monsterZoneCurr) {
            if (monster == givenMonster) {
                doesExist = true;
                break;
            }
        }
        for (Monster monster : monsterZoneRiv) {
            if (monster == givenMonster) {
                doesExist = true;
                break;
            }
        }
        if (!doesExist) return "given monster is neither in rival's monster zone nor current player's.";
        if (((Monster) givenMonster).getMonsterType() != Monster.MonsterType.WARRIOR)
            return "Only Warrior monster can get equipped by this card";
        if (monsterZoneCurr[indexOfThisMonster] == givenMonster) {
            Board board = game.getCurrentPlayer().getBoard();
            if (board.getCardPositions()[0][indexOfThisMonster] == Board.CardPosition.ATK)
                ((Monster) givenMonster).ATK += ((Monster) givenMonster).getDEF();
            if (board.getCardPositions()[0][indexOfThisMonster] != Board.CardPosition.ATK)
                ((Monster) givenMonster).DEF += ((Monster) givenMonster).getATK();
        }
        if (monsterZoneRiv[indexOfThisMonster] == givenMonster) {
            Board board = game.getRival().getBoard();
            if (board.getCardPositions()[0][indexOfThisMonster] == Board.CardPosition.ATK)
                ((Monster) givenMonster).ATK += ((Monster) givenMonster).getDEF();
            if (board.getCardPositions()[0][indexOfThisMonster] != Board.CardPosition.ATK)
                ((Monster) givenMonster).DEF += ((Monster) givenMonster).getATK();
        }
        return givenMonster + " is equipped successfully!";

    }

}
