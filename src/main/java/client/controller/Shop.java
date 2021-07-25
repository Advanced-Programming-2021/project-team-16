package client.controller;

import client.controller.Login;

import java.io.IOException;

public class Shop {
    public static synchronized String buy(String cardName) {
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
    }


    public static String menuName() {
        return "Shop Menu";
    }
}
