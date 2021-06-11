package graphicview;

import controller.GameMenu;
import controller.MainMenu;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import model.Game;
import model.person.Player;
import model.person.User;

import java.io.IOException;

public class GameView {
    private static Game game;
    public Label myLP;
    public Label rivalNickname;
    public Rectangle rivalAvatar;
    public Label selectedCardDescription;
    public Label rivalUsername;
    public Rectangle myAvatar;
    public Rectangle selectedCard;
    public Label myUsername;
    public Label rivalLP;
    public Label myNickname;
    private Player player;
    private Player rival;
    private Stage stage;

    public static void startGame(Integer rounds, User user2) {
        GameMenu.duel(user2, rounds, true);
        game = GameMenu.getCurrentGame();
        Player firstPlayer = game.getCurrentPlayer().getUser() == MainMenu.getCurrentUser() ?
                game.getCurrentPlayer() : game.getRival();
        Player secondPlayer = game.getCurrentPlayer() == firstPlayer ? game.getRival() : game.getCurrentPlayer();
        try {
            //first board
            FXMLLoader loader = new FXMLLoader(ProfileMenu.class.getResource("/fxml/gameBoard.fxml"));
            Parent firstBoard = loader.load();
            LoginMenu.getMainStage().setScene(new Scene(firstBoard));
            ((GameView) loader.getController()).showBoard(firstPlayer, LoginMenu.getMainStage());
            //second board
            loader = new FXMLLoader(ProfileMenu.class.getResource("/fxml/gameBoard.fxml"));
            Parent secondBoard = loader.load();
            Stage secondStage = new Stage();
            secondStage.setScene(new Scene(secondBoard));
            secondStage.show();
            ((GameView) loader.getController()).showBoard(secondPlayer, secondStage);
        } catch (IOException ignored) {
        }
        System.out.println("here!");
    }

    private void showBoard(Player player, Stage stage) {
        this.stage = stage;
        this.player = player;
        player.setGameView(this);
        this.rival = game.getCurrentPlayer() == player ? game.getRival() : game.getCurrentPlayer();
        myAvatar.setFill(player.getUser().getAvatarRec().getFill());
        myNickname.setText(player.getUser().getNickname());
        myUsername.setText(player.getUser().getUsername());
        myLP.setText(String.valueOf(player.getLP()));
        rivalAvatar.setFill(rival.getUser().getAvatarRec().getFill());
        rivalNickname.setText(rival.getUser().getNickname());
        rivalUsername.setText(rival.getUser().getUsername());
        rivalLP.setText(String.valueOf(rival.getLP()));
    }

    public static void forTest(){
        FXMLLoader loader = new FXMLLoader(ProfileMenu.class.getResource("/fxml/gameBoard.fxml"));
        Parent firstBoard = null;
        try {
            firstBoard = loader.load();
            LoginMenu.getMainStage().setScene(new Scene(firstBoard));
            GameView thisView  = loader.getController();
            thisView.myAvatar.setFill(Color.GREEN);
            thisView.myNickname.setText("myNickname");
            thisView.myUsername.setText("my username");
            thisView.myLP.setText("8000");
            thisView.rivalAvatar.setFill(Color.RED);
            thisView.rivalNickname.setText("rivalNickname");
            thisView.rivalUsername.setText("rival username");
            thisView.rivalLP.setText("7000");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

