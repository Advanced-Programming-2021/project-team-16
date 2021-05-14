package model.card.spell.fieldspells;

import controller.GameMenu;
import model.Board;
import model.Game;
import model.card.Card;
import model.card.monster.Monster;
import model.card.spell.Spell;

import java.util.HashMap;

public class FieldSpell extends Spell {

    boolean isActivated = false;
    HashMap<Monster.MonsterType, Integer> deltaATK;
    HashMap<Monster.MonsterType, Integer> deltaDEF;

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

    public boolean isActivated() {
        return isActivated;
    }
}
