package server.controller;

import server.modell.User;
import server.view.ShowServer;

public class ScoreboardServer {
    public static String showScoreboard() {
        //    User.sort(User.getAllUsers());
       return ShowServer.showScoreBoard(User.getAllUsers());

    }
}
