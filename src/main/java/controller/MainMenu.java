package controller;

import model.person.User;

public class MainMenu {
    private static User currentUser;


    public static void enterMenu(String name) {
        if (name.equals("Login")) {

        }
        if (name.equals("Duel")) {
        }
        if (name.equals("Deck")) {
        }
        if (name.equals("Scoreboard")) {
        }
        if (name.equals("Profile")) {
        }
        if (name.equals("Shop")) {
        }
        if (name.equals("ImportAndExport")) {
        } else {
            System.out.println("invalid command");
        }
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User user) {

    }

    public static String menuName() {
        return "Main Menu";

    }

}