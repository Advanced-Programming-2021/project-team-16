package graphicview;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;


public class ShopMenu {
    private static Stage stage;

    public static void enterMenu() throws IOException {
        FXMLLoader loader = new FXMLLoader(ProfileMenu.class.getResource("/fxml/shop.fxml"));
        Parent root = loader.load();
        LoginMenu.getMainStage().setScene(new Scene(root));
    }
    public void changeScene(String fxml) throws IOException {
        Parent pane =FXMLLoader.load(getClass().getResource(fxml));
        stage.getScene().setRoot(pane);
    }
    public void backButton(ActionEvent event) throws IOException {
        this.changeScene("/fxml/main.fxml");
    }
}
