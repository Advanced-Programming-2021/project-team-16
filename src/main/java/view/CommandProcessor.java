package view;

import controller.*;
import model.Board;
import model.Game;
import model.card.Card;
import model.person.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandProcessor {
    private static Scanner scanner = new Scanner(System.in);

    private static HashMap<String, String> getCommandData(String command) {
        HashMap<String, String> data = new HashMap<>();
        Matcher matcher = Pattern.compile("--(\\S+) ([^\\s-]+)").matcher(command);
        while (matcher.find()) data.put(matcher.group(1), matcher.group(2));
        return data;
    }

    private static void login() {
        HashMap<String, String> data;
        for (String command = scanner.nextLine().trim(); !command.equals(Enums.LoginCommands.LOGOUT.getRegex()) &&
                !command.equals(Enums.LoginCommands.EXIT.getRegex()); command = scanner.nextLine().trim()) {
            if (command.matches(Enums.LoginCommands.LOGIN.getRegex())) {
                data = getCommandData(command);
                String result = Login.login(data.get("username"), data.get("password"));
                System.out.println(result);
                if (result.equals("user logged in successfully!")) mainMenu();
            } else if (command.matches(Enums.LoginCommands.CREATE_USER.getRegex())) {
                data = getCommandData(command);
                System.out.println(Login.signUp(data.get("username"), data.get("password"), data.get("nickname")));
            } else if (command.equals(Enums.LoginCommands.SHOW_CURRENT.getRegex()))
                System.out.println(Login.menuName());
            else if (command.matches(Enums.LoginCommands.ENTER_MENU.getRegex()))
                System.out.println("please login first");
            else System.out.println("invalid command");
        }
        System.out.println("user logged out successfully!");

    }

    private static void mainMenu() {
    }

    private static void shop() {
        String command = scanner.nextLine().trim();
        while (!command.matches(Enums.ShopCommands.EXIT.getRegex())) {

            if (command.matches(Enums.ShopCommands.SHOP_BUY.getRegex())) {
                System.out.println(Shop.buy(command));
            } else if (command.equals(Enums.ShopCommands.SHOP_SHOW.getRegex())) {
                System.out.println(Shop.allCardsOfShop(command));
            } else System.out.println("invalid command!");
            command = scanner.nextLine().trim();
        }
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
                data = getCommandData(command);
                System.out.println(Profile.changeNickname(data.get("nickname")));
            } else if (command.matches(Enums.ProfileCommands.CHANGE_PASSWORD.getRegex())) {
                data = getCommandData(command);
                System.out.println(Profile.changePassword(data.get("current"), data.get("new"));
            } else System.out.println("invalid command");
        }
    }

    public static void gameMenu() {
        HashMap<String, String> data;
        for (String command = scanner.nextLine().trim(); !command.equals(Enums.GameMenuCommands.EXIT.getRegex()); command = scanner.nextLine().trim()) {
            if (command.equals(Enums.GameMenuCommands.SHOW_CURRENT.getRegex()))
                System.out.println(GameMenu.menuName());
            else if (command.equals(Enums.GameMenuCommands.ENTER_MENU.getRegex()))
                System.out.println("menu navigation is not possible");
            else if (command.matches(Enums.GameMenuCommands.DUEL.getRegex())) {
                data = getCommandData(command);
                User secondUser = User.getUserByUsername(data.get("second-player"));
                User firstUser = MainMenu.getCurrentUser();
                String error = null;
                if (secondUser == null) error = "there is no player with this username";
                else if (firstUser.getActiveDeck() == null) error = firstUser.getUsername() + " has no active deck";
                else if (secondUser.getActiveDeck() == null) error = secondUser.getUsername() + " has no active deck";
                else if (!firstUser.getActiveDeck().isDeckValid())
                    error = firstUser.getUsername() + "’s deck is invalid";
                else if (!secondUser.getActiveDeck().isDeckValid())
                    error = secondUser.getUsername() + "’s deck is invalid";
                else if (!data.get("rounds").equals("1") && !data.get("rounds").equals("3"))
                    error = "number of rounds is not supported";
                if (error != null) System.out.println(error);
                else {
                    System.out.println("duel started successfully");
                    GameMenu.duel(secondUser, Integer.parseInt(data.get("rounds")));
                }
            } else if (command.matches(Enums.GameMenuCommands.AI_DUEL.getRegex())) {
                User firstUser = MainMenu.getCurrentUser();
                String rounds = getCommandData(command).get("rounds");
                String error = null;
                if (firstUser.getActiveDeck() == null) error = firstUser.getUsername() + " has no active deck";
                else if (!firstUser.getActiveDeck().isDeckValid())
                    error = firstUser.getUsername() + "’s deck is invalid";
                else if (!rounds.equals("1") && !rounds.equals("3"))
                    error = "number of rounds is not supported";
                if (error != null) System.out.println(error);
                else {
                    System.out.println("duel started successfully");
                    GameMenu.duel(null, Integer.parseInt(rounds));
                }
            } else System.out.println("invalid command");
        }
    }

    public static void game() {
        Game game = GameMenu.getCurrentGame();
        boolean isSelectedCardForOpponent;
        Matcher matcher;
        for (String command = scanner.nextLine().trim(); !command.equals(Enums.GameCommands.END_PHASE.getRegex()); command = scanner.nextLine().trim()) {
            Show.showBoard();
            if ((matcher = Pattern.compile(Enums.GameCommands.SELECT_CARD.getRegex()).matcher(command)).find()) {
                isSelectedCardForOpponent = matcher.group(3) != null;
                boolean isSelectionValid = true;
                int index = -1;
                Board.Zone zone = getZoneByZoneName(matcher.group(1));
                if (matcher.group(2) == null && zone != Board.Zone.FIELD_SPELL) isSelectionValid = false;
                if (matcher.group(2) != null) {
                    if (matcher.group(2).length() > 3) isSelectionValid = false;
                    else index = Integer.parseInt(matcher.group(2));
                    if (index == 0) isSelectionValid = false;
                    if (zone == null) isSelectionValid = false;
                    else
                        switch (zone) {
                            case HAND -> {
                                if (index > 6) isSelectionValid = false;
                            }
                            case MONSTER, SPELL_AND_TRAP -> {
                                if (index > 5) isSelectionValid = false;
                            }
                            case GRAVE, DECK -> isSelectionValid = false;
                        }
                }
                if (isSelectedCardForOpponent && zone == Board.Zone.HAND)
                    System.out.println("you can't choose your opponent's hand");
                else if (!isSelectionValid) System.out.println("invalid selection");
                else System.out.println(game.selectCard(zone, index, isSelectedCardForOpponent));
            } else if (command.equals(Enums.GameCommands.DESELECT_CARD.getRegex())) System.out.println(game.deselect());
            else if (command.equals("summon")) System.out.println(game.summon(null)); //TODO : summon type??
            else if (command.equals("set")) System.out.println(game.set());
            else if (command.equals("flip-summon")) System.out.println(game.flipSummon());
            else if ((matcher = Pattern.compile("attack (\\d+)").matcher(command)).find()) {
                boolean isIndexValid = true;
                int index = -1;
                if (matcher.group(1).length() > 3) isIndexValid = false;
                else {
                    index = Integer.parseInt(matcher.group(1));
                    if (index < 1 || index > 5) isIndexValid = false;
                }
                if (isIndexValid) System.out.println(game.attack(index));
                else System.out.println("index is not valid");
            } else if (command.matches("attack\\sdirect")) System.out.println(game.attack(-1));
            else if (command.matches("activate\\seffect")) System.out.println(game.activeEffect());
            else if (command.matches("show\\sgraveyard")) {
                ArrayList<Card> currentGrave = game.getCurrentPlayer().getBoard().getGrave();
                ArrayList<Card> rival = game.getRival().getBoard().getGrave();
                if (currentGrave.size() == 0) System.out.println("your graveyard empty");
                else {
                    System.out.println("your graveyard:");
                    Show.showCardArray(game.getCurrentPlayer().getBoard().getGrave());
                }
                if (rival.size() == 0) System.out.println("rival's graveyard empty");
                else {
                    System.out.println("rival's graveyard:");
                    Show.showCardArray(game.getRival().getBoard().getGrave());
                }
                while (!scanner.nextLine().trim().equals("back")) ;

            } else if (command.matches("card\\sshow\\s--selected")) System.out.println(game.showSelectedCard());
            else if (command.equals("surrender")) game.surrendered();
            else System.out.println("invalid command");
            if (game.didSbWin()) return;
        }
    }

    /*public static String scan() {
        return scanner.nextLine();

    }*/ // -> harchi lazeme bayad methode joda dashte bashe ke badan beshe tooye gerafic in method haro jodagoone eslah card


    private static void scoreboard() {
        for (String command = scanner.nextLine().trim(); !command.equals(Enums.ScoreboardCommands.EXIT.getRegex()); command = scanner.nextLine().trim()) {
            if (command.equals(Enums.ScoreboardCommands.SHOW_SCOREBOARD.getRegex()))
                Scoreboard.showScoreboard();
            else if (command.equals(Enums.ScoreboardCommands.SHOW_CURRENT.getRegex()))
                System.out.println(Scoreboard.menuName());
            else if (command.equals(Enums.ScoreboardCommands.ENTER_MENU.getRegex()))
                System.out.println("menu navigation is not possible");
            else System.out.println("invalid command");
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
        return getZoneByZoneName(zoneName);
    }

    private static Board.Zone getZoneByZoneName(String zoneName) {
        Board.Zone zone = null;
        switch (zoneName.toLowerCase()) {
            case "field" -> zone = Board.Zone.FIELD_SPELL;
            case "monster" -> zone = Board.Zone.MONSTER;
            case "spell" -> zone = Board.Zone.SPELL_AND_TRAP;
            case "grave" -> zone = Board.Zone.GRAVE;
            case "deck" -> zone = Board.Zone.DECK;
            case "hand" -> zone = Board.Zone.HAND;
        }
        return zone;
    }
}

