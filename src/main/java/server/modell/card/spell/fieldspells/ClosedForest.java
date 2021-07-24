package server.modell.card.spell.fieldspells;

import server.modell.Board;
import server.controller.GameServer;
import server.modell.Game;
import server.modell.card.Card;
import server.modell.card.monster.Monster;

public class ClosedForest extends FieldSpell {
    int numberOfGraveMonsters = 0;

    public ClosedForest() {
        super("ClosedForest", "All Beast-Type monsters you control gain 100" +
                " ATK for each monster in your Graveyard. Field Spell Cards cannot be activated. Field Spell Cards cannot" +
                " be activated during the turn this card is destroyed.");
    }


    public String action(boolean isUndo) {
        isActivated = !isUndo;
        Game game = GameServer.getCurrentGame();
        Board board = game.getCurrentPlayer().getBoard();
        if (!isUndo) {
            numberOfGraveMonsters = 0;
            for (Card card : board.getGrave())
                if (card instanceof Monster) numberOfGraveMonsters++;

        }
        for (Card card : board.getDeck()) changeATK(card, isUndo);
        for (Card card : board.getGrave()) changeATK(card, isUndo);
        for (Monster monster : board.getMonsterZone()) changeATK(monster, isUndo);
        for (Card card : board.getHand()) changeATK(card, isUndo);
        changeBackground(isUndo);
        return this.name + " has made changes to beast monsters";
    }

    private void changeATK(Card card, boolean isUndo) {
        int delta = numberOfGraveMonsters * 100;
        if (isUndo) delta *= -1;
        if (card instanceof Monster && ((Monster) card).getMonsterType() == Monster.MonsterType.BEAST)
            ((Monster) card).increaseATK(delta);
    }

}
