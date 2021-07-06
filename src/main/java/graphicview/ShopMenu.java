package graphicview;

import controller.MainMenu;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Game;
import model.card.Card;
import model.person.User;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class ShopMenu implements Initializable {
    private static Stage stage;
    @FXML
    private Label notif;
    @FXML
    private Label cardName;
    @FXML
    private Label cardPrice;
    @FXML
    private ImageView cardImage;
    @FXML
    private VBox chosenCard;
    @FXML
    private ScrollPane scroll;
    @FXML
    private GridPane grid;
    public static final String CURRENCY = "$";
    private Image image;
    private MyListener myListener;

    public ShopMenu() {
    }

    public void backOnAction(ActionEvent actionEvent) throws IOException {
        this.changeScene("/fxml/main.fxml");
    }

    public static void enterMenu() throws IOException {
        FXMLLoader loader = new FXMLLoader(ProfileMenu.class.getResource("/fxml/shop.fxml"));
        Parent root = loader.load();
        stage = LoginMenu.getMainStage();
        stage.setScene(new Scene(root));
        stage.getScene().setOnKeyPressed(keyEvent -> {
            if (Game.CHEAT_KEYS.match(keyEvent)) GameView.openCheatPopup(stage, null);
        });
    }

    public void changeScene(String fxml) throws IOException {
        Parent pane = FXMLLoader.load(getClass().getResource(fxml));
        stage.getScene().setRoot(pane);
    }
    public void cardListMenu(ActionEvent event) throws IOException {
        this.changeScene("/fxml/cardList.fxml");
    }

    public void backToShopMenu(ActionEvent event) throws IOException {
        this.changeScene("/fxml/shop.fxml");
    }

    ////////////////////////////////////////////////////////////////////////
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    cards.addAll(getData());

        if(cards.size()>0){
            setChosenCard(cards.get(0));
            myListener=new MyListener() {
                @Override
                public void onClickListener(CardsOfShop card) {
                    setChosenCard(card);
                }
            };
        }
        int column = 0;
        int row = 0;
        try {
            for (int i = 0; i < cards.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/fxml/item.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                ItemShop itemShop = fxmlLoader.getController();
                itemShop.setData(cards.get(i),myListener);

                if (column == 3) {
                    column = 0;
                    row++;
                }
                grid.add(anchorPane, column++, row);
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_COMPUTED_SIZE);

                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_COMPUTED_SIZE);

                GridPane.setMargin(anchorPane, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void setChosenCard(CardsOfShop card) {
        cardName.setText(card.getName());
        cardPrice.setText(CURRENCY + card.getPrice());
        image = new Image(getClass().getResourceAsStream(card.getImgSrc()));///////////////////
        cardImage.setImage(image);
    }
private List<CardsOfShop> cards=new ArrayList<>();
    CardsOfShop card;
   private List<CardsOfShop> getData(){
       List<CardsOfShop> cards =new ArrayList<>();

     //  for (int i = 0; i < 20; i++) {
           card =new CardsOfShop();
           card.setName("Gate Guardian");
           card.setPrice(20000);
           card.setImgSrc("/png/card/Gate Guardian.jpg");
           cards.add(card);

            card =new CardsOfShop();
            card.setName("CallOfTheHaunted");
            card.setPrice(3500);
            card.setImgSrc("/png/card/CallOfTheHaunted.jpg");
            cards.add(card);

            card =new CardsOfShop();
            card.setName("Dark Hole");
            card.setPrice(2500);
            card.setImgSrc("/png/card/CallOfTheHaunted.jpg");
            cards.add(card);

            card =new CardsOfShop();
            card.setName("Gate Guardian");
            card.setPrice(20000);
            card.setImgSrc("/png/card/Gate Guardian.jpg");
            cards.add(card);

            card =new CardsOfShop();
            card.setName("CallOfTheHaunted");
            card.setPrice(3500);
            card.setImgSrc("/png/card/CallOfTheHaunted.jpg");
            cards.add(card);

            card =new CardsOfShop();
            card.setName("Dark Hole");
            card.setPrice(2500);
            card.setImgSrc("/png/card/Dark Hole.jpg");
            cards.add(card);
     //  }
return cards;
   }
    public void buyCard(ActionEvent actionEvent) {

        if (MainMenu.getCurrentUser().getMoney() < card.getPrice()) notif.setText("You don't have enough money!");
        MainMenu.getCurrentUser().decreaseMoney((int) card.getPrice());
      //MainMenu.getCurrentUser().addCard((CardsOfShop) card);
         notif.setText("successful!");
    }
}
