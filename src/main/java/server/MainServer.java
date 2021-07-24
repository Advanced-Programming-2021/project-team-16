package server;

import controller.DeckMenu;
import controller.GameMenu;
import server.controller.*;
import server.modell.User;
import server.view.ShowServer;
import view.Show;

import java.io.*;
import java.util.*;
import java.net.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

                            } else if (received.startsWith("register") || received.startsWith("login") || received.startsWith("shop buy") || received.startsWith("scoreboard show") || received.startsWith("duel --new") || received.startsWith("deck create") || received.startsWith("deck add-card") || received.startsWith("deck set-activate") || received.startsWith("deck show --all")  ) {
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
  //      Matcher matcher;
        HashMap<String, String> data;
        if (command.startsWith("register")) {
            String[] parts = command.split(" ");
            return ServerLoginController.signUp(parts[1], parts[2], parts[3]);
        } else if (command.startsWith("login")) {
            String[] parts = command.split(" ");
            return ServerLoginController.login(parts[1], parts[2]);
        } else if (command.startsWith("shop buy")) {
            String[] parts = command.split("shop buy ");
            return ShopServer.buy(parts[1]);
        } else if (command.startsWith("scoreboard show")) {
            return ScoreboardServer.showScoreboard();
        } else if (command.startsWith("duel --new")) {
            data = getData(command);
            User secondUser = User.getUserByUsername(data.get("second-player"));
            String error = GameServer.isDuelPossibleWithError(data.get("rounds"), secondUser, false);
            if (error != null) System.out.println(error);
            else {
                System.out.println("duel started successfully");
                //     String[] parts = command.split(" ");
                return GameServer.duel(secondUser, Integer.parseInt(data.get("rounds")));
            }
        } else if (command.startsWith("deck create")){
            String[] parts = command.split(" ");
            return DeckMenuServer.create(parts[2]);
        }
        else if (command.startsWith("deck add-card")){
            data = getData(command);
           return DeckMenuServer.addCardToDeck(data.get("card"), data.get("deck"), true);
        } else if (command.startsWith("deck set-activate")){
            String[] parts = command.split(" ");
            return DeckMenuServer.activate(parts[2]);
        }  else if (command.startsWith("deck show --all")) {
            return ShowServer.showAllDecks();
        }
        return "";
    }

    private static HashMap<String, String> getData(String command) {
        HashMap<String, String> data = new HashMap<>();
        Matcher matcher = Pattern.compile("--(\\S+) ([^\\-]+)").matcher(command);
        while (matcher.find()) data.put(matcher.group(1).trim(), matcher.group(2).trim());
        return data;
    }
}
