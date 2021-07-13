package controller;

import server.model.User;

public class MainMenu {
    private static User currentUser;


    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User user) {
            currentUser = user;
    }

    public static String menuName() {
        return "Main Menu";

    }

}

