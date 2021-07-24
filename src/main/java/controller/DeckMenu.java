package controller;

import model.Deck;
import server.controller.MainMenuServer;
import server.modell.card.Card;
import view.Show;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;


public class DeckMenu {
    public static String create(String name) {
        String result;
        try {
            Login.dataOut.writeUTF("deck create " + name);
            Login.dataOut.flush();
            result = Login.dataIn.readUTF();

        } catch (IOException x) {
            x.printStackTrace();
            result = "error creating deck";
        }
        return result;
//         if(name.equals("")) return "please enter a name";
//        else if (MainMenuServer.getCurrentUser().getDeckByName(name.toLowerCase(Locale.ROOT)) != null) {
//            return "deck with name " + name + " already exists";
//        } else {
//            Deck deck = new Deck(name);
//             MainMenuServer.getCurrentUser().addDeck(deck);
//            return "deck created successfully!";
//        }

    }

    public static String delete(String name) {
        String result;
        try {
            Login.dataOut.writeUTF("deck delete " + name);
            Login.dataOut.flush();
            result = Login.dataIn.readUTF();

        } catch (IOException x) {
            x.printStackTrace();
            result = "delete deck error";
        }
        return result;
//        if (MainMenuServer.getCurrentUser().getDeckByName(name) == null) {
//            return "deck with name " + name + " does not exist";
//        } else {
//            ArrayList<String> mainDeckCards = MainMenuServer.getCurrentUser().getDeckByName(name).getMainDeckCards();
//            ArrayList<String> sideDeckCards = MainMenuServer.getCurrentUser().getDeckByName(name).getSideDeckCards();
//            if (mainDeckCards != null) {
//                for (String mainDeckCard : mainDeckCards) {
//
//
//                    MainMenuServer.getCurrentUser().addCard(Objects.requireNonNull(Card.getCardByName(mainDeckCard)));
//
//                }
//            }
//            if (sideDeckCards != null) {
//                for (String sideDeckCard : sideDeckCards) {
//
//
//                    MainMenuServer.getCurrentUser().addCard(Objects.requireNonNull(Card.getCardByName(sideDeckCard)));
//
//                }
//            }
//            for (String cardName:MainMenuServer.getCurrentUser().getDeckByName(name).getMainDeckCards()) {
//                Card card = Card.make(cardName);
//                MainMenuServer.getCurrentUser().getCards().add(card);
//            }
//            for (String cardName:MainMenuServer.getCurrentUser().getDeckByName(name).getSideDeckCards()) {
//                Card card = Card.make(cardName);
//                MainMenuServer.getCurrentUser().getCards().add(card);
//            }
//            MainMenuServer.getCurrentUser().removeDeck(MainMenuServer.getCurrentUser().getDeckByName(name));
//
//            if(MainMenuServer.getCurrentUser().getActiveDeck() != null && name.equals(MainMenuServer.getCurrentUser().getActiveDeck().getName()))
//                MainMenuServer.getCurrentUser().setActiveDeck(null);
//            return "deck deleted successfully";
//        }
    }

    public static String activate(String name) {
        String result;
        try {
            Login.dataOut.writeUTF("deck set-activate " + name);
            Login.dataOut.flush();
            result = Login.dataIn.readUTF();

        } catch (IOException x) {
            x.printStackTrace();
            result = "delete deck error";
        }
        return result;
//        if (MainMenuServer.getCurrentUser().getDeckByName(name) == null) {
//            return "deck with name " + name + " does not exist";
//        } else {
//            MainMenuServer.getCurrentUser().setActiveDeck(MainMenuServer.getCurrentUser().getDeckByName(name));
//            return "deck activated successfully";
//        }

    }

    public static String addCardToDeck(String cardName, String deckName, boolean isMain) {

        String result;
        try {
            Login.dataOut.writeUTF("deck add-card --card " + cardName + " --deck " + deckName);
            Login.dataOut.flush();
            result = Login.dataIn.readUTF();

        } catch (IOException x) {
            x.printStackTrace();
            result = "delete deck error";
        }
        return result;
//        Card card = Card.getCardByName(cardName);
//
//
//        if (!MainMenuServer.getCurrentUser().hasCard(cardName)) return "card with name " + cardName + " does not exist";
//        if (MainMenuServer.getCurrentUser().getDeckByName(deckName) == null)
//            return "deck with name " + deckName + " does not exist";
//        if (isMain) {
//            if (MainMenuServer.getCurrentUser().getDeckByName(deckName).MainIsFull()) return "main deck is full";
//            if (MainMenuServer.getCurrentUser().getDeckByName(deckName).isLimited(card))
//                return "there are already three cards with name " + "\"" +cardName + "\"" + " in deck " + "\"" + deckName + "\"";
//            assert card != null;
//            MainMenuServer.getCurrentUser().getDeckByName(deckName).addCardToMainDeck(card);
//            MainMenuServer.getCurrentUser().removeCard(card);
//        } else {
//            if (MainMenuServer.getCurrentUser().getDeckByName(deckName).SideIsFull()) return "side deck is full";
//            if (MainMenuServer.getCurrentUser().getDeckByName(deckName).isLimited(card))
//                return "there are already three cards with name " + "\"" +cardName + "\"" + " in deck " + "\"" + deckName + "\"";
//            assert card != null;
//            MainMenuServer.getCurrentUser().getDeckByName(deckName).addCardToSideDeck(card);
//            MainMenuServer.getCurrentUser().removeCard(card);
//        }
//
//        return "card added to deck successfully";
    }


    public static String removeCardFromDeck(String cardName, String deckName, boolean isMain) {
        boolean flag = false;
        Card card = Card.getCardByName(cardName);
        Deck userDeck = MainMenuServer.getCurrentUser().getDeckByName(deckName);
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
            MainMenuServer.getCurrentUser().getDeckByName(deckName).removeCardFromMain(card);
            MainMenuServer.getCurrentUser().getCards().add(card);
        } else {
            for (String sideDeckCard : sideDeckCards) {
                if (Card.getCardByName(sideDeckCard) == card) {
                    flag = true;
                    break;
                }
            }
            if (!flag) return "card with name " + cardName + " does not exist in side deck";
            MainMenuServer.getCurrentUser().getDeckByName(deckName).removeCardFromSide(card);
            MainMenuServer.getCurrentUser().getCards().add(card);
        }
        return "card removed form deck successfully";
    }

    public static void showUsersCards() {
        ArrayList<Card> userCards = MainMenuServer.getCurrentUser().getCards();
        if (userCards != null) {
            Card.sort(userCards);
            Show.showCardArray(userCards);
        }
    }

    public static String menuName() {
        return "Deck Menu";

    }

}