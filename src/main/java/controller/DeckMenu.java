package controller;

import model.Deck;
import model.card.Card;
import view.Show;

import java.util.ArrayList;

public class DeckMenu {
    public static String create(String name) {

        if (MainMenu.getCurrentUser().getDeckByName(name) != null) {
            return "deck with name " + name + " already exists";
        } else {
            Deck deck = new Deck(name);
            MainMenu.getCurrentUser().addDeck(deck);
            return "deck created successfully!";
        }

    }

    public static String delete(String name) {
        if (MainMenu.getCurrentUser().getDeckByName(name) == null) {
            return "deck with name " + name + " does not exist";
        } else {
            ArrayList<String> mainDeckCards = MainMenu.getCurrentUser().getDeckByName(name).getMainDeckCards();
            ArrayList<String> sideDeckCards = MainMenu.getCurrentUser().getDeckByName(name).getSideDeckCards();
            if (mainDeckCards != null) {
                for (String mainDeckCard : mainDeckCards) {
                    MainMenu.getCurrentUser().addCard(Card.getCardByName(mainDeckCard));
                }
            }
            if (sideDeckCards != null) {
                for (String sideDeckCard : sideDeckCards) {
                    MainMenu.getCurrentUser().addCard(Card.getCardByName(sideDeckCard));
                }
            }
            MainMenu.getCurrentUser().removeDeck(MainMenu.getCurrentUser().getDeckByName(name));
            return "deck deleted successfully";
        }
    }

    public static String activate(String name) {
        if (MainMenu.getCurrentUser().getDeckByName(name) == null) {
            return "deck with name " + name + " does not exist";
        } else {
            MainMenu.getCurrentUser().setActiveDeck(MainMenu.getCurrentUser().getDeckByName(name));
            return "deck activated successfully";
        }

    }

    public static String addCardToDeck(String cardName, String deckName, boolean isMain) {
        Card card = Card.getCardByName(cardName);


        if (!MainMenu.getCurrentUser().hasCard(cardName)) return "card with name " + cardName + " does not exist";
        if (MainMenu.getCurrentUser().getDeckByName(deckName) == null)
            return "deck with name " + deckName + " does not exist";
        if (isMain) {
            if (MainMenu.getCurrentUser().getDeckByName(deckName).MainIsFull()) return "main deck is full";
            if (MainMenu.getCurrentUser().getDeckByName(deckName).isLimited(card))
                return "there are already three cards with name " + cardName + " in deck " + deckName;
            MainMenu.getCurrentUser().getDeckByName(deckName).addCardToMainDeck(card);
        } else {
            if (MainMenu.getCurrentUser().getDeckByName(deckName).SideIsFull()) return "side deck is full";
            if (MainMenu.getCurrentUser().getDeckByName(deckName).isLimited(card))
                return "there are already three cards with name " + cardName + " in deck " + deckName;
            MainMenu.getCurrentUser().getDeckByName(deckName).addCardToSideDeck(card);
        }

        return "card added to deck successfully";
    }


    public static String removeCardFromDeck(String cardName, String deckName, boolean isMain) {
        Card card = Card.getCardByName(cardName);
        if (!MainMenu.getCurrentUser().hasCard(cardName)) return "card with name " + cardName + " does not exist";
        if (MainMenu.getCurrentUser().getDeckByName(deckName) == null)
            return "deck with name " + deckName + " does not exist";
        if (isMain) {
            MainMenu.getCurrentUser().getDeckByName(deckName).removeCardFromMain(card);
        } else {
            MainMenu.getCurrentUser().getDeckByName(deckName).removeCardFromSide(card);
        }
        return "card removed form deck successfully";
    }

    public static String showUsersCards() {
        ArrayList<Card> userCards = MainMenu.getCurrentUser().getCards();
        Card.sort(userCards);
        Show.showCardArray(userCards);
        return null;
    }

    public static String menuName() {
        return "Deck Menu";

    }
}
