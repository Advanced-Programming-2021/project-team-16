package client.controller;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Login {
    public static Socket sock;
    public static DataInputStream dataIn;
    public static DataOutputStream dataOut;

    public static void initializeNetwork()   {
        try {
            sock = new Socket("localhost", 6789);
            dataIn = new DataInputStream(sock.getInputStream());
            dataOut = new DataOutputStream(sock.getOutputStream());
        } catch (IOException x) {
            x.printStackTrace();
        }
    }

    public static synchronized String signUp(String username, String password, String nickname) {
        String result;
        try {
            dataOut.writeUTF("register " + username + " " + password + " " + nickname);
            dataOut.flush();
             result = dataIn.readUTF();

        } catch (IOException x) {
            x.printStackTrace();
            result = "sign up error";
        }
        return result;
    }

    public static String login(String username, String password) {
        String result;
        try {
            dataOut.writeUTF("login " + username + " " + password);
            dataOut.flush();
             result = dataIn.readUTF();
        } catch (IOException x) {
            x.printStackTrace();
            result = " login error";
        }
        return result;
    }

    public static String menuName() {
        return "Login Menu";
    }

}
