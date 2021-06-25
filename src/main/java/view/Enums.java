package view;

public class Enums {

    public enum LoginCommands {
        LOGIN("user login (--username (\\w+) --password (\\w+)|--password (\\w+) --username (\\w+))"),
        CREATE_USER("user create (--username (\\w+) --nickname (\\w+) --password (\\w+)|" +
                "--password (\\w+) --username (\\w+) --nickname (\\w+)|--nickname (\\w+) --password (\\w+) --username (\\w+)|" +
                "--username (\\w+) --password (\\w+) --nickname (\\w+)|--nickname (\\w+) --username (\\w+) --password (\\w+)|" +
                "--password (\\w+) --nickname (\\w+) --username (\\w+))"),
        SHOW_CURRENT("menu show-current"),
        ENTER_MENU("menu enter (.*)"),
        EXIT("(menu exit|exit)");
        private final String regex;

        public String getRegex() {
            return regex;
        }

        LoginCommands(String regex) {
            this.regex = regex;
        }
    }

    public static final String LOGIN_HELP = """
            user login --username <username> --password <password>
            user create --username <username> --nickname <nickname> --password <password>
            menu show-current
            menu enter <menu name>
            exit
            """;

    public enum MainMenuCommands {
        SHOW_CURRENT("menu show-current"),
        ENTER_MENU("menu enter (\\w+)"),
        LOGOUT("user logout"),
        EXIT("menu exit");
        private final String regex;

        public String getRegex() {
            return regex;
        }

        MainMenuCommands(String regex) {
            this.regex = regex;
        }

    }

    public static final String MAIN_Menu_HELP = """
            menu show-current
            menu enter <Duel,Deck,Scoreboard,Profile,Shop,Import/Export>
            user logout
            menu exit
            """;


    public enum ShopCommands {
        SHOP_BUY("shop buy (.*)"),
        SHOW_ALL_CARDS("shop show --all"),
        SHOW_CURRENT("menu show-current"),
        ENTER_MENU("menu enter (.*)"),
        SHOW_CARD("card show (.*)"),
        EXIT("menu exit");
        private final String regex;

        public String getRegex() {
            return regex;
        }

        ShopCommands(String regex) {
            this.regex = regex;
        }

    }

    public static final String SHOP_HELP = """
            shop buy <card name>
            shop show --all
            card show <card name>
            menu show-current
            menu enter <name>
            menu exit
            """;

    public enum DeckMenuCommands {
        SHOW_CARD("card show (.*)"),
        CREATE_DECK("deck create ([a-zA-Z ]+)"),
        DELETE_DECK("deck delete ([a-zA-Z ]+)"),
        SET_ACTIVE_DECK("deck set-activate (.*)"),
        ADD_CARD_TO_MAIN("deck add-card (--card (.+) --deck ([a-zA-Z ]+)$|--deck ([a-zA-Z ]+) --card (.+)$)"),
        ADD_CARD_TO_SIDE("deck add-card (--card ([a-zA-Z ]+) --deck ([a-zA-Z ]+) --side|--card ([a-zA-Z ]+) --side --deck ([a-zA-Z ]+)|" +
                "--deck ([a-zA-Z ]+) --card ([a-zA-Z ]+) --side|--deck ([a-zA-Z ]+) --side --card ([a-zA-Z ]+)|" +
                "--side --deck ([a-zA-Z ]+) --card ([a-zA-Z ]+)|--side --card ([a-zA-Z ]+) --deck ([a-zA-Z ]+))"),
        RM_CARD_FROM_MAIN("deck rm-card (--card (.+) --deck ([a-zA-Z ]+)$|--deck ([a-zA-Z ]+) --card (.+)$)"),
        RM_CARD_FROM_SIDE("deck rm-card (--card ([a-zA-Z ]+) --deck ([a-zA-Z ]+) --side|--card ([a-zA-Z ]+) --side --deck ([a-zA-Z ]+)|" +
                "--deck ([a-zA-Z ]+) --card ([a-zA-Z ]+) --side|--deck ([a-zA-Z ]+) --side --card ([a-zA-Z ]+)|" +
                "--side --deck ([a-zA-Z ]+) --card ([a-zA-Z ]+)|--side --card ([a-zA-Z ]+) --deck ([a-zA-Z ]+))"),
        SHOW_ALL_DECKS("deck show --all"),
        SHOW_MAIN_DECK("deck show --deck-name ([a-zA-Z ]+)$"),
        SHOW_SIDE_DECK("deck show --deck-name ([a-zA-Z ]+) --side"),
        SHOW_DECK_CARDS("deck show --cards"),
        ENTER_MENU("menu enter (.*)"),
        SHOW_CURRENT("menu show-current"),
        EXIT("menu exit");


        private final String regex;

        public String getRegex() {
            return regex;
        }

        DeckMenuCommands(String regex) {
            this.regex = regex;
        }

    }

    public static final String DECK_HELP = """
            deck create <deck name>
            deck delete <deck name>
            deck set-activate <deck name>
            deck add-card --card <card name> --deck <deck name> (--side)
            deck rm-card --card <card name> --deck <deck name> (--side)
            deck show --all
            deck show --deck-name <deck name> (--side)
            deck show --cards
            card show <card name>
            menu show-current
            menu enter <name>
            menu exit
            """;

    public enum ImportExportCommands {
        IMPORT_CARD("import card (\\w+)"),
        EXPORT_CARD("export card (\\w+)"),
        ENTER_MENU("menu enter (.*)"),
        SHOW_CURRENT("menu show-current"),
        EXIT("menu exit");
        private final String regex;

        public String getRegex() {
            return regex;
        }

        ImportExportCommands(String regex) {
            this.regex = regex;
        }
    }

    public static final String IMPORT_EXPORT_HELP = """
            import card <card name>
            export card <card name>
            menu show-current
            menu enter <name>
            menu exit
            """;

    public enum ProfileCommands {
        CHANGE_NICKNAME("profile change --nickname (\\w+)"),
        CHANGE_PASSWORD("profile change --password (--current (\\w+) --new (\\w+)|--new (\\w+) --current (\\w+))"),
        CHANGE_USERNAME("profile change --username (\\w+)"),
        SHOW_CURRENT("menu show-current"),
        ENTER_MENU("menu enter (.*)"),
        EXIT("menu exit");

        private final String regex;

        public String getRegex() {
            return regex;
        }

        ProfileCommands(String regex) {
            this.regex = regex;
        }

    }

    public static final String PROFILE_HELP = """
            profile change --nickname <new nickname>
            profile change --password --current <current password> --new <new password>
            profile change --username <new username>
            menu show-current
            menu enter <name>
            menu exit
            """;

    public enum ScoreboardCommands {
        SHOW_SCOREBOARD("scoreboard show"),
        SHOW_CURRENT("menu show-current"),
        ENTER_MENU("menu enter (.*)"),
        EXIT("menu exit");

        String regex;

        public String getRegex() {
            return regex;
        }

        ScoreboardCommands(String regex) {
            this.regex = regex;
        }

    }

    public static final String SCOREBOARD_HELP = """
            scoreboard show
            menu show-current
            menu enter <name>
            menu exit
            """;

    public enum GameMenuCommands {
        DUEL("duel --new (--second-player (\\w+) --rounds (\\d+)|--rounds (\\d+) --second-player (\\w+))"),
        AI_DUEL("duel --new (--ai --rounds (\\d+)|--rounds (\\d+) --ai)"),
        SHOW_CURRENT("menu show-current"),
        ENTER_MENU("menu enter (.+)"),
        EXIT("menu exit");


        private final String regex;

        public String getRegex() {
            return regex;
        }

        GameMenuCommands(String regex) {
            this.regex = regex;
        }
    }
    public static final String DUEL_HELP = """
            duel --new --second-player <second player username> --rounds <1/3>
            duel --new --ai --rounds <1/3>
            menu show-current
            menu enter <name>
            menu exit
            """;
    public static final String GAME_HELP_MAIN = """
            select --monster <number> (--opponent)
            select --spell <number> (--opponent)
            select --hand <number>
            select --field (--opponent)
            select -d
            card show --selected
            card show <card name>
            summon
            set
            flip-summon
            set --position (attack|defense)
            activate effect
            show graveyard
            surrender
            next phase
            """;
    public static final String GAME_HELP_BATTLE = """
            select --monster <number> (--opponent)
            select -d
            card show --selected
            card show <card name>
            attack <number of rival's monster>
            attack direct
            show graveyard
            surrender
            next phase
            """;
    public enum GameCommands {
        SUMMON("summon"),
        SET("set"),
        ATTACK("attack (\\d+)"),
        ATTACK_DIRECT("attack direct"),
        ACTIVE_EFFECT("activate effect"),
        FLIP_SUMMON("flip-summon"),
        SET_POSITION("set --position (attack|defense)"),
        SHOW_GRAVE("show graveyard( --opponent)?"),
        SHOW_SELECTED("card show --selected"),
        SURRENDER("surrender"),
        SELECT_CARD("select --(\\w+)\\s*(\\d+)?( --opponent)?"),
        DESELECT_CARD("select -d"),
        SHOW_CARD("card show (.*)"),
        END_PHASE("next phase"),
        HELP_MAIN("help-main"),
        HELP_BATTLE("help-battle"),
        ADD_CARD("add card (.*)"),
        RM_CARD("rm card (.*)");
        private final String regex;

        public String getRegex() {
            return regex;
        }

        GameCommands(String regex) {
            this.regex = regex;
        }
    }

    public enum Cheat {
        INCREASE_LP("increase --LP (\\d+)"),
        INCREASE_MONEY("increase --money (\\d+)"),
        WIN_DUEL("duel set-winner"),
        SET_AND_SUMMON_AGAIN("not summon/set yet");
        private final String regex;

        public String getRegex() {
            return regex;
        }

        Cheat(String regex) {
            this.regex = regex;
        }
    }
}
