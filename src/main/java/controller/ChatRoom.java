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
            result = "chatroom error";
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
        System.out.println("enter your group chat name:");
        Thread readMessage = new Thread(new Runnable() {
            @Override
            public void run() {

                while (true) {
                    try {
                        // read the message sent to this client
                       // System.out.println("Waiting");
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

//user create --username username2 --nickname nickname2 --password 1234asdf2
//user login --username username2 --password 1234asdf2


//shop buy SupplySquad
//shop buy SupplySquad
//shop buy SupplySquad
//shop buy CommandKnight
//shop buy CommandKnight
//shop buy CommandKnight
//shop buy Terratiger
//shop buy Terratiger
//shop buy Terratiger
//shop buy Man-EaterBug
//shop buy Man-EaterBug
//shop buy Man-EaterBug
//shop buy MirageDragon
//shop buy MirageDragon
//shop buy MirageDragon
//shop buy BeastKingBarbaros
//shop buy BeastKingBarbaros
//shop buy BeastKingBarbaros
//shop buy TheCalculator
//shop buy TheCalculator
//shop buy TheCalculator
//shop buy GateGuardian
//shop buy GateGuardian
//shop buy GateGuardian
//shop buy Marshmallon
//shop buy Marshmallon
//shop buy Marshmallon
//shop buy Texchanger
//shop buy Texchanger
//shop buy Texchanger
//shop buy TimeSeal
//shop buy TimeSeal
//shop buy TimeSeal
//shop buy NegateAttack
//shop buy NegateAttack
//shop buy NegateAttack
//shop buy TrapHole
//shop buy TrapHole
//shop buy TrapHole
//shop buy Scanner
//shop buy MindCrush
//shop buy MagicCylinder
//shop buy MagicCylinder
//shop buy MagicCylinder
//shop buy MirrorForce
//shop buy MirrorForce
//shop buy MirrorForce






//deck add-card --card SupplySquad --deck ali
//deck add-card --card SupplySquad --deck ali
//deck add-card --card SupplySquad --deck ali
//deck add-card --card CommandKnight --deck ali
//deck add-card --card CommandKnight --deck ali
//deck add-card --card CommandKnight --deck ali
//deck add-card --card Man-EaterBug --deck ali
//deck add-card --card Man-EaterBug --deck ali
//deck add-card --card Man-EaterBug --deck ali
//deck add-card --card MirageDragon --deck ali
//deck add-card --card MirageDragon --deck ali
//deck add-card --card MirageDragon --deck ali
//deck add-card --card BeastKingBarbaros --deck ali
//deck add-card --card BeastKingBarbaros --deck ali
//deck add-card --card BeastKingBarbaros --deck ali
//deck add-card --card TheCalculator --deck ali
//deck add-card --card TheCalculator --deck ali
//deck add-card --card TheCalculator --deck ali
//deck add-card --card GateGuardian --deck ali
//deck add-card --card GateGuardian --deck ali
//deck add-card --card GateGuardian --deck ali
//deck add-card --card Marshmallon --deck ali
//deck add-card --card Marshmallon --deck ali
//deck add-card --card Marshmallon --deck ali
//deck add-card --card Texchanger --deck ali
//deck add-card --card Texchanger --deck ali
//deck add-card --card Texchanger --deck ali
//deck add-card --card TimeSeal --deck ali
//deck add-card --card TimeSeal --deck ali
//deck add-card --card TimeSeal --deck ali
//deck add-card --card NegateAttack --deck ali
//deck add-card --card NegateAttack --deck ali
//deck add-card --card NegateAttack --deck ali
//deck add-card --card TrapHole --deck ali
//deck add-card --card TrapHole --deck ali
//deck add-card --card TrapHole --deck ali
//deck add-card --card Scanner --deck ali
//deck add-card --card MindCrush --deck ali
//deck add-card --card Terratiger --deck ali
//deck add-card --card Terratiger --deck ali
//deck add-card --card Terratiger --deck ali
//deck add-card --card MirrorForce --deck ali
//deck add-card --card MirrorForce --deck ali
//deck add-card --card MirrorForce --deck ali
//deck add-card --card MagicCylinder --deck ali
//deck add-card --card MagicCylinder --deck ali
//deck add-card --card MagicCylinder --deck ali




//deck add-card --card MirageDragon --deck mmad
//deck add-card --card MirageDragon --deck mmad
//deck add-card --card MirageDragon --deck mmad
//deck add-card --card BeastKingBarbaros --deck mmad
//deck add-card --card BeastKingBarbaros --deck mmad
//deck add-card --card BeastKingBarbaros --deck mmad
//deck add-card --card TheCalculator --deck mmad
//deck add-card --card TheCalculator --deck mmad
//deck add-card --card TheCalculator --deck mmad
//deck add-card --card GateGuardian --deck mmad
//deck add-card --card GateGuardian --deck mmad
//deck add-card --card GateGuardian --deck mmad