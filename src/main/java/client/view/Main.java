package client.view;

import client.controller.Login;

public class Main {
    public static void main(String[] args) {
       Login.initializeNetwork();
        //UpdateStatus.beforeRun();
        CommandProcessor.login();
       // CommandProcessor.mainMenu();
        //UpdateStatus.afterRun();
    }
}
