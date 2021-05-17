package model.person;

import model.Board;
import model.card.Card;

import java.util.ArrayList;

public class Player {
    protected User user;
    protected int LP;
    protected Board board;
    private int gameScore = 0;


    public Player(User user) {
        this.user = user;
        LP = 8000;
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

    public void setGameScore(int amount) {
        this.gameScore = amount;
    }

    public void increaseGameScore(int amount) {
        this.gameScore += amount;
    }

    public int getGameScore() {
        return gameScore;
    }

    public boolean askPlayerToActive(Card c) {
        System.out.println("Do U Want to Call " + c.getName());
        return false;
    }

}
