package server.modell.card.monster;

import server.modell.Board;
import server.controller.GameServer;
import server.modell.Game;

public class Suijin extends Monster {
    boolean isUsedUp;

    public Suijin() {
        super("Suijin", "During damage calculation in your opponent's turn, if this card is being attacked:" +
                        " You can target the attacking monster; make that target's ATK 0 during damage calculation only" +
                        " (this is a Quick Effect). This effect can only be used once while this card is face-up on the field.",
                8700, MonsterType.AQUA, 7, 2500, 2400);
    }

    public String action(Board.CardPosition position) {
        Game game = GameServer.getCurrentGame();
        int lpDamage = position == Board.CardPosition.ATK ? ATK : DEF;
        game.getRival().decreaseLP(lpDamage);
        isUsedUp = true;
        game.removeCardFromZone(game.getSelectedCard(), Board.Zone.MONSTER, game.getSelectedZoneIndex(), game.getRival().getBoard());
        game.putCardInZone(game.getSelectedCard(), Board.Zone.GRAVE, null, game.getRival().getBoard());
        return "You attacked a suijin and Your monster card is destroyed and you received " + lpDamage + " battle damage";
    }

    public boolean isUsedUp() {
        return isUsedUp;
    }
}
