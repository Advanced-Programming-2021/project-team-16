package view;

import controller.GameMenu;
import model.Board;
import model.Game;
import model.card.Card;

import java.util.ArrayList;
import java.util.Scanner;

public class CommandProcessor {
    private static Scanner scanner = new Scanner(System.in);
    private static ArrayList getCommandMatcher(String command, String regex) {
    } //!!!!!!!!!!!

    private static void login() {
    }

    private static void mainMenu() {
    }

    private static void shop() {
    }

    private static void deckMenu() {
    }

    private static void profile() {
    }

    public static void game() {
        Game game = GameMenu.getCurrentGame();
        //bade har ettefagh:  if (didSbWin()) return;
        //if sb surrendered -> surrendered();
        //hamleye mostaghim -> attack(-1)
    }

    public static String scan() {
        return scanner.nextLine();

    }

    private static void scoreboard() {
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

