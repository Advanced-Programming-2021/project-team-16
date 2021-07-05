package graphicview;

import controller.MainMenu;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.Deck;
import model.Game;
import model.card.Card;
import model.person.User;

import java.io.IOException;

public class EditDeck {

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
    private Card selected;
    boolean isMain;
    private Card chosenCard;
    String[] deck = DeckMenu.getDeckName();
    User user = MainMenu.getCurrentUser();
    Deck selectedDeck = user.getDeckByName(deck[0].trim());
    public void backOnAction(ActionEvent actionEvent) {
        DeckMenu.enterMenu();
    }

    public void loadBoard(){
        Rectangle cards;
        int row = 0;
        int column = 0;

                   avatar.setFill(user.getAvatarRec().getFill());
                   mainC.setText(String.valueOf(selectedDeck.getMainDeckCards().size()));
                   sideC.setText(String.valueOf(selectedDeck.getSideDeckCards().size()));
                   owner.setText(user.getUsername());
                   dName.setText(deck[0]);
                   if(selectedDeck.getName().equals(user.getActiveDeck().getName()))
                        setActiveBtn.setVisible(false);
                    for (String name: selectedDeck.getMainDeckCards()) {
                       Card card = Card.getCardByName(name);
                       cards = new Rectangle();
                       cards.setHeight(85);
                       cards.setWidth(68);
                        Rectangle finalCards = cards;

                        cards.setOnMouseClicked(new EventHandler<MouseEvent>() {
                            public void handle(MouseEvent me) {

                                selectedCard.setFill(finalCards.getFill());
                                selected = getCardByRectangle(finalCards);
                                selectedCardDescription.setText(selected.getCardProperties());
                                removeBtn.setVisible(true);
                                isMain = true;
                            }
                        });
                        cards.setOnMouseEntered(new EventHandler<MouseEvent>() {
                            public void handle(MouseEvent me) {
                               finalCards.setCursor(Cursor.HAND);
                                int depth = 100;  //Setting the uniform variable for the glow width and height

                                DropShadow borderGlow= new DropShadow();
                                borderGlow.setOffsetY(0f);
                                borderGlow.setOffsetX(0f);
                                borderGlow.setColor(Color.GOLD);
                                borderGlow.setWidth(depth);
                                borderGlow.setHeight(depth);

                                finalCards.setEffect(borderGlow);
                            }
                        });
                        cards.setOnMouseExited(new EventHandler<MouseEvent>() {
                            public void handle(MouseEvent me) {
                                finalCards.setCursor(Cursor.DEFAULT);
                                int depth = 0;  //Setting the uniform variable for the glow width and height

                                DropShadow borderGlow= new DropShadow();
                                borderGlow.setOffsetY(0f);
                                borderGlow.setOffsetX(0f);
                                borderGlow.setColor(Color.RED);
                                borderGlow.setWidth(depth);
                                borderGlow.setHeight(depth);

                                finalCards.setEffect(borderGlow);
                            }
                        });
                       assert card != null;
                       card.setSizes(false);
                       cards.setFill(card.getRectangle().getFill());
                       mainBoard.add(cards,row,column);
                       row++;
                       if(row== 10){
                           row = 0;
                           column++;
                       }
                    }
                row = 0;
                    column = 0;
        for (String name: selectedDeck.getSideDeckCards()) {
            Card card = Card.getCardByName(name);
            cards = new Rectangle();
            cards.setHeight(90);
            cards.setWidth(65);
            Rectangle finalCards = cards;
            cards.setOnMouseClicked(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent me) {

                    selectedCard.setFill(finalCards.getFill());
                    selected = getCardByRectangle(finalCards);
                    selectedCardDescription.setText(selected.getCardProperties());
                    removeBtn.setVisible(true);
                    isMain = false;
                }
            });
            cards.setOnMouseEntered(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent me) {
                    finalCards.setCursor(Cursor.HAND);
                    int depth = 70;  //Setting the uniform variable for the glow width and height

                    DropShadow borderGlow= new DropShadow();
                    borderGlow.setOffsetY(0f);
                    borderGlow.setOffsetX(0f);
                    borderGlow.setColor(Color.GOLD);
                    borderGlow.setWidth(depth);
                    borderGlow.setHeight(depth);

                    finalCards.setEffect(borderGlow);
                }
            });
            cards.setOnMouseExited(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent me) {
                    finalCards.setCursor(Cursor.DEFAULT);
                    int depth = 0;  //Setting the uniform variable for the glow width and height

                    DropShadow borderGlow= new DropShadow();
                    borderGlow.setOffsetY(0f);
                    borderGlow.setOffsetX(0f);
                    borderGlow.setColor(Color.RED);
                    borderGlow.setWidth(depth);
                    borderGlow.setHeight(depth);

                    finalCards.setEffect(borderGlow);
                }
            });
            assert card != null;
            card.setSizes(false);
            cards.setFill(card.getRectangle().getFill());
            sideBoard.add(cards,row,column);
            row++;
            if(row== 10){
                row = 0;
                column++;
            }
        }
    }


    public void removeBtnOnAction(ActionEvent actionEvent) {
        String message = controller.DeckMenu.removeCardFromDeck(selected.getName(),deck[0].trim(),isMain);
        if(message.equals("card removed form deck successfully")){
            loadBoard();
            selectedCard.setFill(Color.BLACK);
            selectedCardDescription.setText("card removed form deck successfully");
            removeBtn.setVisible(false);

        }
    }

    public void setActiveBtnOnAction(ActionEvent actionEvent) {
    }

    public void AddCardBtnOnAction(ActionEvent actionEvent) throws IOException {
        LoginMenu.getMainStage().setScene(new Scene(FXMLLoader.load(LoginMenu.class.getResource("/fxml/addCard.fxml"))));
    }

    public void deleteBtnOnAction(ActionEvent actionEvent) {
    }
    public void addCardBoard() {
        Rectangle cards;
        int row = 0;
        int column = 0;
        for (String name : selectedDeck.getMainDeckCards()) {
            Card card = Card.getCardByName(name);
            cards = new Rectangle();
            cards.setHeight(85);
            cards.setWidth(68);
            Rectangle finalCards = cards;

            cards.setOnMouseClicked(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent me) {

                    selectedCard.setFill(finalCards.getFill());
                    selected = getCardByRectangle(finalCards);
                    selectedCardDescription1.setText(selected.getCardProperties());
                    removeBtn.setVisible(true);
                    isMain = true;
                }
            });
            cards.setOnMouseEntered(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent me) {
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
            });
            cards.setOnMouseExited(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent me) {
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
            });
            assert card != null;
            card.setSizes(false);
            cards.setFill(card.getRectangle().getFill());
            mainBoard.add(cards, row, column);
            row++;
            if (row == 10) {
                row = 0;
                column++;
            }
        }
        row = 0;
        column = 0;
        for (String name : selectedDeck.getSideDeckCards()) {
            Card card = Card.getCardByName(name);
            cards = new Rectangle();
            cards.setHeight(90);
            cards.setWidth(65);
            Rectangle finalCards = cards;
            cards.setOnMouseClicked(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent me) {

                    selectedCard.setFill(finalCards.getFill());
                    selected = getCardByRectangle(finalCards);
                    selectedCardDescription1.setText(selected.getCardProperties());
                    removeBtn.setVisible(true);
                    isMain = false;
                }
            });
            cards.setOnMouseEntered(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent me) {
                    finalCards.setCursor(Cursor.HAND);
                    int depth = 70;  //Setting the uniform variable for the glow width and height

                    DropShadow borderGlow = new DropShadow();
                    borderGlow.setOffsetY(0f);
                    borderGlow.setOffsetX(0f);
                    borderGlow.setColor(Color.GOLD);
                    borderGlow.setWidth(depth);
                    borderGlow.setHeight(depth);

                    finalCards.setEffect(borderGlow);
                }
            });
            cards.setOnMouseExited(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent me) {
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
            });
            assert card != null;
            card.setSizes(false);
            cards.setFill(card.getRectangle().getFill());
            sideBoard.add(cards, row, column);
            row++;
            if (row == 10) {
                row = 0;
                column++;
            }
        }
        row = 0;
        column = 0;
        for (Card card : user.getCards()) {
            cards = new Rectangle();
            cards.setHeight(90);
            cards.setWidth(65);
            Rectangle finalCards = cards;

            cards.setOnMouseClicked(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent me) {

                    selectedCard.setFill(finalCards.getFill());
                    selected = getCardByRectangle(finalCards);
                    selectedCardDescription1.setText(selected.getCardProperties());
                    isMain = true;
                }
            });
            cards.setOnMouseEntered(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent me) {
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
            });
            cards.setOnMouseExited(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent me) {
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
            });
            assert card != null;
            card.setSizes(false);
            cards.setFill(card.getRectangle().getFill());
            allCards.add(cards, row, column);
            row++;
            if (row == 10) {
                row = 0;
                column++;
            }

        }
    }
    public Card getCardByRectangle(Rectangle rectangle){
        Card rCard = null;
        for (Card card:Card.getCards()) {
            if(card.getRectangle().getFill() == rectangle.getFill())
                rCard = card;
        }
        return rCard;
    }
}
