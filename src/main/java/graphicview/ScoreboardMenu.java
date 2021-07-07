package graphicview;

import controller.MainMenu;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import model.person.User;

import java.io.IOException;
import java.util.ArrayList;

public class ScoreboardMenu {
    public static GridPane scoreboardPane;

    public static void enterMenu() {
        try {
            Parent root = FXMLLoader.load(ScoreboardMenu.class.getResource("/fxml/scoreboard.fxml"));
            LoginMenu.getMainStage().setScene(new Scene(root));
            scoreboardPane = (GridPane) root;
            scoreboardPane.setBackground(GraphicUtils.getBackground("/png/texture/score.jpg"));
            showScoreboard();
        } catch (IOException ignored) {
        }
    }

    private static void showScoreboard() {
        ArrayList<User> users = User.getAllUsers();
        Label nickname, score,rateLabel;
        User.sort(users);
        int rate = 1;
        for (int i = 1; i <= users.size() && i <= 20; i++) {
            if (i != 1 && users.get(i - 1).getScore() != users.get(i - 2).getScore())
                rate = i;
            nickname = new Label(users.get(i - 1).getNickname());
            score = new Label("" + users.get(i - 1).getScore());
            rateLabel = new Label(rate + "");
            nickname.setStyle("-fx-font-size: 15;");
            score.setStyle("-fx-font-size: 15;");
            rateLabel.setStyle("-fx-font-size: 15;");
            if (MainMenu.getCurrentUser() == users.get(i-1)) {
                nickname.setTextFill(Color.LIGHTGREEN);
                score.setTextFill(Color.LIGHTGREEN);
                rateLabel.setTextFill(Color.LIGHTGREEN);
            } else {
                nickname.setTextFill(Color.BLUE);
                score.setTextFill(Color.BLUE);
                rateLabel.setTextFill(Color.WHITE);
            }
            scoreboardPane.add(rateLabel, 0, i);
            scoreboardPane.add(nickname, 1, i);
            scoreboardPane.add(score, 2, i);
        }
    }


    public void enterMainMenu() throws IOException {
        graphicview.MainMenu.enterMenu();
    }
}
