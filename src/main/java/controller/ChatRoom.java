package controller;

import java.util.Scanner;
import java.io.IOException;

public class ChatRoom {

    public static void showChatRoom() {
        String msg;
        Scanner scn = new Scanner(System.in);
        String result;
        try {
            Login.dataOut.writeUTF("show-chatroom");
            //  Login.dataOut.flush();
//                msg = scn.nextLine();
//                Login.dataOut.writeUTF(msg);
//
//                result = Login.dataIn.readUTF();

        } catch (IOException x) {
            x.printStackTrace();
            result = "sign up error";
        }
//            System.out.println(result);

        // sendMessage thread
        Thread sendMessage = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {

                    // read the message to deliver.
                    String msg = scn.nextLine();

                    try {
                        // write on the output stream
                        Login.dataOut.writeUTF(msg);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        // readMessage thread
        Thread readMessage = new Thread(new Runnable() {
            @Override
            public void run() {

                while (true) {
                    try {
                        // read the message sent to this client
                        System.out.println("Waiting");
                        String msg = Login.dataIn.readUTF();
                        System.out.println(msg);
                    } catch (IOException e) {

                        e.printStackTrace();
                    }
                }
            }
        });

        readMessage.start();
        sendMessage.run();
    }
    public static String menuName() {
        return "ChatRoom Menu";
    }
}
//user create --username username --nickname nickname --password 1234asdf
//user login --username username --password 1234asdf
//menu enter ChatRoom
//show-chatroom