package graphicview;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

public class MainMenu {
    public void scoreboardButtonOnAction(ActionEvent actionEvent) {
        ScoreboardMenu.enterMenu();
    }
    public static void enterMenu() throws IOException {
        LoginMenu.getMainStage().setScene(new Scene(FXMLLoader.load(LoginMenu.class.getResource("/fxml/main.fxml"))));

    }

    public void logoutButtonOnAction(ActionEvent actionEvent) throws IOException {
        controller.MainMenu.setCurrentUser(null);
        LoginMenu.enterMenu();
    }


    public void deckButtonOnAction(ActionEvent actionEvent) throws IOException {
        DeckMenu.enterMenu();
    }

    public void duelButtonOnAction(ActionEvent actionEvent) {
        DuelMenu.enterMenu();
    }

    public void shopButtonOnAction(ActionEvent actionEvent) {
        //TODO: ShopMenu.enterMenu();
    }

    public void ProfileButtonOnAction(ActionEvent actionEvent) {
        ProfileMenu.enterMenu();
    }

    public void importButtonOnAction(ActionEvent actionEvent) {
        //TODO: importExport.enterMenu();
    }
}
