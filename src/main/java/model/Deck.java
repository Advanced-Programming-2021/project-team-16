package model;

import model.card.Card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class Deck {
    private String name;
    private ArrayList<Card> mainDeck = new ArrayList<>();
    private ArrayList<Card> sideDeck = new ArrayList<>();
    private static ArrayList<Deck> decks = new ArrayList<>();
    private ArrayList<String> mainCardNames;
    private ArrayList<String> sideCardNames;

    public Deck(String name, ArrayList<String> mainCardNames, ArrayList<String> sideCardNames) {
        this.name = name;
        decks.add(this);
        this.mainCardNames = mainCardNames;
        this.sideCardNames = sideCardNames;
        for (String mainCardName : mainCardNames) mainDeck.add(Card.make(mainCardName));
        for (String sideCardName : sideCardNames) sideDeck.add(Card.make(sideCardName));

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
        this.sideDeck.add(card);
        this.sideCardNames.add(card.getName());
    }

    public void addCardToMainDeck(Card card) {
        this.mainDeck.add(card);
        this.mainCardNames.add(card.getName());
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

    public boolean isDeckValid() {
        int numberOfOneCard = 0;
        ArrayList<Card> allCards = new ArrayList<>(mainDeck);
        allCards.addAll(sideDeck);
        for (Card card : allCards) {
            for (Card card2 : allCards) {
                if (card.getName().equals(card2.getName())) numberOfOneCard++;
            }
            if (numberOfOneCard > 3) return false;
        }
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

    public ArrayList<Card> getMainDeckCards() {
        return mainDeck;
    }

    public static ArrayList<Deck> getAllDecks() {
        return decks;
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


}
