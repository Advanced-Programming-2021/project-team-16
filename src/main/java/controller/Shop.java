package controller;

import model.card.Card;

import java.util.ArrayList;
import java.util.Comparator;

public class Shop {
    public static String buy(String cardName) {
        Card card = Card.getCardByName(cardName);
        //  ArrayList<Card> cardsOfShop =Card.getCards();
        if (card == null) {
            return "there is no card with this name";
        }
        if (MainMenu.getCurrentUser().getMoney() < card.getPrice()) {
            return "not enough money";
        }
        MainMenu.getCurrentUser().decreaseMoney(card.getPrice());
        MainMenu.getCurrentUser().addCard(card);
        // cardsOfShop.remove(card);
        return "shopping is successfully!";
    }

    public static String allCardsOfShop(String cardName) {
        Card card = Card.getCardByName(cardName);
        ArrayList<Card> cardsOfShop = Card.getCards();

        if (card == null) {
            return "there is no card with this name";
        }
        if (buy(cardName).equals("shopping is successfully!")) {
            cardsOfShop.remove(card);
        }
        String allCards = "";
        cardsOfShop.sort(Comparator.comparing(Card::getName));
        for (Card card1 : cardsOfShop) {
            allCards = allCards.concat(card1.toString() + "\n");
        }
        return allCards;
    }

    public static String menuName() {
        return "Shop Menu";

    }
}
