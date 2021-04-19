package model.card.monster;

import model.card.Card;

public class RitualMonster extends Monster {
    protected Card ritualSpell;
    protected int sumOfTributeLevels;


    public RitualMonster(String name, String description, int price, MonsterType monsterType, int level, int ATK, int DEF
            , Card ritualSpell, int sumOfTributeLevels) {
        super(name, description, price, monsterType, level, ATK, DEF);
        this.ritualSpell = ritualSpell;
        this.sumOfTributeLevels = sumOfTributeLevels;
    }
}
