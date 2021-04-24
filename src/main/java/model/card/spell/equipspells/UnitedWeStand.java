package model.card.spell.equipspells;

import model.Board;
import model.Game;
import model.card.Card;
import model.card.monster.Monster;
import model.card.spell.Spell;

public class UnitedWeStand extends Spell {
    Boolean doesExist;

    public UnitedWeStand() {
        super("United We Stand", "Spell", SpellType.EQUIP
                , "The equipped monster gains 800 ATK/DEF for each face-up monster you control.", "Unlimited", 4300);
    }

    public String action(Game game, Card givenMonster) {
        int counter = 0;
        Monster[] monsterZoneCurr = game.getCurrentPlayer().getBoard().getMonsterZone();
        Monster[] monsterZoneRiv = game.getRival().getBoard().getMonsterZone();
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
        Monster[] monsterZone = game.getCurrentPlayer().getBoard().getMonsterZone();
        Board board = game.getCurrentPlayer().getBoard();
        for (int i = 0; i < monsterZone.length; i++) {
            if (monsterZone[i] != null && board.getCardPositions()[0][i] == Board.CardPosition.REVEAL_DEF ||
                    board.getCardPositions()[0][i] == Board.CardPosition.ATK) counter++;

        }
        ((Monster) givenMonster).ATK += 800 * counter;
        return givenMonster + " is equipped successfully!";
    }
}
