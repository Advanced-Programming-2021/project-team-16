package model.card;


import model.person.Player;

import java.util.ArrayList;

public abstract class Card {
    private static ArrayList<Card> cards = new ArrayList<>();
    protected String name;
    protected String description;
    protected int price;
    protected Player owner = null;

    public Card(String name, String description, int price) {
        this.name = name;
        this.description = description;
        this.price = price;
        cards.add(this);
    }

    public void setOwner(Player player) {
        owner = player;
    }

    public static Card getCardByName(String name) {
        for (Card card : cards) {
            if (card.name.equals(name)) {
                return card;
            }
        }
        return null;
    }

    public static void sort(ArrayList<Card> cards) {///////////
    }

    public static ArrayList<Card> getCards() {
        return cards;

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

