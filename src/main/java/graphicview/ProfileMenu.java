package graphicview;

import controller.Profile;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import server.model.User;

import java.io.IOException;

public class ProfileMenu {

    public TextField newNickname;
    public Label username;
    public Rectangle avatar;
    public Label nickname;
    public TextField newPass;
    public TextField oldPass;
    public TextField avatarName;
    public Label changeAlert;
    public Label changeAvatarExplanation;

    public static void enterMenu(){
        try {
            FXMLLoader loader = new FXMLLoader(ProfileMenu.class.getResource("/fxml/profile.fxml"));
            Parent root = loader.load();
            ((GridPane) root).setBackground(GraphicUtils.getBackground("/png/texture/profile.png"));
            LoginMenu.getMainStage().setScene(new Scene(root));
            ((ProfileMenu)loader.getController()).showProfileInformation();
        } catch (IOException ignored) {}
    }

    private void showProfileInformation() {
        changeAvatarExplanation.setText("Put your picture in:\n resource/png/profile");
        User user = controller.MainMenu.getCurrentUser();
        nickname.setText("nickname : " + user.getNickname());
        username.setText("username : " + user.getUsername());
        avatar.setFill(user.getAvatarRec().getFill());
    }

    public void changeNickname() {
        User user = controller.MainMenu.getCurrentUser();
        String result = Profile.changeNickname(newNickname.getText());
        changeAlert.setText(result);
        if (result.contains("success")) changeAlert.setTextFill(Color.DARKGREEN);
        else changeAlert.setTextFill(Color.DARKRED);
        nickname.setText("nickname : " + user.getNickname());
    }

    public void changePass() {
        String result = Profile.changePassword(oldPass.getText(),newPass.getText());
        changeAlert.setText(result);
        if (result.contains("success")) changeAlert.setTextFill(Color.DARKGREEN);
        else changeAlert.setTextFill(Color.DARKRED);
    }

    public void changeAvatar() {
        User user = controller.MainMenu.getCurrentUser();
        String result = Profile.changeAvatar(avatarName.getText());
        changeAlert.setText(result);
        if (result.contains("success")) changeAlert.setTextFill(Color.DARKGREEN);
        else changeAlert.setTextFill(Color.DARKRED);
        avatar.setFill(user.getAvatarRec().getFill());

    }

    public void enterMainMenu() throws IOException {
        MainMenu.enterMenu();
    }
}
