package view;

import model.Deck;
import model.Game;
import model.Phase;
import model.card.Card;
import model.person.User;

import java.util.ArrayList;

public class Show {
    public static void showCardArray(ArrayList cards) {
    }

    public static void showSingleCard(String cardName) {
    }

    public static void showScoreBoard(ArrayList<User> users) {
        int rate = 1;
        System.out.println(rate + "-" + users.get(0).toString());
        for (int i = 2; i <= users.size(); i++) {
            if (users.get(i - 1).getMoney() != users.get(i - 2).getMoney())
                rate = i;
            System.out.println(rate + "-" + users.get(i - 1).toString());
        }
    }

    public static void showGraveCards(Game game) {
        for (Card c : game.getCurrentPlayer().getBoard().getGrave()) {
            System.out.println(c);
        }
        for (Card c : game.getRival().getBoard().getGrave()) {
            System.out.println(c);
        }


    }


    public static void showDeck(Deck deck) {
    }

    public static void showAllDecks() {
    }

    public static void showBoard(Card[][] myBoard, Card[][] rivalBoars) {
    }

    public static void showGameMessage(String gameMessage) {
        System.out.println(gameMessage);
    }

    public static void showPhase(Phase phase) {
        System.out.println("phase:" + phase.getPhaseName());
    }

    public static void showImportantGameMessage(String importantMessage) {
        System.out.println(importantMessage);
    }

}
