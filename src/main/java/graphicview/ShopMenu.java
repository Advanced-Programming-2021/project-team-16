package graphicview;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Game;

import java.io.IOException;


public class ShopMenu {
    private static Stage stage;

    public static void enterMenu() throws IOException {
        FXMLLoader loader = new FXMLLoader(ProfileMenu.class.getResource("/fxml/shop.fxml"));
        Parent root = loader.load();
        stage = LoginMenu.getMainStage();
        stage.setScene(new Scene(root));
        stage.getScene().setOnKeyPressed(keyEvent -> {
            if (Game.CHEAT_KEYS.match(keyEvent)) GameView.openCheatPopup(stage,null);
        });
    }
    public void changeScene(String fxml) throws IOException {
        Parent pane =FXMLLoader.load(getClass().getResource(fxml));
        stage.getScene().setRoot(pane);
    }
    public void backButton(ActionEvent event) throws IOException {
        this.changeScene("/fxml/main.fxml");
    }

    public void cardListMenu(ActionEvent event) throws IOException {
        this.changeScene("/fxml/cardList.fxml");
    }

    public void backToShopMenu(ActionEvent event) throws IOException {
        this.changeScene("/fxml/shop.fxml");
    }
}
//fx:controller="graphicview.ShopMenu"