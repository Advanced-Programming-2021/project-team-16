package model.person;

import model.Board;
import model.card.Card;

public class Player {
    protected User user;
    protected int LP;
    protected Board board;


    public Player(User user) {
        this.user = user;
        LP = 8000;
        board = new Board(user.getActiveDeck().getMainDeck());
    }

    public void decreaseLP(int amount) {
        LP -= amount;
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

    public boolean askPlayerToActive(Card c) {
        System.out.println("Do U Want to Call " + c.getName());
        return false;
    }

}
