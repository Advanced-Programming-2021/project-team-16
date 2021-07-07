package graphicview;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import model.card.Card;

public class ItemShop {
    @FXML
    private Label priceLable;
    @FXML
    private ImageView img;
    private CardsOfShop card;
    private MyListener myListener;
//    @FXML
//    private void click(MouseEvent mouseEvent) {
//        myListener.onClickListener(card);
//    }
//Card card1;
//    public void setData(CardsOfShop card, MyListener myListener) {
//        this.card = card;
//        this.myListener = myListener;
//        priceLable.setText(ShopMenu.CURRENCY + card.getPrice());
//        Image image = new Image(getClass().getResourceAsStream(card.getImgSrc()));
//        img.setImage(image);
//    }
}
