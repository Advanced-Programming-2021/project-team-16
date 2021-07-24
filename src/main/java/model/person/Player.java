package model.person;

import controller.GameMenu;
import graphicview.GameView;
import javafx.animation.Animation;
import javafx.animation.Transition;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import model.Board;
import server.model.card.Card;
import server.model.User;

import java.util.ArrayList;

public class Player {
    protected Player rival;
    protected User user;
    protected int LP;
    protected Board board;
    private int gameScore;
    private int maxLp = 0;
    private int winningRounds = 0;
    private GameView gameView;

    public Player(User user) {
        this.user = user;
        LP = 8000;
        gameScore = 0;
        if (user != null) {
            ArrayList<Card> cards = new ArrayList<>();
            for (String cardName : user.getActiveDeck().getMainDeck()) {
                Card card = Card.make(cardName);
                if (card != null) cards.add(card);
            }
            board = new Board(cards, this);
        }
    }

    public void won() {
        if (LP > maxLp) maxLp = LP;
        winningRounds++;
        increaseGameScore(1000);


    }

    public void decreaseLP(int amount) {
        if (LP > amount) {
            LP -= amount;
            GameView.playSound("LP-decrease.wav");
        } else {
            amount = LP;
            GameMenu.getCurrentGame().setWinner(rival);
            LP = 0;
            if (GameMenu.getCurrentGame().isGraphical()) gameView.doLostAction();
        }
        if(GameMenu.getCurrentGame().isGraphical()) {
            changeGraphicLPs();
            int finalAmount = amount;
            Animation animation = new Transition() {
                {
                    setCycleDuration(Duration.millis(1000));
                }

                protected void interpolate(double v) {
                    Color color;
                    try {
                        color = Color.rgb(
                                (int) (v * (8000 - LP) / 8 * 255 / 1000 + (1 - v) * (8000 - LP - finalAmount) / 8 * 255 / 1000)
                                , (int) (v * LP / 8 * 170 / 1000 + (1 - v) * (LP + finalAmount) / 8 * 170 / 1000), 0);
                    } catch (Exception e) {
                        color = Color.rgb(0, 200, 0);
                    }
                    gameView.myLP.setTextFill(color);
                    rival.gameView.rivalLP.setTextFill(color);
                }
            };
            animation.play();
        }
    }

    public void increaseLP(int amount) {
        LP += amount;

        if(GameMenu.getCurrentGame().isGraphical()) {
            changeGraphicLPs();

            Animation animation = new Transition() {
                {
                    setCycleDuration(Duration.millis(1000));
                }

                protected void interpolate(double v) {
                    Color color;
                    try {
                        color = Color.rgb(
                                (int) (v * (8000 - LP) / 8 * 255 / 1000 + (1 - v) * (8000 - LP + amount) / 8 * 255 / 1000)
                                , (int) (v * LP / 8 * 170 / 1000 + (1 - v) * (LP - amount) / 8 * 170 / 1000), 0);
                    } catch (Exception e) {
                        color = Color.rgb(0, 200, 0);
                    }
                    gameView.myLP.setTextFill(color);
                    rival.gameView.rivalLP.setTextFill(color);
                }
            };
            animation.play();
        }
    }

    public void setLP(int LP) {
        this.LP = LP;
        if(GameMenu.getCurrentGame().isGraphical()) {
            changeGraphicLPs();
            Color color = Color.rgb(0, 170, 0);
            gameView.myLP.setTextFill(color);
            rival.gameView.rivalLP.setTextFill(color);
        }
    }

    private void changeGraphicLPs() {
        if (!GameMenu.getCurrentGame().isGraphical()) return;
        this.gameView.getMyLP().setText(String.valueOf(LP));
        rival.gameView.getRivalLP().setText(String.valueOf(LP));
    }

    public int getLP() {
        return LP;
    }

    public Board getBoard() {
        return board;
    }

    public User getUser() {
        return user;
    }


    public void increaseGameScore(int amount) {
        this.gameScore += amount;
    }

    public int getGameScore() {
        return gameScore;
    }

    public int getMaxLp() {
        return maxLp;
    }

    public int getWinningRounds() {
        return winningRounds;
    }

    public void setGameView(GameView gameView) {
        this.gameView = gameView;
    }

    public void setRival(Player rival) {
        this.rival = rival;
    }

    public Player getRival() {
        return rival;
    }

    public GameView getGameView() {
        return gameView;
    }

    @Override
    public String toString() {
        return user.getNickname() + ": " + LP;
    }
}
