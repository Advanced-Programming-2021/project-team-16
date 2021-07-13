package server.controller;

import controller.MainMenu;
import model.person.User;
import server.model.ServerUser;

public class ServerLoginController {
    public static synchronized String signUp(String username, String password, String nickname) {

        if (username.length() == 0) return "enter a username";
        if (ServerUser.getUserByUsername(username) != null)
            return "User with username " + username + " already exists";
        if (ServerUser.getUserByNickname(nickname) != null)
            return "User with nickname " + nickname + " already exists";
        if (!ServerUser.getPasswordWeakness(password).equals("strong")) return User.getPasswordWeakness(password);
        new ServerUser(username, password, nickname);
        return "User created successfully!";
    }
    public static String login(String username, String password) {
        ServerUser user = ServerUser.getUserByUsername(username);
        if (user == null || !user.getPassword().equals(password)) return "Username and password didn’t match!";
        //MainMenu.setCurrentUser(user);
        return "user logged in successfully!";

    }
}
