package model.card.spell.equipspells;

import model.Game;
import model.card.Card;
import model.card.monster.Monster;
import model.card.spell.Spell;

public class SwordOfDarkDestruction extends Spell {
    Boolean doesExist;

    public SwordOfDarkDestruction() {
        super("Sword of dark destruction", "Spell", SpellType.EQUIP
                , "A DARK monster equipped with this card increases its ATK by 400 points and decreases its DEF by 200 points.", "Unlimited", 4300);
    }

    public String action(Game game, Card givenMonster) {
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
        if (((Monster) givenMonster).getMonsterType() == Monster.MonsterType.FAIRY ||
                ((Monster) givenMonster).getMonsterType() == Monster.MonsterType.SPELL_CASTER) {
            ((Monster) givenMonster).ATK += 400;
            ((Monster) givenMonster).DEF -= 200;
            return givenMonster + " is equipped successfully!";
        }
        return givenMonster + " is equipped but Sword Of Dark Destruction only affects Fairy and Spell Caster monsters!";
    }
}
