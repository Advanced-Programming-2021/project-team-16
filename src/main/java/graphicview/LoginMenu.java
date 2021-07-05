package graphicview;


import controller.Login;
import controller.UpdateStatus;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
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
        UpdateStatus.beforeRun();
        launch(args);
        UpdateStatus.afterRun();
    }

    public static void enterMenu() throws IOException {
        Parent root = FXMLLoader.load(LoginMenu.class.getResource("/fxml/login.fxml"));
        ((BorderPane) root).setBackground(GraphicUtils.getBackground("/png/background/login.png"));
        getMainStage().setScene(new Scene(root));

    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("YuGiOh!");
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));
        stage.setScene(new Scene(root));
        ((BorderPane) root).setBackground(GraphicUtils.getBackground("/png/background/login.png"));
        mainStage = stage;
        stage.show();
    }

    public static Stage getMainStage() {
        return mainStage;
    }

    public void signup() {
        String result = Login.signUp(usernameSignup.getText(), passwordSignup.getText(), nickname.getText());
        signupError.setText(result);
        if (result.contains("success")) signupError.setTextFill(Color.DARKGREEN);
        else signupError.setTextFill(Color.DARKRED);

    }

    public void login() throws IOException {
        String result = Login.login(usernameLogin.getText(), passwordLogin.getText());
        loginError.setText(result);
        loginError.setTextFill(Color.DARKRED);
        if (result.contains("success")) graphicview.MainMenu.enterMenu();
    }
}
