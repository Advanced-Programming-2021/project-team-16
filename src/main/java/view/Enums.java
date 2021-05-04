package view;

public class Enums {

    public enum LoginCommands {
        LOGIN("user login (--username (\\w+) --password (\\w+)|--password (\\w+) --username (\\w+))"),
        LOGOUT("user logout"),
        CREATE_USER("user create (--username (\\w+) --nickname (\\w+) --password (\\w+)|" +
                "--password (\\w+) --username (\\w+) --nickname (\\w+)|--nickname (\\w+) --password (\\w+) --username (\\w+)|" +
                "--username (\\w+) --password (\\w+) --nickname (\\w+)|--nickname (\\w+) --username (\\w+) --password (\\w+)|" +
                "--password (\\w+) --nickname (\\w+) --username (\\w+))"),
        SHOW_CURRENT("menu show-current"),
        ENTER_MENU("menu enter (.*)"),
        EXIT("menu exit");
        private String regex;

        public String getRegex() {
            return regex;
        }

        LoginCommands(String regex) {
            this.regex = regex;
        }
    }

    public enum MainMenuCommands {}

    public enum ShopCommands {
        //show card -> page 11 -> doc phase 1
    }

    public enum DeckMenuCommands {
        //show card -> page 11 -> doc phase 1
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
        SHOW_CARD("card show (\\w+)");
        private final String regex;

        public String getRegex() {
            return regex;
        }

        GameCommands(String regex) {
            this.regex = regex;
        }
    }

}
