package controller;

import model.person.User;

public class Profile {
    public static String changeNickname(String newNickname) {
        if (MainMenu.getCurrentUser().getNickname().equals(newNickname)) return "this is your current nickname";
        if (User.getUserByNickname(newNickname) != null)
            return "user with nickname " + newNickname + " already exists";
        MainMenu.getCurrentUser().setNickname(newNickname);
        return "nickname changed successfully!";
    }

    public static String changePassword(String currentPassword, String newPassword) {
        User user = MainMenu.getCurrentUser();
        if (!user.getPassword().equals(currentPassword)) return "current password is invalid";
        if (newPassword.equals(currentPassword)) return "please enter a new password";
        if (!User.getPasswordWeakness(newPassword).equals("strong")) return User.getPasswordWeakness(newPassword);
        user.setPassword(newPassword);
        return "password changed successfully!";

    }

    public static String menuName() {
        return "Profile Menu";

    }

}
