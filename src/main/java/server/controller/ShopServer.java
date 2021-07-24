package server.controller;


import server.modell.card.Card;

import static server.controller.MainMenuServer.getCurrentUser;

public class ShopServer {
    public static String buy(String cardName) {
        Card card = Card.make(cardName);
        if (card == null) return "there is no card with this name";
        if (getCurrentUser().getMoney() < card.getPrice()) return "not enough money";
       getCurrentUser().decreaseMoney(card.getPrice());
       getCurrentUser().addCard(card);
        return "shopping is successfully!";
    }
}
