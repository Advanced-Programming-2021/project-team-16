package model.person;

import model.Deck;
import model.card.Card;

import java.util.ArrayList;

import static java.util.Collections.swap;

public class User {
    private static ArrayList<User> users = new ArrayList<>();
    private String username;
    private String password;
    private String nickname;
    private int money;
    private int score;
    private int gameScore;
    private ArrayList<Deck> decks;
    private Deck activeDeck;
    private ArrayList<String> cardNames;

    public static ArrayList<User> getAllUsers() {
        return users;
    }

    public User(String username, String password, String nickname, int money, int score, ArrayList<String> cardNames,
                ArrayList<Deck> decks, Deck activeDeck) {
        this.money = money;
        this.score = score;
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.cardNames = cardNames;
        this.decks = decks;
        this.activeDeck = activeDeck;
        users.add(this);
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

    public static String getPasswordWeakness(String password) {
        if (password.length() < 7) return "password must have at least 7 characters";
        boolean hasLetter = false, hasDigit = false;
        for (int i = 0; i < password.length(); i++) {
            if (Character.isDigit(password.charAt(i))) hasDigit = true;
            else if (Character.isLetter(password.charAt(i))) hasLetter = true;
        }
        if (!hasDigit) return "password must have at least 1 digit";
        if (!hasLetter) return "password must have at least 1 letter";
        return "strong";
    }

    public void setActiveDeck(Deck deck) {
        if (this.decks.contains(deck)) {
            this.activeDeck = deck;
        }

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

    public void increaseGameScore(int amount) {
        this.gameScore += amount;
    }

    public void setPassword(String newPass) {
        this.password = newPass;
    }

    public void setNickname(String newNickname) {
        this.nickname = newNickname;
    }

    public void setGameScore(int amount) {
        this.gameScore = amount;
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

    public int getGameScore() {
        return gameScore;
    }


    public ArrayList<Deck> getDecks() {
        return decks;
    }

    public Deck getDeckByName(String name) {
        for (Deck deck : decks) {
            if (deck.getName().equals(name))
                return deck;
        }
        return null;
    }

    public ArrayList<Card> getCards() {
        ArrayList<Card> cards = new ArrayList<>();
        for (String cardName : cardNames) cards.add(Card.make(cardName));
        return cards;
    }

    public Deck getActiveDeck() {
        return activeDeck;
    }


    public void addDeck(Deck deck) {
        this.decks.add(deck);
    }

    public void removeDeck(Deck deck) {
        this.decks.remove(deck);
    }

    public void addCard(Card card) {
        this.cardNames.add(card.getName());
    }

    @Override
    public String toString() {
        return nickname + ": " + score;
    }
}
