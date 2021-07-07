package graphicview;

import controller.MainMenu;
import controller.Shop;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Game;
import model.card.Card;
import model.person.User;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class ShopMenu  {


    private static ShopMenu controllerShopMenu;
    User user = MainMenu.getCurrentUser();
    public Rectangle selectedCard;
    private Card selected;
    public Label selectedCardDescription;
    public Label errorTxt;
    public GridPane shopCards;
    public GridPane userCardsPane;
    public Button buyBtn;
    public Button showCardListBtn;
    public Label error;
    public Label success;
    public Rectangle selectedCard1;
    public Label selectedCardDescription1;
    public Label price;
    public Label urMoney;
    public Button backBtn1;
    //cards count
    //TODO: اول بیا همه اسما رو از آپدیت استاتوس براشون اینجا مثل مثالا براشون شمارنده بزار
    int battleOXCount = 0;
    int axeRaiderCount = 0;







    public static void enterMenu()  {
        try {
            FXMLLoader loader = new FXMLLoader(DeckMenu.class.getResource("/fxml/shop.fxml"));
            //loader.setController(controllerShopMenu);
            Parent root = loader.load();
            ((GridPane) root).setBackground(GraphicUtils.getBackground("/png/texture/GUI_T_Detail_ComboBase01.dds14.png"));
            LoginMenu.getMainStage().setScene(new Scene(root));
            controllerShopMenu = loader.getController();
            //ShopMenu.setControllerShop(loader.getController());
            controllerShopMenu.loadBoard();
        } catch (IOException ignored) {
        }
    }
    public static ShopMenu getControllerShop() {
        return controllerShopMenu;
    }
    public static void setControllerShop (ShopMenu controllerShopMenu) {
         ShopMenu.controllerShopMenu = controllerShopMenu;
    }
    public void loadUserCards() {
        Rectangle cards;
        int row = 0;
        int column = 0;
        backBtn1.setOnMouseClicked(this::enterMenu1);
        for (Card card : user.getCards()) {
            cards = card;
            cards.setHeight(90);
            cards.setWidth(65);
            Rectangle finalCards = cards;

            cards.setOnMouseClicked(me -> {
                selectedCard1.setFill(finalCards.getFill());
                selected = card;
                selectedCardDescription1.setText(selected.getCardProperties());


            });
            cards.setOnMouseEntered(me -> glowCardEffect(finalCards));
            cards.setOnMouseExited(me -> undoGlowEffect(finalCards));
            card.setSizes(false);
            cards.setFill(card.getRectangle().getFill());
            userCardsPane.add(cards, row, column);
            row++;
            if (row == 10) {
                row = 0;
                column++;
            }

        }

    }
    public void loadBoard() {
        Rectangle cards;
       int row = 0;
       int column = 0;
        urMoney.setText(String.valueOf(user.getMoney()));
        for (Card card : Card.getCards()) {
            cards = card;
            cards.setHeight(90);
            cards.setWidth(65);
            Rectangle finalCards = cards;

            cards.setOnMouseClicked(me -> {
                cardsOnClick();
                selectedCard.setFill(finalCards.getFill());
                selected = card;
                selectedCardDescription.setText(selected.getCardProperties());
                errorTxt.setText("");
                price.setText(String.valueOf(selected.getPrice()));
            });
            cards.setOnMouseEntered(me -> glowCardEffect(finalCards));
            cards.setOnMouseExited(me -> undoGlowEffect(finalCards));
            card.setSizes(false);
            cards.setFill(card.getRectangle().getFill());
            shopCards.add(cards, row, column);
            row++;
            if (row == 10) {
                row = 0;
                column++;
            }

        }
    }

    public void cardsOnClick() {
        buyBtn.setVisible(true);
        showCardListBtn.setVisible(true);
        buyBtn.setOnMouseClicked(this::buyBtnOnClick);
        showCardListBtn.setOnMouseClicked(this::showCardListBtnOnClick);
    }
    private void undoGlowEffect(Rectangle finalCards) {
        finalCards.setCursor(Cursor.DEFAULT);
        int depth = 0;

        DropShadow borderGlow = new DropShadow();
        borderGlow.setOffsetY(0f);
        borderGlow.setOffsetX(0f);
        borderGlow.setColor(Color.RED);
        borderGlow.setWidth(depth);
        borderGlow.setHeight(depth);

        finalCards.setEffect(borderGlow);
    }

    private void glowCardEffect(Rectangle finalCards) {
        finalCards.setCursor(Cursor.HAND);
        int depth = 100;

        DropShadow borderGlow = new DropShadow();
        borderGlow.setOffsetY(0f);
        borderGlow.setOffsetX(0f);
        borderGlow.setColor(Color.GOLD);
        borderGlow.setWidth(depth);
        borderGlow.setHeight(depth);

        finalCards.setEffect(borderGlow);
    }

    public void buyBtnOnClick(MouseEvent mouseEvent) {
           String message = Shop.buy(selected.getName());
           if(message.equals("shopping is successfully!")){
               urMoney.setText(String.valueOf(user.getMoney()));
               success.setText("Card was shopped successfully!");
           }
           else error.setText(message);
    }
    public void showCardListBtnOnClick(MouseEvent mouseEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(DeckMenu.class.getResource("/fxml/cardlist.fxml"));
            loader.setController(controllerShopMenu);
            Parent root = loader.load();
            ((AnchorPane) root).setBackground(GraphicUtils.getBackground("/png/shop/cardList.jpg"));
            LoginMenu.getMainStage().setScene(new Scene(root));
            //ShopMenu.setControllerShop(loader.getController());
            controllerShopMenu.loadUserCards();
        } catch (IOException ignored) {
        }
    }
    public void enterCardListMenu(){
        try {
        FXMLLoader loader = new FXMLLoader(DeckMenu.class.getResource("/fxml/cardlist.fxml"));
        //loader.setController(controllerShopMenu);
        Parent root = loader.load();
//        ((AnchorPane) root).setBackground(GraphicUtils.getBackground("/png/texture/cardList.jpg"));
        LoginMenu.getMainStage().setScene(new Scene(root));
       // ShopMenu.setControllerShop(loader.getController());
        //controllerShopMenu.loadUserCards();
    } catch (IOException ignored) {
    }
    }
    public void enterMainMenu() throws IOException {
        graphicview.MainMenu.enterMenu();
    }

    public void enterMenu1(MouseEvent mouseEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(DeckMenu.class.getResource("/fxml/shop.fxml"));
            //loader.setController(controllerShopMenu);
            Parent root = loader.load();
            ((GridPane) root).setBackground(GraphicUtils.getBackground("/png/texture/GUI_T_Detail_ComboBase01.dds14.png"));
            LoginMenu.getMainStage().setScene(new Scene(root));
            ShopMenu.setControllerShop(loader.getController());
            ShopMenu.getControllerShop().loadBoard();
           // controllerShopMenu.loadBoard();
        } catch (IOException ignored) {
        }
    }

    //TODO: بعد همون اسمایی که از آپدیت استاتوی برداشتی رو دقیقا خودشو بزار توی ایف مثل مثالا و شمارندش رو پلاس پلاس کن
    public void countMonsters(){
        for (Card card: user.getCards()) {
            if(card.getName().equals("Battle OX")) battleOXCount++;
            if(card.getName().equals("Axe Raider")) axeRaiderCount++;
            // do ta monster aval update status ro zadam to az edamash boro manam miram spella


        }
    }
}
