package view;

import controller.*;
import graphicview.GraphicUtils;
import graphicview.LoginMenu;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Board;
import model.Game;
import model.Phase;
import model.card.Card;
import model.card.monster.Monster;
import model.person.AI;
import model.person.Player;
import server.model.User;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CommandProcessor {
    public static final Scanner scanner = new Scanner(System.in);
    private static boolean isAnswerYes;
    private static String cardNameInput = "name";
    private static int indexOfArray;

    private static HashMap<String, String> getCommandData(String command) {
        HashMap<String, String> data = new HashMap<>();
        Matcher matcher = Pattern.compile("--(\\S+) ([^\\-]+)").matcher(command);
        while (matcher.find()) data.put(matcher.group(1).trim(), matcher.group(2).trim());
        return data;
    }


    private static Matcher getCommandMatcher(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(input);
    }

    public static void login() {
        System.out.print("welcome to duel links!\n");
        HashMap<String, String> data;
        for (String command = scanner.nextLine().trim(); !command.matches(Enums.LoginCommands.EXIT.getRegex()); command = scanner.nextLine().trim()) {
            if (command.matches(Enums.LoginCommands.LOGIN.getRegex())) {
                data = getCommandData(command);
                String result = Login.login(data.get("username"), data.get("password"));
                System.out.print(result + "\n");
                if (result.equals("user logged in successfully!")) mainMenu();
            } else if (command.matches(Enums.LoginCommands.CREATE_USER.getRegex())) {
                data = getCommandData(command);
                System.out.println(Login.signUp(data.get("username"), data.get("password"), data.get("nickname")));
            } else if (command.equals(Enums.LoginCommands.SHOW_CURRENT.getRegex()))
                System.out.println(Login.menuName());
            else if (command.startsWith(Enums.LoginCommands.SHOW_CHAT_ROOM.getRegex())) {
                ChatRoom.showChatRoom();}
              //  System.out.println("show-chatroom");}
            else if (command.matches(Enums.LoginCommands.ENTER_MENU.getRegex()))
                System.out.println("please login first");
            else if (command.equals("help")) System.out.println(Enums.LOGIN_HELP);
            else System.out.println("invalid command");
        }
     //   if (LoginMenu.getMainStage() != null) LoginMenu.getMainStage().close();
    }


    public static void mainMenu() {
        Matcher matcher;
        String command = scanner.nextLine().trim();
        while (!command.equals(Enums.MainMenuCommands.EXIT.getRegex()) && !command.equals(Enums.MainMenuCommands.LOGOUT.getRegex())) {

            if (command.matches(Enums.MainMenuCommands.ENTER_MENU.getRegex())) {
                matcher = getCommandMatcher(command, Enums.MainMenuCommands.ENTER_MENU.getRegex());
                if (matcher.find()) {
                    switch (matcher.group(1)) {
                        case "Duel" -> gameMenu();
                        case "Deck" -> deckMenu();
                        case "Scoreboard" -> scoreboard();
                        case "Profile" -> profile();
                        case "Shop" -> shop();
                        case "ChatRoom" -> chatRoom();
                      //  case "ChatRoom2" -> chatRoom2();
                        case "Import/Export" -> importExportMenu();
                        default -> System.out.println("invalid command");
                    }
                }
            } else if (command.equals(Enums.MainMenuCommands.SHOW_CURRENT.getRegex())) {
                System.out.println(MainMenu.menuName());

            } else if (command.equals("help")) System.out.println(Enums.MAIN_Menu_HELP);
            else System.out.println("invalid command");

            command = scanner.nextLine().trim();
        }
        System.out.print("user logged out successfully!\n");
    }

    private static void shop() {
        String command = scanner.nextLine().trim();
        Matcher matcher;
        while (!command.matches(Enums.ShopCommands.EXIT.getRegex())) {
            if ((matcher = getCommandMatcher(command, Enums.ShopCommands.SHOP_BUY.getRegex())).find())
                System.out.println(Shop.buy(matcher.group(1)));
            else if (command.equals(Enums.ShopCommands.SHOW_ALL_CARDS.getRegex())) Show.showCardsInShop();
            else if ((matcher = getCommandMatcher(command, Enums.ShopCommands.SHOW_CARD.getRegex())).find())
                Show.showSingleCard(Card.getCardByName(matcher.group(1)));
            else if (command.equals(Enums.ShopCommands.SHOW_CURRENT.getRegex())) System.out.println(Shop.menuName());
            else if (command.matches(Enums.ShopCommands.ENTER_MENU.getRegex()))
                System.out.println("menu navigation is not possible");
            else if ((matcher = getCommandMatcher(command, Enums.Cheat.INCREASE_MONEY.getRegex())).find())
                MainMenu.getCurrentUser().increaseMoney(Integer.parseInt(matcher.group(1)));
            else if (command.equals("help")) System.out.println(Enums.SHOP_HELP);
            else System.out.println("invalid command!");
            command = scanner.nextLine().trim();
        }
    }


    private static void deckMenu() {
        Matcher matcher;
        HashMap<String, String> data;
        String command = scanner.nextLine().trim();
        while (!command.equals(Enums.DeckMenuCommands.EXIT.getRegex())) {

            if (command.matches(Enums.DeckMenuCommands.CREATE_DECK.getRegex())) {
                matcher = getCommandMatcher(command, Enums.DeckMenuCommands.CREATE_DECK.getRegex());
                if (matcher.find())
                    System.out.println(DeckMenu.create(matcher.group(1)));
            } else if (command.matches(Enums.DeckMenuCommands.DELETE_DECK.getRegex())) {
                matcher = getCommandMatcher(command, Enums.DeckMenuCommands.DELETE_DECK.getRegex());
                if (matcher.find())
                    System.out.println(DeckMenu.delete(matcher.group(1)));
            } else if ((getCommandMatcher(command, Enums.DeckMenuCommands.ADD_CARD_TO_SIDE.getRegex())).find()) {
                data = getCommandData(command);
                System.out.println(DeckMenu.addCardToDeck(data.get("card"), data.get("deck"), false));
            } else if ((getCommandMatcher(command, Enums.DeckMenuCommands.ADD_CARD_TO_MAIN.getRegex())).find()) {
                data = getCommandData(command);
                System.out.println(DeckMenu.addCardToDeck(data.get("card"), data.get("deck"), true));
            } else if ((getCommandMatcher(command, Enums.DeckMenuCommands.RM_CARD_FROM_SIDE.getRegex())).find()) {
                data = getCommandData(command);
                System.out.println(DeckMenu.removeCardFromDeck(data.get("card"), data.get("deck"), false));
            } else if ((getCommandMatcher(command, Enums.DeckMenuCommands.RM_CARD_FROM_MAIN.getRegex())).find()) {
                data = getCommandData(command);
                System.out.println(DeckMenu.removeCardFromDeck(data.get("card"), data.get("deck"), true));
            } else if (command.matches(Enums.DeckMenuCommands.SET_ACTIVE_DECK.getRegex())) {
                matcher = getCommandMatcher(command, Enums.DeckMenuCommands.SET_ACTIVE_DECK.getRegex());
                if (matcher.find())
                    System.out.println(DeckMenu.activate(matcher.group(1)));
            } else if (command.equals(Enums.DeckMenuCommands.SHOW_ALL_DECKS.getRegex())) {
                Show.showAllDecks();
            } else if (command.matches(Enums.DeckMenuCommands.SHOW_MAIN_DECK.getRegex())) {
                matcher = getCommandMatcher(command, Enums.DeckMenuCommands.SHOW_MAIN_DECK.getRegex());
                if (matcher.find())
                    Show.showMainDeck(matcher.group(1));
            } else if (command.matches(Enums.DeckMenuCommands.SHOW_SIDE_DECK.getRegex())) {
                matcher = getCommandMatcher(command, Enums.DeckMenuCommands.SHOW_SIDE_DECK.getRegex());
                if (matcher.find())
                    Show.showSideDeck(matcher.group(1).toLowerCase(Locale.ROOT));
            } else if (command.equals(Enums.DeckMenuCommands.SHOW_DECK_CARDS.getRegex())) {
                DeckMenu.showUsersCards();
            } else if (command.equals(Enums.DeckMenuCommands.SHOW_CURRENT.getRegex())) {
                System.out.println(DeckMenu.menuName());
            } else if (command.matches(Enums.DeckMenuCommands.ENTER_MENU.getRegex()))
                System.out.println("menu navigation is not possible");
            else if ((matcher = getCommandMatcher(command, Enums.DeckMenuCommands.SHOW_CARD.getRegex())).find())
                Show.showSingleCard(Card.getCardByName(matcher.group(1)));
            else if (command.equals("help")) System.out.println(Enums.DECK_HELP);
            else System.out.println("invalid command!");
            command = scanner.nextLine().trim();
        }
    }

    private static void profile() {
        User user = MainMenu.getCurrentUser();
        System.out.println("username: " + user.getUsername());
        System.out.println("nick name: " + user.getNickname());
        System.out.println("score: " + user.getScore());
        System.out.println("money: " + user.getMoney());
        HashMap<String, String> data;
        for (String command = scanner.nextLine().trim(); !command.equals(Enums.ProfileCommands.EXIT.getRegex()); command = scanner.nextLine().trim()) {
            if (command.equals(Enums.ProfileCommands.SHOW_CURRENT.getRegex()))
                System.out.println(Profile.menuName());
            else if (command.matches(Enums.ProfileCommands.ENTER_MENU.getRegex()))
                System.out.println("menu navigation is not possible");
            else if (command.matches(Enums.ProfileCommands.CHANGE_NICKNAME.getRegex())) {
                data = getCommandData(command);
                System.out.println(Profile.changeNickname(data.get("nickname")));
            } else if (command.matches(Enums.ProfileCommands.CHANGE_USERNAME.getRegex())) {
                data = getCommandData(command);
                System.out.println(Profile.changeUsername(data.get("username")));
            } else if (command.matches(Enums.ProfileCommands.CHANGE_PASSWORD.getRegex())) {
                data = getCommandData(command);
                System.out.println(Profile.changePassword(data.get("current"), data.get("new")));
            } else if (command.equals("help")) System.out.println(Enums.PROFILE_HELP);
            else System.out.println("invalid command");
        }
    }

    public static void gameMenu() {
        HashMap<String, String> data;
        for (String command = scanner.nextLine().trim(); !command.equals(Enums.GameMenuCommands.EXIT.getRegex()); command = scanner.nextLine().trim()) {
            if (command.equals(Enums.GameMenuCommands.SHOW_CURRENT.getRegex()))
                System.out.println(GameMenu.menuName());
            else if (command.matches(Enums.GameMenuCommands.ENTER_MENU.getRegex()))
                System.out.println("menu navigation is not possible");
            else if (command.matches(Enums.GameMenuCommands.DUEL.getRegex())) {
                data = getCommandData(command);
                User secondUser = User.getUserByUsername(data.get("second-player"));
                String error = GameMenu.isDuelPossibleWithError(data.get("rounds"), secondUser, false);
                if (error != null) System.out.println(error);
                else {
                    System.out.println("duel started successfully");
                    GameMenu.duel(secondUser, Integer.parseInt(data.get("rounds")), false);
                }
            } else if (command.matches(Enums.GameMenuCommands.AI_DUEL.getRegex())) {
                String rounds = getCommandData(command).get("rounds");
                String error = GameMenu.isDuelPossibleWithError(rounds, null, true);
                if (error != null) System.out.println(error);
                else {
                    System.out.println("duel started successfully");
                    GameMenu.duel(null, Integer.parseInt(rounds), false);
                }
            } else if (command.equals("help")) System.out.println(Enums.DUEL_HELP);
            else System.out.println("invalid command");
        }
    }

    public static void game() {
        Game game = GameMenu.getCurrentGame();
        boolean isSelectedCardForOpponent;
        Matcher matcher;
        for (String command = scanner.nextLine().trim(); !command.equals(Enums.GameCommands.END_PHASE.getRegex()); command = scanner.nextLine().trim()) {
            try {
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
                    else System.out.println(game.selectCard(zone, index - 1, isSelectedCardForOpponent));
                } else if (command.equals(Enums.GameCommands.DESELECT_CARD.getRegex()))
                    System.out.println(game.deselect());
                else if (command.equals(Enums.GameCommands.SUMMON.getRegex())) System.out.println(game.summon());
                else if (command.equals(Enums.GameCommands.SET.getRegex())) System.out.println(game.set());
                else if (command.equals(Enums.GameCommands.FLIP_SUMMON.getRegex()))
                    System.out.println(game.flipSummon());
                else if ((matcher = Pattern.compile(Enums.GameCommands.SET_POSITION.getRegex()).matcher(command)).find()) {
                    boolean isAttack = matcher.group(1).equals("attack");
                    System.out.println(game.setMonsterPosition(isAttack));
                } else if ((matcher = Pattern.compile(Enums.GameCommands.ATTACK.getRegex()).matcher(command)).find()) {
                    boolean isIndexValid = true;
                    int index = -1;
                    if (matcher.group(1).length() > 3) isIndexValid = false;
                    else {
                        index = Integer.parseInt(matcher.group(1));
                        if (index < 1 || index > 5) isIndexValid = false;
                    }
                    if (isIndexValid) {
                        System.out.println(game.attack(index - 1));
                        if (game.hasBattlePhaseEnded()) {
                            game.setBattlePhaseEnded(false);
                            break;
                        }
                    } else System.out.println("index is not valid");
                } else if (command.matches(Enums.GameCommands.ATTACK_DIRECT.getRegex())) {
                    System.out.println(game.attack(-1));
                    if (game.hasBattlePhaseEnded()) {
                        game.setBattlePhaseEnded(false);
                        break;
                    }
                } else if (command.matches(Enums.GameCommands.ACTIVE_EFFECT.getRegex()))
                    System.out.println(game.activeEffect());
                else if ((matcher = getCommandMatcher(command, Enums.GameCommands.SHOW_GRAVE.getRegex())).find()) {
                    Show.showGraveYard(matcher.group(1));
                    while (!scanner.nextLine().trim().equals("back"))
                        System.out.println("use \"back\" command for exiting");
                } else if (command.matches(Enums.GameCommands.SHOW_SELECTED.getRegex()))
                    System.out.println(game.showSelectedCard());
                else if (command.equals(Enums.GameCommands.SURRENDER.getRegex())) game.surrendered();
                else if ((matcher = getCommandMatcher(command, Enums.GameCommands.SHOW_CARD.getRegex())).find())
                    Show.showSingleCard(Card.getCardByName(matcher.group(1)));
                else if ((matcher = Pattern.compile(Enums.Cheat.INCREASE_LP.getRegex()).matcher(command)).find())
                    game.getCurrentPlayer().increaseLP(Integer.parseInt(matcher.group(1)));
                else if (command.equals(Enums.Cheat.WIN_DUEL.getRegex()))
                    game.setWinner(game.getCurrentPlayer());
                else if (command.equals(Enums.Cheat.SET_AND_SUMMON_AGAIN.getRegex())) game.setHasSummonedOrSet(false);
                else if (command.equals("help")) {
                    if (game.getCurrentPhase() == Phase.BATTLE) System.out.println(Enums.GAME_HELP_BATTLE);
                    else System.out.println(Enums.GAME_HELP_MAIN);
                } else if ((matcher = getCommandMatcher(command, Enums.GameCommands.ADD_CARD.getRegex())).find())
                    System.out.println(game.addCardToHand(matcher.group(1)));
                else if ((matcher = getCommandMatcher(command, Enums.GameCommands.RM_CARD.getRegex())).find())
                    System.out.println(game.removeCardFromHand(matcher.group(1)));
                else System.out.println("invalid command");
                if (
                        command.matches(Enums.GameCommands.ACTIVE_EFFECT.getRegex()) ||
                                command.matches(Enums.GameCommands.SUMMON.getRegex()) ||
                                command.matches(Enums.GameCommands.SET.getRegex()) ||
                                command.matches(Enums.GameCommands.ATTACK_DIRECT.getRegex()) ||
                                command.matches(Enums.GameCommands.ATTACK.getRegex()) ||
                                command.matches(Enums.GameCommands.FLIP_SUMMON.getRegex()) ||
                                command.matches(Enums.GameCommands.SET_POSITION.getRegex())
                ) {
                    game.deselect();
                    Show.showBoard();
                }
                if (game.didSbWin()) return;
            } catch (Exception e) {
                System.out.println(" something went wrong :(");
            }
        }
        game.deselect();
    }


    private static void scoreboard() {
        for (String command = scanner.nextLine().trim(); !command.equals(Enums.ScoreboardCommands.EXIT.getRegex());
             command = scanner.nextLine().trim()) {
            if (command.equals(Enums.ScoreboardCommands.SHOW_SCOREBOARD.getRegex()))
                Scoreboard.showScoreboard();
            else if (command.equals(Enums.ScoreboardCommands.SHOW_CURRENT.getRegex()))
                System.out.println(Scoreboard.menuName());
            else if (command.matches(Enums.ScoreboardCommands.ENTER_MENU.getRegex()))
                System.out.println("menu navigation is not possible");
            else if (command.equals("help")) System.out.println(Enums.SCOREBOARD_HELP);
            else System.out.println("invalid command");
        }
    }

    public static void chatRoom() {
        for (String command = scanner.nextLine().trim(); !command.equals(Enums.ChatRoomCommands.EXIT.getRegex());
             command = scanner.nextLine().trim()) {
            if (command.equals(Enums.ChatRoomCommands.SHOW_CHAT_ROOM.getRegex())) {
                ChatRoom.showChatRoom();
//                command = scanner.nextLine().trim();
//                String result = ChatRoom.showChatRoom(command);
//                System.out.print(result + "\n");
            }
            else if (command.equals(Enums.ChatRoomCommands.SHOW_CURRENT.getRegex()))
                System.out.println(ChatRoom.menuName());///////////////////////////////////////////
            else if (command.matches(Enums.ChatRoomCommands.ENTER_MENU.getRegex()))
                System.out.println("menu navigation is not possible");
            else if (command.equals("help")) System.out.println(Enums.SCOREBOARD_HELP);
            else System.out.println("invalid command");
        }
    }




    private static void importExportMenu() {
        String command = scanner.nextLine().trim();
        while (!command.equals(Enums.ImportExportCommands.EXIT.getRegex())) {
            if (command.matches(Enums.ImportExportCommands.IMPORT_CARD.getRegex())) {
                Matcher matcher = getCommandMatcher(command, Enums.ImportExportCommands.IMPORT_CARD.getRegex());
                if (matcher.find())
                    ImportExport.importCard(Card.getCardByName(matcher.group(1)));
            } else if (command.matches(Enums.ImportExportCommands.EXPORT_CARD.getRegex())) {
                Matcher matcher = getCommandMatcher(command, Enums.ImportExportCommands.EXPORT_CARD.getRegex());
                if (matcher.find())
                    ImportExport.exportCard(Card.getCardByName(matcher.group(1)));
            } else if (command.matches(Enums.ImportExportCommands.ENTER_MENU.getRegex()))
                System.out.println("menu navigation is not possible");
            else if (command.equals(Enums.ImportExportCommands.SHOW_CURRENT.getRegex()))
                System.out.println(ImportExport.menuName());
            else if (command.equals("help")) System.out.println(Enums.IMPORT_EXPORT_HELP);
            else System.out.println("invalid command!");
            command = scanner.nextLine().trim();
        }
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


    public static boolean yesNoQuestion(String question) {
        isAnswerYes = false;
        Game game = GameMenu.getCurrentGame();
        Player currentPlayer = game.getCurrentPlayer();
        if (currentPlayer instanceof AI) return true;
        if (game.isGraphical()) {
            Button yesButton = new Button("yes");
            Button noButton = new Button("no");
            HBox hBox = new HBox(GraphicUtils.getGoodLabel(question), yesButton, noButton);
            hBox.setSpacing(10);
            hBox.setBackground(GraphicUtils.getGreyBackground());
            Stage newStage = new Stage();
            newStage.setScene(new Scene(hBox));
            yesButton.setOnMouseClicked(e -> {
                isAnswerYes = true;
                newStage.close();
            });
            noButton.setOnMouseClicked(e -> {
                isAnswerYes = false;
                newStage.close();
            });
            newStage.showAndWait();
            return isAnswerYes;
        } else {
            System.out.println(question);
            return "yes".equals(scanner.nextLine());
        }

    }


    public static String getCardName(String whyDoYouNidThis) {
        Game game = GameMenu.getCurrentGame();
        if (game.getCurrentPlayer() instanceof AI) {
            Random random = new Random();
            ArrayList<Card> allCards = Card.getCards();
            Card card = allCards.get(random.nextInt(allCards.size()));
            return card.getName();
        }
        if (game.isGraphical()) {
            Button doneButton = new Button("done");
            TextField cardNameField = new TextField();
            cardNameField.setPromptText("card name");
            HBox hBox = new HBox(cardNameField, doneButton);
            VBox vBox = new VBox(GraphicUtils.getGoodLabel(whyDoYouNidThis), hBox);
            hBox.setSpacing(10);
            vBox.setSpacing(10);
            vBox.setBackground(GraphicUtils.getGreyBackground());
            Stage newStage = new Stage();
            newStage.setScene(new Scene(vBox));
            doneButton.setOnMouseClicked(e -> {
                cardNameInput = cardNameField.getText();
                newStage.close();
            });
            newStage.showAndWait();
            return cardNameInput;
        } else {
            System.out.println(whyDoYouNidThis);
            return scanner.nextLine();
        }
    }

    public static int getIndexOfCardArray(ArrayList<Card> cards, String goal) {
        Game game = GameMenu.getCurrentGame();
        if (game.getCurrentPlayer() instanceof AI) return 0;
        if (game.isGraphical()) {
            indexOfArray = -1;
            Stage newStage = new Stage();
            HBox cardsWithNames = new HBox();
            ScrollPane scrollPane = new ScrollPane(cardsWithNames);
            scrollPane.setMinHeight(160);
            VBox cardsAndGoal = new VBox(GraphicUtils.getGoodLabel(goal + "\n(close the stage to cancel the operation.)"), scrollPane);
            cardsAndGoal.setMaxWidth(500);
            newStage.setScene(new Scene(cardsAndGoal));
            cardsWithNames.setSpacing(10);
            for (int i = 0; i < cards.size(); i++) {
                Card copiedCard = Card.make(cards.get(i).getName());
                copiedCard.setHeight(120);
                copiedCard.setWidth(80);
                int finalI = i;
                copiedCard.setOnMouseClicked(e -> {
                    indexOfArray = finalI;
                    newStage.close();
                });
                VBox vBox = new VBox(copiedCard, GraphicUtils.getGoodLabel(copiedCard.getName()));
                cardsWithNames.getChildren().add(vBox);
            }

            newStage.showAndWait();
            return indexOfArray;
        } else {
            System.out.println("choose an index from these cards: " + goal);
            Show.showCardArray(cards);
            String command = scanner.nextLine().trim();
            int index = -1;
            while (!command.equals("cancel")) {
                String error = null;
                if (!command.matches("\\d+")) error = "invalid command";
                else {
                    index = Integer.parseInt(command);
                    if (cards.size() < index || index == 0) error = "invalid index";
                }
                if (error == null) return index - 1;
                System.out.println(error + ". please try again or \"cancel\" the operation");
                command = scanner.nextLine().trim();
            }
            return -1;
        }

    }

    public static int getMonsterFromGrave(boolean isMyGrave) {
        Game game = GameMenu.getCurrentGame();
        if (game.getCurrentPlayer() instanceof AI)
            return ((AI) GameMenu.getCurrentGame().getCurrentPlayer()).getMonsterFromGrave(isMyGrave);
        ArrayList<Card> grave = isMyGrave ?
                game.getCurrentPlayer().getBoard().getGrave() : game.getRival().getBoard().getGrave();
        String graveOwner = isMyGrave ? "your" : "rival's";
        if (game.isGraphical()) {
            ArrayList<Card> monsters = new ArrayList<>();
            HashMap<Card, Integer> monstersWithIndex = new HashMap<>();
            for (int i = 0; i < grave.size(); i++) {
                Card card = grave.get(i);
                if (card instanceof Monster) {
                    monsters.add(card);
                    monstersWithIndex.put(card, i);
                }
            }
            int index = getIndexOfCardArray(monsters, "choose a monster from " + graveOwner + " grave:");
            if (index == -1) return -1;
            else return monstersWithIndex.get(monsters.get(index));
        } else {
            Show.showCardArray(grave);
            System.out.println("choose an index from " + graveOwner + " grave");
            String command = scanner.nextLine().trim();
            int index = -1;
            while (!command.equals("cancel")) {
                String error = null;
                if (!command.matches("\\d+")) error = "invalid command";
                else {
                    index = Integer.parseInt(command) - 1;
                    if (grave.size() <= index || index < 0) error = "invalid index";
                    else if (!(grave.get(index) instanceof Monster)) error = "this is not a monster";
                }
                if (error == null) return index;
                System.out.println(error + ". please try again or \"cancel\" the operation");
                command = scanner.nextLine().trim();
            }
            return -1;
        }
    }


    public static int[] getTribute(int numberOfTributes, boolean isFromMonsterZone) {
        Game game = GameMenu.getCurrentGame();
        if (game.getCurrentPlayer() instanceof AI) {
            return ((AI) GameMenu.getCurrentGame().getCurrentPlayer()).getTribute(numberOfTributes, isFromMonsterZone);
        }
        int[] indexes = new int[numberOfTributes];
        if (game.isGraphical()) {
            ArrayList<Card> monsters = new ArrayList<>();
            HashMap<Card, Integer> monstersWithIndex = new HashMap<>();
            Card[] cards = isFromMonsterZone ? game.getCurrentPlayer().getBoard().getMonsterZone() : game.getCurrentPlayer().getBoard().getHand();
            for (int i = 0; i < cards.length; i++) {
                Card card = cards[i];
                if (card instanceof Monster) {
                    monsters.add(card);
                    monstersWithIndex.put(card, i);
                }
            }
            for (int i = 0; i < numberOfTributes; i++) {
                int indexOfArrayList = getIndexOfCardArray(monsters, "please choose monster(s) for tribute(s)");
                if (indexOfArrayList == -1) return null;
                Card card = monsters.get(indexOfArrayList);
                indexes[i] = monstersWithIndex.get(card);
                monsters.remove(card);
            }
        } else {
            String fromWhere = isFromMonsterZone ? "monster zone" : "hand";
            System.out.println("please enter " + numberOfTributes + " index(es) for tribute from your " + fromWhere);
            Arrays.fill(indexes, -1);
            String command;
            while (Arrays.stream(indexes).anyMatch(i -> i == -1)) {
                command = scanner.nextLine().trim();
                if (command.equals("cancel"))
                    return null;
                else if (command.matches("\\d+")) {
                    String error = null;
                    int index = Integer.parseInt(command) - 1;
                    if (Arrays.stream(indexes).anyMatch(i -> i == index))
                        error = "you have already chosen this";
                    else if (isFromMonsterZone) {
                        if (index < 0 || index > 4)
                            error = "invalid index";
                        else if (GameMenu.getCurrentGame().getCurrentPlayer().getBoard().getMonsterZone()[index] == null)
                            error = "there are no monsters on this address";
                    } else {
                        if (index < 0 || index > 5)
                            error = "invalid index";
                        else if (GameMenu.getCurrentGame().getCurrentPlayer().getBoard().getHand()[index] == null)
                            error = "this index is empty";
                    }
                    if (error == null)
                        for (int i = 0; i < indexes.length; i++) {
                            if (indexes[i] == -1) {
                                indexes[i] = index;
                                break;
                            }
                        }
                    else System.out.println(error);
                } else System.out.println("invalid command.");
            }
        }
        return indexes;
    }

}

