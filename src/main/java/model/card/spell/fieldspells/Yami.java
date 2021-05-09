package model.card.spell.fieldspells;

import model.Game;
import model.card.monster.Monster;
import model.card.spell.Spell;

public class Yami extends Spell {
    private boolean isAtivated = false;
    public Yami() {
        super("Yami", "Spell", SpellType.FIELD, "All Fiend and Spellcaster monsters on the field gain 200 ATK/DEF, also all Fairy monsters on the field lose 200 ATK/DEF.", "Unlimited", 4300);
    }

    public String action(Game game) {
        // Board board = game.getCurrentPlayer().getBoard();
        // ArrayList<Card> bord=
        //     if (!board.(monster)) return "There are no monsters here.";


        // boolean done = true;
        Monster[] monsterZone = game.getCurrentPlayer().getBoard().getMonsterZone();
        Monster[] monsterZoneTwo = game.getRival().getBoard().getMonsterZone();
        for (Monster monster : monsterZone) {
            for (Monster monster1 : monsterZoneTwo) {
                if ((monster != null) && (monster1 != null)) {
                    if (monster.getMonsterType() == Monster.MonsterType.SPELL_CASTER ||
                            monster.getMonsterType() == Monster.MonsterType.FIEND ||
                            monster1.getMonsterType() == Monster.MonsterType.SPELL_CASTER ||
                            monster1.getMonsterType() == Monster.MonsterType.FIEND) {
                       /* monster.ATK += 200;
                        monster.DEF += 200;
                        monster1.ATK += 200;
                        monster1.DEF += 200;*/
                    }
                    if (monster.getMonsterType() == Monster.MonsterType.FAIRY
                            || monster1.getMonsterType() == Monster.MonsterType.FAIRY) {
                        /*monster.ATK -= 200;
                        monster.DEF -= 200;
                        monster1.ATK -= 200;
                        monster1.DEF -= 200;*/
                    }
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