package client.view;

import client.controller.Login;
import server.modell.Board;
import server.modell.Deck;
import server.modell.Phase;
import server.controller.GameServer;
import server.controller.MainMenuServer;
import server.modell.Game;
import server.modell.card.Card;
import server.modell.card.monster.Monster;
import server.modell.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;


public class Show {
    public static void showCardsInShop() {
        String result;
        try {
            Login.dataOut.writeUTF("shop show --all");

            //  Login.dataOut.flush();
//                msg = scn.nextLine();
//                Login.dataOut.writeUTF(msg);
//
//                result = Login.dataIn.readUTF();

        } catch (IOException x) {
            x.printStackTrace();
            result = "card show error";
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
        String result;
        try {
            Login.dataOut.writeUTF("card show "+card);
            //  Login.dataOut.flush();
//                msg = scn.nextLine();
//                Login.dataOut.writeUTF(msg);
//
//                result = Login.dataIn.readUTF();

        } catch (IOException x) {
            x.printStackTrace();
            result = "single card show error";
        }
    }

//   public static String showScoreBoard(ArrayList<User> users) {
//       int rate = 1;
//       System.out.println(rate + "-" + users.get(0).toString());
//       for (int i = 2; i <= users.size(); i++) {
//           if (users.get(i - 1).getScore() != users.get(i - 2).getScore())
//               rate = i;
//           System.out.println(rate + "-" + users.get(i - 1).toString());
//       }
//   return "done!";
//   }


    public static void showGraveYard(String opponent) {
        Game game = GameServer.getCurrentGame();
        Board board = opponent == null ? game.getCurrentPlayer().getBoard() : game.getRival().getBoard();
        ArrayList<Card> grave = board.getGrave();
        if (grave.isEmpty()) System.out.println("graveyard is empty");
        else Show.showCardArray(grave);
    }

    public static String showMainDeck(String deckName) {
        String result;
        try {
            Login.dataOut.writeUTF("deck show --deck-name "+ deckName);
            Login.dataOut.flush();
            result = Login.dataIn.readUTF();

        } catch (IOException x) {
            x.printStackTrace();
            result = "show scoreboard error";
        }
        return result;
    }

    public static String showSideDeck(String deckName) {
        String result;
        try {
            Login.dataOut.writeUTF("deck show --deck-name "+ deckName + " --side");
            Login.dataOut.flush();
            result = Login.dataIn.readUTF();

        } catch (IOException x) {
            x.printStackTrace();
            result = "show scoreboard error";
        }
        return result;
    }

    public static void showAllDecks() {
        String result;
        try {
            Login.dataOut.writeUTF("deck show --all");

        } catch (IOException x) {
            x.printStackTrace();
            result = "deck show error";
        }
    }

    public static void showBoard() {
        Game game = GameServer.getCurrentGame();
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
      System.out.println(gameMessage);
    }

    public static void showPhase(Phase phase) {
        String message = "phase:" + phase.getPhaseName();
         System.out.println(message);
    }

    public static void showImportantGameMessage(String importantMessage) {
         System.out.println(importantMessage);
    }

}
