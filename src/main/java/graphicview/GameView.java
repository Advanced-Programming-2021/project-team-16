package graphicview;

import controller.GameMenu;
import controller.MainMenu;
import javafx.animation.FadeTransition;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
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
import model.person.AI;
import model.person.Player;
import model.person.User;

import java.io.IOException;


public class GameView {
    private static Game game;
    public Label myLP;
    public Label rivalNickname;
    public Rectangle rivalAvatar;
    public Rectangle myAvatar;
    public Label rivalUsername;
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
    public Rectangle selectedCard;
    public Label selectedCardDescription;
    public Pane myFieldSpell;
    public Pane rivalFieldSpell;
    private Player player;
    private Player rival;
    private Stage stage;
    private final Popup popup = new Popup();
    private final VBox popupVBox = new VBox();


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
            //starting game
            game.runGraphical();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("here!");
    }

    public static void hideNode(Node node) {
        FadeTransition ft = new FadeTransition(Duration.millis(1000), node);
        ft.setFromValue(1.0);
        ft.setToValue(0.0);
        ft.play();
    }

    public static void showNode(Node node) {
        FadeTransition ft = new FadeTransition(Duration.millis(1000), node);
        ft.setFromValue(0.0);
        ft.setToValue(1.0);
        ft.play();
    }

    public Stage getStage() {
        return stage;
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
        selectedCardDescription.setWrapText(true);
        selectedCardDescription.setMaxWidth(279);
        popup.setAnchorX(400);
        popup.setAnchorY(390);
        popup.getContent().add(popupVBox);
        popupVBox.setSpacing(5);
    }

    public void doLostAction() {
        if (game.getRound() == 1) endGame(game.endRound());
        else {
            String result = game.getResultOfOneRound(player);
            if (result.contains("whole match")) {
                endGame(result);
            }
        }
    }

    private void endGame(String result) {
        showMessage(result, true);
        FadeTransition ft = new FadeTransition(Duration.millis(1000), stage.getScene().getRoot());
        ft.setFromValue(1.0);
        ft.setToValue(0.0);
        ft.setDelay(Duration.seconds(1));
        ft.setOnFinished(actionEvent -> {
            Stage fakeStage = LoginMenu.getMainStage() == stage ? rival.getGameView().stage : stage;
            fakeStage.close();
            DuelMenu.enterMenu();
        });
        ft.play();
    }

    public Label getMyLP() {
        return myLP;
    }

    public Label getRivalLP() {
        return rivalLP;
    }

    public void showMessage(String message, boolean isImportant) {
        if (player instanceof AI) return;
        Label label = new Label(message);
        if (isImportant) {
            label.setTextFill(Color.BROWN);
            label.setStyle("-fx-font-size: 30");
        } else label.setStyle("-fx-font-size: 20");
        label.setAlignment(Pos.CENTER);
        popupVBox.getChildren().add(label);
        popup.show(stage);
        stage.getScene().getRoot().setOpacity(0.5);
        FadeTransition ft = new FadeTransition(Duration.millis(1000), label);
        ft.setFromValue(1.0);
        ft.setToValue(0.0);
        ft.setDelay(Duration.millis(popupVBox.getChildren().size() * 1000));
        ft.play();
        ft.setOnFinished(actionEvent -> {
            popupVBox.getChildren().remove(((FadeTransition) actionEvent.getSource()).getNode());
            if (popupVBox.getChildren().isEmpty()) {
                popup.hide();
                stage.getScene().getRoot().setOpacity(1);
            }
        });


    }


    public void goToNextPhase() {
        if (game.getRival() == player) showMessage("not your turn", false);
        else game.goToNextPhase();
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
}

