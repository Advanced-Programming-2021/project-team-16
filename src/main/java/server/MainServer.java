package server;

import server.controller.ServerLoginController;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MainServer {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(1205);
            while (true) {
                Socket socket = serverSocket.accept();
                new Thread(() -> {
                    try {
                        DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
                        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                        while (true) {
                            String input = dataInputStream.readUTF();
                            String result = process(input);
                            if (result.equals("")) break;
                            dataOutputStream.writeUTF(result);
                            dataOutputStream.flush();
                        }
                        dataInputStream.close();
                        socket.close();
                        serverSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static String process(String command) {
        if (command.startsWith("register")) {
            String[] parts = command.split(" ");
           return ServerLoginController.signUp(parts[1], parts[2], parts[3]);
        } else if (command.startsWith("login")) {
            String[] parts = command.split(" ");
            return ServerLoginController.login(parts[1], parts[2]);
        } else if (command.startsWith("multiply")) {
            String[] parts = command.split(" ");
            //return String.valueOf(ServerController.multiplyByTwo(Integer.parseInt(parts[1])));
        }
        return "";
    }
}
