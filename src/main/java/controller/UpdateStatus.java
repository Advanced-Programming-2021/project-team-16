package controller;

import model.card.Card;
import model.card.monster.*;
import model.card.spell.Spell;

public class UpdateStatus {
    public static void makeAllCards() {
        makeAllMonsters();
        makeAllSpells();
        makeAllTraps();
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
        new TerratigerTheEmpoweredWarrior();
        new TheTricky();
        new Monster("Spiral Serpent", "When huge whirlpools lay cities asunder, it is the hunger of this " +
                "sea serpent at work. No one has ever escaped its dreaded Spiral Wave to accurately describe the terror " +
                "they experienced.", 11700, Monster.MonsterType.SEA_SERPENT, 8, 2900, 2900);

    }

    public static void makeAllSpells() {
        new Spell("Advanced Ritual Art", "Spell", Spell.SpellType.RITUAL
                , "This card can be used to Ritual Summon any 1 Ritual Monster. You must also send Normal Monsters from your Deck to the" +
                " Graveyard whose total Levels equal the Level of that Ritual Monster.", "Unlimited", 3000);
        new Spell("Change of Heart", "Spell", Spell.SpellType.NORMAL
                , "Target 1 monster your opponent controls; take control of it until the End Phase.", "Limited", 2500);
        new Spell("Dark Hole", "Spell", Spell.SpellType.NORMAL
                , "Destroy all monsters on the field.", "Unlimited", 2500);

        new Spell("Harpie's Feather Duster", "Spell", Spell.SpellType.NORMAL
                , "Destroy all Spells and Traps your opponent controls.", "Limited", 2500);

        new Spell("Messenger of peace", "Spell", Spell.SpellType.CONTINUES
                , "Monsters with 1500 or more ATK cannot declare an attack. Once per turn, " +
                "during your Standby Phase, pay 100 LP or destroy this card.", "Unlimited", 4000);

        new Spell("Monster Reborn", "Spell", Spell.SpellType.NORMAL
                , "Target 1 monster in either GY; Special Summon it.", "Limited", 2500);

        new Spell("Mystical space typhoon", "Spell", Spell.SpellType.QUICK_PLAY
                , "Target 1 Spell/Trap on the field; destroy that target.", "Unlimited", 3500);

        new Spell("Pot of Greed", "Spell", Spell.SpellType.NORMAL,
                "Draw 2 cards.", "Limited", 2500);

        new Spell("Raigeki", "Spell", Spell.SpellType.NORMAL
                , "Destroy all monsters your opponent controls.", "Limited", 2500);

        new Spell("Ring of defense", "Spell", Spell.SpellType.QUICK_PLAY
                , "When a Trap effect that inflicts damage is activated: Make that effect damage 0.", "Unlimited", 3500);

        new Spell("Spell Absorption", "Spell", Spell.SpellType.CONTINUES
                , "Each time a Spell Card is activated, gain 500 Life Points immediately after it resolves.", "Unlimited", 4000);

        new Spell("Supply Squad", "Spell", Spell.SpellType.CONTINUES
                , "Once per turn, if a monster(s) you control is " +
                "destroyed by battle or card effect: Draw 1 card.", "Unlimited", 4000);

        new Spell("Swords of Revealing Light", "Spell", Spell.SpellType.NORMAL
                , "After this card's activation, it remains on the field, but destroy it during the End Phase of your opponent's 3rd turn. When this card is activated: If your opponent controls a face-down monster, flip all monsters they control face-up. While this card is face-up on the field, your opponent's monsters cannot declare an attack."
                , "Unlimited", 2500);

        new Spell("Terraforming", "Spell", Spell.SpellType.NORMAL
                , "Add 1 Field Spell from your Deck to your hand.", "Limited", 2500);

        new Spell("Twin Twisters", "Spell", Spell.SpellType.QUICK_PLAY
                , "Discard 1 card, then target up to 2 Spells/Traps on the field; destroy them.", "Unlimited", 3500);

        new Spell("Black Pendant", "Spell", Spell.SpellType.EQUIP
                , "The equipped monster gains 500 ATK. When this card is sent from the field to " +
                "the Graveyard: Inflict 500 damage to your opponent.", "Unlimited", 4300);

        new Spell("Magnum Shield", "Spell", Spell.SpellType.EQUIP
                , "Equip only to a Warrior-Type monster. Apply this effect, depending on its battle position." +
                "● Attack Position: It gains ATK equal to its original DEF." +
                "● Defense Position: It gains DEF equal to its original ATK.", "Unlimited", 4300);

        new Spell("Sword of dark destruction", "Spell", Spell.SpellType.EQUIP
                , "A DARK monster equipped with this card increases its " +
                "ATK by 400 points and decreases its DEF by 200 points.", "Unlimited", 4300);

        new Spell("United We Stand", "Spell", Spell.SpellType.EQUIP
                , "The equipped monster gains 800 ATK/DEF for each face-up monster" +
                " you control.", "Unlimited", 4300);
//        new Spell
//        new Spell         -->  for field spells
//        new Spell
//        new Spell
    }

    public static void makeAllTraps() {
    }
}
