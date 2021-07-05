package graphicview;

import controller.MainMenu;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import model.Deck;
import model.person.User;

import java.io.IOException;
import java.util.ArrayList;

public class DeckMenu {

    private static DeckMenu controllerDeckMenu;


    public GridPane decks;
    public Label click;
    public Label activeD;
    public Label otherD;
    public Label error;
    public TextField deckName;
    public Label successful;
    public Button backButton;
    static final String[] decksName = new String[1];
    public static void enterMainDeckMenu()  {
        try {
            FXMLLoader loader = new FXMLLoader(DeckMenu.class.getResource("/fxml/deckMenu.fxml"));
            Parent root = loader.load();
            LoginMenu.getMainStage().setScene(new Scene(root));
            controllerDeckMenu = loader.getController();
        } catch (IOException ignored) {
        }
    }
    public static void enterMenu()  {
       try {
           FXMLLoader loader = new FXMLLoader(DeckMenu.class.getResource("/fxml/deck.fxml"));
           loader.setController(controllerDeckMenu);
           Parent root = loader.load();
           LoginMenu.getMainStage().setScene(new Scene(root));
           controllerDeckMenu.showAllDecks();
       } catch (IOException ignored) {
       }

    }
    public void enterMainMenu() throws IOException {
        graphicview.MainMenu.enterMenu();
    }
    public  void showAllDecks() {
        backButton.setOnMouseClicked(this::deckBack);
        click.setVisible(false);
        activeD.setVisible(true);
        otherD.setVisible(true);
        Label Main,Side,ActiveDeckL,OtherDecksL;
        Button ActiveDeck, OtherDecks;
        boolean hasActiveDeck = false;
        User user = MainMenu.getCurrentUser();
        ArrayList<Deck> userDecks = user.getDecks();

        if (user.getActiveDeck() != null) {
            hasActiveDeck = true;
            Deck activeDeck = user.getActiveDeck();
            ActiveDeck = new Button("  " + activeDeck.getName());
            ActiveDeck.setStyle("-fx-background-color: #ffffff; ");
            ActiveDeck.setOnAction(e -> {
                try {
                     decksName[0] = ActiveDeck.getText();
                     //EditDeck.makeBtnInvisible();
                    FXMLLoader loader = new FXMLLoader(DeckMenu.class.getResource("/fxml/info.fxml"));
                    Parent root = loader.load();
                    LoginMenu.getMainStage().setScene(new Scene(root));
                    EditDeck.setControllerEditDeck(loader.getController());
                    EditDeck.getControllerEditDeck().loadBoard();

                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            });

            ActiveDeck.setOnMouseEntered(me -> ActiveDeck.setStyle("-fx-text-fill: blue;"+
                     "-fx-background-color:#ffffff;"));

            ActiveDeck.setOnMouseExited(me -> ActiveDeck.setStyle("-fx-text-fill: black;" +
                    "-fx-background-color:#ffffff;"));

            decks.add(ActiveDeck,0,1);
            Main = new Label(String.valueOf(activeDeck.getMainDeckCards().size()));
            Side = new Label(String.valueOf(activeDeck.getSideDeckCards().size()));
        }else{
            ActiveDeckL = new Label("         - ");
            decks.add(ActiveDeckL,0,1);
            Main = new Label(" - ");
            Side = new Label(" - ");
        }

        decks.add(Main,1,1);
        decks.add(Side,2,1);

        if (userDecks != null && userDecks.size() != 0) {
                Deck.sort(userDecks);
                for (int j=0,i=0; j < userDecks.size(); j++,i++) {
                    if (hasActiveDeck && userDecks.get(j) == user.getActiveDeck()) {
                        i--;
                        continue;
                    }
                    OtherDecks = new Button("  " + userDecks.get(j).getName());
                    OtherDecks.setStyle("-fx-background-color: #ffffff; ");
                    Button finalOtherDecks = OtherDecks;
                    OtherDecks.setOnAction(e -> {
                        try {
                            decksName[0] = finalOtherDecks.getText();
                            FXMLLoader loader = new FXMLLoader(DeckMenu.class.getResource("/fxml/info.fxml"));
                            Parent root = loader.load();
                            LoginMenu.getMainStage().setScene(new Scene(root));
                            EditDeck.setControllerEditDeck(loader.getController());
                            EditDeck.getControllerEditDeck().loadBoard();                               // EditDeck.loadBoard();
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    });
                    OtherDecks.setOnMouseEntered(me -> finalOtherDecks.setStyle("-fx-text-fill: blue;"+
                            "-fx-background-color:#ffffff;"));
                    OtherDecks.setOnMouseExited(me -> finalOtherDecks.setStyle("-fx-text-fill: black;" +
                            "-fx-background-color:#ffffff;"));
                    Main = new Label(String.valueOf(userDecks.get(j).getMainDeckCards().size()));
                    Side = new Label(String.valueOf(userDecks.get(j).getSideDeckCards().size()));
                    decks.add(OtherDecks,0,i+3);
                    decks.add(Main,1,i+3);
                    decks.add(Side,2,i+3);
                }
            }
               if(userDecks != null && userDecks.size() == 0 || userDecks != null && userDecks.size() == 1 && hasActiveDeck) {
                OtherDecksL = new Label("         - ");
                Main = new Label(" - ");
                Side = new Label(" - ");
                decks.add(OtherDecksL,0,3);
                decks.add(Main,1,3);
                decks.add(Side,2,3);
            }




    }
 

    public void onAction(ActionEvent actionEvent) {
        enterMenu();
    }


    public void createOnAction(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(DeckMenu.class.getResource("/fxml/newDeck.fxml"));
            LoginMenu.getMainStage().setScene(new Scene(root));
        } catch (IOException ignored) {
        }

    }


    public void deckBack(MouseEvent mouseEvent) {
        enterMainDeckMenu();
    }
    public static String[] getDeckName(){return decksName;}

    public void createBtn(ActionEvent actionEvent) {
        String message = controller.DeckMenu.create(deckName.getText());
        if(message.equals("deck created successfully!")) {
            error.setText("");
            successful.setText(message);
        }
        else {
            successful.setText("");
            error.setText(message);
        }
    }
}
