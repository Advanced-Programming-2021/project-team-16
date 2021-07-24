package server.modell.card.monster;

import controller.GameMenu;
import model.Board;
import server.controller.GameServer;
import server.modell.Game;
import server.modell.card.Card;


public class CommandKnight extends Monster {
    private boolean doneAction = false;

    public CommandKnight() {
        super("CommandKnight", "All Warrior-Type monsters you control gain 400 ATK. If you control another" +
                        " monster, monsters your opponent controls cannot target this card for an attack.", 2100
                , MonsterType.WARRIOR, 4, 1200, 1900);
    }


    public void action(boolean isUndo) {
        Game game = GameServer.getCurrentGame();
        changeATK(game.getCurrentPlayer().getBoard(), isUndo);
        doneAction = true;
    }


    private void changeATK(Board board, boolean isUndo) {
        for (Card card : board.getDeck()) changeATK(card, isUndo);
        for (Card card : board.getGrave()) changeATK(card, isUndo);
        for (Monster monster : board.getMonsterZone()) changeATK(monster, isUndo);
        for (Card card : board.getHand()) changeATK(card, isUndo);
    }

    private void changeATK(Card card, boolean isUndo) {
        int deltaATK = isUndo ? -400 : 400;
        if (card instanceof Monster) ((Monster) card).increaseATK(deltaATK);
    }

    public boolean hasDoneAction() {
        return doneAction;
    }
}
