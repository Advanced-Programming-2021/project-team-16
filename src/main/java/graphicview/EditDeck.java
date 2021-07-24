package graphicview;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.Deck;
import server.controller.MainMenuServer;
import server.modell.card.Card;
import server.modell.User;

import java.util.Iterator;

public class EditDeck {

    private static EditDeck controllerEditDeck;

    @FXML
    private AnchorPane anchorPane;
    public GridPane mainBoard;
    public GridPane sideBoard;
    public GridPane allCards;
    public Rectangle selectedCard;
    public Label selectedCardDescription;
    public Label selectedCardDescription1;
    public Button removeBtn;
    public Button setActiveBtn;
    public Button deleteBtn;
    public Button addCardBtn;
    public Rectangle avatar;
    public Label mainC;
    public Label sideC;
    public Label owner;
    public Label dName;
    public Button add2Main;
    public Button add2Side;
    public Button backButton;
    public Label errorTxt;
    private Card selected;
    private boolean isAddCardBoard;
    boolean isMain;
    private Card chosenCard;
    String[] deck = DeckMenu.getDeckName();
    User user = MainMenuServer.getCurrentUser();
    Deck selectedDeck = user.getDeckByName(deck[0].trim());

    public static EditDeck getControllerEditDeck() {
        return controllerEditDeck;
    }

    public static void setControllerEditDeck(EditDeck controllerEditDeck) {
        EditDeck.controllerEditDeck = controllerEditDeck;
    }

    public void backOnAction(MouseEvent mouseEvent) {
        DeckMenu.enterMenu();
    }

//    public void backOnAction2(MouseEvent mouseEvent) {
//        try {
//
//            //EditDeck.makeBtnInvisible();
//            FXMLLoader loader = new FXMLLoader(DeckMenu.class.getResource("/fxml/info.fxml"));
//            Parent root = loader.load();
//            ((GridPane) root).setBackground(GraphicUtils.getBackground("/png/texture/GUI_T_Detail_ComboBase01.dds14.png"));
//            LoginMenu.getMainStage().setScene(new Scene(root));
//            EditDeck.setControllerEditDeck(loader.getController());
//            EditDeck.getControllerEditDeck().loadBoard();
//
//        } catch (IOException ioException) {
//            ioException.printStackTrace();
//        }
//    }

//    public void addToMain(MouseEvent mouseEvent) {
//        String message = controller.DeckMenu.addCardToDeck(selected.getName(), deck[0].trim(), true);
//        if (!message.equals("there are already three cards with name " + "\"" + selected.getName() + "\"" + " in deck " + "\"" + deck[0].trim() + "\"")) {
//            addCardBoard();
//        } else errorTxt.setText(message);
//    }

//    public void addToSide(MouseEvent mouseEvent) {
//        String message = controller.DeckMenu.addCardToDeck(selected.getName(), deck[0].trim(), false);
//        if (!message.equals("there are already three cards with name " + "\"" + selected.getName() + "\"" + " in deck " + "\"" + deck[0].trim() + "\"")) {
//            addCardBoard();
//        } else errorTxt.setText(message);
//    }

//    public void loadBoard() {
//        Rectangle
//                cards;
//        int row = 0;
//        int column = 0;
//
//
//        removeAllNodes(mainBoard);
//        removeAllNodes(sideBoard);
//        avatar.setFill(user.getAvatarRec().getFill());
//        mainC.setText(String.valueOf(selectedDeck.getMainDeckCards().size()));
//        sideC.setText(String.valueOf(selectedDeck.getSideDeckCards().size()));
//        owner.setText(user.getUsername());
//        dName.setText(deck[0]);
//        if (user.getActiveDeck() != null && selectedDeck.getName().equals(user.getActiveDeck().getName()))
//            setActiveBtn.setVisible(false);
//        for (String name : selectedDeck.getMainDeckCards()) {
//            Card card = Card.make(name);
//            cards = card;
//            cards.setHeight(85);
//            cards.setWidth(68);
//            Rectangle finalCards = cards;
//
//            cards.setOnMouseClicked(me -> {
//                selectedCard.setFill(finalCards.getFill());
//                selected = card;
//                selectedCardDescription.setText(selected.getCardProperties());
//                removeBtn.setVisible(true);
//                isMain = true;
//            });
//            cards.setOnMouseEntered(me -> glowCardEffect(finalCards));
//            cards.setOnMouseExited(me -> undoGlowEffect(finalCards));
//            card.setSizes(false);
//            cards.setFill(card.getRectangle().getFill());
//            mainBoard.add(cards, row, column);
//            row++;
//            if (row == 10) {
//                row = 0;
//                column++;
//            }
//        }
//        row = 0;
//        column = 0;
//        for (String name : selectedDeck.getSideDeckCards()) {
//            Card card = Card.make(name);
//            cards = new Rectangle();
//            isAddCardBoard = false;
//            doWhatIsNeededForCards(cards, card, selectedCardDescription);
//            assert card != null;
//            card.setSizes(false);
//            cards.setFill(card.getRectangle().getFill());
//            sideBoard.add(cards, row, column);
//            row++;
//            if (row == 10) {
//                row = 0;
//                column++;
//            }
//        }
//    }

    private void doWhatIsNeededForCards(Rectangle cards, Card card, Label selectedCardDescription) {
        cards.setHeight(90);
        cards.setWidth(65);
        cards.setOnMouseClicked(me -> {

            selectedCard.setFill(cards.getFill());
            selected = card;
            selectedCardDescription.setText(selected.getCardProperties());
            removeBtn.setVisible(true);
            isMain = false;
            if (isAddCardBoard) {
                System.out.println(user.getCards().size());
                errorTxt.setText("");
                add2Main.setVisible(false);
                add2Side.setVisible(false);
            }
        });
        cards.setOnMouseEntered(me -> {
            cards.setCursor(Cursor.HAND);
            int depth = 70;  //Setting the uniform variable for the glow width and height

            DropShadow borderGlow = new DropShadow();
            borderGlow.setOffsetY(0f);
            borderGlow.setOffsetX(0f);
            borderGlow.setColor(Color.GOLD);
            borderGlow.setWidth(depth);
            borderGlow.setHeight(depth);

            cards.setEffect(borderGlow);
        });
        cards.setOnMouseExited(me -> undoGlowEffect(cards));
    }

    private void undoGlowEffect(Rectangle finalCards) {
        finalCards.setCursor(Cursor.DEFAULT);
        int depth = 0;  //Setting the uniform variable for the glow width and height

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
        int depth = 100;  //Setting the uniform variable for the glow width and height

        DropShadow borderGlow = new DropShadow();
        borderGlow.setOffsetY(0f);
        borderGlow.setOffsetX(0f);
        borderGlow.setColor(Color.GOLD);
        borderGlow.setWidth(depth);
        borderGlow.setHeight(depth);

        finalCards.setEffect(borderGlow);
    }


//    public void removeBtnOnAction(ActionEvent actionEvent) {
//        String message = controller.DeckMenu.removeCardFromDeck(selected.getName(), deck[0].trim(), isMain);
//        if (message.equals("card removed form deck successfully")) {
//            loadBoard();
//            selectedCard.setFill(Color.BLACK);
//            selectedCardDescription.setText("card removed form deck successfully");
//            removeBtn.setVisible(false);
//        }
//    }

    public void setActiveBtnOnAction(ActionEvent actionEvent) {
        String message = controller.DeckMenu.activate(deck[0].trim());
        if (message.equals("deck activated successfully")) {
            setActiveBtn.setVisible(false);
            DeckMenu.enterMenu();
        }
    }

//    public void AddCardBtnOnAction(ActionEvent actionEvent) throws IOException {
//        FXMLLoader loader = new FXMLLoader(DeckMenu.class.getResource("/fxml/addCard.fxml"));
//        loader.setController(controllerEditDeck);
//        Parent root = loader.load();
//        ((GridPane) root).setBackground(GraphicUtils.getBackground("/png/texture/GUI_T_Detail_ComboBase01.dds14.png"));
//        LoginMenu.getMainStage().setScene(new Scene(root));
//        controllerEditDeck.addCardBoard();
//    }

    public void deleteBtnOnAction(ActionEvent actionEvent) {
        String message = controller.DeckMenu.delete(deck[0].trim());
        if (message.equals("deck deleted successfully")) {
            DeckMenu.enterMenu();
        }
    }

//    public void addCardBoard() {
//        backButton.setOnMouseClicked(this::backOnAction2);
//        add2Main.setOnMouseClicked(this::addToMain);
//        add2Side.setOnMouseClicked(this::addToSide);
//        removeAllNodes(allCards);
//        removeAllNodes(mainBoard);
//        removeAllNodes(sideBoard);
//        Rectangle cards;
//        int row = 0;
//        int column = 0;
//        for (String name : selectedDeck.getMainDeckCards()) {
//            Card card = Card.make(name);
//            cards = card;
//            assert cards != null;
//            cards.setHeight(85);
//            cards.setWidth(68);
//            Rectangle finalCards = cards;
//
//            cards.setOnMouseClicked(me -> {
//                add2Main.setVisible(false);
//                add2Side.setVisible(false);
//                selectedCard.setFill(finalCards.getFill());
//                selected = card;
//                selectedCardDescription1.setText(selected.getCardProperties());
//                errorTxt.setText("");
//                removeBtn.setVisible(true);
//                isMain = true;
//            });
//            cards.setOnMouseEntered(me -> glowCardEffect(finalCards));
//            cards.setOnMouseExited(me -> undoGlowEffect(finalCards));
//            card.setSizes(false);
//            cards.setFill(card.getRectangle().getFill());
//            mainBoard.add(cards, row, column);
//            row++;
//            if (row == 10) {
//                row = 0;
//                column++;
//            }
//        }
//        row = 0;
//        column = 0;
//        for (String name : selectedDeck.getSideDeckCards()) {
//            Card card = Card.make(name);
//            cards = card;
//            assert cards != null;
//            isAddCardBoard = true;
//            doWhatIsNeededForCards(cards, card, selectedCardDescription1);
//            card.setSizes(false);
//            cards.setFill(card.getRectangle().getFill());
//            sideBoard.add(cards, row, column);
//            row++;
//            if (row == 10) {
//                row = 0;
//                column++;
//            }
//        }
//        row = 0;
//        column = 8;
//        for (Card card : user.getCards()) {
//            cards = card;
//            cards.setHeight(90);
//            cards.setWidth(65);
//            Rectangle finalCards = cards;
//
//            cards.setOnMouseClicked(me -> {
//                addCardOnClick();
//                selectedCard.setFill(finalCards.getFill());
//                selected = card;
//                selectedCardDescription1.setText(selected.getCardProperties());
//                errorTxt.setText("");
//                isMain = true;
//            });
//            cards.setOnMouseEntered(me -> glowCardEffect(finalCards));
//            cards.setOnMouseExited(me -> undoGlowEffect(finalCards));
//            card.setSizes(false);
//            cards.setFill(card.getRectangle().getFill());
//            allCards.add(cards, row, column);
//            row++;
//            if (row == 9) {
//                row = 0;
//                column++;
//            }
//
//        }
//    }

    public void removeAllNodes(GridPane gridPane) {
        Iterator<Node> it = gridPane.getChildren().iterator();
        while (it.hasNext()) {
            it.next();
            it.remove();
        }
    }

//    public void addCardOnClick() {
//        add2Side.setVisible(true);
//        add2Main.setVisible(true);
//        add2Main.setOnMouseClicked(this::addToMain);
//        add2Side.setOnMouseClicked(this::addToSide);
//    }
}

