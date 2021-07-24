package graphicview;

import controller.GameMenu;
import controller.MainMenu;
import javafx.animation.FadeTransition;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Board;
import model.Game;
import server.model.card.Card;
import model.person.AI;
import model.person.Player;
import server.model.User;
import view.Enums;
import view.Show;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class GameView {
    private static Game game;
    private static boolean isMuted;
    private static MediaPlayer backgroundMusic;
    public Label myLP;
    public Label rivalNickname;
    public Rectangle rivalAvatar;
    public Rectangle myAvatar;
    public Label rivalUsername;
    public Label myUsername;
    public Label rivalLP;
    public Label myNickname;
    public GridPane board;
    public HBox rivalHand;
    public HBox rivalSpells;
    public HBox rivalMonsters;
    public HBox myMonsters;
    public HBox mySpells;
    public HBox myHand;
    public Rectangle selectedCard;
    public Label selectedCardDescription;
    public BorderPane myFieldSpell;
    public BorderPane rivalFieldSpell;
    public VBox buttonsOfGameActions;
    public Button summonButton;
    public Button flipButton;
    public Button setButton;
    public Button activeEffectButton;
    public Button setPositionButton;
    public Button attackButton;
    public Rectangle muteRectangle;
    private Player player;
    private Player rival;
    private Stage stage;
    private final Popup popup = new Popup();
    private final VBox popupVBox = new VBox();

    public static void startGame(Integer rounds, User user2) {
        GameMenu.duel(user2, rounds, true);
        game = GameMenu.getCurrentGame();
        isMuted = false;
    }

    public static void showGameView() {
        Player firstPlayer = game.getCurrentPlayer().getUser() == MainMenu.getCurrentUser() ?
                game.getCurrentPlayer() : game.getRival();
        Player secondPlayer = game.getCurrentPlayer() == firstPlayer ? game.getRival() : game.getCurrentPlayer();
        try {
            //first board
            FXMLLoader loader = new FXMLLoader(ProfileMenu.class.getResource("/fxml/gameBoard.fxml"));
            Parent firstBoard = loader.load();
            LoginMenu.getMainStage().setScene(new Scene(firstBoard));
            ((GameView) loader.getController()).showBoard(firstPlayer, LoginMenu.getMainStage());
            //second board
            loader = new FXMLLoader(ProfileMenu.class.getResource("/fxml/gameBoard.fxml"));
            Parent secondBoard = loader.load();
            Stage secondStage = new Stage();
            secondStage.setScene(new Scene(secondBoard));
            if (!(secondPlayer instanceof AI)) secondStage.show();
            ((GameView) loader.getController()).showBoard(secondPlayer, secondStage);
            //starting game
            backgroundMusic = new MediaPlayer(new Media(GameView.class.getResource("/sounds/background.mp3").toExternalForm()));
            backgroundMusic.setOnEndOfMedia(() -> {
                backgroundMusic.seek(Duration.ZERO);
                backgroundMusic.play();
            });
            backgroundMusic.play();
            game.runGraphical();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void hideNode(Node node) {
        FadeTransition ft = new FadeTransition(Duration.millis(1000), node);
        ft.setFromValue(1.0);
        ft.setToValue(0.0);
        ft.play();
    }

    public static void showNode(Node node) {
        FadeTransition ft = new FadeTransition(Duration.millis(1000), node);
        ft.setFromValue(0.0);
        ft.setToValue(1.0);
        ft.play();
    }

    public static void openCheatPopup(Stage stage, Player player) {
        Popup popup = new Popup();
        TextField textField = new TextField();
        textField.setPromptText("cheat code");
        Button button = new Button("cheat");
        button.setOnMouseClicked(e -> {
            processCheatCode(textField.getText(), player);
            popup.hide();
        });
        HBox hBox = new HBox(textField, button);
        hBox.setMinHeight(100);
        hBox.setMinWidth(300);
        popup.getContent().add(hBox);
        popup.setAnchorX(400);
        popup.setAnchorY(390);
        popup.show(stage);
    }

    private static void processCheatCode(String cheatCode, Player player) {
        Matcher matcher;
        if (game != null && !game.isOver()) {
            if ((matcher = Pattern.compile(Enums.Cheat.INCREASE_LP.getRegex()).matcher(cheatCode)).find())
                player.increaseLP(Integer.parseInt(matcher.group(1)));
            else if (cheatCode.equals(Enums.Cheat.WIN_DUEL.getRegex())) player.getRival().getGameView().doLostAction();
            else if (cheatCode.equals(Enums.Cheat.SET_AND_SUMMON_AGAIN.getRegex())) game.setHasSummonedOrSet(false);
            else if ((matcher = Pattern.compile(Enums.Cheat.ADD_CARD.getRegex()).matcher(cheatCode)).find())
                game.addCardToHand(matcher.group(1), player);
        } else if ((matcher = Pattern.compile(Enums.Cheat.INCREASE_MONEY.getRegex()).matcher(cheatCode)).find())
            if (MainMenu.getCurrentUser() != null) {
                MainMenu.getCurrentUser().increaseMoney(Integer.parseInt(matcher.group(1)));
                ShopMenu.getControllerShop().urMoney.setText(String.valueOf(MainMenu.getCurrentUser().getMoney()));
            }
    }

    public static void playSound(String name) {
        if (game != null && !isMuted())
            new MediaPlayer(new Media(GameView.class.getResource("/sounds/" + name).toExternalForm())).play();
    }

    public static boolean isMuted() {
        return isMuted;
    }

    public Stage getStage() {
        return stage;
    }

    private void showBoard(Player player, Stage stage) {
        this.stage = stage;
        this.player = player;
        player.setGameView(this);
        this.rival = game.getCurrentPlayer() == player ? game.getRival() : game.getCurrentPlayer();
        board.setBackground(new Background(new BackgroundFill(
                new ImagePattern(new Image(getClass().getResource("/png/field/fie_normal.bmp").toExternalForm())),
                CornerRadii.EMPTY, Insets.EMPTY)));
        myAvatar.setFill(player.getUser().getAvatarRec().getFill());
        myNickname.setText(player.getUser().getNickname());
        myUsername.setText(player.getUser().getUsername());
        myLP.setText(String.valueOf(player.getLP()));
        rivalAvatar.setFill(rival.getUser().getAvatarRec().getFill());
        rivalNickname.setText(rival.getUser().getNickname());
        rivalUsername.setText(rival.getUser().getUsername());
        rivalLP.setText(String.valueOf(rival.getLP()));
        selectedCardDescription.setWrapText(true);
        selectedCardDescription.setMaxWidth(279);
        popup.setAnchorX(400);
        popup.setAnchorY(390);
        popup.getContent().add(popupVBox);
        popupVBox.setSpacing(5);
        myLP.setTextFill(Color.rgb(0, 170, 0));
        rivalLP.setTextFill(Color.rgb(0, 170, 0));
        stage.getScene().setOnKeyPressed(keyEvent -> {
            if (Game.CHEAT_KEYS.match(keyEvent)) openCheatPopup(stage, player);
        });
        setRivalBoardDragOverDirectAttack(rivalMonsters);
        setRivalBoardDragOverDirectAttack(rivalSpells);
        setRivalBoardDragOverDirectAttack(rivalHand);
        shapeMuteRec();
    }


    public void doLostAction() {
        game.setWinner(rival);
        playSound("end-turn.wav");
        if (game.getRound() == 1) {
            endGame(game.endRound());
        }
        else {
            String result = game.getResultOfOneRound(player);
            if (result.contains("whole match")) endGame(result);
            else {
                showMessage(result, true);
                game.runGraphical();
            }
        }
    }

    private void endGame(String result) {
        game.setOver(true);
        backgroundMusic.stop();
        showMessage(result, true);
        FadeTransition ft = new FadeTransition(Duration.millis(1000), stage.getScene().getRoot());
        ft.setFromValue(1.0);
        ft.setToValue(0.0);
        ft.setDelay(Duration.seconds(1));
        ft.setOnFinished(actionEvent -> {
            Stage fakeStage = LoginMenu.getMainStage() == stage ? rival.getGameView().stage : stage;
            fakeStage.close();
            DuelMenu.enterMenu();
        });
        ft.play();
    }

    public Label getMyLP() {
        return myLP;
    }

    public Label getRivalLP() {
        return rivalLP;
    }

    public void showMessage(String message, boolean isImportant) {
        if (player instanceof AI) if (isImportant) rival.getGameView().showMessage(message, true);
        else return;
        Label label = new Label(message);
        label.setWrapText(true);
        label.setMaxWidth(460);
        if (isImportant) {
            label.setTextFill(Color.BROWN);
            label.setStyle("-fx-font-size: 30");
        } else label.setStyle("-fx-font-size: 20");
        label.setAlignment(Pos.CENTER);
        popupVBox.getChildren().add(label);
        popup.show(stage);
        stage.getScene().getRoot().setOpacity(0.5);
        FadeTransition ft = new FadeTransition(Duration.millis(1000), label);
        ft.setFromValue(1.0);
        ft.setToValue(0.0);
        ft.setDelay(Duration.millis(popupVBox.getChildren().size() * 1000));
        ft.play();
        ft.setOnFinished(actionEvent -> {
            popupVBox.getChildren().remove(((FadeTransition) actionEvent.getSource()).getNode());
            if (popupVBox.getChildren().isEmpty()) {
                popup.hide();
                stage.getScene().getRoot().setOpacity(1);
            }
        });
    }


    public void summon() {
        showMessage(game.summon(), false);
        game.deselect();
    }

    public void set() {
        showMessage(game.set(), false);
        game.deselect();
    }

    public void flipSummon() {
        showMessage(game.flipSummon(), false);
        game.deselect();
    }

    public void activeEffect() {
        showMessage(game.activeEffect(), false);
        game.deselect();
    }

    public void changePosition() {
        Popup popup = new Popup();
        HBox positionChooser = new HBox();
        positionChooser.setBackground(GraphicUtils.getGreyBackground());
        Label label = new Label("choose a position :   ");
        label.setStyle("-fx-font: 15 arial;");
        Button attackPosition = new Button("attack");
        Button defensePosition = new Button("defense");
        attackPosition.setOnMouseClicked(mouseEvent -> {
            popup.hide();
            showMessage(game.setMonsterPosition(true), false);
            game.deselect();
        });
        defensePosition.setOnMouseClicked(mouseEvent -> {
            popup.hide();
            showMessage(game.setMonsterPosition(false), false);
            game.deselect();
        });

        popup.setAnchorX(600);
        popup.setAnchorY(400);
        positionChooser.getChildren().add(label);
        positionChooser.getChildren().add(attackPosition);
        positionChooser.getChildren().add(defensePosition);
        popup.getContent().add(positionChooser);
        positionChooser.setSpacing(5);
        popup.show(stage);
    }

    public void attack() {
        Popup popup = new Popup();
        HBox attackedChooser = new HBox();
        Label label = new Label("choose a monster :   ");
        label.setStyle("-fx-font: 15 arial;");
        attackedChooser.setBackground(new Background(new BackgroundFill(Color.LIGHTGREY,
                CornerRadii.EMPTY,
                Insets.EMPTY)));
        Button[] monsterButtons = new Button[5];
        for (int i = 1; i <= 5; i++) {
            monsterButtons[i - 1] = new Button(String.valueOf(i));
            int finalI = i;
            monsterButtons[i - 1].setOnMouseClicked(mouseEvent -> {
                popup.hide();
                showMessage(game.attack(finalI - 1), false);
                game.deselect();
                if (game.hasBattlePhaseEnded()) {
                    game.setBattlePhaseEnded(false);
                    game.goToNextPhase();
                }
            });
        }
        Button attackDirect = new Button("attack directly");
        attackDirect.setOnMouseClicked(mouseEvent -> {
            popup.hide();
            showMessage(game.attack(-1), false);
            game.deselect();
            if (game.hasBattlePhaseEnded()) {
                game.setBattlePhaseEnded(false);
                game.goToNextPhase();
            }
        });
        popup.setAnchorX(600);
        popup.setAnchorY(400);
        attackedChooser.getChildren().add(label);
        attackedChooser.getChildren().add(monsterButtons[3]);
        attackedChooser.getChildren().add(monsterButtons[1]);
        attackedChooser.getChildren().add(monsterButtons[0]);
        attackedChooser.getChildren().add(monsterButtons[2]);
        attackedChooser.getChildren().add(monsterButtons[4]);
        attackedChooser.getChildren().add(attackDirect);
        popup.getContent().add(attackedChooser);
        attackedChooser.setSpacing(2);
        popup.show(stage);
    }


    public void goToNextPhase() {
        if (game.getRival() == player) showMessage("not your turn", false);
        else game.goToNextPhase();
    }

    public void showGraveyard() {
        HBox bothGraves = new HBox();
        bothGraves.getChildren().addAll(addCardsToGrave(player, "Your Graveyard:"),
                addCardsToGrave(rival, "Rival's Graveyard:"));
        Button closeGY = new Button("close");
        VBox GYsAndButton = new VBox();
        GYsAndButton.setMaxHeight(300);
        GYsAndButton.getChildren().addAll(bothGraves, closeGY);
        Popup popup = new Popup();
        popup.getContent().add(GYsAndButton);
        popup.setAnchorY(stage.getHeight() / 2);
        popup.setAnchorX(stage.getWidth() / 2 - 200);
        popup.show(stage);
        closeGY.setOnMouseClicked(mouseEvent -> popup.hide());
    }


    private ScrollPane addCardsToGrave(Player player, String graveName) {
        VBox graphicGrave = new VBox();
        graphicGrave.setSpacing(5);
        Label label = new Label(graveName);
        label.setStyle("-fx-font-size: 15");
        label.setTextFill(Color.BROWN);
        graphicGrave.getChildren().add(label);
        for (Card card : player.getBoard().getGrave()) {
            if (card != null) {
                Card copiedCard = Card.make(card.getName());
                copiedCard.setSizes(false);
                copiedCard.setShowDescriptionOnMouseClicked(stage);
                graphicGrave.getChildren().add(copiedCard);
            }
        }
        ScrollPane scrollPane = new ScrollPane(graphicGrave);
        scrollPane.setFitToWidth(true);
        return scrollPane;
    }


    public void surrender() {
        if (game.getRival() == player) showMessage("not your turn", false);
        game.surrendered();
        doLostAction();
    }

    public void makeActionsVisible(boolean isMonster, boolean isMainPhase) {
        for (Node child : buttonsOfGameActions.getChildren()) {
            child.setVisible(false);
        }
        if (isMonster) {
            if (isMainPhase) {
                summonButton.setVisible(true);
                setButton.setVisible(true);
                setPositionButton.setVisible(true);
                flipButton.setVisible(true);
            } else attackButton.setVisible(true);
        } else if (isMainPhase) {
            setButton.setVisible(true);
            activeEffectButton.setVisible(true);
        }
    }

    private void setRivalBoardDragOverDirectAttack(Node node) {
        node.setOnDragOver(new EventHandler<>() {
            public void handle(DragEvent event) {
                if (event.getGestureSource() != this && event.getDragboard().hasString())
                    event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                event.consume();
            }
        });

        node.setOnDragDropped((DragEvent event) -> {
            Dragboard db = event.getDragboard();
            if (db.hasString()) {
                Game game = GameMenu.getCurrentGame();
                Matcher matcher = Pattern.compile("(\\d+) # (.*)").matcher(db.getString());
                if (matcher.find()) {
                    boolean hasCurrentPlayerDoneTheAct = matcher.group(2).equals(game.getCurrentPlayer().getUser().getUsername());
                    if (hasCurrentPlayerDoneTheAct) {
                        game.selectCard(Board.Zone.MONSTER, Integer.parseInt(matcher.group(1)), false);
                        Show.showGameMessage(game.attack(-1));
                    } else Show.showGameMessage("not your turn");
                }
                event.setDropCompleted(true);
            } else event.setDropCompleted(false);
            event.consume();
        });
    }

    private void shapeMuteRec() {
        muteRectangle.setHeight(30);
        muteRectangle.setWidth(30);
        muteRectangle.setFill(new ImagePattern(new Image(getClass().getResource("/png/background/unmuted.png").toExternalForm())));
        muteRectangle.setOnMouseClicked(me -> {
            if (isMuted()) {
                muteRectangle.setFill(new ImagePattern(new Image(getClass().getResource("/png/background/unmuted.png").toExternalForm())));
                isMuted = false;
                backgroundMusic.play();
            } else {
                muteRectangle.setFill(new ImagePattern(new Image(getClass().getResource("/png/background/muted.png").toExternalForm())));
                isMuted = true;
                backgroundMusic.pause();
            }
        });
    }


    public void pauseGame() {
        Stage newStage = new Stage();
        BorderPane borderPane = new BorderPane();
        borderPane.setMinHeight(100);
        borderPane.setMinWidth(160);
        Button resume = new Button("resume");
        resume.setOnMouseClicked(e -> newStage.close());
        borderPane.setCenter(resume);
        newStage.setScene(new Scene(borderPane));
        backgroundMusic.pause();
        stage.hide();
        newStage.showAndWait();
        backgroundMusic.play();
        stage.show();
    }
}

