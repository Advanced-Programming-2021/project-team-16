package model.card;

import model.card.monster.CommandKnight;
import model.card.monster.GraveYardEffectMonster;
import model.card.monster.Monster;
import model.card.monster.Suijin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public abstract class Card implements Comparable<Card> {

    private static ArrayList<Card> cards = new ArrayList<>();
    protected String name;
    protected String description;
    protected int price;
    public Random random = new Random();

    public static Card make(String cardName) {
        //... all cards in update status except Monster(...)
        Card card = switch (cardName) {
            case "Command Knight" -> new CommandKnight();
            case "Yomi Ship" -> new GraveYardEffectMonster("Yomi Ship", "If this card is destroyed by battle and sent to the GY: " +
                    "Destroy the monster that destroyed this card.", 1700, Monster.MonsterType.AQUA, 3, 800,
                    1400, true);
            case "Suijin" -> new Suijin();
            default -> Monster.clone((Monster) getCardByName(cardName));
        };
        return card;
        //TODO
    }

    public int getPrice() {
        return price;
    }

    public Card(String name, String description, int price) {
        this.name = name;
        this.description = description;
        this.price = price;
        for (Card card : cards) if (card.getName().equals(this.getName())) return;
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
        return name + ": " + price;
    }

    public String desToString() {
        return name + ": " + description;
    }

    public String getName() {
        return name;
    }


    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Card)) return false;
        Card card = (Card) o;
        return name.equals(card.name);
    }
}

