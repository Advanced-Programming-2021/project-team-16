package controller;

import model.Game;
import model.person.AI;
import model.person.Player;
import model.person.User;

import java.util.Random;

public class GameMenu {
    private static Game currentGame;

    public static void duel(User user2, int round) {
        Player player1 = new Player(MainMenu.getCurrentUser());
        Player player2 = user2 == null ? new AI() : new Player(user2);
        if ((new Random()).nextBoolean()) setCurrentGame(new Game(player1, player2, round));
        else setCurrentGame(new Game(player2, player1, round));
        currentGame.play();
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
}
