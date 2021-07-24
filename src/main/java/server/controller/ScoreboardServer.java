package server.controller;

import server.modell.User;
import view.Show;

public class ScoreboardServer {
    public static String showScoreboard() {
        //    User.sort(User.getAllUsers());
       return Show.showScoreBoard(User.getAllUsers());

    }
}
