package view;

import controller.UpdateStatus;

public class Main {
    public static void main(String[] args) {

        UpdateStatus.beforeRun();
        CommandProcessor.login();
        UpdateStatus.afterRun();
    }
}
