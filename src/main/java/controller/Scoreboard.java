package controller;

import server.model.User;
import view.Show;

public class Scoreboard {
    public static void showScoreboard() {
    //    User.sort(User.getAllUsers());
        Show.showScoreBoard(User.getAllUsers());
    }
    public static String menuName() {
        return "Scoreboard Menu";
    }
}
