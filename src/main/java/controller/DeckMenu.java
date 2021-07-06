package controller;

import model.Deck;
import model.card.Card;
import model.card.monster.Monster;
import view.Show;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;


public class DeckMenu {
    public static String create(String name) {
         if(name.equals("")) return "please enter a name";
        else if (MainMenu.getCurrentUser().getDeckByName(name.toLowerCase(Locale.ROOT)) != null) {
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

                        
                        MainMenu.getCurrentUser().addCard(Objects.requireNonNull(Card.getCardByName(mainDeckCard)));

                }
            }
            if (sideDeckCards != null) {
                for (String sideDeckCard : sideDeckCards) {


                        MainMenu.getCurrentUser().addCard(Objects.requireNonNull(Card.getCardByName(sideDeckCard)));

                }
            }
            for (String cardName:MainMenu.getCurrentUser().getDeckByName(name).getMainDeckCards()) {
                Card card = Card.make(cardName);
                MainMenu.getCurrentUser().getCards().add(card);
            }
            for (String cardName:MainMenu.getCurrentUser().getDeckByName(name).getSideDeckCards()) {
                Card card = Card.make(cardName);
                MainMenu.getCurrentUser().getCards().add(card);
            }
            MainMenu.getCurrentUser().removeDeck(MainMenu.getCurrentUser().getDeckByName(name));

            if(MainMenu.getCurrentUser().getActiveDeck() != null && name.equals(MainMenu.getCurrentUser().getActiveDeck().getName()))
                MainMenu.getCurrentUser().setActiveDeck(null);
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
                return "there are already three cards with name " + "\"" +cardName + "\"" + " in deck " + "\"" + deckName + "\"";
            assert card != null;
            MainMenu.getCurrentUser().getDeckByName(deckName).addCardToMainDeck(card);
            //MainMenu.getCurrentUser().getCards().remove(card);
        } else {
            if (MainMenu.getCurrentUser().getDeckByName(deckName).SideIsFull()) return "side deck is full";
            if (MainMenu.getCurrentUser().getDeckByName(deckName).isLimited(card))
                return "there are already three cards with name " + "\"" +cardName + "\"" + " in deck " + "\"" + deckName + "\"";
            assert card != null;
            MainMenu.getCurrentUser().getDeckByName(deckName).addCardToSideDeck(card);
            MainMenu.getCurrentUser().getCards().remove(card);
        }

        return "card added to deck successfully";
    }


    public static String removeCardFromDeck(String cardName, String deckName, boolean isMain) {
        boolean flag = false;
        Card card = Card.getCardByName(cardName);
        Deck userDeck = MainMenu.getCurrentUser().getDeckByName(deckName);
        if (userDeck == null)
            return "deck with name " + deckName + " does not exist";
        ArrayList<String> mainDeckCards = userDeck.getMainDeckCards();
        ArrayList<String> sideDeckCards = userDeck.getSideDeckCards();

        if (isMain) {
            for (String mainDeckCard : mainDeckCards) {
                if (Card.getCardByName(mainDeckCard) == card) {
                    flag = true;
                    break;
                }
            }
            if (!flag) return "card with name " + cardName + " does not exist in main deck";
            MainMenu.getCurrentUser().getDeckByName(deckName).removeCardFromMain(card);
            MainMenu.getCurrentUser().getCards().add(card);
        } else {
            for (String sideDeckCard : sideDeckCards) {
                if (Card.getCardByName(sideDeckCard) == card) {
                    flag = true;
                    break;
                }
            }
            if (!flag) return "card with name " + cardName + " does not exist in side deck";
            MainMenu.getCurrentUser().getDeckByName(deckName).removeCardFromSide(card);
            MainMenu.getCurrentUser().getCards().add(card);
        }
        return "card removed form deck successfully";
    }
        public static void showUsersCards() {
            ArrayList<Card> userCards = MainMenu.getCurrentUser().getCards();
            if (userCards != null) {
                Card.sort(userCards);
                Show.showCardArray(userCards);
            }
        }

        public static String menuName () {
            return "Deck Menu";

        }

}