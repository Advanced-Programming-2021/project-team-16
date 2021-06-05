package graphicview;


import controller.Login;
import controller.MainMenu;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Deck;
import model.person.User;

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

    public static void enterMenu() throws IOException {
        LoginMenu.getMainStage().setScene(new Scene(FXMLLoader.load(LoginMenu.class.getResource("/fxml/login.fxml"))));

    }

    public static Stage getMainStage() {
        return mainStage;
    }

    public void login() throws IOException {
       loginError.setText(Login.login(usernameLogin.getText(), passwordLogin.getText()));
        forTest();
    }

    public void signup() {
        signupError.setText(Login.signUp(usernameSignup.getText(), passwordSignup.getText(), nickname.getText()));
    }

    private static void forTest() throws IOException {
        User[] users = new User[30];
        for (int i = 0; i < 30; i++) {
            users[i] = new User("username" + i, "", "nickname" + i);
            users[i].increaseScore(100 * i);
        }
        users[0].increaseScore(20*100);
        users[1].increaseScore(19*100);
        MainMenu.setCurrentUser(users[25]);
        users[25].setActiveDeck(new Deck("deck26"));
        graphicview.MainMenu.enterMenu();
    }
}
