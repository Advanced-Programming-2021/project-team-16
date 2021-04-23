package model.card.trap;

import model.Board;
import model.Game;
import model.card.Card;

public class MirrorForce extends Trap {
    public MirrorForce() {
        super("MirrorForce",
                "Trap",
                TrapType.NORMAL,
                "When an opponent's monster declares an attack: Destroy all your opponent's Attack Position monsters.",
                "Unlimited",
                2000
        );
    }

    public String action(Game game, Card card) {// dar tabe action card neveshte she.
        // Card card=game.getRival().getBoard();
        game.removeCardFromZone(card, Board.Zone.MONSTER, game.getSelectedZoneIndex(), game.getRival().getBoard());
        game.putCardInZone(card, Board.Zone.GRAVE, null, game.getRival().getBoard());
        return "done!";
    }
}