package controller;

import model.person.User;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Login {
    private static Socket socket;
    private static DataInputStream dataInputStream;
    private static DataOutputStream dataOutputStream;

    public static void initializeNetwork() {
        try {
            socket = new Socket("localhost", 1205);
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
        } catch (IOException x) {
            x.printStackTrace();
        }
    }

    public static synchronized String signUp(String username, String password, String nickname) {
        String result;
        try {
            dataOutputStream.writeUTF("register " + username + " " + password + " " + nickname);
            dataOutputStream.flush();
             result = dataInputStream.readUTF();

        } catch (IOException x) {
            x.printStackTrace();
            result = "sign up error";
        }
        return result;
    }

    public static String login(String username, String password) {
        String result;
        try {
            dataOutputStream.writeUTF("login " + username + " " + password);
            dataOutputStream.flush();
             result = dataInputStream.readUTF();
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
