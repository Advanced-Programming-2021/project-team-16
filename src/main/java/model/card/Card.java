package model.card;

import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Popup;
import javafx.stage.Stage;
import model.card.monster.*;
import model.card.spell.*;
import model.card.spell.fieldspells.ClosedForest;
import model.card.spell.fieldspells.FieldSpell;
import model.card.trap.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public abstract class Card extends Rectangle implements Comparable<Card> {

    private static final ArrayList<Card> cards = new ArrayList<>();
    private static final ArrayList<Popup> popups = new ArrayList<>();

    public static final Paint UNKNOWN_CARD_FILL = new ImagePattern(new Image(Card.class.getResource("/png/card/Unknown.jpg").toExternalForm()));
    protected String name;
    protected String description;
    protected int price;
    protected Rectangle rectangle;


    public Card(String name, String description, int price) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.rectangle = new Rectangle();
        try {
            rectangle.setFill(new ImagePattern(new Image(getClass().getResource("/png/card/" + name + ".jpg").toExternalForm())));
        } catch (NullPointerException e) {
            rectangle.setFill(new ImagePattern(new Image(getClass().getResource("/png/card/" + name + ".png").toExternalForm())));
        }
        rectangle.setHeight(120);
        rectangle.setWidth(80);
        setOnMouseEntered(mouseEvent -> setCursor(Cursor.HAND));
        setOnMouseExited(mouseEvent -> setCursor(Cursor.DEFAULT));
        setFill(rectangle.getFill());
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
                    2500, Card.getCardByName("Novox's Prayer"));

            case "Crab Turtle" -> new RitualMonster("Crab Turtle", "This monster can only be Ritual Summoned with the Ritual Spell " +
                    "Card, \"Turtle Oath\". You must also offer monsters whose total Level Stars equal 8 or more as a Tribute" +
                    " from the field or your hand.", 10200, Monster.MonsterType.AQUARITUAL, 7, 2550, 2500,
                    Card.getCardByName("Turtle Oath"));
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
            case "Dark Hole" -> new DarkHole();
            case "Harpie's Feather Duster" -> new HarpiesFeatherDuster();
            case "Messenger of peace" -> new MessengerOfPeace();
            case "Monster Reborn" -> new MonsterReborn();
            case "Mystical space typhoon" -> new MysticalSpaceTyphoon();
            case "Pot of Greed" -> new PotOfGreed();
            case "Raigeki" -> new Raigeki();
            case "Spell Absorption" -> new SpellAbsorption();
            case "Supply Squad" -> new SupplySquad();
            case "Terraforming" -> new Terraforming();
            case "Twin Twisters" -> new TwinTwisters();
            case "ClosedForest" -> new ClosedForest();
            case "Umiiruka" -> new FieldSpell("Umiiruka", "Increase the ATK of all WATER monsters by 500 points and decrease " +
                    "their DEF by 400 points.", new HashMap<>() {{
                put(Monster.MonsterType.AQUA, 500);
            }},
                    new HashMap<>() {{
                        put(Monster.MonsterType.AQUA, -400);
                    }});
            case "Forest" -> new FieldSpell("Forest", "All Insect, Beast, Plant, and Beast-Warrior monsters on the field gain 200 ATK/DEF.",
                    new HashMap<>() {{
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

    public static Rectangle getBlackRectangle(boolean isHand) {
        Rectangle blackRec = new Rectangle();
        blackRec.setFill(Color.BLACK);
        if (isHand) {
            blackRec.setWidth(60);
            blackRec.setHeight(90);
        } else {
            blackRec.setWidth(70);
            blackRec.setHeight(100);
        }
        blackRec.setVisible(false);
        return blackRec;
    }

    public String getDescription() {
        return description;
    }

    public int getLevel() {
        if (this instanceof Monster)
            return this.getLevel();

        return 0;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public void setSide(boolean isToFace) {
        if (isToFace)
            setFill(rectangle.getFill());
        else
            setFill(UNKNOWN_CARD_FILL);
    }

    public void setSizes(boolean isHand) {
        if (isHand) {
            setWidth(60);
            setHeight(90);
        } else {
            setWidth(70);
            setHeight(100);
        }
    }

    public void setShowDescriptionOnMouseClicked(Stage stage) {
        Label label = new Label(getCardProperties());
        label.setWrapText(true);
        label.setStyle("-fx-font-size: 13");
        label.setBackground(new Background(new BackgroundFill(Color.LIGHTYELLOW,
                CornerRadii.EMPTY,
                Insets.EMPTY)));
        label.setMinSize(50,50);
        label.setMaxWidth(200);
        HBox hBox = new HBox(label);
        Popup popup = new Popup();
        popups.add(popup);
        popup.setAnchorY(stage.getHeight()/2);
        popup.setAnchorX(stage.getWidth()/2);
        popup.getContent().add(hBox);
        setOnMouseClicked(e -> popup.show(stage));
        stage.getScene().setOnMouseClicked(e->{
            for (Popup popup1 : popups) {
                if (popup1.isShowing()) popup1.hide();
            }
        });
    }

    public String getCardProperties() {
        String properties;
        if (this instanceof Monster) {
            Monster monster = (Monster) this;
            properties = "Name: " + monster.getName() + "\n" +
                    "Level: " + monster.getLevel() + "\n" +
                    "Type: " + monster.getMonsterType() + "\n" +
                    "ATK: " + monster.getATK() + "\n" +
                    "DEF: " + monster.getDEF() + "\n" +
                    "Description: " + monster.getDescription();
        } else if (this instanceof Spell) {
            Spell spell = (Spell) this;
            properties = "Name: " + spell.getName() + "\n" +
                    "Spell" + "\n" +
                    "Type: " + spell.getSpellType() + "\n" +
                    "Description: " + spell.getDescription();
        } else {
            Trap trap = (Trap) this;
            properties = "Name: " + trap.getName() + "\n" +
                    "Trap" + "\n" +
                    "Type: " + trap.getTrapType() + "\n" +
                    "Description: " + trap.getDescription();
        }
        return properties;

    }

    @Override
    public int compareTo(Card other) {
        return this.name.compareTo(other.name);
    }

    @Override
    public String toString() {
        return name + ": " + price;
    }

}

