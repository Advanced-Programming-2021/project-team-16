package model;

import model.card.Card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Deck {
    private String name;
    private ArrayList<Card> mainDeck; //= new ArrayList<>();
    private ArrayList<Card> sideDeck; //= new ArrayList<>();
    private ArrayList<Deck> decks = new ArrayList<>();


    public Deck(String name) {
        this.name = name;
        decks.add(this);
        mainDeck = new ArrayList<>();
        sideDeck = new ArrayList<>();
    }

    public static ArrayList<Card> getRandomDeck() {
        //baraye ai lazeme
        //TODO

        return null;
    }

    public void addCardToSideDeck(Card card) {
        sideDeck.add(card);
    }

    public void addCardToMainDeck(Card card) {
        mainDeck.add(card);
    }

    public void removeCardFromMain(Card card) {
        for (Card c : mainDeck) {
            if (c.equals(card)) {
                mainDeck.remove(card);
                return;
            }
        }
    }

    public void removeCardFromSide(Card card) {
        for (Card c : sideDeck) {
            if (c.equals(card)) {
                sideDeck.remove(card);
                return;
            }
        }
    }

    public ArrayList<Card> getSideDeck() {
        return sideDeck;
    }

    public ArrayList<Card> getMainDeck() {
        return mainDeck;
    }

    public boolean SideIsFull() {
        return sideDeck.size() >= 15;
    }

    public boolean MainIsFull() {
        return mainDeck.size() >= 60;
    }

    public void shuffle(String deck) {
        switch (deck) {
            case "Main" -> Collections.shuffle(mainDeck);
            case "Side" -> Collections.shuffle(sideDeck);
        }
    }

    public boolean isMainDeckValid() {
        return this.mainDeck.size() <= 60 && this.mainDeck.size() >= 40;
    }

    public boolean isSideDeckValid() {
        return this.sideDeck.size() <= 15;
    }

    public static void sort(ArrayList<Deck> decks) {
        decks.sort(Comparator.comparing(o -> o.name));

    }

    public String toString() {
        return name;
    }

    public Card drawOneCard(Game game, Board board) {

        ArrayList<Card> deck = board.getDeck();
        if (deck.isEmpty()) return null;
        return deck.remove(deck.size() - 1);
    }

    public String getName() {
        return name;
    }

    public ArrayList<Card> getMainDeckCards() {
        return mainDeck;
    }

    public ArrayList<Card> getSideDeckCards() {
        return sideDeck;
    }

    public boolean isLimited(Card card) {
        int number = 0;
        for (Card eachCard : this.sideDeck) {
            if (eachCard.equals(card))
                number++;
        }
        for (Card eachCard : this.mainDeck) {
            if (eachCard.equals(card))
                number++;
        }
        return number >= 3;
    }
//    public static Deck getDeckByName(String name) {
//        return
//    }
}
