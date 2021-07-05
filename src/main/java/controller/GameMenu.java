package controller;

import graphicview.GameView;
import graphicview.GraphicUtils;
import graphicview.LoginMenu;
import javafx.animation.Animation;
import javafx.animation.Transition;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import model.Game;
import model.person.AI;
import model.person.Player;
import model.person.User;

import java.util.Random;

public class GameMenu {
    private static Game currentGame;
    private static int countOfFlips = 0;

    public static void duel(User user2, int round, boolean isGraphical) {
        Player player1 = new Player(MainMenu.getCurrentUser());
        Player player2 = user2 == null ? new AI() : new Player(user2);
        boolean doesFirsPlayerStart = (new Random()).nextBoolean();
        if (doesFirsPlayerStart) setCurrentGame(new Game(player1, player2, round));
        else setCurrentGame(new Game(player2, player1, round));
        if (!isGraphical) currentGame.playConsole();
        else showCoinDropAndStartGame(doesFirsPlayerStart);
    }


    public static void showCoinDropAndStartGame(boolean isPlayerWinning) {
        countOfFlips = 0;
        ImagePattern tail = new ImagePattern(new Image(GameMenu.class.getResource("/png/coin/tail.png").toExternalForm()));
        ImagePattern head = new ImagePattern(new Image(GameMenu.class.getResource("/png/coin/head.png").toExternalForm()));
        Rectangle headRec = getRectangle(isPlayerWinning, tail, head, false);
        Rectangle tailRec = getRectangle(isPlayerWinning, tail, head, true);
        HBox hBox = new HBox();
        hBox.setMinSize(400, 400);
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(50);
        hBox.getChildren().addAll(tailRec, headRec);
        Label label = new Label("Choose One:");
        label.setStyle("-fx-font-size: 20");
        label.setTextFill(Color.BLUE);
        VBox vBox = new VBox(label, hBox);
        vBox.setSpacing(20);
        vBox.setBackground(GraphicUtils.getBackground(Color.BEIGE));
        LoginMenu.getMainStage().setScene(new Scene(vBox));
    }

    private static Rectangle getRectangle(boolean isPlayerWinning, ImagePattern tail, ImagePattern head, boolean isTail) {
        Rectangle chosenRec = new Rectangle(150, 150);
        if (isTail) chosenRec.setFill(tail);
        else chosenRec.setFill(head);
        chosenRec.setOnMouseClicked(e -> {
            boolean shouldEndWithTail = false;
            if (!isPlayerWinning && !isTail || (isPlayerWinning && isTail)) shouldEndWithTail = true;
            int normalHeight = 200;
            Rectangle rectangle = new Rectangle(normalHeight, normalHeight);
            rectangle.setFill(head);
            Animation flipToTail = getFlipTransition(rectangle, tail, normalHeight);
            Animation flipToHead = getFlipTransition(rectangle, head, normalHeight);
            Rectangle tailRec = new Rectangle();
            tailRec.setFill(tail);
            tailRec.setHeight(40);
            tailRec.setWidth(40);
            Rectangle headRec = new Rectangle();
            headRec.setFill(head);
            headRec.setWidth(40);
            headRec.setHeight(40);
            Button startGame = new Button("start Duel!");
            startGame.setOnMouseClicked(mouseEvent -> {
                currentGame.playGraphical();
                GameView.showGameView();
            });
            HBox pane = new HBox(rectangle);
            pane.setMinSize(normalHeight, normalHeight);
            pane.setAlignment(Pos.CENTER);
            VBox vBox;
            if (isTail) vBox = new VBox(headRec, pane, tailRec, startGame);
            else vBox = new VBox(tailRec, pane, headRec, startGame);
            vBox.setAlignment(Pos.CENTER);
            vBox.setSpacing(20);
            vBox.setMinHeight(400);
            vBox.setMinWidth(400);
            LoginMenu.getMainStage().setScene(new Scene(vBox));
            boolean finalShouldEndWithTail = shouldEndWithTail;
            flipToTail.setOnFinished(actionEvent -> {
                if (countOfFlips != 3 || !finalShouldEndWithTail)
                    flipToHead.play();
            });
            flipToHead.setOnFinished(actionEvent -> {
                countOfFlips++;
                if (countOfFlips < 4) flipToTail.play();
            });
            flipToTail.play();
            GameView.playSound("coin.mp3");
        });
        return chosenRec;
    }

    private static Animation getFlipTransition(Rectangle rectangle, ImagePattern finalImage, int normalHeight) {
        return new Transition() {
            {
                setCycleDuration(new Duration(150));
            }

            @Override
            protected void interpolate(double v) {
                if (v <= 0.5) rectangle.setHeight((1 - v * 2) * normalHeight);
                else {
                    rectangle.setFill(finalImage);
                    rectangle.setHeight((2 * v - 1) * normalHeight);
                }
            }
        };
    }


    public static String menuName() {
        return "Duel Menu";
    }

    public static Game getCurrentGame() {
        return currentGame;
    }

    public static void setCurrentGame(Game currentGame) {
        GameMenu.currentGame = currentGame;
    }

    public static String isDuelPossibleWithError(String rounds, User secondUser, boolean isAI) {
        User firstUser = MainMenu.getCurrentUser();
        if (firstUser.getActiveDeck() == null) return "you have no active deck";
        if (!firstUser.getActiveDeck().isDeckValid())
            return "your deck is invalid";
        if (!isAI) {
            if (secondUser == null) return "there is no player with this username";
            if (secondUser == firstUser) return "you can't play with yourself";
            if (secondUser.getActiveDeck() == null) return secondUser.getUsername() + " has no active deck";
            if (!secondUser.getActiveDeck().isDeckValid())
                return secondUser.getUsername() + "’s deck is invalid";
        }
        if (!rounds.equals("1") && !rounds.equals("3"))
            return "number of rounds is not supported";
        return null;
    }
}
