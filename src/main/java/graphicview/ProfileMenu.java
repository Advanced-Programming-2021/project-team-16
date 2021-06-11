package graphicview;

import controller.Profile;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.person.User;

import java.io.IOException;

public class ProfileMenu {

    public Label changeNicknameAlert;
    public TextField newNickname;
    public Label username;
    public Rectangle avatar;
    public Label nickname;
    public TextField newPass;
    public TextField oldPass;
    public Label changePassAlert;
    public TextField avatarName;
    public Label changeAvatarAlert;

    public static void enterMenu(){
        try {
            FXMLLoader loader = new FXMLLoader(ProfileMenu.class.getResource("/fxml/profile.fxml"));
            Parent root = loader.load();
            LoginMenu.getMainStage().setScene(new Scene(root));
            ((ProfileMenu)loader.getController()).showProfileInformation();
        } catch (IOException ignored) {}
    }

    private void showProfileInformation() {
        User user = controller.MainMenu.getCurrentUser();
        nickname.setText("nickname : " + user.getNickname());
        username.setText("username : " + user.getUsername());
        avatar.setFill(user.getAvatarRec().getFill());
    }

    public void changeNickname() {
        User user = controller.MainMenu.getCurrentUser();
        String result = Profile.changeNickname(newNickname.getText());
        changeNicknameAlert.setText(result);
        if (result.contains("success")) changeNicknameAlert.setTextFill(Color.GREEN);
        else changeNicknameAlert.setTextFill(Color.RED);
        nickname.setText("nickname : " + user.getNickname());
    }

    public void changePass() {
        String result = Profile.changePassword(oldPass.getText(),newPass.getText());
        changePassAlert.setText(result);
        if (result.contains("success")) changePassAlert.setTextFill(Color.GREEN);
        else changePassAlert.setTextFill(Color.RED);
    }

    public void enterMainMenu() throws IOException {
        MainMenu.enterMenu();
    }

    public void changeAvatar() {
        User user = controller.MainMenu.getCurrentUser();
        String result = Profile.changeAvatar(avatarName.getText());
        changeAvatarAlert.setText(result);
        if (result.contains("success")) changeAvatarAlert.setTextFill(Color.GREEN);
        else changeAvatarAlert.setTextFill(Color.RED);
        avatar.setFill(user.getAvatarRec().getFill());

    }
}
