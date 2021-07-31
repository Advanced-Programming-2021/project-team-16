package controller;

import model.person.User;
import consoleView.Show;

public class Scoreboard {
    public static void showScoreboard() {
        User.sort(User.getAllUsers());
        Show.showScoreBoard(User.getAllUsers());
    }
    public static String menuName() {
        return "Scoreboard Menu";
    }
}
