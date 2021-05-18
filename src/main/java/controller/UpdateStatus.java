package controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Deck;
import model.card.Card;
import model.card.monster.*;
import model.card.spell.*;
import model.card.spell.equipspells.BlackPendant;
import model.card.spell.equipspells.MagnumShield;
import model.card.spell.equipspells.SwordOfDarkDestruction;
import model.card.spell.equipspells.UnitedWeStand;
import model.card.spell.fieldspells.ClosedForest;
import model.card.spell.fieldspells.FieldSpell;
import model.card.trap.*;
import model.person.User;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UpdateStatus {
    public static void beforeRun() {
        makeAllCards();
        makeUsers();
    }

    public static void afterRun() {
        saveUsers();
    }


    public static void makeAllCards() {
        makeAllMonsters();
        makeAllSpells();
        makeAllTraps();
    }

    public static void saveUsers() {
        try {
            FileWriter writer = new FileWriter("users_json.txt");
            writer.write(new Gson().toJson(User.getAllUsers()));
            writer.close();
        } catch (IOException e) {
            System.out.println("error");
        }
    }

    public static void makeUsers() {
        try {
            String jsonUsers = new String(Files.readAllBytes(Paths.get("users_json.txt")));
            ArrayList<User> users = new Gson().fromJson(jsonUsers, new TypeToken<List<User>>() {
            }.getType());
            for (User user : users) {
                Deck fakeActiveDeck = null;
                if (user.getActiveDeck() != null) {
                    String activeDeckName = user.getActiveDeck().getName();
                    for (Deck deck : user.getDecks())
                        if (deck.getName().equals(activeDeckName)) fakeActiveDeck = deck;
                    user.getDecks().remove(fakeActiveDeck);
                    user.getDecks().add(user.getActiveDeck());
                }
            }
            User.setUsers(users);
        } catch (IOException e) {
            System.out.println("error");
        }
    }


    public static void makeAllMonsters() {
        new CommandKnight();
        new Monster("Battle OX", "A monster with tremendous power, it destroys enemies with a swing of " +
                "its axe.", 2900, Monster.MonsterType.BEAST_WARRIOR, 4, 1700, 1000);
        new Monster("Axe Raider", "An axe-wielding monster of tremendous strength and agility.", 3100,
                Monster.MonsterType.WARRIOR, 4, 1700, 1150);
        new GraveYardEffectMonster("Yomi Ship", "If this card is destroyed by battle and sent to the GY: " +
                "Destroy the monster that destroyed this card.", 1700, Monster.MonsterType.AQUA, 3, 800,
                1400, true);
        new Monster("Horn Imp", "A small fiend that dwells in the dark, its single horn makes it a " +
                "formidable opponent.", 2500, Monster.MonsterType.FIEND, 4, 1100, 1000);
        new Monster("Silver Fang", "A snow wolf that's beautiful to the eye, but absolutely vicious in " +
                "battle.", 1700, Monster.MonsterType.BEAST, 4, 1200, 800);
        new Suijin();
        new Monster("Fireyarou", "A malevolent creature wrapped in flames that attacks enemies with " +
                "intense fire.", 2500, Monster.MonsterType.PYRO, 4, 1300, 1000);
        new Monster("Curtain of the dark ones", "A curtain that a spellcaster made, it is said to raise" +
                " a dark power.", 700, Monster.MonsterType.SPELL_CASTER, 2, 600, 500);
        new Monster("Feral Imp", "A playful little fiend that lurks in the dark, waiting to attack an " +
                "unwary enemy.", 2800, Monster.MonsterType.FIEND, 4, 1300, 1400);
        new Monster("Dark magician", "The ultimate wizard in terms of attack and defense.", 8300,
                Monster.MonsterType.SPELL_CASTER, 7, 2500, 2100);
        new Monster("Wattkid", "A creature that electrocutes opponents with bolts of lightning.",
                1300, Monster.MonsterType.THUNDER, 3, 1000, 500);
        new Monster("Baby dragon", "Much more than just a child, this dragon is gifted with untapped power."
                , 1600, Monster.MonsterType.DRAGON, 3, 1200, 700);
        new Monster("Hero of the east", "Feel da strength ah dis sword-swinging samurai from da Far East.",
                1700, Monster.MonsterType.WARRIOR, 3, 1100, 1000);
        new Monster("Battle warrior", "A warrior that fights with his bare hands!!!", 1300,
                Monster.MonsterType.WARRIOR, 3, 700, 1000);
        new Monster("Crawling dragon", "This weakened dragon can no longer fly, but is still a deadly" +
                " force to be reckoned with.", 3900, Monster.MonsterType.DRAGON, 5, 1600, 1400);
        new Monster("Flame manipulator", "This Spellcaster attacks enemies with fire-related spells such" +
                " as \"Sea of Flames\" and \"Wall of Fire\".", 1500, Monster.MonsterType.SPELL_CASTER, 3
                , 900, 1000);
        new Monster("Blue-Eyes white dragon", "This legendary dragon is a powerful engine of destruction." +
                " Virtually invincible, very few have faced this awesome creature and lived to tell the tale.",
                11300, Monster.MonsterType.DRAGON, 8, 3000, 2500);
        new RitualMonster("Skull Guardian", "This monster can only be Ritual Summoned with the Ritual " +
                "Spell Card, \"Novox's Prayer\". You must also offer monsters whose total Level Stars equal 7 or more as" +
                " a Tribute from the field or your hand.", 7900, Monster.MonsterType.WARRIORITUAL, 7, 2050,
                2500, Card.getCardByName("Novox's Prayer"), 7);
        new RitualMonster("Crab Turtle", "This monster can only be Ritual Summoned with the Ritual Spell " +
                "Card, \"Turtle Oath\". You must also offer monsters whose total Level Stars equal 8 or more as a Tribute" +
                " from the field or your hand.", 10200, Monster.MonsterType.AQUARITUAL, 7, 2550, 2500,
                Card.getCardByName("Turtle Oath"), 8);
        new Monster("Slot Machine", "The machine's ability is said to vary according to its slot results."
                , 7500, Monster.MonsterType.MACHINE, 7, 2000, 2300);
        new Monster("Haniwa", "An earthen figure that protects the tomb of an ancient ruler.", 600,
                Monster.MonsterType.ROCK, 2, 500, 500);
        new ManEaterBug();
        new GateGuardian();
        new Scanner();
        new Monster("Bitron", "A new species found in electronic space. There's not much information on it."
                , 1000, Monster.MonsterType.CYBERSE, 2, 200, 2000);
        new Marshmallon();
        new BeastKingBarbaros();
        new Texchanger();
        new Monster("Leotron", "A territorial electronic monster that guards its own domain.", 2500,
                Monster.MonsterType.CYBERSE, 4, 2000, 0);
        new TheCalculator();
        new Monster("Alexandrite Dragon", "Many of the czars' lost jewels can be found in the scales of" +
                " this priceless dragon. Its creator remains a mystery, along with how they acquired the imperial " +
                "treasures. But whosoever finds this dragon has hit the jackpot... whether they know it or not.",
                2600, Monster.MonsterType.DRAGON, 4, 2000, 100);
        new MirageDragon();
        new HeraldOfCreation();
        new GraveYardEffectMonster("Exploder Dragon", "If this card is destroyed by battle and sent to the" +
                " Graveyard: Destroy the monster that destroyed it. Neither player takes any battle damage from attacks" +
                " involving this attacking card.", 1000, Monster.MonsterType.DRAGON, 3, 1000, 0, false);
        new Monster("Warrior Dai Grepher", "The warrior who can manipulate dragons. Nobody knows his " +
                "mysterious past.", 3400, Monster.MonsterType.WARRIOR, 4, 1700, 1600);
        new Monster("Dark Blade", "They say he is a dragon-manipulating warrior from the dark world. His" +
                " attack is tremendous, using his great swords with vicious power.",
                3500, Monster.MonsterType.WARRIOR, 4, 1800, 1500);
        new Monster("Wattaildragon", "\"Capable of indefinite flight. Attacks by wrapping its body with electricity and ramming into opponents.\n" +
                "IMPORTANT: Capturing the \"\"Wattaildragon\"\" is forbidden by the Ancient Rules and is a Level 6 " +
                "offense, the minimum sentence for which is imprisonment for no less than 2500 heliocycles.\"",
                5800, Monster.MonsterType.DRAGON, 6, 2500, 1000);
        new Terratiger();
        new TheTricky();
        new Monster("Spiral Serpent", "When huge whirlpools lay cities asunder, it is the hunger of this " +
                "sea serpent at work. No one has ever escaped its dreaded Spiral Wave to accurately describe the terror " +
                "they experienced.", 11700, Monster.MonsterType.SEA_SERPENT, 8, 2900, 2900);

    }

    public static void makeAllSpells() {
        new AdvancedRitualArt();
        new ChangeOfHeart();
        new DarkHole();
        new HarpiesFeatherDuster();
        new MessengerOfPeace();
        new MonsterReborn();
        new MysticalSpaceTyphoon();
        new PotOfGreed();
        new Raigeki();
        new RingOfDefense();
        new SpellAbsorption();
        new SupplySquad();
        new SwordsOfRevealingLight();
        new Terraforming();
        new TwinTwisters();
        new BlackPendant();
        new MagnumShield();
        new SwordOfDarkDestruction();
        new UnitedWeStand();
        new ClosedForest();
        new FieldSpell("Umiiruka", "Increase the ATK of all WATER monsters by 500 points and decrease " +
                "their DEF by 400 points.", new HashMap<>() {{
            put(Monster.MonsterType.AQUA, 500);
        }},
                new HashMap<>() {{
                    put(Monster.MonsterType.AQUA, -400);
                }});
        new FieldSpell("Forest", "All Insect, Beast, Plant, and Beast-Warrior monsters on the field gain 200 ATK/DEF.",
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
        new FieldSpell("Yami", "All Fiend and Spellcaster monsters on the field gain 200 ATK/DEF, also " +
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
    }

    public static void makeAllTraps() {
        new CallOfTheHaunted();
        new MagicCylinder();
        new MagicJammer();
        new MindCrush();
        new MirrorForce();
        new NegateAttack();
        new SolemnWarning();
        new TimeSeal();
        new TorrentialTribute();
        new TrapHole();
    }
}
