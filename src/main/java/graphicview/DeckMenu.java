package graphicview;

import controller.MainMenu;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import model.Deck;
import model.person.User;

import java.io.IOException;
import java.util.ArrayList;

import static view.Show.showAllDecks;

public class DeckMenu {




    @FXML
    public GridPane decks;
    public Label click;
    public Label activeD;
    public Label otherD;
    public Label error;
    public TextField deckName;
    public Label successful;
    public static void enterMainDeckMenu()  {

        try {
            Parent root = FXMLLoader.load(DeckMenu.class.getResource("/fxml/deckMenu.fxml"));
            LoginMenu.getMainStage().setScene(new Scene(root));
        } catch (IOException ignored) {
        }

    }
    public static void enterMenu()  {

       try {
           Parent root = FXMLLoader.load(DeckMenu.class.getResource("/fxml/deck.fxml"));
           LoginMenu.getMainStage().setScene(new Scene(root));
       } catch (IOException ignored) {
       }

    }
    public void enterMainMenu() throws IOException {
        graphicview.MainMenu.enterMenu();
    }
    public  void showAllDecks() {

        click.setVisible(false);
        activeD.setVisible(true);
        otherD.setVisible(true);
        Label Main,Side;
        Button ActiveDeck, OtherDecks;
        boolean hasActiveDeck = false;
        User user = MainMenu.getCurrentUser();
        ArrayList<Deck> userDecks = user.getDecks();
        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                try {
                    LoginMenu.getMainStage().setScene(new Scene(FXMLLoader.load(LoginMenu.class.getResource("/fxml/info.fxml"))));
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        };

        if (user.getActiveDeck() != null) {
            hasActiveDeck = true;
            Deck activeDeck = user.getActiveDeck();
            ActiveDeck = new Button("  " + activeDeck.getName());
            ActiveDeck.setStyle("-fx-background-color: #ffffff; ");
            ActiveDeck.setOnAction(event);

            ActiveDeck.setOnMouseEntered(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent me) {
                   ActiveDeck.setStyle("-fx-text-fill: blue;"+
                            "-fx-background-color:#ffffff;");

                }
            });

            ActiveDeck.setOnMouseExited(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent me) {

                    ActiveDeck.setStyle("-fx-text-fill: black;" +
                            "-fx-background-color:#ffffff;");
                }
            });


            Main = new Label(String.valueOf(activeDeck.getMainDeckCards().size()));
            Side = new Label(String.valueOf(activeDeck.getSideDeckCards().size()));
        }else{
            ActiveDeck = new Button("         - ");
            Main = new Label(" - ");
            Side = new Label(" - ");
        }
        decks.add(ActiveDeck,0,1);
        decks.add(Main,1,1);
        decks.add(Side,2,1);

        if (userDecks != null && userDecks.size() != 0) {
                Deck.sort(userDecks);
                for (int j = 0; j < userDecks.size(); j++) {
                    if (hasActiveDeck && userDecks.get(j) == user.getActiveDeck()) continue;
                    OtherDecks = new Button("  " + userDecks.get(j).getName());
                    OtherDecks.setStyle("-fx-background-color: #ffffff; ");
                    OtherDecks.setOnAction(event);
                    Button finalOtherDecks = OtherDecks;
                    OtherDecks.setOnMouseEntered(new EventHandler<MouseEvent>() {
                        public void handle(MouseEvent me) {
                            finalOtherDecks.setStyle("-fx-text-fill: blue;"+
                                    "-fx-background-color:#ffffff;");

                        }
                    });
                    OtherDecks.setOnMouseExited(new EventHandler<MouseEvent>() {
                        public void handle(MouseEvent me) {

                            finalOtherDecks.setStyle("-fx-text-fill: black;" +
                                    "-fx-background-color:#ffffff;");
                        }
                    });
                    Main = new Label(String.valueOf(userDecks.get(j).getMainDeckCards().size()));
                    Side = new Label(String.valueOf(userDecks.get(j).getSideDeckCards().size()));
                    decks.add(OtherDecks,0,j+3);
                    decks.add(Main,1,j+3);
                    decks.add(Side,2,j+3);
                }
            }
               if(userDecks != null && userDecks.size() == 0 || userDecks != null && userDecks.size() == 1 && hasActiveDeck) {
                OtherDecks = new Button("         - ");
                Main = new Label(" - ");
                Side = new Label(" - ");
                decks.add(OtherDecks,0,3);
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
