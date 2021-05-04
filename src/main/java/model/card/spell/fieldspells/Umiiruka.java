package model.card.spell.fieldspells;

import model.Game;
import model.card.monster.Monster;
import model.card.spell.Spell;

public class Umiiruka extends Spell {
    private boolean isAtivated = false;
    public Umiiruka() {
        super("Umiiruka", "Spell", SpellType.FIELD, "Increase the ATK of all WATER monsters by 500 points and decrease their DEF by 400 points.", "Unlimited", 4300);
    }

    public String action(Game game) {

        Monster[] monsterZone = game.getCurrentPlayer().getBoard().getMonsterZone();
        for (Monster monster : monsterZone) {
            if (monster != null) {
                if (monster.getMonsterType() == Monster.MonsterType.AQUA) {
                    monster.ATK += 500;
                    monster.DEF -= 400;
                }

            }

        }
        Monster[] monsterZoneTwo = game.getRival().getBoard().getMonsterZone();
        for (Monster monster : monsterZoneTwo) {
            if (monster != null) {
                if (monster.getMonsterType() == Monster.MonsterType.AQUA) {
                    monster.ATK += 500;
                    monster.DEF -= 400;
                }

            }
        }
        isAtivated = true;
        super.action();
        return "done!";
    }

    public boolean isActivated() {
        return isAtivated;
    }
}


