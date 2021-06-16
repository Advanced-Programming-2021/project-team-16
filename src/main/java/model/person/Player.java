package model.person;

import controller.GameMenu;
import graphicview.GameView;
import model.Board;
import model.card.Card;

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
            for (String cardName : user.getActiveDeck().getMainDeck()) cards.add(Card.make(cardName));
            board = new Board(cards,this);
        }
    }

    public void won() {
        if (LP > maxLp) maxLp = LP;
        winningRounds++;
        increaseGameScore(1000);


    }

    public void decreaseLP(int amount) {
        if (LP > amount)
            LP -= amount;
        else {
            GameMenu.getCurrentGame().setWinner(rival);
            LP = 0;
            if (GameMenu.getCurrentGame().isGraphical()) gameView.doLostAction();
        }
        changeGraphicLPs();
    }

    public void increaseLP(int amount) {
        LP += amount;
        changeGraphicLPs();
    }

    public void setLP(int LP) {
        this.LP = LP;
        changeGraphicLPs();
    }

    private void changeGraphicLPs(){
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
