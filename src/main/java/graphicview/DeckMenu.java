package graphicview;

import controller.MainMenu;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
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
    public static void enterMenu()  {

       try {
           Parent root = FXMLLoader.load(DeckMenu.class.getResource("/fxml/deck.fxml"));
           LoginMenu.getMainStage().setScene(new Scene(root));
        //decksPane = decks;

           DeckMenu DM = new DeckMenu();
           System.out.println(DM.decks);
          // DM.showAllDecks();
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
        Label ActiveDeck, OtherDecks,Main,Side;
        boolean hasActiveDeck = false;
        User user = MainMenu.getCurrentUser();
        ArrayList<Deck> userDecks = user.getDecks();
        if (user.getActiveDeck() != null) {
            hasActiveDeck = true;
            Deck activeDeck = user.getActiveDeck();
            ActiveDeck = new Label("  " + activeDeck.getName());
            Main = new Label(String.valueOf(activeDeck.getMainDeckCards().size()));
            Side = new Label(String.valueOf(activeDeck.getSideDeckCards().size()));
            decks.add(ActiveDeck,0,1);
            decks.add(Main,1,1);
            decks.add(Side,2,1);
        }else{
            ActiveDeck = new Label("         - ");
            Main = new Label(" - ");
            Side = new Label(" - ");
            decks.add(ActiveDeck,0,1);
            decks.add(Main,1,1);
            decks.add(Side,2,1);
        }

            if (userDecks != null && userDecks.size() != 0) {
                Deck.sort(userDecks);
                for (int j = 0; j < userDecks.size(); j++) {
                    if (hasActiveDeck && userDecks.get(j) == user.getActiveDeck()) continue;
                    OtherDecks = new Label("  " + userDecks.get(j).getName());
                    Main = new Label(String.valueOf(userDecks.get(j).getMainDeckCards().size()));
                    Side = new Label(String.valueOf(userDecks.get(j).getSideDeckCards().size()));
                    decks.add(OtherDecks,0,j+3);
                    decks.add(Main,1,j+3);
                    decks.add(Side,2,j+3);
                }
            }
               if(userDecks != null && userDecks.size() == 0 || userDecks != null && userDecks.size() == 1 && hasActiveDeck) {
                OtherDecks = new Label("         - ");
                Main = new Label(" - ");
                Side = new Label(" - ");
                decks.add(OtherDecks,0,3);
                decks.add(Main,1,3);
                decks.add(Side,2,3);
            }





    }
}
