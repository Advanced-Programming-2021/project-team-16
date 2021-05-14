package model.card;

import model.card.monster.*;
import model.card.spell.*;
import model.card.spell.equipspells.BlackPendant;
import model.card.spell.equipspells.MagnumShield;
import model.card.spell.equipspells.SwordOfDarkDestruction;
import model.card.spell.equipspells.UnitedWeStand;
import model.card.spell.fieldspells.ClosedForest;
import model.card.spell.fieldspells.FieldSpell;
import model.card.trap.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

public abstract class Card implements Comparable<Card> {

    private static ArrayList<Card> cards = new ArrayList<>();
    protected String name;
    protected String description;
    protected int price;
    public Random random = new Random();

    public static Card make(String cardName) {
        //... all cards in update status except Monster(...)
        Card card = switch (cardName) {
            case "Command Knight" -> new CommandKnight();
            case "Yomi Ship" -> new GraveYardEffectMonster("Yomi Ship", "If this card is destroyed by battle and sent to the GY: " +
                    "Destroy the monster that destroyed this card.", 1700, Monster.MonsterType.AQUA, 3, 800,
                    1400, true);
            case "Suijin" -> new Suijin();
            case "Skull Guardian" -> new RitualMonster("Skull Guardian", "This monster can only be Ritual Summoned with the Ritual " +
                    "Spell Card, \"Novox's Prayer\". You must also offer monsters whose total Level Stars equal 7 or more as" +
                    " a Tribute from the field or your hand.", 7900, Monster.MonsterType.WARRIORITUAL, 7, 2050,
                    2500, Card.getCardByName("Novox's Prayer"), 7);

            case "Crab Turtle" -> new RitualMonster("Crab Turtle", "This monster can only be Ritual Summoned with the Ritual Spell " +
                    "Card, \"Turtle Oath\". You must also offer monsters whose total Level Stars equal 8 or more as a Tribute" +
                    " from the field or your hand.", 10200, Monster.MonsterType.AQUARITUAL, 7, 2550, 2500,
                    Card.getCardByName("Turtle Oath"), 8);
            case "ManEaterBug" -> new ManEaterBug();
            case "GateGuardian" -> new GateGuardian();
            case "Scanner" -> new Scanner();
            case "Marshmallon" -> new Marshmallon();
            case "BeastKingBarbaros" -> new BeastKingBarbaros();
            case "Texchanger" -> new Texchanger();
            case "TheCalculator" -> new TheCalculator();
            case "MirageDragon" -> new MirageDragon();
            case "new HeraldOfCreation" -> new HeraldOfCreation();
            case "Exploder Dragon" -> new GraveYardEffectMonster("Exploder Dragon", "If this card is destroyed by battle and sent to the" +
                    " Graveyard: Destroy the monster that destroyed it. Neither player takes any battle damage from attacks" +
                    " involving this attacking card.", 1000, Monster.MonsterType.DRAGON, 3, 1000, 0, false);
            case "TerratigerTheEmpoweredWarrior" -> new TerratigerTheEmpoweredWarrior();
            case "TheTricky" -> new TheTricky();
            case "AdvancedRitualArt" -> new AdvancedRitualArt();
            case "ChangeOfHeart" -> new ChangeOfHeart();
            case "DarkHole" -> new DarkHole();
            case "HarpiesFeatherDuster" -> new HarpiesFeatherDuster();
            case "MessengerOfPeace" -> new MessengerOfPeace();
            case "MonsterReborn" -> new MonsterReborn();
            case "MysticalSpaceTyphoon" -> new MysticalSpaceTyphoon();
            case "PotOfGreed" -> new PotOfGreed();
            case "Raigeki" -> new Raigeki();
            case "RingOfDefense" -> new RingOfDefense();
            case "SpellAbsorption" -> new SpellAbsorption();
            case "SupplySquad" -> new SupplySquad();
            case "SwordsOfRevealingLight" -> new SwordsOfRevealingLight();
            case "Terraforming" -> new Terraforming();
            case "TwinTwisters" -> new TwinTwisters();
            case "BlackPendant" -> new BlackPendant();
            case "MagnumShield" -> new MagnumShield();
            case "SwordOfDarkDestruction" -> new SwordOfDarkDestruction();
            case "UnitedWeStand" -> new UnitedWeStand();
            case "ClosedForest" -> new ClosedForest();
            case "Umiiruka" -> new FieldSpell("Umiiruka", "Increase the ATK of all WATER monsters by 500 points and decrease " +
                    "their DEF by 400 points.", new HashMap<Monster.MonsterType, Integer>() {{
                put(Monster.MonsterType.AQUA, 500);
            }},
                    new HashMap<Monster.MonsterType, Integer>() {{
                        put(Monster.MonsterType.AQUA, -400);
                    }});
            case "Forest" -> new FieldSpell("Forest", "All Insect, Beast, Plant, and Beast-Warrior monsters on the field gain 200 ATK/DEF.",
                    new HashMap<Monster.MonsterType, Integer>() {{
                        put(Monster.MonsterType.BEAST, 200);
                        put(Monster.MonsterType.BEAST_WARRIOR, 200);
                        put(Monster.MonsterType.INSECT, 200);
                    }},
                    new HashMap<Monster.MonsterType, Integer>() {{
                        put(Monster.MonsterType.BEAST, 200);
                        put(Monster.MonsterType.BEAST_WARRIOR, 200);
                        put(Monster.MonsterType.INSECT, 200);
                    }});
            case "Yami" -> new FieldSpell("Yami", "All Fiend and Spellcaster monsters on the field gain 200 ATK/DEF, also " +
                    "all Fairy monsters on the field lose 200 ATK/DEF.",
                    new HashMap<Monster.MonsterType, Integer>() {{
                        put(Monster.MonsterType.FIEND, 200);
                        put(Monster.MonsterType.SPELL_CASTER, 200);
                        put(Monster.MonsterType.FAIRY, -200);
                    }},
                    new HashMap<Monster.MonsterType, Integer>() {{
                        put(Monster.MonsterType.FIEND, 200);
                        put(Monster.MonsterType.SPELL_CASTER, 200);
                        put(Monster.MonsterType.FAIRY, -200);
                    }});
            case "CallOfTheHaunted" -> new CallOfTheHaunted();
            case "MagicCylinder" -> new MagicCylinder();
            case "MagicJammer" -> new MagicJammer();
            case "MindCrush" -> new MindCrush();
            case "MirrorForce" -> new MirrorForce();
            case "NegateAttack" -> new NegateAttack();
            case "SolemnWarning" -> new SolemnWarning();
            case "TimeSeal" -> new TimeSeal();
            case "TorrentialTribute" -> new TorrentialTribute();
            case "TrapHole" -> new TrapHole();
            default -> Monster.clone((Monster) getCardByName(cardName));
        };
        return card;
        //TODO
    }

    public int getPrice() {
        return price;
    }

    public Card(String name, String description, int price) {
        this.name = name;
        this.description = description;
        this.price = price;
        for (Card card : cards) if (card.getName().equals(this.getName())) return;
        cards.add(this);
    }

    public static Card getCardByName(String name) {
        for (Card card : cards) {
            if (card.name.equals(name)) {
                return card;
            }
        }
        return null;
    }

    public static void sort(ArrayList<Card> cards) {
        Collections.sort(cards);
    }

    public static ArrayList<Card> getCards() {
        return cards;
    }

    @Override
    public int compareTo(Card other) {
        return this.name.compareTo(other.name);
    }

    @Override
    public String toString() {
        return name + ": " + price;
    }

    public String desToString() {
        return name + ": " + description;
    }

    public String getName() {
        return name;
    }


    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Card)) return false;
        Card card = (Card) o;
        return name.equals(card.name);
    }
}

