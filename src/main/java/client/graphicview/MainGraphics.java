package client.graphicview;

import server.view.UpdateStatus;
import javafx.application.Application;

public class MainGraphics {
    public static void main(String[] args) {
        Application.launch(LoginMenu.class,args);
        UpdateStatus.afterRun();
    }
}
