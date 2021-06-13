package graphicview;

import controller.GameMenu;
import controller.MainMenu;
import javafx.animation.FadeTransition;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;
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
    public GridPane board;
    public HBox rivalHand;
    public HBox rivalSpells;
    public HBox rivalMonsters;
    public HBox myMonsters;
    public HBox mySpells;
    public HBox myHand;
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
        board.setBackground(new Background(new BackgroundFill(
                new ImagePattern(new Image(getClass().getResource("/png/field/fie_normal.bmp").toExternalForm())),
                CornerRadii.EMPTY, Insets.EMPTY)));
        myAvatar.setFill(player.getUser().getAvatarRec().getFill());
        myNickname.setText(player.getUser().getNickname());
        myUsername.setText(player.getUser().getUsername());
        myLP.setText(String.valueOf(player.getLP()));
        rivalAvatar.setFill(rival.getUser().getAvatarRec().getFill());
        rivalNickname.setText(rival.getUser().getNickname());
        rivalUsername.setText(rival.getUser().getUsername());
        rivalLP.setText(String.valueOf(rival.getLP()));
    }

    public static void forTest() {
        FXMLLoader loader = new FXMLLoader(ProfileMenu.class.getResource("/fxml/gameBoard.fxml"));
        try {
            Parent firstBoard = loader.load();
            LoginMenu.getMainStage().setScene(new Scene(firstBoard));
            GameView thisView = loader.getController();
            thisView.myAvatar.setFill(Color.GREEN);
            thisView.myNickname.setText("myNickname");
            thisView.myUsername.setText("my username");
            thisView.myLP.setText("8000");
            thisView.rivalAvatar.setFill(Color.RED);
            thisView.rivalNickname.setText("rivalNickname");
            thisView.rivalUsername.setText("rival username");
            thisView.rivalLP.setText("7000");
            thisView.board.setBackground(new Background(new BackgroundFill(
                    new ImagePattern(new Image(GameView.class.getResource("/png/field/fie_normal.bmp").toExternalForm())),
                    CornerRadii.EMPTY, Insets.EMPTY)));
            thisView.stage = LoginMenu.getMainStage();
            thisView.endGame("zizi won the game and the score is: 1000-0\n                          round 2");
            Rectangle rectangle = new Rectangle();
            rectangle.setFill(Color.RED);
            rectangle.setWidth(60);
            rectangle.setHeight(90);
            thisView.rivalHand.getChildren().set(1, rectangle);
            FadeTransition ft = new FadeTransition(Duration.millis(1000), rectangle);
            ft.setFromValue(1.0);
            ft.setToValue(0.0);
            ft.play();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void doLostAction() {
        if (game.getRound() == 1) endGame(game.endRound());
        else {
            String result = game.getResultOfOneRound(player);
            if (result.contains("whole match")) {
                endGame(result);
                rival.getGameView().endGame(result);
            }
        }
    }

    private void endGame(String result) {
        showMessage(result,true);
        stage.getScene().setOnMouseClicked(mouseEvent -> DuelMenu.enterMenu());
    }

    public Label getMyLP() {
        return myLP;
    }

    public Label getRivalLP() {
        return rivalLP;
    }

    public void showMessage(String message, boolean isImportant) {
        Popup popup = new Popup();
        Label resultLabel = new Label(message);
        resultLabel.setTextFill(Color.BROWN);
        if (isImportant) resultLabel.setStyle("-fx-font-size: 30");
        else resultLabel.setStyle("-fx-font-size: 20");
        popup.getContent().add(resultLabel);
        popup.setAnchorX(400);
        popup.setAnchorY(390);
        resultLabel.setAlignment(Pos.CENTER);
        popup.show(stage);
        stage.getScene().getRoot().setOpacity(0.5);
        stage.getScene().setOnMouseClicked(mouseEvent -> {
            popup.hide();
            stage.getScene().getRoot().setOpacity(1);
        });

    }

}

