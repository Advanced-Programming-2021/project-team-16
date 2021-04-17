package model.card.monster;

import model.card.Card;

public class Monster extends Card {
    protected int level;
    protected int ATK;
    protected int DEF;
    protected MonsterType monsterType;

    public Monster(String name, String description, int price, MonsterType monsterType, int level, int ATK, int DEF) {
        super(name, description, price);
        this.monsterType = monsterType;
        this.ATK = ATK;
        this.DEF = DEF;
        this.level = level;
    }

    public enum MonsterType {
        WARRIOR,
        BEAST,
        FIEND,
        AQUA,
        PYRO,
        SPELL_CASTER,
        THUNDER,
        DRAGON,
        MACHINE,
        ROCK,
        INSECT,
        CYBERSE,
        FAIRY,
        BEAST_WARRIOR,
        SEA_SERPENT
    }

    public int getLevel() {
        return level;
    }

    public int getATK() {
        return ATK;
    }

    public int getDEF() {
        return DEF;
    }

    public MonsterType getMonsterType() {
        return monsterType;
    }
}
