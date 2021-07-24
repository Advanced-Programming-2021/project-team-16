package server.modell.card.spell.fieldspells;

import javafx.scene.layout.Background;
import server.modell.Board;
import server.controller.GameServer;
import server.modell.Game;
import server.modell.card.Card;
import server.modell.card.monster.Monster;
import server.modell.card.spell.Spell;

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
        Game game = GameServer.getCurrentGame();
        doActionOnBoard(game.getCurrentPlayer().getBoard(), isUndo);
        doActionOnBoard(game.getRival().getBoard(), isUndo);
        if(game.isGraphical()) changeBackground(isUndo);
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
        Game game = GameServer.getCurrentGame();
        Background background;
//        if (isUndo) background = GraphicUtils.getBackground("/png/field/fie_normal.bmp");
//        else background = GraphicUtils.getBackground("/png/field/" + name + ".bmp");
//        game.getCurrentPlayer().getGameView().board.setBackground(background);
//        game.getRival().getGameView().board.setBackground(background);
    }

    public boolean isActivated() {
        return isActivated;
    }
}
