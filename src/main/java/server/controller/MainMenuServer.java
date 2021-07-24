package server.controller;

import server.model.User;

public class MainMenuServer {
    private static User currentUser;
    public static User getCurrentUser() {
        return currentUser;
    }
    public static void setCurrentUser(User user) {
        currentUser = user;
    }
}
