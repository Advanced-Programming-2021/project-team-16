package graphicview;


import controller.Login;
import controller.UpdateStatus;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import view.CommandProcessor;

import java.io.IOException;

public class LoginMenu extends Application {
    public final static KeyCombination PHASE_ONE = new KeyCodeCombination(KeyCode.DIGIT1, KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN);
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
        ((BorderPane) root).setBackground(GraphicUtils.getBackground("/png/texture/50151.png"));
        getMainStage().setScene(new Scene(root));
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("YuGiOh!");
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));
        stage.setScene(new Scene(root));
        ((BorderPane) root).setBackground(GraphicUtils.getBackground("/png/texture/50151.png"));
        mainStage = stage;
        stage.show();
        UpdateStatus.beforeRun();
        stage.getScene().setOnKeyPressed(keyEvent -> {
            if (PHASE_ONE.match(keyEvent)) {
                LoginMenu.getMainStage().hide();
                CommandProcessor.login();
            }
        });
    }

    public static Stage getMainStage() {
        return mainStage;
    }

    public void signup() {
        String result = Login.signUp(usernameSignup.getText(), passwordSignup.getText(), nickname.getText());
        signupError.setText(result);
        if (result.contains("success")) signupError.setTextFill(Color.DARKGREEN);
        else signupError.setStyle("-fx-background-color: #d72a7b;");

    }

    public void login() throws IOException {
        String result = Login.login(usernameLogin.getText(), passwordLogin.getText());
        loginError.setText(result);
//        loginError.setTextFill(Color.DARKRED);
        loginError.setStyle("-fx-background-color: #d72a7b;");
        if (result.contains("success")) graphicview.MainMenu.enterMenu();
    }
}
