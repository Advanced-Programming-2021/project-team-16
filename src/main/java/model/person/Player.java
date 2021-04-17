package model.person;

import model.Board;

public class Player {
    protected User user;
    protected int LP;
    protected Board board = new Board(user.getActiveDeck().getMainDeck());


    public Player(User user) {
        this.user = user;
        LP = 8000;
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
}
