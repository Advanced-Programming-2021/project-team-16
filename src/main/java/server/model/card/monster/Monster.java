package server.model.card.monster;

import server.model.card.Card;

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
        SEA_SERPENT,
        AQUARITUAL,
        WARRIORITUAL
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

    public void increaseATK(int amount) {
        ATK += amount;
    }

    public void increaseDEF(int amount) {
        DEF += amount;
    }

    public void decreaseATK(int amount) {
        ATK -= amount;
    }

    public void decreaseDEF(int amount) {
        DEF -= amount;
    }

    public static Monster clone(Monster monster) {
        if (monster == null) return null;
        return new Monster(monster.name, monster.description, monster.price, monster.monsterType, monster.level, monster.ATK, monster.DEF);
    }

    public static boolean isMonsterNormal(Monster monster) {
        return !(monster instanceof RitualMonster) && !(monster instanceof CommandKnight)
                && !(monster instanceof GraveYardEffectMonster) && !(monster instanceof HeraldOfCreation)
                && !(monster instanceof ManEaterBug) && !(monster instanceof Marshmallon) && !(monster instanceof MirageDragon) &&
                !(monster instanceof Scanner) && !(monster instanceof specialSummonable) && !(monster instanceof Suijin) &&
                !(monster instanceof Terratiger) && !(monster instanceof Texchanger) && !(monster instanceof TheCalculator);
    }
}
