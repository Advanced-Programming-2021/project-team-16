package model.card;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
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

    private static final ArrayList<Card> cards = new ArrayList<>();
    protected String name;
    protected String description;
    protected int price;
    protected Rectangle rectangle;


    public Card(String name, String description, int price) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.rectangle = new Rectangle();
        rectangle.setFill(new ImagePattern(new Image(getClass().getResource("/png/card/" + name + ".jpg").toExternalForm())));
        for (Card card : cards) if (card.getName().equals(this.getName())) return;
        cards.add(this);
    }

    public static Card make(String cardName) {
        return switch (cardName) {
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
            case "Man-Eater Bug" -> new ManEaterBug();
            case "Gate Guardian" -> new GateGuardian();
            case "Scanner" -> new Scanner();
            case "Marshmallon" -> new Marshmallon();
            case "Beast King Barbaros" -> new BeastKingBarbaros();
            case "Texchanger" -> new Texchanger();
            case "The Calculator" -> new TheCalculator();
            case "Mirage Dragon" -> new MirageDragon();
            case "Herald of Creation" -> new HeraldOfCreation();
            case "Exploder Dragon" -> new GraveYardEffectMonster("Exploder Dragon", "If this card is destroyed by battle and sent to the" +
                    " Graveyard: Destroy the monster that destroyed it. Neither player takes any battle damage from attacks" +
                    " involving this attacking card.", 1000, Monster.MonsterType.DRAGON, 3, 1000, 0, false);
            case "Terratiger, the Empowered Warrior" -> new Terratiger();
            case "The Tricky" -> new TheTricky();
            case "Advanced Ritual Art" -> new AdvancedRitualArt();
            case "Change of Heart" -> new ChangeOfHeart();
            case "Dark Hole" -> new DarkHole();
            case "Harpie's Feather Duster" -> new HarpiesFeatherDuster();
            case "Messenger of peace" -> new MessengerOfPeace();
            case "Monster Reborn" -> new MonsterReborn();
            case "Mystical space typhoon" -> new MysticalSpaceTyphoon();
            case "Pot of Greed" -> new PotOfGreed();
            case "Raigeki" -> new Raigeki();
            case "Ring of defense" -> new RingOfDefense();
            case "Spell Absorption" -> new SpellAbsorption();
            case "Supply Squad" -> new SupplySquad();
            case "Swords of Revealing Light" -> new SwordsOfRevealingLight();
            case "Terraforming" -> new Terraforming();
            case "Twin Twisters" -> new TwinTwisters();
            case "Black Pendant" -> new BlackPendant();
            case "Magnum Shield" -> new MagnumShield();
            case "Sword of dark destruction" -> new SwordOfDarkDestruction();
            case "United We Stand" -> new UnitedWeStand();
            case "ClosedForest" -> new ClosedForest();
            case "Umiiruka" -> new FieldSpell("Umiiruka", "Increase the ATK of all WATER monsters by 500 points and decrease " +
                    "their DEF by 400 points.", new HashMap<Monster.MonsterType, Integer>() {{
                put(Monster.MonsterType.AQUA, 500);
            }},
                    new HashMap<>() {{
                        put(Monster.MonsterType.AQUA, -400);
                    }});
            case "Forest" -> new FieldSpell("Forest", "All Insect, Beast, Plant, and Beast-Warrior monsters on the field gain 200 ATK/DEF.",
                    new HashMap<Monster.MonsterType, Integer>() {{
                        put(Monster.MonsterType.BEAST, 200);
                        put(Monster.MonsterType.BEAST_WARRIOR, 200);
                        put(Monster.MonsterType.INSECT, 200);
                    }},
                    new HashMap<>() {{
                        put(Monster.MonsterType.BEAST, 200);
                        put(Monster.MonsterType.BEAST_WARRIOR, 200);
                        put(Monster.MonsterType.INSECT, 200);
                    }});
            case "Yami" -> new FieldSpell("Yami", "All Fiend and Spellcaster monsters on the field gain 200 ATK/DEF, also " +
                    "all Fairy monsters on the field lose 200 ATK/DEF.",
                    new HashMap<>() {{
                        put(Monster.MonsterType.FIEND, 200);
                        put(Monster.MonsterType.SPELL_CASTER, 200);
                        put(Monster.MonsterType.FAIRY, -200);
                    }},
                    new HashMap<>() {{
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
    }

    public int getPrice() {
        return price;
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
        for (int i = cards.size() - 1; i > 0; i--)
            for (int j = 0; j < i; j++) {
                if (cards.get(j).getName().compareTo(cards.get(j + 1).getName()) > 0)
                    Collections.swap(cards, j, j + 1);
            }
    }

    public static ArrayList<Card> getCards() {
        return cards;
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

    public Rectangle getRectangle() {
        return rectangle;
    }

    @Override
    public int compareTo(Card other) {
        return this.name.compareTo(other.name);
    }

    @Override
    public String toString() {
        return name + ": " + price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Card)) return false;
        Card card = (Card) o;
        return name.equals(card.name);
    }
}

