package controller;

import model.person.User;

public class Login {
    public static String signUp(String username, String password, String nickname) {
        if (username.length() == 0) return "enter a username";
        if (User.getUserByUsername(username) != null)
            return "user with username " + username + " already exists";
        if (User.getUserByNickname(nickname) != null)
            return "user with nickname " + nickname + " already exists";
        if (!User.getPasswordWeakness(password).equals("strong")) return User.getPasswordWeakness(password);
        new User(username, password, nickname);
        return "user created successfully!";
    }

    public static String login(String username, String password) {
        User user = User.getUserByUsername(username);
        if (user == null || !user.getPassword().equals(password)) return "Username and password didn’t match!";
        MainMenu.setCurrentUser(user);
        return "user logged in successfully!";

    }

    public static String menuName() {
        return "Login Menu";
    }

}
