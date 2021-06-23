package model.card.spell.fieldspells;

import controller.GameMenu;
import model.Board;
import model.Game;
import model.card.Card;
import model.card.monster.Monster;

public class ClosedForest extends FieldSpell {
    int numberOfGraveMonsters = 0;

    public ClosedForest() {
        super("ClosedForest", "All Beast-Type monsters you control gain 100" +
                " ATK for each monster in your Graveyard. Field Spell Cards cannot be activated. Field Spell Cards cannot" +
                " be activated during the turn this card is destroyed.");
    }


    public String action(boolean isUndo) {
        isActivated = !isUndo;
        Game game = GameMenu.getCurrentGame();
        Board board = game.getCurrentPlayer().getBoard();
        if (!isUndo) {
            numberOfGraveMonsters = 0;
            for (Card card : board.getGrave())
                if (card instanceof Monster) numberOfGraveMonsters++;
            for (Card card : game.getRival().getBoard().getGrave())
                if (card instanceof Monster) numberOfGraveMonsters++;
        }
        for (Card card : board.getDeck()) changeATK(card, isUndo);
        for (Card card : board.getGrave()) changeATK(card, isUndo);
        for (Monster monster : board.getMonsterZone()) changeATK(monster, isUndo);
        for (Card card : board.getHand()) changeATK(card, isUndo);
        return this.name + " has made changes to beast monsters";
    }

    private void changeATK(Card card, boolean isUndo) {
        int delta = numberOfGraveMonsters * 100;
        if (isUndo) delta *= -1;
        if (card instanceof Monster && ((Monster) card).getMonsterType() == Monster.MonsterType.BEAST)
            ((Monster) card).increaseATK(delta);
    }

}
