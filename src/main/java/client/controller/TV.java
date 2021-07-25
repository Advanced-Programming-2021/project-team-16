package client.controller;

import java.io.IOException;

public class TV {
    public static String TV() {
        String result;
        try {
            Login.dataOut.writeUTF("show TV");
            Login.dataOut.flush();
            result = Login.dataIn.readUTF();

        } catch (
                IOException x) {
            x.printStackTrace();
            result = "TV error";
        }
        return result;
    }

    public static String menuName() {
        return "TV";
    }
}