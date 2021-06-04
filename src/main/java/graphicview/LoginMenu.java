package graphicview;


import controller.Login;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginMenu extends Application {
    public TextField usernameLogin;
    public TextField passwordLogin;
    public TextField usernameSignup;
    public TextField passwordSignup;
    public TextField nickname;
    public Label loginError;
    public Label signupError;
    private static Stage mainStage;
    private static Scene mainScene;

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("YuGiOh!");
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));
        stage.setScene(mainScene = new Scene(root));
        mainStage = stage;
        stage.show();
    }

    public static Stage getMainStage() {
        return mainStage;
    }

    public static Scene getMainScene() {
        return mainScene;
    }

    public void login() throws IOException {
        loginError.setText(Login.login(usernameLogin.getText(), passwordLogin.getText()));
        LoginMenu.getMainStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("/fxml/scoreboard.fxml"))));
    }

    public void signup() {
        signupError.setText(Login.signUp(usernameSignup.getText(),passwordSignup.getText(), nickname.getText()));
    }
}
