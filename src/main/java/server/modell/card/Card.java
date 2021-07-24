package server.modell.card;

import controller.GameMenu;
import graphicview.GraphicUtils;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.*;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Popup;
import javafx.stage.Stage;
import model.Board;
import server.controller.GameServer;
import server.modell.Game;
import server.modell.card.monster.*;
import server.modell.card.spell.*;
import server.modell.card.spell.fieldspells.ClosedForest;
import server.modell.card.spell.fieldspells.FieldSpell;
import server.modell.card.trap.*;
import server.modell.Player;
import view.Show;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Card extends Rectangle implements Comparable<Card> {

    private static final ArrayList<Card> cards = new ArrayList<>();
    private static final ArrayList<Popup> popups = new ArrayList<>();


   // public static final Paint UNKNOWN_CARD_FILL = new ImagePattern(new Image(Card.class.getResource("/png/card/Unknown.jpg").toExternalForm()));

    protected String name;
    protected String description;
    protected int price;
    protected Rectangle rectangle;


    public Card(String name, String description, int price) {
        this.name = name;
        this.description = description;
        this.price = price;
      //  this.rectangle = new Rectangle();
  //    try {
  //        rectangle.setFill(new ImagePattern(new Image(getClass().getResource("/png/card/" + name + ".jpg").toExternalForm())));
  //    } catch (NullPointerException e) {
  //        rectangle.setFill(new ImagePattern(new Image(getClass().getResource("/png/card/" + name + ".png").toExternalForm())));
  //    }
  //    rectangle.setHeight(120);
  //    rectangle.setWidth(80);
  //    setOnMouseEntered(mouseEvent -> setCursor(Cursor.HAND));
  //    setOnMouseExited(mouseEvent -> setCursor(Cursor.DEFAULT));
  //    setFill(rectangle.getFill());
  //    setSizes(false);
        for (Card card : cards) if (card.getName().equals(this.getName())) return;
        cards.add(this);

    }


    public static Card make(String cardName) {
        return switch (cardName) {
            case "CommandKnight" -> new CommandKnight();
            case "YomiShip" -> new GraveYardEffectMonster("YomiShip", "If this card is destroyed by battle and sent to the GY: " +
                    "Destroy the monster that destroyed this card.", 1700, Monster.MonsterType.AQUA, 3, 800,
                    1400, true);
            case "Suijin" -> new Suijin();
            case "SkullGuardian" -> new RitualMonster("SkullGuardian", "This monster can only be Ritual Summoned with the Ritual " +
                    "Spell Card, \"Novox's Prayer\". You must also offer monsters whose total Level Stars equal 7 or more as" +
                    " a Tribute from the field or your hand.", 7900, Monster.MonsterType.WARRIORITUAL, 7, 2050,
                    2500, Card.getCardByName("Novox's Prayer"));

            case "CrabTurtle" -> new RitualMonster("CrabTurtle", "This monster can only be Ritual Summoned with the Ritual Spell " +
                    "Card, \"Turtle Oath\". You must also offer monsters whose total Level Stars equal 8 or more as a Tribute" +
                    " from the field or your hand.", 10200, Monster.MonsterType.AQUARITUAL, 7, 2550, 2500,
                    Card.getCardByName("Turtle Oath"));
            case "Man-EaterBug" -> new ManEaterBug();
            case "GateGuardian" -> new GateGuardian();
            case "Scanner" -> new Scanner();
            case "Marshmallon" -> new Marshmallon();
            case "BeastKingBarbaros" -> new BeastKingBarbaros();
            case "Texchanger" -> new Texchanger();
            case "TheCalculator" -> new TheCalculator();
            case "MirageDragon" -> new MirageDragon();
            case "HeraldofCreation" -> new HeraldOfCreation();
            case "ExploderDragon" -> new GraveYardEffectMonster("ExploderDragon", "If this card is destroyed by battle and sent to the" +
                    " Graveyard: Destroy the monster that destroyed it. Neither player takes any battle damage from attacks" +
                    " involving this attacking card.", 1000, Monster.MonsterType.DRAGON, 3, 1000, 0, false);
            case "TerratigertheEmpoweredWarrior" -> new Terratiger();
            case "TheTricky" -> new TheTricky();
            case "AdvancedRitualArt" -> new AdvancedRitualArt();
            case "DarkHole" -> new DarkHole();
            case "HarpiesFeatherDuster" -> new HarpiesFeatherDuster();
            case "Messengerofpeace" -> new MessengerOfPeace();
            case "MonsterReborn" -> new MonsterReborn();
            case "Mysticalspacetyphoon" -> new MysticalSpaceTyphoon();
            case "PotofGreed" -> new PotOfGreed();
            case "Raigeki" -> new Raigeki();
            case "SpellAbsorption" -> new SpellAbsorption();
            case "SupplySquad" -> new SupplySquad();
            case "Terraforming" -> new Terraforming();
            case "TwinTwisters" -> new TwinTwisters();
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
      // else
      //     setFill(UNKNOWN_CARD_FILL);
    }

//    public void setSizes(boolean isHand) {
//        if (GameServer.getCurrentGame() != null && !GameServer.getCurrentGame().isGraphical()) return;
//        if (isHand) {
//            setWidth(60);
//            setHeight(90);
//        } else {
//            setWidth(70);
//            setHeight(100);
//        }
//    }

//    public void setShowDescriptionOnMouseClicked(Stage stage) {
//        if (GameMenu.getCurrentGame() != null && !GameMenu.getCurrentGame().isGraphical()) return;
//        Label label = new Label(getCardProperties());
//        label.setTextFill(Color.WHITE);
//        label.setWrapText(true);
//        label.setStyle("-fx-font-size: 13");
//        label.setBackground(GraphicUtils.getBackground(Color.DARKBLUE));
//        label.setMinSize(50, 50);
//        label.setMaxWidth(200);
//        HBox hBox = new HBox(label);
//        Popup popup = new Popup();
//        popups.add(popup);
//        popup.setAnchorY(stage.getHeight() / 2);
//        popup.setAnchorX(stage.getWidth() / 2);
//        popup.getContent().add(hBox);
//        setOnMouseClicked(e -> popup.show(stage));
//        stage.getScene().setOnMouseClicked(e -> {
//            for (Popup popup1 : popups) if (popup1.isShowing()) popup1.hide();
//        });
//    }

//    public void setAttackedOnDraggedOver(int index) {
//        if (GameMenu.getCurrentGame() != null && !GameMenu.getCurrentGame().isGraphical()) return;
//        setOnDragOver(new EventHandler<>() {
//            public void handle(DragEvent event) {
//                if (event.getGestureSource() != this && event.getDragboard().hasString())
//                    event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
//                event.consume();
//            }
//        });
//
//        setOnDragDropped((DragEvent event) -> {
//            Dragboard db = event.getDragboard();
//            if (db.hasString()) {
//                Game game = GameMenu.getCurrentGame();
//                Matcher matcher = Pattern.compile("(\\d+) # (.*)").matcher(db.getString());
//                if (matcher.find()) {
//                    boolean hasCurrentPlayerDoneTheAct = matcher.group(2).equals(game.getCurrentPlayer().getUser().getUsername());
//                    if (hasCurrentPlayerDoneTheAct) {
//                        game.selectCard(Board.Zone.MONSTER, Integer.parseInt(matcher.group(1)), false);
//                        Show.showGameMessage(game.attack(index));
//                    } else Show.showGameMessage("not your turn");
//                }
//                event.setDropCompleted(true);
//            } else event.setDropCompleted(false);
//            event.consume();
//        });
//    }

//    public void setAttackingOnDragged(int index, Player player) {
//        if (GameMenu.getCurrentGame() != null && !GameMenu.getCurrentGame().isGraphical()) return;
//        setOnDragDetected((MouseEvent event) -> {
//            Dragboard db = startDragAndDrop(TransferMode.ANY);
//            ClipboardContent content = new ClipboardContent();
//            content.putString(index + " # " + player.getUser().getUsername());
//            db.setContent(content);
//        });
//        setOnMouseDragged((MouseEvent event) -> event.setDragDetect(true));
//    }

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

