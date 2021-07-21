package controller;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;
import java.io.IOException;

public class ChatRoom {

// Java implementation for multithreaded chat client
// Save file as Client.java

    // final static int ServerPort = 1234;

        public static void showChatRoom ()
        {
            String msg;
            Scanner scn = new Scanner(System.in);
            try {
                Login.sock = new Socket("localhost", 8888);
                Login.dataIn = new DataInputStream(Login.sock.getInputStream());
                Login.dataOut = new DataOutputStream(Login.sock.getOutputStream());
            } catch (IOException x) {
                x.printStackTrace();
            }

            // getting localhost ip
//            InetAddress ip = InetAddress.getByName("localhost");
//
//            // establish the connection
//            Socket s = new Socket(ip, ServerPort);
//
//            // obtaining input and out streams
//            DataInputStream dis = new DataInputStream(s.getInputStream());
//            DataOutputStream dos = new DataOutputStream(s.getOutputStream());

            // sendMessage thread
            Thread sendMessage = new Thread(new Runnable()
            {
                @Override
                public void run() {
                    while (true) {

                        // read the message to deliver.
                        String msg = scn.nextLine();

                        try {
                            // write on the output stream
                            Login.dataOut.writeUTF(msg);
                            System.out.println(msg);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });


            // readMessage thread
            Thread readMessage = new Thread(new Runnable()
            {
                @Override
                public void run() {

                    while (true) {
                        try {
                            // read the message sent to this client
                            String msg = Login.dataIn.readUTF();
                            System.out.println(msg);
                        } catch (IOException e) {

                            e.printStackTrace();
                        }
                    }
                }
            });

            sendMessage.start();
            readMessage.start();


        }

    public static String menuName() {
        return "ChatRoom Menu";
    }
}
//user create --username username --nickname nickname --password 1234asdf
//user login --username username --password 1234asdf
//menu enter ChatRoom
//show-chatroom