package view;

import controller.GameMenu;
import model.Board;
import model.Deck;
import model.Game;
import model.Phase;
import model.card.Card;
import model.card.monster.Monster;
import model.card.spell.Spell;
import model.card.trap.Trap;
import model.person.User;

import java.util.ArrayList;


public class Show {
    public static void showCardArray() {
        ArrayList<Card> cards = Card.getCards();
        for (Card card : cards) {
            System.out.println(card.getName() + ":" + card.getDescription());
        }
    }

    public static void showSingleCard(String cardName) {
        Card card = Card.getCardByName(cardName);
        if (card instanceof Monster) {
            Monster monster = (Monster) card;
            System.out.println("Name: " + monster.getName());
            System.out.println("Level: " + monster.getLevel());
            System.out.println("Type: " + monster.getMonsterType());
            System.out.println("ATK: " + monster.getATK());
            System.out.println("DEF" + monster.getDEF());
            System.out.println("Description: " + monster.getDescription());
        }
        if (card instanceof Spell) {
            Spell spell = (Spell) card;
            System.out.println("Name: " + spell.getName());
            System.out.println("Spell");
            System.out.println("Type: " + spell.getSpellType());
            System.out.println("Description: " + spell.getDescription());
        }
        if (card instanceof Trap) {
            Trap trap = (Trap) card;
            System.out.println("Name: " + trap.getName());
            System.out.println("Trap");
            System.out.println("Type: " + trap.getTrapType());
            System.out.println("Description: " + trap.getDescription());
        }
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

    public static void showBoard(Board.CardPosition[][] myBoard, Board.CardPosition[][] rivalBoars) {
        Board board = GameMenu.getCurrentGame().getCurrentPlayer().getBoard();
        String fz = "";
        String monsterCardZone = "";
        String spellAndTrapZone = "";
        int dn = board.getDeck().size();
        int gy = board.getGrave().size();
        if (board.getFieldSpell() == null) {
            fz = "E";
        }
        if (board.getFieldSpell() != null) {
            fz = "O";
        }
        Board boardR = GameMenu.getCurrentGame().getRival().getBoard();
        int dnR = boardR.getDeck().size();
        int gyR = boardR.getGrave().size();
        if (boardR.getFieldSpell() == null) {
            fz = "E";
        }
        if (boardR.getFieldSpell() != null) {
            fz = "O";
        }
        String rivalNickname = GameMenu.getCurrentGame().getRival().getUser().getNickname();
        int rivalLP = GameMenu.getCurrentGame().getRival().getLP();
        System.out.println(rivalNickname + ":" + rivalLP);
        System.out.print("\t");
        for (int i = 0; i < boardR.getHand().length; i++) {
            System.out.println("c\t");
        }
        System.out.println(dnR);
        System.out.print("\t");
        for (int i = 0; i < boardR.getSpellAndTrapZone().length; i++) {
            if (rivalBoars[0][i] == Board.CardPosition.ACTIVATED) {
                spellAndTrapZone = "O";
            }
            if (rivalBoars[0][i] == Board.CardPosition.HIDE_DEF) {
                spellAndTrapZone = "H";
            }
            System.out.println(spellAndTrapZone + "\t");
        }
        System.out.print("\t");
        for (int i = 0; i < boardR.getMonsterZone().length; i++) {
            if (rivalBoars[1][i] == Board.CardPosition.REVEAL_DEF) {
                monsterCardZone = "DO";
            }
            if (rivalBoars[1][i] == Board.CardPosition.HIDE_DEF) {
                monsterCardZone = "DH";
            }
            if (rivalBoars[1][i] == Board.CardPosition.ATK) {
                monsterCardZone = "OO";
            }
            System.out.println(monsterCardZone + "\t");
        }
        System.out.println(gyR + "\t\t\t\t\t\t" + fz + "\n\n");
        System.out.println("--------------------------\n\n");
        System.out.println(fz + "\t\t\t\t\t\t" + gy);
        System.out.print("\t");
        for (int i = 0; i < board.getMonsterZone().length; i++) {
            if (myBoard[0][i] == Board.CardPosition.REVEAL_DEF) {
                monsterCardZone = "DO";
            }
            if (myBoard[0][i] == Board.CardPosition.HIDE_DEF) {
                monsterCardZone = "DH";
            }
            if (myBoard[0][i] == Board.CardPosition.ATK) {
                monsterCardZone = "OO";
            }
            System.out.println(monsterCardZone + "\t");
        }
        System.out.print("\t");
        for (int i = 0; i < board.getSpellAndTrapZone().length; i++) {
            if (myBoard[1][i] == Board.CardPosition.ACTIVATED) {
                spellAndTrapZone = "O";
            }
            if (myBoard[1][i] == Board.CardPosition.HIDE_DEF) {
                spellAndTrapZone = "H";
            }
            System.out.println(spellAndTrapZone + "\t");
        }
        System.out.println("  \t\t\t\t\t\t" + dn);
        System.out.print("\t");
        for (int i = 0; i < board.getHand().length; i++) {
            System.out.println("c\t");
        }
        String playerNickname = GameMenu.getCurrentGame().getCurrentPlayer().getUser().getNickname();
        int playerLP = GameMenu.getCurrentGame().getCurrentPlayer().getLP();
        System.out.println(playerNickname + ":" + playerLP);
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
