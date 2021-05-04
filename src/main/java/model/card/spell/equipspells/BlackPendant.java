package model.card.spell.equipspells;

import controller.GameMenu;
import model.Game;
import model.card.Card;
import model.card.monster.Monster;
import model.card.spell.Spell;

public class BlackPendant extends Spell {
    private boolean doesExist = false;
    private boolean isAtivated = false;

    public BlackPendant() {
        super("Black Pendant", "Spell", SpellType.EQUIP
                , "The equipped monster gains 500 ATK. When this card is sent from the field to " +
                        "the Graveyard: Inflict 500 damage to your opponent.", "Unlimited", 4300);
    }

    public String action(Card givenMonster) {
        Game game = GameMenu.getCurrentGame();
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
        ((Monster) givenMonster).increaseATK(500);
        isAtivated = true;
        super.action();
        return givenMonster + " is equipped successfully!";
    }

    public boolean isActivated() {
        return isAtivated;
    }
}
