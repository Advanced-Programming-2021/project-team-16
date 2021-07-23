package server;

import controller.Login;
import server.controller.ServerLoginController;

import java.io.*;
import java.util.*;
import java.net.*;

public class MainServer {
    // Vector to store active clients
    static Vector<ChatHandler> ar = new Vector<>();

    // counter for clients
    static int i = 0;
    Scanner scn = new Scanner(System.in);
    private String name;
    static DataInputStream dataInputStream;
    static DataOutputStream dataOutputStream;
    static Socket socket;



    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(8888);
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
                                System.out.println("welcomee");

                                String name = dataInputStream.readUTF();

                                System.out.println("hello " + name);
                                ChatHandler mtch = new ChatHandler(socket,name, dataInputStream, dataOutputStream);
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

                            } else if(received.startsWith("register") || received.startsWith("login")){
                                String result = process(received);
                                if (result.equals("")) break;
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
// Java implementation of  Server side
// It contains two classes : Server and ClientHandler
// Save file as Server.java


    // Server class


    //
//    public static void main(String[] args) throws IOException {
//        // server is listening on port 1234
//        ServerSocket ss = new ServerSocket(1234);
//
//        Socket s;
//
//        // running infinite loop for getting
//        // client request
//        while (true) {
//            // Accept the incoming request
//            s = ss.accept();
//
//            System.out.println("New client request received : " + s);
//
//            // obtain input and output streams
//            DataInputStream dis = new DataInputStream(s.getInputStream());
//            DataOutputStream dos = new DataOutputStream(s.getOutputStream());
//            new Thread(() -> {
//            String received;
//            while (true) {
//                try {
//                    // receive the string
//                    received = dis.readUTF();
//
//                    System.out.println(received);
//                } catch (IOException e) {
//
//                    e.printStackTrace();
//                } }
//                }).start();
//                    if (received.equals("show-chatroom")) {
//                        // Create a new handler object for handling this request.
//                        ChatHandler mtch = new ChatHandler(s, "client " + i, dis, dos);
//
//                        // Create a new Thread with this object.
//                        Thread t = new Thread(mtch);
//
//                        System.out.println("Adding this client to active client list");
//
//                        // add this client to active clients list
//                        ar.add(mtch);
//
//                        // start the thread.
//                        t.start();
//                    }
//
//
//                // increment i for new client.
//                // i is used for naming only, and can be replaced
//                // by any naming scheme
//                i++;
//
//            }
//        }
//    }
    static String process(String command) {
        if (command.startsWith("register")) {
            String[] parts = command.split(" ");
            return ServerLoginController.signUp(parts[1], parts[2], parts[3]);
        } else if (command.startsWith("login")) {
            String[] parts = command.split(" ");
            return ServerLoginController.login(parts[1], parts[2]);
        }
        return "";
    }
}
