package controller;

import server.model.User;
import view.Show;

import java.io.IOException;

public class Scoreboard {
    public static String showScoreboard() {
        String result;
        try {
            Login.dataOut.writeUTF("scoreboard show");
            Login.dataOut.flush();
            result = Login.dataIn.readUTF();

        } catch (IOException x) {
            x.printStackTrace();
            result = "show scoreboard error";
        }
        return result;

    //    User.sort(User.getAllUsers());
      //  Show.showScoreBoard(User.getAllUsers());
    }
    public static String menuName() {
        return "Scoreboard Menu";
    }
}
