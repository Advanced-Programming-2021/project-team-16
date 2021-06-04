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

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("YuGiOh!");
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));
        stage.setScene(new Scene(root));
        mainStage = stage;
        stage.show();
    }
    public static void enterLoginMenu() throws IOException {
        LoginMenu.getMainStage().setScene(new Scene(FXMLLoader.load(LoginMenu.class.getResource("/fxml/login.fxml"))));

    }

    public static Stage getMainStage() {
        return mainStage;
    }

    public void login() throws IOException {
        loginError.setText(Login.login(usernameLogin.getText(), passwordLogin.getText()));
    }

    public void signup() {
        signupError.setText(Login.signUp(usernameSignup.getText(),passwordSignup.getText(), nickname.getText()));
    }
}
