package view;

import model.Deck;
import model.card.Card;
import model.person.User;

import java.util.ArrayList;

public class Show {
    public static void showCardArray(ArrayList cards) {
    }

    public static void showSingleCard(Card card) {
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

    //   public static void showGraveCards(){
//        for(Card card : grave)
//
//
//   }
    public static void showDeck(Deck deck) {
    }

    public static void showAllDecks() {
    }

    public static void showBoard(Card[][] myBoard, Card[][] rivalBoars) {
    }
}
