package graphicview;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

public class MainMenu {
    public void scoreboardButtonOnAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/scoreboard.fxml"));
        LoginMenu.getMainStage().getScene().setRoot(root);
    }
    public static void enterMainMenu() throws IOException {
        LoginMenu.getMainStage().setScene(new Scene(FXMLLoader.load(LoginMenu.class.getResource("/fxml/main.fxml"))));

    }

    public void logoutButtonOnAction(ActionEvent actionEvent) {
    }

    public void backButtonOnAction(ActionEvent actionEvent) {

    }
}
