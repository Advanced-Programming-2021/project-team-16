package model.card.monster;

import controller.GameMenu;
import model.Game;
import model.card.Card;


public class CommandKnight extends Monster {
    private boolean doneAction = false;

    public CommandKnight() {
        super("Command Knight", "All Warrior-Type monsters you control gain 400 ATK. If you control another" +
                        " monster, monsters your opponent controls cannot target this card for an attack.", 2100
                , MonsterType.WARRIOR, 4, 1200, 1900);
    }


    public void action() {
        Game game = GameMenu.getCurrentGame();
        for (Card card : Card.getCards()) {
            if (card instanceof Monster) ((Monster) card).ATK += 400;
        }
        doneAction = true;
    }

    public void undoAction() {
        Game game = GameMenu.getCurrentGame();
        for (Card card : Card.getCards()) {
            if (card instanceof Monster) ((Monster) card).ATK -= 400;
        }
        doneAction = false;
    }

    public boolean hasDoneAction() {
        return doneAction;
    }
}
