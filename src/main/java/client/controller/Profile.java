package client.controller;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import server.controller.MainMenuServer;
import server.modell.User;

public class Profile {

    public static String changeUsername(String newUsername) {
        if (newUsername.length() == 0) return "choose a username";
        if (MainMenuServer.getCurrentUser().getUsername().equals(newUsername)) return "this is your current username";
        if (User.getUserByUsername(newUsername) != null)
            return "user with username " + newUsername + " already exists";
        MainMenuServer.getCurrentUser().setUsername(newUsername);
        return "username changed successfully!";
    }

    public static String changeNickname(String newNickname) {
        if (newNickname.length() == 0) return "choose a nickname";
        if (MainMenuServer.getCurrentUser().getNickname().equals(newNickname)) return "this is your current nickname";
        if (User.getUserByNickname(newNickname) != null)
            return "user with nickname " + newNickname + " already exists";
        MainMenuServer.getCurrentUser().setNickname(newNickname);
        return "nickname changed successfully!";
    }

    public static String changePassword(String currentPassword, String newPassword) {
        User user = MainMenuServer.getCurrentUser();
        if (!user.getPassword().equals(currentPassword)) return "current password is invalid";
        if (newPassword.equals(currentPassword)) return "please enter a new password";
        if (!User.getPasswordWeakness(newPassword).equals("strong")) return User.getPasswordWeakness(newPassword);
        user.setPassword(newPassword);
        return "password changed successfully!";

    }

    public static String changeAvatar(String name){
        User user = MainMenuServer.getCurrentUser();
        try {
            String avatarPath = "/png/profile/" + name;
            user.setAvatarPath(avatarPath);
            user.getAvatarRec().setFill(new ImagePattern(new Image(String.valueOf(Profile.class.getResource(
                    avatarPath )))));
            return "your avatar changed successfully";
        }catch (Exception e){
            return "some thing went wrong!";
        }

    }
    public static String menuName() {
        return "Profile Menu";
    }

}
