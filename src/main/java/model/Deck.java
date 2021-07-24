package model;

import server.modell.card.Card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Deck {
    private final String name;

    private static ArrayList<Deck> decks = new ArrayList<>();
    private final ArrayList<String> mainCardNames = new ArrayList<>();
    private final ArrayList<String> sideCardNames = new ArrayList<>();

    public Deck(String name) {
        this.name = name;
        decks.add(this);
    }

    public static ArrayList<Card> getRandomMainDeck() {
        ArrayList<Card> randomDeck = new ArrayList<>();
        ArrayList<Card> cards = Card.getCards();
        int numberOfCards = cards.size();
        Random random = new Random();
        for (int i = 0; i < 45; i++) randomDeck.add(Card.make(cards.get(random.nextInt(numberOfCards)).getName()));
        return randomDeck;
    }

    public void addCardToSideDeck(Card card) {
        this.sideCardNames.add(card.getName());
    }

    public void addCardToMainDeck(Card card) {
        this.mainCardNames.add(card.getName());
    }

    public void removeCardFromMain(Card card) {
        for (String c : mainCardNames) {
            if (c.equals(card.getName())) {
                mainCardNames.remove(card.getName());
                return;
            }
        }
    }

    public void removeCardFromSide(Card card) {
        for (String c : sideCardNames) {
            if (c.equals(card.getName())) {
                sideCardNames.remove(card.getName());

                return;
            }
        }
    }

    public ArrayList<String> getSideDeck() {
        return sideCardNames;
    }

    public ArrayList<String> getMainDeck() {
        return mainCardNames;
    }

    public boolean SideIsFull() {
        return sideCardNames.size() >= 15;
    }

    public boolean MainIsFull() {
        return mainCardNames.size() >= 60;
    }


    public boolean isMainDeckValid() {
        return this.mainCardNames.size() <= 60 && this.mainCardNames.size() >= 40;
    }

    public boolean isSideDeckValid() {
        return this.sideCardNames.size() <= 15;
    }

    public boolean isDeckValid() {

        return isMainDeckValid() && isSideDeckValid();
    }

    public static void sort(ArrayList<Deck> decks) {
        for (int j = decks.size() - 1; j > 0; j--)
            for (int i = 0; i < j; i++)
                if (decks.get(i).name.compareTo(decks.get(i + 1).name) > 0) Collections.swap(decks, i, i + 1);
    }

    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getMainDeckCards() {
        return mainCardNames;
    }
    public  int getMainCount(){
        int count = 0;
        for (int i = 0; i < getMainDeckCards().size(); i++) {
            count++;
        }
        return count;
    }
    public  int getSideCount(){
        int count = 0;
        for (int i = 0; i < getSideDeckCards().size(); i++) {
            count++;
        }
        return count;
    }
    public static ArrayList<Deck> getAllDecks() {
        return decks;
    }

    public ArrayList<String> getSideDeckCards() {
        return sideCardNames;
    }

    public boolean isLimited(Card card) {
        int number = 0;
        for (String eachCard : this.sideCardNames) {
            if (eachCard.equals(card.getName()))
                number++;
        }
        for (String eachCard : this.mainCardNames) {
            if (eachCard.equals(card.getName()))
                number++;
        }
        return number >= 3;
    }

    public static void setDecks(ArrayList<Deck> decks) {
        Deck.decks = decks;
    }
}
