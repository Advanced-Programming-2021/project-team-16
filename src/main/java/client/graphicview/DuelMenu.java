package client.graphicview;

import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

public class DuelMenu {
    public TextField secondPlayerUsername;
    public ChoiceBox<String> rounds ;
    public ChoiceBox<String> roundsWithAI;
    public Label duelError;
    public Label aiDuelError;

    public static void  enterMenu(){
        try {
            FXMLLoader loader = new FXMLLoader(DuelMenu.class.getResource("/fxml/duel.fxml"));
            Parent root = loader.load();
            LoginMenu.getMainStage().setScene(new Scene(root));
            ((DuelMenu)loader.getController()).arrangeChoiceBox();
        } catch (IOException ignored) {}
    }

    private void arrangeChoiceBox(){
        String[] possibleRounds = { "1","3" };
        rounds.setItems(FXCollections.observableArrayList(possibleRounds));
        roundsWithAI.setItems(FXCollections.observableArrayList(possibleRounds));
    }

 // public void duel() {
 //     String error = GameMenu.isDuelPossibleWithError(String.valueOf(rounds.getValue())
 //             , User.getUserByUsername(secondPlayerUsername.getText()),false);
 //     if (error != null) duelError.setText(error);
 //     else GameView.startGame(Integer.parseInt(String.valueOf(rounds.getValue()))
 //             , User.getUserByUsername(secondPlayerUsername.getText()));
 // }

//    public void duelWithAI() {
//        String error = GameMenu.isDuelPossibleWithError(String.valueOf(roundsWithAI.getValue()), null,true);
//        if (error != null) aiDuelError.setText(error);
////        else GameView.startGame(Integer.parseInt(String.valueOf(roundsWithAI.getValue())), null);
//    }

    public void enterMainMenu() throws IOException {
        MainMenu.enterMenu();
    }
}
