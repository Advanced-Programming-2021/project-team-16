package view;

import controller.Login;
import server.UpdateStatus;

public class Main {
    public static void main(String[] args) {
        Login.initializeNetwork();
        //UpdateStatus.beforeRun();
        CommandProcessor.login();
        //UpdateStatus.afterRun();
    }
}
