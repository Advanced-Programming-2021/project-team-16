package view;

import controller.GameMenu;
import controller.MainMenu;
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
        else if (card instanceof Monster) {
            Monster monster = (Monster) card;
            System.out.println("Name: " + monster.getName());
            System.out.println("Level: " + monster.getLevel());
            System.out.println("Type: " + monster.getMonsterType());
            System.out.println("ATK: " + monster.getATK());
            System.out.println("DEF: " + monster.getDEF());
            System.out.println("Description: " + monster.getDescription());
        } else if (card instanceof Spell) {
            Spell spell = (Spell) card;
            System.out.println("Name: " + spell.getName());
            System.out.println("Spell");
            System.out.println("Type: " + spell.getSpellType());
            System.out.println("Description: " + spell.getDescription());
        } else if (card instanceof Trap) {
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
            if (users.get(i - 1).getScore() != users.get(i - 2).getScore())
                rate = i;
            System.out.println(rate + "-" + users.get(i - 1).toString());
        }
    }


    public static void showGraveYard() {
        Game game = GameMenu.getCurrentGame();
        ArrayList<Card> currentGrave = game.getCurrentPlayer().getBoard().getGrave();
        ArrayList<Card> rival = game.getRival().getBoard().getGrave();
        if (currentGrave.size() == 0) System.out.println("your graveyard is empty");
        else {
            System.out.println("your graveyard:");
            Show.showCardArray(game.getCurrentPlayer().getBoard().getGrave());
        }
        if (rival.size() == 0) System.out.println("rival's graveyard is empty");
        else {
            System.out.println("rival's graveyard:");
            Show.showCardArray(game.getRival().getBoard().getGrave());
        }
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
        User user = MainMenu.getCurrentUser();
        ArrayList<Deck> userDecks = user.getDecks();
        System.out.println("Decks:");
        System.out.println("Active deck:");
        if (user.getActiveDeck() != null) {
            Deck activeDeck = user.getActiveDeck();
            if (activeDeck.isMainDeckValid() && activeDeck.isDeckValid()) validation = "valid";
            else validation = "invalid";
            System.out.println(activeDeck.getName() + ":  main deck " + activeDeck.getMainDeckCards().size()
                    + ", side deck" + activeDeck.getSideDeckCards().size() + ", " + validation);
        }
        System.out.println("Other decks:");
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

    public static void showBoard() {
        Board board = GameMenu.getCurrentGame().getCurrentPlayer().getBoard();
        String fz = "";
        String monsterCardZone = "";
        String spellAndTrapZone = "";
        int dn = board.getDeck().size();
        int gy = board.getGrave().size();
        Board.CardPosition[][] myBoard = board.getCardPositions();
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
        Board.CardPosition[][] rivalBoars = boardR.getCardPositions();
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
            if (boardR.getSpellAndTrapZone()[i] == null) spellAndTrapZone = "E";
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
            if (boardR.getMonsterZone()[i] == null) monsterCardZone = "E";
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
            if (board.getMonsterZone()[i] == null) monsterCardZone = "E";
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
            if (board.getSpellAndTrapZone()[i] == null) spellAndTrapZone = "E";
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
