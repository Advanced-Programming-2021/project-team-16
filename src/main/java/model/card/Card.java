package model.card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public abstract class Card implements Comparable<Card> {

    private static ArrayList<Card> cards = new ArrayList<>();
    protected String name;
    protected String description;
    protected int price;
    public Random random = new Random();

    public Card(String name, String description, int price) {
        this.name = name;
        this.description = description;
        this.price = price;
        cards.add(this);
    }

    public static Card getCardByName(String name) {
        for (Card card : cards) {
            if (card.name.equals(name)) {
                return card;
            }
        }
        return null;
    }

    public static void sort(ArrayList<Card> cards) {
        Collections.sort(cards);
    }

    public static ArrayList<Card> getCards() {
        return cards;
    }

    @Override
    public int compareTo(Card other) {
        return this.name.compareTo(other.name);
    }

    @Override
    public String toString() {
        return name + ":" + description;
    }

    public String getName() {
        return name;
    }


    public String getDescription() {
        return description;
    }

}

