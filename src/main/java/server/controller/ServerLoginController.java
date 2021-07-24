package server.controller;


import server.modell.User;

public class ServerLoginController {
    public static synchronized String signUp(String username,String password, String nickname) {

        if (username.length() == 0) return "enter a username";
        if (User.getUserByUsername(username) != null)
            return "User with username " + username + " already exists";
        if (User.getUserByNickname(nickname) != null)
            return "User with nickname " + nickname + " already exists";
        if (!User.getPasswordWeakness(password).equals("strong")) return User.getPasswordWeakness(password);
        new User(username, password, nickname);
        return "User created successfully!";
    }
    public static String login(String username, String password) {
        User user = User.getUserByUsername(username);
        if (user == null || !user.getPassword().equals(password)) return "Username and password didn’t match!";
        MainMenuServer.setCurrentUser(user);
        return "user logged in successfully!";

    }
}
