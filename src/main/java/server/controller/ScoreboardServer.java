package server.controller;

import server.model.User;
import view.Show;

public class ScoreboardServer {
    public static String showScoreboard() {
        //    User.sort(User.getAllUsers());
       return Show.showScoreBoard(User.getAllUsers());

    }
}
