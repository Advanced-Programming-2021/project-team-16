package controller;

import model.card.Card;
import model.card.monster.GraveYardEffectMonster;
import model.card.monster.Monster;
import model.card.monster.RitualMonster;

public class UpdateStatus {
    public static void makeAllCards() {
        makeAllMonsters();
        makeAllSpells();
        makeAllTraps();
    }

    public static void makeAllMonsters() {
        new RitualMonster("Skull Guardian", "This monster can only be Ritual Summoned with the Ritual " +
                "Spell Card, \"Novox's Prayer\". You must also offer monsters whose total Level Stars equal 7 or more as" +
                " a Tribute from the field or your hand.", 7900, Monster.MonsterType.WARRIOR, 7, 2050,
                2500, Card.getCardByName("Novox's Prayer"), 7);
        new RitualMonster("Crab Turtle", "This monster can only be Ritual Summoned with the Ritual Spell " +
                "Card, \"Turtle Oath\". You must also offer monsters whose total Level Stars equal 8 or more as a Tribute" +
                " from the field or your hand.", 10200, Monster.MonsterType.AQUA, 7, 2550, 2500,
                Card.getCardByName("Turtle Oath"), 8);
        new GraveYardEffectMonster("Yomi Ship", "If this card is destroyed by battle and sent to the GY: " +
                "Destroy the monster that destroyed this card.", 1700, Monster.MonsterType.AQUA, 3, 800, 1400, true);
        new GraveYardEffectMonster("Exploder Dragon", "If this card is destroyed by battle and sent to the" +
                " Graveyard: Destroy the monster that destroyed it. Neither player takes any battle damage from attacks" +
                " involving this attacking card.\n", 1000, Monster.MonsterType.DRAGON, 3, 1000, 0, false);
    }

    public static void makeAllSpells() {
    }

    public static void makeAllTraps() {
    }
}
