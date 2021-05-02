package model.person;

import model.Deck;
import model.card.Card;

import java.util.ArrayList;

import static java.util.Collections.swap;

public class User {
    private static ArrayList<User> users;
    private String username;
    private String password;
    private String nickname;
    private int money;
    private int score;
    private ArrayList<Deck> decks = new ArrayList<Deck>();
    private ArrayList<Card> cards = new ArrayList<Card>();
    private Deck activeDeck;

    public static ArrayList<User> getAllUsers() {
        return users;
    }

    public static void sort(ArrayList<User> users) {
        boolean change = true;
        for (int i = users.size(); i > 0 && change; i--) {
            change = false;
            for (int j = 0; j < i; j++)
                if (users.get(j).score < users.get(j + 1).score ||
                        (users.get(j).score == users.get(j + 1).score &&
                                users.get(j).getNickname().compareTo(users.get(j + 1).getNickname()) > 0)) {
                    swap(users, j, j + 1);
                    change = true;
                }
        }
    }

    public User(String username, String password, String nickname) {
        this.money = 100000;
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        users.add(this);
    }

    public static User getUserByUsername(String username) {
        for (User user : users)
            if (user.username.equals(username)) return user;
        return null;
    }

    public static User getUserByNickname(String nickname) {
        for (User user : users)
            if (user.nickname.equals(nickname)) return user;
        return null;
    }

    public void setActiveDeck(Deck deck) {
        if (this.decks.contains(deck))
            this.activeDeck = deck;
    }

    public void increaseMoney(int amount) {
        this.money += amount;
    }

    public void decreaseMoney(int amount) {
        this.money -= amount;
    }

    public void increaseScore(int amount) {
        this.score += amount;
    }

    public void setPassword(String newPass) {
        this.password = newPass;
    }

    public void setNickname(String newNickname) {
        this.nickname = newNickname;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getNickname() {
        return nickname;
    }

    public int getMoney() {
        return money;
    }

    public ArrayList<Deck> getDecks() {
        return decks;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public Deck getActiveDeck() {
        return activeDeck;
    }


    public void addDeck(Deck deck) {
        this.decks.add(deck);
    }

    public void addCard(Card card) {
        this.cards.add(card);
    }

    @Override
    public String toString() {
        return this.getNickname() + ":" + this.getMoney();
    }
}
