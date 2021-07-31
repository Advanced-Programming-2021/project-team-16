package controller;

import model.card.Card;

public class Shop {
    public static String buy(String cardName) {
        Card card = Card.make(cardName);
        if (card == null) return "there is no card with this name";
        if (MainMenu.getCurrentUser().getMoney() < card.getPrice()) return "not enough money";
        MainMenu.getCurrentUser().decreaseMoney(card.getPrice());
        MainMenu.getCurrentUser().addCard(card);
        return "shopping is successfully!";
    }


    public static String menuName() {
        return "Shop Menu";
    }
}
