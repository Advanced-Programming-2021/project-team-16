package model.card;


import java.util.ArrayList;

public abstract class Card {
    private static ArrayList<Card> cards = new ArrayList<>();
    protected String name;
    protected String description;
    protected int price;

    public Card(String name, String description, int price) {
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

    public static void sort(ArrayList<Card> cards) {///////////
    }

    public static ArrayList<Card> getCards() {
        return cards;

    }

    public String toString() {
        return null;
    }

    public String getName() {
        return name;
    }


    public String getDescription() {
        return description;
    }

}

