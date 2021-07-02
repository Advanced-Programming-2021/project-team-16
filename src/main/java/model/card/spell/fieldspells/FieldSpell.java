package model.card.spell.fieldspells;

import controller.GameMenu;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.ImagePattern;
import model.Board;
import model.Game;
import model.card.Card;
import model.card.monster.Monster;
import model.card.spell.Spell;

import java.util.HashMap;

public class FieldSpell extends Spell {

    protected boolean isActivated = false;
    protected HashMap<Monster.MonsterType, Integer> deltaATK;
    protected HashMap<Monster.MonsterType, Integer> deltaDEF;

    public FieldSpell(String name, String description, HashMap<Monster.MonsterType, Integer> deltaATK,
                      HashMap<Monster.MonsterType, Integer> deltaDEF) {
        super(name, "Spell", SpellType.FIELD, description, "Unlimited", 4300);
        this.deltaATK = deltaATK;
        this.deltaDEF = deltaDEF;
    }

    public FieldSpell(String name, String description) {
        super(name, "Spell", SpellType.FIELD, description, "Unlimited", 4300);
    }


    public String action(boolean isUndo) {
        Game game = GameMenu.getCurrentGame();
        doActionOnBoard(game.getCurrentPlayer().getBoard(), isUndo);
        doActionOnBoard(game.getRival().getBoard(), isUndo);
        changeBackground(isUndo);
        isActivated = !isUndo;
        return this.name + " has made changes to all monsters";

    }

    private void doActionOnBoard(Board board, boolean isUndo) {
        for (Card card : board.getDeck()) changeATKAndDEF(card, isUndo);
        for (Card card : board.getGrave()) changeATKAndDEF(card, isUndo);
        for (Monster monster : board.getMonsterZone()) changeATKAndDEF(monster, isUndo);
        for (Card card : board.getHand()) changeATKAndDEF(card, isUndo);
    }

    private void changeATKAndDEF(Card card, boolean isUndo) {
        int delta;
        if (card instanceof Monster && deltaATK.containsKey(((Monster) card).getMonsterType())) {
            delta = deltaATK.get(((Monster) card).getMonsterType());
            if (isUndo) delta *= -1;
            ((Monster) card).increaseATK(delta);
        }
        if (card instanceof Monster && deltaDEF.containsKey(((Monster) card).getMonsterType())) {
            delta = deltaDEF.get(((Monster) card).getMonsterType());
            if (isUndo) delta *= -1;
            ((Monster) card).increaseDEF(delta);
        }
    }

    protected void changeBackground(boolean isUndo) {
        Game game = GameMenu.getCurrentGame();
        Background background;
        if (isUndo) background = new Background(new BackgroundFill(
                new ImagePattern(new Image(getClass().getResource("/png/field/fie_normal.bmp").toExternalForm())),
                CornerRadii.EMPTY, Insets.EMPTY));
        else background = new Background(new BackgroundFill(
                new ImagePattern(new Image(getClass().getResource("/png/field/" + name + ".bmp").toExternalForm())),
                CornerRadii.EMPTY, Insets.EMPTY));
        game.getCurrentPlayer().getGameView().board.setBackground(background);
        game.getRival().getGameView().board.setBackground(background);
    }

    public boolean isActivated() {
        return isActivated;
    }
}
