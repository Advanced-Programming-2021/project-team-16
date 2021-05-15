package model;

import model.card.Card;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

public class Deck {
    private String name;
    //    private ArrayList<Card> mainDeck = new ArrayList<>();
//    private ArrayList<Card> sideDeck = new ArrayList<>();
    private static ArrayList<Deck> decks = new ArrayList<>();
    private ArrayList<String> mainCardNames;
    private ArrayList<String> sideCardNames;

    public Deck(String name, ArrayList<String> mainCardNames, ArrayList<String> sideCardNames) {
        this.name = name;
        decks.add(this);
        this.mainCardNames = mainCardNames;
        this.sideCardNames = sideCardNames;
//        for (String mainCardName : mainCardNames) mainDeck.add(Card.make(mainCardName));
//        for (String sideCardName : sideCardNames) sideDeck.add(Card.make(sideCardName));

    }

    public static ArrayList<Card> getRandomMainDeck() {
        ArrayList<Card> randomDeck = new ArrayList<>();
        ArrayList<Card> cards = Card.getCards();
        int numberOfCards = cards.size();
        Random random = new Random();
        for (int i = 0; i < 45; i++) randomDeck.add(cards.get(random.nextInt(numberOfCards)));
        return randomDeck;
    }

    public static Deck getDeckByName(String name) {
        for (Deck deck : decks) {
            if (deck.name.equals(name)) return deck;
        }
        return null;
    }

    public void addCardToSideDeck(Card card) {
        //this.sideDeck.add(card);
        this.sideCardNames.add(card.getName());
    }

    public void addCardToMainDeck(Card card) {
        // this.mainDeck.add(card);
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

    public ArrayList<String> getMainDeckCards() {
        return mainCardNames;
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


}
