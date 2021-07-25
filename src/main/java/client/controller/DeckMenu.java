package client.controller;


import java.io.IOException;


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
    }

    public static String activate(String name) {
        String result;
        try {
            Login.dataOut.writeUTF("deck set-activate " + name);
            Login.dataOut.flush();
            result = Login.dataIn.readUTF();

        } catch (IOException x) {
            x.printStackTrace();
            result = "active deck error";
        }
        return result;

    }

    public static String addCardToDeck(String cardName, String deckName, String isMainOrNot) {

        String result;
        try {
            Login.dataOut.writeUTF("deck add-card Main --card " + cardName + " --deck " + deckName +"|" +"deck add-card Side --card " + cardName + " --deck " + deckName);
            Login.dataOut.flush();
            result = Login.dataIn.readUTF();

        } catch (IOException x) {
            x.printStackTrace();
            result = "add card to deck error";
        }
        return result;
    }


    public static String removeCardFromDeck(String cardName, String deckName, String isMainOrNot) {
        String result;
        try {
            Login.dataOut.writeUTF("deck rm-card --card " + cardName + " --deck "+ deckName + " " + isMainOrNot);
            Login.dataOut.flush();
            result = Login.dataIn.readUTF();

        } catch (IOException x) {
            x.printStackTrace();
            result = "remove card from deck error";
        }
        return result;

    }

    public static String showUsersCards() {
        String result;
        try {
            Login.dataOut.writeUTF("deck show --cards");
            Login.dataOut.flush();
            result = Login.dataIn.readUTF();

        } catch (IOException x) {
            x.printStackTrace();
            result = "deck show error";
        }
        return result;
    }

    public static String menuName() {
        return "Deck Menu";

    }

}