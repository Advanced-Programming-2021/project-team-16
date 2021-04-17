package model;

import model.card.Card;

import java.util.ArrayList;

public class Deck {
    private String name;
    private ArrayList<Card> mainDeck = new ArrayList<>();
    private ArrayList<Card> sideDeck = new ArrayList<>();

    public Deck(String name) {
        this.name = name;
    }

    public void removeCard(Card card) {
    }


    public ArrayList<Card> getMainDeck() {
        return mainDeck;
    }

    public static void sort(ArrayList<Deck> decks) {///////
    }

    public String toString() {
        return null;
    }
}
