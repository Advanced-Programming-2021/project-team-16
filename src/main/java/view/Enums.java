package view;

public class Enums {

    public enum LoginCommands {
        LOGIN("user login --username (\\w) --password (\\w)"),
        LOGOUT("user logout"),
        CREATE_USER("user create --username (\\w) --nickname (\\w) --password (\\w)"),
        SHOW_CURRENT("menu show-current"),
        ENTER_MENU("menu enter (.*)"),
        EXIT("menu exit");
        String regex;

        public String getRegex() {
            return regex;
        }

        LoginCommands(String regex) {
            this.regex = regex;
        }
    }

    public enum MainMenuCommands {}

    public enum ShopCommands {}

    public enum DeckMenuCommands {}

    public enum ProfileCommands {
        CHANGE_NICKNAME("profile change --nickname (\\w)"),
        CHANGE_PASSWORD("profile change --password --current (\\w) --new (\\w)"),
        SHOW_CURRENT("menu show-current"),
        ENTER_MENU("menu enter (.*)"),
        EXIT("menu exit");

        String regex;

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
        SHOW_CURRENT("menu show-current"),
        ENTER_MENU("menu enter (.*)"),
        EXIT("menu exit");


        String regex;

        public String getRegex() {
            return regex;
        }

        GameMenuCommands(String regex) {
            this.regex = regex;
        }
    }

}
