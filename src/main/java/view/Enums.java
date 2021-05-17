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
        private String regex;

        public String getRegex() {
            return regex;
        }

        LoginCommands(String regex) {
            this.regex = regex;
        }
    }

    public enum MainMenuCommands {
        SHOW_CURRENT("menu show-current"),
        ENTER_MENU("menu enter (\\w+)"),
        LOGOUT("user logout"),
        EXIT("menu exit");
        private String regex;

        public String getRegex() {
            return regex;
        }

        MainMenuCommands(String regex) {
            this.regex = regex;
        }

    }

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

    public enum DeckMenuCommands {
        SHOW_CARD("card show (.*)"),
        CREATE_DECK("deck create (.*)"),
        DELETE_DECK("deck delete (.*)"),
        SET_ACTIVE_DECK("deck set-activate (.*)"),
        ADD_CARD_TO_MAIN("deck add-card (--card (.+) --deck (.+)|--deck (.+) --card (.+))"),
        ADD_CARD_TO_SIDE("deck add-card (--card (.+) --deck (.+) --side|--deck (.+) --side --card (.+)|" +
                "--side --card (.+) --deck (.+)|--side --deck (.+) --card (.+)|--card (.+) --side --deck (.+)|" +
                "--deck (.+) --card (.+) --side)"),
        RM_CARD_FROM_MAIN("deck rm-card (--card (.+) --deck (.+)|--deck (.+) --card (.+))"),
        RM_CARD_FROM_SIDE("deck rm-card (--card (.+) --deck (.+) --side|--deck (.+) --side --card (.+)|" +
                "--side --card (.+) --deck (.+)|--side --deck (.+) --card (.+)|--card (.+) --side --deck (.+)|" +
                "--deck (.+) --card (.+) --side)"),
        SHOW_ALL_DECKS("deck show --all"),
        SHOW_MAIN_DECK("deck show --deck-name (.+)"),
        SHOW_SIDE_DECK("deck show (--deck-name (.+) --side|--deck-name (.+) --side)"),
        SHOW_DECK_CARDS("deck show --cards"),
        ENTER_MENU("menu enter (.*)"),
        SHOW_CURRENT("menu show-current"),
        EXIT("menu exit");


        private String regex;

        public String getRegex() {
            return regex;
        }

        DeckMenuCommands(String regex) {
            this.regex = regex;
        }

    }

    public enum ImportExportCommands {
        IMPORT_CARD("import card (\\w+)"),
        EXPORT_CARD("export card (\\w+)"),
        ENTER_MENU("menu enter (.*)"),
        SHOW_CURRENT("menu show-current"),
        EXIT("menu exit");
        private String regex;

        public String getRegex() {
            return regex;
        }

        ImportExportCommands(String regex) {
            this.regex = regex;
        }
    }

    public enum ProfileCommands {
        CHANGE_NICKNAME("profile change --nickname (\\w+)"),
        CHANGE_PASSWORD("profile change --password (--current (\\w+) --new (\\w+)|--new (\\w+) --current (\\w+))"),
        SHOW_CURRENT("menu show-current"),
        ENTER_MENU("menu enter (.*)"),
        EXIT("menu exit");

        private String regex;

        public String getRegex() {
            return regex;
        }

        ProfileCommands(String regex) {
            this.regex = regex;
        }

    }

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

    public enum GameMenuCommands {
        DUEL("duel --new (--second-player (\\w+) --rounds (\\d+)|--rounds (\\d+) --second-player (\\w+))"),
        AI_DUEL("duel --new (--ai --rounds (\\d+)|--rounds (\\d+) --ai)"),
        SHOW_CURRENT("menu show-current"),
        ENTER_MENU("menu enter (.+)"),
        EXIT("menu exit");


        private String regex;

        public String getRegex() {
            return regex;
        }

        GameMenuCommands(String regex) {
            this.regex = regex;
        }
    }

    public enum GameCommands {
        SELECT_CARD("select --(\\w+) (\\d+)?( --opponent)?"),
        DESELECT_CARD("select -d"),
        SHOW_CARD("card show (.*)"),
        END_PHASE("end-phase");
        private final String regex;

        public String getRegex() {
            return regex;
        }

        GameCommands(String regex) {
            this.regex = regex;
        }
    }
}
