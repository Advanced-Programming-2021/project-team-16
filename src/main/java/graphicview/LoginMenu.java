package graphicview;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class LoginMenu extends Application {
    public TextField username;
    public TextField password;
    public TextField usernameSignup;
    public TextField passwordSignup;
    public TextField RePasswordSignup;

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("YuGiOh!");
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void login(MouseEvent mouseEvent) {
        System.out.println(username);
    }

    public void signup(MouseEvent mouseEvent) {

    }
}
