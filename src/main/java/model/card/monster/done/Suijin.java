package model.card.monster.done;

import model.Board;
import model.Game;
import model.card.monster.Monster;

public class Suijin extends Monster {
    boolean isUsedUp;

    public Suijin() {
        super("Suijin", "During damage calculation in your opponent's turn, if this card is being attacked:" +
                        " You can target the attacking monster; make that target's ATK 0 during damage calculation only" +
                        " (this is a Quick Effect). This effect can only be used once while this card is face-up on the field.",
                0, MonsterType.AQUA, 7, 2500, 2400);
    }

    public void action(Game game) {
        game.getCurrentPlayer().decreaseLP(2500);
        isUsedUp = true;
        game.removeCardFromZone(game.getSelectedCard(), Board.Zone.MONSTER, game.getSelectedZoneIndex(), game.getCurrentPlayer().getBoard());
        game.putCardInZone(game.getSelectedCard(), Board.Zone.GRAVE, null, game.getCurrentPlayer().getBoard());
    }

    public boolean isUsedUp() {
        return isUsedUp;
    }
}
