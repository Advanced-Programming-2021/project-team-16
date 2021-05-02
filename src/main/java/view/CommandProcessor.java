package view;

import controller.GameMenu;
import controller.Login;
import controller.Profile;
import controller.Scoreboard;
import model.Board;
import model.Game;
import model.card.Card;

import java.util.HashMap;
import java.util.Scanner;

public class CommandProcessor {
    private static Scanner scanner = new Scanner(System.in);

    private static HashMap<String, String> getCommandMatcher(String command, String regex) {

    } //!!!!!!!!!!!

    private static void login() {
        HashMap<String, String> data;
        for (String command = scanner.nextLine().trim(); !command.equals(Enums.LoginCommands.LOGOUT.getRegex()) &&
                !command.equals(Enums.LoginCommands.EXIT.getRegex()); command = scanner.nextLine().trim()) {
            if (command.matches(Enums.LoginCommands.LOGIN.getRegex())) {
                data = getCommandMatcher(command, Enums.LoginCommands.LOGIN.getRegex());
                String result = Login.login(data.get("username"), data.get("password"));
                System.out.println(result);
                if (result.equals("user logged in successfully!")) mainMenu();
            } else if (command.matches(Enums.LoginCommands.CREATE_USER.getRegex())) {
                data = getCommandMatcher(command, Enums.LoginCommands.CREATE_USER.getRegex());
                System.out.println(Login.signUp(data.get("username"), data.get("password"), data.get("nickname")));
            } else if (command.equals(Enums.LoginCommands.SHOW_CURRENT.getRegex()))
                System.out.println(Login.menuName());
            else if (command.matches(Enums.LoginCommands.ENTER_MENU.getRegex()))
                System.out.println("please login first");
        }
        System.out.println("user logged out successfully!");

    }

    private static void mainMenu() {
    }

    private static void shop() {
    }

    private static void deckMenu() {
    }

    private static void profile() {

        HashMap<String, String> data;
        for (String command = scanner.nextLine().trim(); !command.equals(Enums.ProfileCommands.EXIT.getRegex()); command = scanner.nextLine().trim()) {
            if (command.equals(Enums.ProfileCommands.SHOW_CURRENT.getRegex()))
                System.out.println(Profile.menuName());
            else if (command.equals(Enums.ProfileCommands.ENTER_MENU.getRegex()))
                System.out.println("menu navigation is not possible");
            else if (command.matches(Enums.ProfileCommands.CHANGE_NICKNAME.getRegex())) {
                data = getCommandMatcher(command, Enums.ProfileCommands.CHANGE_NICKNAME.getRegex());
                System.out.println(Profile.changeNickname(data.get("nickname")));
            } else if (command.matches(Enums.ProfileCommands.CHANGE_PASSWORD.getRegex())) {
                data = getCommandMatcher(command, Enums.ProfileCommands.CHANGE_PASSWORD.getRegex());
                System.out.println(Profile.changePassword(data.get("current"), data.get("new"));
            }
        }
    }

    public static void game() {
        Game game = GameMenu.getCurrentGame();
        //bade har ettefagh:  if (didSbWin()) return;
        //if sb surrendered -> surrendered();
        //hamleye mostaghim -> attack(-1)
    }

    /*public static String scan() {
        return scanner.nextLine();

    }*/ // -> harchi lazeme bayad methode joda dashte bashe ke badan beshe tooye gerafic in method haro jodagoone eslah card


    private static void scoreboard() {
        HashMap<String, String> data;
        for (String command = scanner.nextLine().trim(); !command.equals(Enums.ScoreboardCommands.EXIT.getRegex()); command = scanner.nextLine().trim()) {
            if (command.equals(Enums.ScoreboardCommands.SHOW_SCOREBOARD.getRegex()))
                Scoreboard.showScoreboard();
            else if (command.equals(Enums.ScoreboardCommands.SHOW_CURRENT.getRegex()))
                System.out.println(Scoreboard.menuName());
            else if (command.equals(Enums.ScoreboardCommands.ENTER_MENU.getRegex()))
                System.out.println("menu navigation is not possible");
        }
    }

    public static Card askForHandTribute() {
    }

    public static boolean yesNoQuestion(String question) {
        System.out.println(question);
        return "yes".equals(scanner.nextLine());


    }

    public static int getCardIndex() {
        return scanner.nextInt();
    }

    public static String getCardName() {
        return scanner.nextLine();
    }

    public static Board.Zone getZone() {
        String zoneName = scanner.nextLine();
        // zoneName -> switch/case
    }
}

