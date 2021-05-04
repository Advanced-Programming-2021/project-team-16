package model.card.spell.equipspells;

import controller.GameMenu;
import model.Board;
import model.Game;
import model.card.Card;
import model.card.monster.Monster;
import model.card.spell.Spell;

public class UnitedWeStand extends Spell {
    private boolean doesExist = false;
    private boolean isAtivated = false;

    public UnitedWeStand() {
        super("United We Stand", "Spell", SpellType.EQUIP
                , "The equipped monster gains 800 ATK/DEF for each face-up monster" +
                        " you control.", "Unlimited", 4300);
    }

    public String action(Card givenMonster) {
        Game game = GameMenu.getCurrentGame();
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
        ((Monster) givenMonster).increaseATK(800 * counter);
        isAtivated = true;
        super.action();
        return givenMonster + " is equipped successfully!";
    }

    public boolean isActivated() {
        return isAtivated;
    }
}
