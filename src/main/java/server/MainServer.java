package server;

import server.controller.ChatHandler;
import server.controller.ScoreboardServer;
import server.controller.ServerLoginController;
import server.controller.ShopServer;

import java.io.*;
import java.util.*;
import java.net.*;

public class MainServer {
    // Vector to store active clients
    public static Vector<ChatHandler> ar = new Vector<>();

    // counter for clients
    static int i = 0;
    Scanner scn = new Scanner(System.in);
    private String name;
    static DataInputStream dataInputStream;
    static DataOutputStream dataOutputStream;
    static Socket socket;

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(6789);
            while (true) {
                socket = serverSocket.accept();
                System.out.println("New client request received : " + socket);
                new Thread(() -> {
                    try {
                        dataInputStream = new DataInputStream(socket.getInputStream());
                        dataOutputStream = new DataOutputStream(socket.getOutputStream());
                        System.out.println("Creating a new handler for this client...");
                        while (true) {
                            String received = dataInputStream.readUTF();
                            if (received.startsWith("show-chatroom")) {
                                System.out.println("welcome");
                                String name = dataInputStream.readUTF();

                                System.out.println("hello " + name);
                                ChatHandler mtch = new ChatHandler(socket, name, dataInputStream, dataOutputStream);
                                i++;

                                ar.add(mtch);

                                mtch.run();
                                // Create a new Thread with this object.
//                                Thread t = new Thread(mtch);

                                System.out.println("Adding this client to active client list");

                                // add this client to active clients list

                                // start the thread.
//                                t.start();

                                // increment i for new client.
                                // i is used for naming only, and can be replaced
                                // by any naming scheme

                            } else if (received.startsWith("register") || received.startsWith("login") || received.startsWith("shop buy") || received.startsWith("scoreboard show")) {
                                String result = process(received);
                                if (result.equals("asgharrr")) break;
                                dataOutputStream.writeUTF(result);
                                dataOutputStream.flush();
                            }
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
        } else if (command.startsWith("shop buy")){
            String[] parts = command.split("shop buy ");
            return ShopServer.buy(parts[1]);
        } else if (command.startsWith("scoreboard show")){
            return ScoreboardServer.showScoreboard();
        }
        return "";
    }
}
