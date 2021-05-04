package view;


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
    public static void showCardArray(ArrayList cards) {
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
