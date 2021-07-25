package client.controller;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import server.controller.MainMenuServer;
import server.modell.User;

import java.io.IOException;

public class Profile {
 public static String showProfile(){
     String result;
     try {
         Login.dataOut.writeUTF("menu enter Profile");
         Login.dataOut.flush();
         result = Login.dataIn.readUTF();

     } catch (IOException x) {
         x.printStackTrace();
         result = "show profile error";
     }
     return result;
 }
    public static String changeUsername(String newUsername) {
        String result;
        try {
            Login.dataOut.writeUTF("profile change --username " + newUsername);
            Login.dataOut.flush();
            result = Login.dataIn.readUTF();

        } catch (IOException x) {
            x.printStackTrace();
            result = "change username error";
        }
        return result;
    }

    public static String changeNickname(String newNickname) {
        String result;
        try {
            Login.dataOut.writeUTF("profile change --nickname " + newNickname);
            Login.dataOut.flush();
            result = Login.dataIn.readUTF();

        } catch (IOException x) {
            x.printStackTrace();
            result = "change nickname error";
        }
        return result;
    }

    public static String changePassword(String currentPassword, String newPassword) {
        String result;
        try {
            Login.dataOut.writeUTF("profile change --password --current " + currentPassword + " --new " + newPassword);
            Login.dataOut.flush();
            result = Login.dataIn.readUTF();

        } catch (IOException x) {
            x.printStackTrace();
            result = "change password error";
        }
        return result;

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
