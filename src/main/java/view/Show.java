package view;

import controller.GameMenu;
import controller.MainMenu;
import model.Board;
import model.Deck;
import model.Game;
import model.Phase;
import model.card.Card;
import model.card.monster.Monster;
import model.person.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;


public class Show {
    public static void showCardsInShop() {
        ArrayList<Card> cards = Card.getCards();
        Card.sort(cards);
        for (Card card : cards) {
            System.out.println(card.getName() + ":" + card.getPrice());
        }
    }

    public static void showCardArray(ArrayList<Card> cards) {
        for (int i = 1; i <= cards.size(); i++) {
            System.out.println(i + ". " + cards.get(i - 1).getName() + ": " + cards.get(i - 1).getDescription());
        }
    }

    public static void showSingleCard(String cardName) {
        Card card = Card.getCardByName(cardName);
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


    public static void showGraveYard(String opponent) {
        Game game = GameMenu.getCurrentGame();
        Board board = opponent == null ? game.getCurrentPlayer().getBoard() : game.getRival().getBoard();
        ArrayList<Card> grave = board.getGrave();
        if (grave.isEmpty()) System.out.println("graveyard is empty");
        else Show.showCardArray(grave);
    }

    public static void showMainDeck(String deckName) {

        User user = MainMenu.getCurrentUser();
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

    }

    public static void showSideDeck(String deckName) {
        User user = MainMenu.getCurrentUser();
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

    }

    public static void showAllDecks() {
        String validation;
        boolean hasActiveDeck = false;
        User user = MainMenu.getCurrentUser();
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
    }

    public static void showBoard() {
        Game game = GameMenu.getCurrentGame();
        if (game.isGraphical()) return;
        Board rivalBoard = game.getRival().getBoard();
        String fieldZone;
        int numOfHand;
        System.out.println("\t\t" + game.getRival().toString());
        System.out.print("\t");
        numOfHand = rivalBoard.getNumberOfHandCards();
        for (int i = 0; i < numOfHand; i++) System.out.print("c\t");
        System.out.println();
        System.out.println(rivalBoard.getDeck().size());
        printSpellZone(rivalBoard, true);
        printMonsterZone(rivalBoard, true);
        fieldZone = rivalBoard.getFieldSpell() == null ? "E" : "O";
        System.out.println(rivalBoard.getGrave().size() + "\t\t\t\t\t\t" + fieldZone + "\n\n");
        System.out.println("--------------------------\n\n");
        Board myBoard = game.getCurrentPlayer().getBoard();
        fieldZone = myBoard.getFieldSpell() == null ? "E" : "O";
        System.out.println(fieldZone + "\t\t\t\t\t\t" + myBoard.getGrave().size());
        printMonsterZone(myBoard, false);
        printSpellZone(myBoard, false);
        System.out.println("  \t\t\t\t\t\t" + myBoard.getDeck().size());
        numOfHand = myBoard.getNumberOfHandCards();
        for (int i = 0; i < numOfHand; i++) System.out.print("c\t");
        System.out.println();
        System.out.println("\t\t" + game.getCurrentPlayer().toString());
    }

    private static void printMonsterZone(Board board, boolean isRival) {
        String monsterCardZone;
        Card[] monsters = getCardsInRightOrder(board.getMonsterZone());
        Board.CardPosition[] positions = getPositionsInOrder(board.getCardPositions()[0]);
        if (isRival) {
            Collections.reverse(Arrays.asList(monsters));
            Collections.reverse(Arrays.asList(positions));
        }
        System.out.print("\t");
        for (int i = 0; i < monsters.length; i++) {
            if (monsters[i] == null) monsterCardZone = "E";
            else if (positions[i] == Board.CardPosition.REVEAL_DEF) monsterCardZone = "DO";
            else if (positions[i] == Board.CardPosition.HIDE_DEF) monsterCardZone = "DH";
            else monsterCardZone = "OO";
            System.out.print(monsterCardZone + "\t");
        }
        System.out.println();
    }

    private static void printSpellZone(Board board, boolean isRival) {
        Card[] spells = getCardsInRightOrder(board.getSpellAndTrapZone());
        Board.CardPosition[] spellPositions = getPositionsInOrder(board.getCardPositions()[1]);
        if (isRival) {
            Collections.reverse(Arrays.asList(spells));
            Collections.reverse(Arrays.asList(spellPositions));
        }
        String spellAndTrapZone;
        System.out.print("\t");
        for (int i = 0; i < spells.length; i++) {
            if (spells[i] == null) spellAndTrapZone = "E";
            else spellAndTrapZone = spellPositions[i] == Board.CardPosition.ACTIVATED ? "O" : "H";
            System.out.print(spellAndTrapZone + "\t");
        }
        System.out.println();
    }

    private static Card[] getCardsInRightOrder(Card[] cards) {
        Card[] cardsInOrder = new Card[5];
        cardsInOrder[0] = cards[4];
        cardsInOrder[1] = cards[2];
        cardsInOrder[2] = cards[0];
        cardsInOrder[3] = cards[1];
        cardsInOrder[4] = cards[3];
        return cardsInOrder;
    }

    private static Board.CardPosition[] getPositionsInOrder(Board.CardPosition[] positions) {
        Board.CardPosition[] positionsInOrder = new Board.CardPosition[5];
        positionsInOrder[0] = positions[4];
        positionsInOrder[1] = positions[2];
        positionsInOrder[2] = positions[0];
        positionsInOrder[3] = positions[1];
        positionsInOrder[4] = positions[3];
        return positionsInOrder;
    }

    public static void showGameMessage(String gameMessage) {
        if (GameMenu.getCurrentGame().isGraphical())
            GameMenu.getCurrentGame().getCurrentPlayer().getGameView().showMessage(gameMessage, false);
        else System.out.println(gameMessage);
    }

    public static void showPhase(Phase phase) {
        String message = "phase:" + phase.getPhaseName();
        if (GameMenu.getCurrentGame().isGraphical())
            GameMenu.getCurrentGame().getCurrentPlayer().getGameView().showMessage(message, true);
        else System.out.println(message);
    }

    public static void showImportantGameMessage(String importantMessage) {
        if (GameMenu.getCurrentGame().isGraphical()) {
            GameMenu.getCurrentGame().getCurrentPlayer().getGameView().showMessage(importantMessage, true);
            GameMenu.getCurrentGame().getRival().getGameView().showMessage(importantMessage, true);
        } else System.out.println(importantMessage);
    }

}
