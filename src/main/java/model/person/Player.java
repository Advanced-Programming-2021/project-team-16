package model.person;

import model.Board;
import model.card.Card;

import java.util.ArrayList;

public class Player {
    protected User user;
    protected int LP;
    protected Board board;
    private int gameScore;
    private int maxLp = 0;
    private int winningRounds = 0;


    public Player(User user) {
        this.user = user;
        LP = 8000;
        gameScore = 0;
        if (user != null) {
            ArrayList<Card> cards = new ArrayList<>();
            for (String cardName : user.getActiveDeck().getMainDeck()) cards.add(Card.make(cardName));
            board = new Board(cards);
        }

    }

    public void decreaseLP(int amount) {
        if (LP > amount)
            LP -= amount;
        else LP = 0;
    }

    public void won() {
        if (LP > maxLp) maxLp = LP;
        winningRounds++;
        increaseGameScore(1000);


    }

    public void increaseLP(int amount) {
        LP += amount;
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

    public void setLP(int LP) {
        this.LP = LP;
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

    @Override
    public String toString() {
        return user.getNickname() + ": " + LP;
    }
}
