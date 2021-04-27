package model.card.spell.fieldspells;

import model.Game;
import model.card.monster.Monster;
import model.card.spell.Spell;

public class Forest extends Spell {
    public Forest() {
        super("Forest", "Spell", SpellType.FIELD, "All Insect, Beast, Plant, and Beast-Warrior monsters on the field gain 200 ATK/DEF.", "Unlimited", 4300);
    }


    public String action(Game game) {
        Monster[] monsterZone = game.getCurrentPlayer().getBoard().getMonsterZone();
        Monster[] monsterZoneTwo = game.getRival().getBoard().getMonsterZone();
        for (Monster monster : monsterZone) {
            for (Monster monsterTwo : monsterZoneTwo)
                if ((monster != null) && (monsterTwo != null)) {
                    if (monster.getMonsterType() == Monster.MonsterType.BEAST ||
                            monster.getMonsterType() == Monster.MonsterType.BEAST_WARRIOR ||
                            monster.getMonsterType() == Monster.MonsterType.INSECT ||
                            monsterTwo.getMonsterType() == Monster.MonsterType.BEAST ||
                            monsterTwo.getMonsterType() == Monster.MonsterType.BEAST_WARRIOR ||
                            monsterTwo.getMonsterType() == Monster.MonsterType.INSECT) {
                        monster.ATK += 200;
                        monster.DEF += 200;
                        monsterTwo.ATK += 200;
                        monsterTwo.DEF += 200;
                    }
                }
        }
        super.action(game);
        return "done!";
    }
}
