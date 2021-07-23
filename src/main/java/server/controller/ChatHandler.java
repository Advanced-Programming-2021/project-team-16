package server.controller;

import server.MainServer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ChatHandler implements Runnable {


    Scanner scn = new Scanner(System.in);
    private String name;
    final DataInputStream dis;
    final DataOutputStream dos;
    Socket s;
    boolean isloggedin;


    // constructor
    public ChatHandler(Socket s, String name, DataInputStream dis, DataOutputStream dos) {
        this.dis = dis;
        this.dos = dos;
        this.name = name;
        this.s = s;
        isloggedin = true;
    }

    @Override
    public void run() {

        String received;
        try {
            while (true) {
                received = dis.readUTF();

                System.out.println(received);

                if (received.equals("logout")) {
                    this.isloggedin = false;
                    this.s.close();
                    break;
                }
                String client = received.split("@", 2)[1];
                String message = received.split("@", 2)[0];

                for (ChatHandler mc : MainServer.ar) {
                    if (mc.name.equals(client) && mc.isloggedin) {
                        mc.dos.writeUTF(this.name + ": " + message);
                        break;
                    }
                }
            }
            System.out.println("badbakht shodim");

        } catch (Exception e) {

            e.printStackTrace();
        }
        try {
            this.dis.close();
            this.dos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}