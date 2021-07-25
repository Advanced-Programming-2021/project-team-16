package server.view;

import client.controller.Login;
import server.modell.Deck;
import server.controller.MainMenuServer;
import server.modell.User;
import server.modell.card.Card;
import server.modell.card.monster.Monster;

import java.io.IOException;
import java.util.ArrayList;

public class ShowServer {
    public static void showCardsInShop() {
        ArrayList<Card> cards = Card.getCards();
        Card.sort(cards);
        for (Card card : cards) {
            System.out.println(card.getName() + ":" + card.getPrice());
        }
    }
    public static String showMainDeck(String deckName) {
        User user = MainMenuServer.getCurrentUser();
        Deck userDeck = user.getDeckByName(deckName);
        if (user.getDeckByName(deckName) != null) {
            System.out.println("Deck: " + deckName);
            System.out.println("Main deck:");
            ArrayList<String> mainDeckCards = userDeck.getMainDeckCards();
            ArrayList<Card> monsters = new ArrayList<>();
            ArrayList<Card> spellAndTrap = new ArrayList<>();
            for (String mainDeckCard : mainDeckCards) {

                if (Card.getCardByName(mainDeckCard) instanceof Monster) monsters.add(Card.getCardByName(mainDeckCard));
                else spellAndTrap.add(Card.getCardByName(mainDeckCard));
            }
            Card.sort(monsters);
            System.out.println("Monsters:");
            for (Card monster : monsters) {
                System.out.println(monster.desToString());
            }
            System.out.println("Spell and Traps:");
            for (Card card : spellAndTrap) {
                System.out.println(card.desToString());
            }
        } else System.out.println("deck with name " + deckName + " does not exist");
           return "show main deck successful!";
    }
    public static String showSideDeck(String deckName) {
        User user = MainMenuServer.getCurrentUser();
        Deck userDeck = user.getDeckByName(deckName);
        if (user.getDeckByName(deckName) != null) {
            System.out.println("Deck: " + deckName);
            System.out.println("Side deck:");
            ArrayList<String> sideDeckCards = userDeck.getSideDeckCards();
            ArrayList<Card> monsters = new ArrayList<>();
            ArrayList<Card> spellAndTrap = new ArrayList<>();
            for (String sideDeckCard : sideDeckCards) {
                if (Card.getCardByName(sideDeckCard) instanceof Monster) monsters.add(Card.getCardByName(sideDeckCard));
                else spellAndTrap.add(Card.getCardByName(sideDeckCard));
            }
            Card.sort(monsters);
            Card.sort(spellAndTrap);
            System.out.println("Monsters:");
            for (Card monster : monsters) {
                System.out.println(monster.desToString());
            }
            System.out.println("Spell and Traps:");
            for (Card card : spellAndTrap) {
                System.out.println(card.desToString());
            }
        } else System.out.println("deck with name " + deckName + " does not exist");
        return "show side deck successful!";
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

    public static String showScoreBoard(ArrayList<User> users) {
        int rate = 1;
        System.out.println(rate + "-" + users.get(0).toString());
        for (int i = 2; i <= users.size(); i++) {
            if (users.get(i - 1).getScore() != users.get(i - 2).getScore())
                rate = i;
            System.out.println(rate + "-" + users.get(i - 1).toString());
        }
        return "done!";
    }
    public static String showAllDecks() {
        String validation;
        boolean hasActiveDeck = false;
        User user = MainMenuServer.getCurrentUser();
        ArrayList<Deck> userDecks = user.getDecks();
        System.out.println("Decks:");
        System.out.println("Active deck:");
        if (user.getActiveDeck() != null) {
            hasActiveDeck = true;
            Deck activeDeck = user.getActiveDeck();
            if (activeDeck.isMainDeckValid() && activeDeck.isDeckValid()) validation = "valid";
            else validation = "invalid";
            System.out.println(activeDeck.getName() + ":  main deck " + activeDeck.getMainDeckCards().size()
                    + ", side deck " + activeDeck.getSideDeckCards().size() + ", " + validation);
        }
        System.out.println("Other decks:");
        if (hasActiveDeck) {
            if (userDecks != null) {
                Deck.sort(userDecks);
                for (Deck userDeck : userDecks) {
                    if (userDeck == user.getActiveDeck()) continue;
                    if (userDeck.isMainDeckValid() && userDeck.isSideDeckValid()) validation = "valid";
                    else validation = "invalid";
                    System.out.println(userDeck.getName() + ": main deck " + userDeck.getMainDeckCards().size() +
                            ", side deck " + userDeck.getSideDeckCards().size() + ", " + validation);
                }
            }
        } else {
            if (userDecks != null) {
                Deck.sort(userDecks);
                for (Deck userDeck : userDecks) {
                    if (userDeck.isMainDeckValid() && userDeck.isSideDeckValid()) validation = "valid";
                    else validation = "invalid";
                    System.out.println(userDeck.getName() + ": main deck " + userDeck.getMainDeckCards().size() +
                            ", side deck " + userDeck.getSideDeckCards().size() + ", " + validation);
                }
            }
        }
        return "done!";
    }

}
