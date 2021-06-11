package controller;

import model.Game;
import model.person.AI;
import model.person.Player;
import model.person.User;

import java.util.Random;

public class GameMenu {
    private static Game currentGame;

    public static void duel(User user2, int round, boolean isGraphical) {
        Player player1 = new Player(MainMenu.getCurrentUser());
        Player player2 = user2 == null ? new AI() : new Player(user2);
        if ((new Random()).nextBoolean()) setCurrentGame(new Game(player1, player2, round));
        else setCurrentGame(new Game(player2, player1, round));
        if (!isGraphical) currentGame.playConsole();
        else currentGame.playGraphical();
    }


    public static String menuName() {
        return "Duel Menu";
    }

    public static Game getCurrentGame() {
        return currentGame;
    }

    public static void setCurrentGame(Game currentGame) {
        GameMenu.currentGame = currentGame;
    }

    public static String isDuelPossibleWithError(String rounds, User secondUser, boolean isAI) {
        User firstUser = MainMenu.getCurrentUser();
        if (firstUser.getActiveDeck() == null) return "you have no active deck";
        if (!firstUser.getActiveDeck().isDeckValid())
            return "your deck is invalid";
        if (!isAI) {
            if (secondUser == null) return "there is no player with this username";
            if (secondUser == firstUser) return "you can't play with yourself";
            if (secondUser.getActiveDeck() == null) return secondUser.getUsername() + " has no active deck";
            if (!secondUser.getActiveDeck().isDeckValid())
                return secondUser.getUsername() + "’s deck is invalid";
        }
        if (!rounds.equals("1") && !rounds.equals("3"))
            return "number of rounds is not supported";
        return null;
    }
}
