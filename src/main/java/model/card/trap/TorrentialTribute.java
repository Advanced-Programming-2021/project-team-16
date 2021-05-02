package model.card.trap;

import model.Board;
import model.Game;
import model.card.monster.Monster;

public abstract class TorrentialTribute extends Trap {
    public TorrentialTribute() {
        super("TorrentialTribute", "Trap", TrapType.NORMAL, "When a monster(s) is Summoned: Destroy all monsters on the field.", "Unlimited", 2000);
    }


    public String action(Game game) {

        Monster[] monsterZone = game.getCurrentPlayer().getBoard().getMonsterZone();
        for (int i = 0; i < monsterZone.length; i++) {
            Monster monster = monsterZone[i];
            game.removeCardFromZone(monster, Board.Zone.MONSTER, i, game.getCurrentPlayer().getBoard());
        }
        monsterZone = game.getRival().getBoard().getMonsterZone();
        for (int i = 0; i < monsterZone.length; i++) {
            Monster monster = monsterZone[i];
            game.removeCardFromZone(monster, Board.Zone.MONSTER, i, game.getRival().getBoard());
        }

        return "remove monsters successfully";
    }
}