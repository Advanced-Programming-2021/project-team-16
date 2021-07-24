package controller;

import java.io.IOException;

public class Shop {
    public static String buy(String cardName) {
        String result;
        try {
            Login.dataOut.writeUTF("shop buy " + cardName);
            Login.dataOut.flush();
            result = Login.dataIn.readUTF();

        } catch (IOException x) {
            x.printStackTrace();
            result = "card shopping error";
        }
        return result;
     //  Card card = Card.make(cardName);
     //  if (card == null) return "there is no card with this name";
     //  if (MainMenu.getCurrentUser().getMoney() < card.getPrice()) return "not enough money";
     //  MainMenu.getCurrentUser().decreaseMoney(card.getPrice());
     //  MainMenu.getCurrentUser().addCard(card);
     //  return "shopping is successfully!";
    }


    public static String menuName() {
        return "Shop Menu";
    }
}
