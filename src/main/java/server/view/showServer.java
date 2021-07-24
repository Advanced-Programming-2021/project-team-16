package server.view;

import server.modell.User;
import server.modell.card.Card;
import server.modell.card.monster.Monster;

import java.util.ArrayList;

public class showServer {
    public static void showCardsInShop() {
        ArrayList<Card> cards = Card.getCards();
        Card.sort(cards);
        for (Card card : cards) {
            System.out.println(card.getName() + ":" + card.getPrice());
        }
    }

    public static void showCardArray(ArrayList<Card> cards) {
        for (int i = 1; i <= cards.size(); i++) {
            if(cards.get(i-1) instanceof Monster)
                System.out.println(i + ". " + cards.get(i - 1).getName()  + " (level " + cards.get(i - 1).getLevel() + ")"+ ": " + cards.get(i - 1).getDescription());
            else
                System.out.println(i + ". " + cards.get(i - 1).getName()  + ": " + cards.get(i - 1).getDescription());
        }
    }

    public static void showSingleCard(Card card) {
        if (card == null) System.out.println("no card with this name exists");
        else System.out.println(card.getCardProperties());
    }

    public static void showScoreBoard(ArrayList<User> users) {
        int rate = 1;
        System.out.println(rate + "-" + users.get(0).toString());
        for (int i = 2; i <= users.size(); i++) {
            if (users.get(i - 1).getScore() != users.get(i - 2).getScore())
                rate = i;
            System.out.println(rate + "-" + users.get(i - 1).toString());
        }
    }

}
